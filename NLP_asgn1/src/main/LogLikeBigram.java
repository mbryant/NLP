package main;
import java.io.IOException;

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
	
	// this is the function that needs some doing
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