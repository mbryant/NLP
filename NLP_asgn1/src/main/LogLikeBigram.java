package main;
import java.io.IOException;
import java.util.Scanner;

public class LogLikeBigram {
	
	BigramTrainer bt;
	WordReader testReader;
	
	int vocabSize;
	int wordCount;
	
	/* - initializes a bigramTrainer on the training corpus
	 * - builds the bigramDictionary of all the bigram and unigram counts
	 * - (closes and training corpus file)
	 * - opens a wordReader on the heldout data
	 * - opens the heldout data file 
	 */
	public LogLikeBigram(String corpusName, String heldoutName) {
		try {
		
		this.bt = new BigramTrainer(corpusName);	// initialize the unigram trainer for the corpus
		this.bt.buildCounts();	// build the word counts from the trainer
		this.testReader = new WordReader(heldoutName);	// initialize a reader on the heldout data
		this.testReader.openFile();	// open the heldout data file
		
		this.vocabSize = this.bt.getVocabSize();
		this.wordCount = this.bt.getTotalWordCount();
		
		} catch (Exception e) {e.printStackTrace();}
	}
	
	// constructor without a test data set
	public LogLikeBigram(String corpusName) {
		try {
			this.bt = new BigramTrainer(corpusName);
			this.bt.buildCounts();
			this.vocabSize = this.bt.getVocabSize();
			this.wordCount = this.bt.getTotalWordCount();
		} catch (Exception e) {e.printStackTrace();}
		
	}
	
	// return log likelihood of the open file
	public double getLogLikelihood(double alpha, double beta) {
		
		String wd1 = null;
		String wd2 = null;
		double logLikelihood = 0;
		double probBigram;
		double probUnigram2;
		
		if (this.testReader.hasNextItem()) {
			wd1 = this.testReader.readWord();
		} else {System.out.println("please no!");}
		
		probUnigram2 = ((this.bt.getUnigramCount(wd1) + alpha) / (this.wordCount + alpha * this.vocabSize));
		probBigram = (this.bt.getBigramCount("**STOP**", wd1) + beta * probUnigram2) /
					 (1 + beta);
		logLikelihood = logLikelihood + Math.log(probBigram);
		
		while (this.testReader.hasNextItem()) {
			wd2 = this.testReader.readWord();
			probUnigram2 = ((this.bt.getUnigramCount(wd2) + alpha) / (this.wordCount + alpha * this.vocabSize));
			probBigram = (this.bt.getBigramCount(wd1, wd2) + beta * probUnigram2) / 
						 (this.bt.getUnigramCount(wd1) + beta);
			logLikelihood = logLikelihood + Math.log(probBigram);
			wd1 = wd2;			
		}
		
		probUnigram2 = ((0 + alpha) / (this.wordCount + alpha * this.vocabSize));
		probBigram = (this.bt.getBigramCount(wd1, "**STOP**") + beta * probUnigram2) /
					 (this.bt.getUnigramCount(wd1) + beta);
		logLikelihood = logLikelihood + Math.log(probBigram);
		
		this.resetFile();
		
		return logLikelihood;
	}
	
	// the sexy new order, using the mixture-type model with optimized
	// mu, lambda from MainEM
	public double getLogLikelihood2(double mu, double lambda) {
		
		String wd1 = null;
		String wd2 = null;
		double logLikelihood = 0;
		double probBigram;
		double w1Count;
		double w2Count;
		double bgCount;
		
		if (this.testReader.hasNextItem()) {
			wd2 = this.testReader.readWord();
		} else {System.out.println("please no!");}
		
		wd1 = "**STOP**";
		
		w1Count = this.bt.getUnigramCount(wd1);
		w2Count = this.bt.getUnigramCount(wd2);
		bgCount = this.bt.getBigramCount(wd1, wd2);
		if (w1Count != 0) {
			probBigram = ((lambda * bgCount / w1Count) + 
						  ((1 - lambda)*(mu)* w2Count / this.wordCount) + 
						  ((1 - lambda)*(1 - mu) / this.vocabSize));
		} else {
			probBigram = (((1 - lambda)*(mu)* w2Count / this.wordCount) +
						  ((1 - lambda)*(1 - mu) / this.vocabSize));
		}		
		logLikelihood = logLikelihood + Math.log(probBigram);
		
		wd1 = wd2;
		
		while (this.testReader.hasNextItem()) {
			wd2 = this.testReader.readWord();
			w1Count = this.bt.getUnigramCount(wd1);
			w2Count = this.bt.getUnigramCount(wd2);
			bgCount = this.bt.getBigramCount(wd1, wd2);
			if (w1Count != 0) {
				probBigram = ((lambda * bgCount / w1Count) + 
							  ((1 - lambda)*(mu)* w2Count / this.wordCount) + 
							  ((1 - lambda)*(1 - mu) / this.vocabSize));
			} else {
				probBigram = (((1 - lambda)*(mu)* w2Count / this.wordCount) +
							  ((1 - lambda)*(1 - mu) / this.vocabSize));
			}		
			logLikelihood = logLikelihood + Math.log(probBigram);
			wd1 = wd2;			
		}
		
		wd2 = "**STOP**";
		
		w1Count = this.bt.getUnigramCount(wd1);
		w2Count = this.bt.getUnigramCount(wd2);
		bgCount = this.bt.getBigramCount(wd1, wd2);
		if (w1Count != 0) {
			probBigram = ((lambda * bgCount / w1Count) + 
						  ((1 - lambda)*(mu)* w2Count / this.wordCount) + 
						  ((1 - lambda)*(1 - mu) / this.vocabSize));
		} else {
			probBigram = (((1 - lambda)*(mu)* w2Count / this.wordCount) +
						  ((1 - lambda)*(1 - mu) / this.vocabSize));
		}		
		logLikelihood = logLikelihood + Math.log(probBigram);
		
		this.resetFile();
		
		return logLikelihood;
	}
	
	// get the log likelihood of a string using the mixture way
	public double getLogLikelihood2(double mu, double lambda, String s) {
		String wd1 = null;
		String wd2 = null;
		double logLikelihood = 0;
		double probBigram;
		double w1Count;
		double w2Count;
		double bgCount;
		Scanner lineR = new Scanner(s);
		
		if (lineR.hasNext()) {
			wd1 = lineR.next();
		}
		while (lineR.hasNext()) {
			wd2 = lineR.next();
			w1Count = this.bt.getUnigramCount(wd1);
			w2Count = this.bt.getUnigramCount(wd2);
			bgCount = this.bt.getBigramCount(wd1, wd2);
			if (w1Count != 0) {
				probBigram = ((lambda * bgCount / w1Count) + 
							  ((1 - lambda)*(mu)* w2Count / this.wordCount) + 
							  ((1 - lambda)*(1 - mu) / this.vocabSize));
			} else {
				probBigram = (((1 - lambda)*(mu)* w2Count / this.wordCount) +
							  ((1 - lambda)*(1 - mu) / this.vocabSize));
			}		
			logLikelihood = logLikelihood + Math.log(probBigram);
			wd1 = wd2;			
		}
		return logLikelihood;
	}
	
	// log likelihood of a string using the old way
	public double getLogLikelihood(double alpha, double beta, String s) {
		String wd1 = null;
		String wd2 = null;
		double logLikelihood = 0;
		double probBigram;
		double probUnigram2;
		Scanner lineR = new Scanner(s);
		
		if (lineR.hasNext()) {
			wd1 = lineR.next();
		}
		while (lineR.hasNext()) {
			wd2 = lineR.next();
			probUnigram2 = ((this.bt.getUnigramCount(wd2) + alpha) / (this.wordCount + alpha * this.vocabSize));
			probBigram = (this.bt.getBigramCount(wd1, wd2) + beta * probUnigram2) / 
						 (this.bt.getUnigramCount(wd1) + beta);
			logLikelihood = logLikelihood + Math.log(probBigram);
			wd1 = wd2;			
		}
		return logLikelihood;
	}
	
	public void shutter() {
		this.bt.shutter();
		this.testReader.closeFile();
	}
	
	public void resetFile() {
		this.testReader.closeFile();
		try {
			this.testReader.openFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
