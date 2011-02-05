package main;
import java.io.IOException;
import java.util.Scanner;

public class LogLikeUnigram {
	
	UnigramTrainer ut;
	WordReader testReader;
	int vocabSize;
	int wordCount;
	
	// constructor without a test set
	public LogLikeUnigram(String corpusName) {
		try {
			this.ut = new UnigramTrainer(corpusName);
			this.ut.buildCounts();
			this.vocabSize = this.ut.getVocabSize();
			this.wordCount = this.ut.getWordCount();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	// constructor with a test set
	public LogLikeUnigram(String corpusName, String heldoutName) {
		try {
		this.ut = new UnigramTrainer(corpusName);	// initialize the unigram trainer for the corpus
		this.ut.buildCounts();	// build the word counts from the trainer
		this.testReader = new WordReader(heldoutName);	// initialize a reader on the heldout data
		this.testReader.openFile();	// open the heldout data file
		this.vocabSize = this.ut.getVocabSize() + 1; // to account for the UNK word
		this.wordCount = this.ut.getWordCount();	// total words in training data
		} catch (Exception e) {e.printStackTrace();}
	}
	
	// return the log likelihood of a string
	public double getLogLikelihood(double smoothingParam, String s) {
		String wd = null;
		int wdNum;
		double probWord;
		double logProbWord;
		double logLikelihood = 0;
		Scanner lineR = new Scanner(s);
		
		while (lineR.hasNext()) {
			wd = lineR.next();
			wdNum = this.ut.getCount(wd);
			probWord = ((double)wdNum + smoothingParam) / (double)(this.wordCount + this.vocabSize * smoothingParam);
			logProbWord = Math.log(probWord);
			logLikelihood = logLikelihood + logProbWord;
		}
		
		return logLikelihood;
		
	}
	
	// return the log likelihood of an entire file
	public double getLogLikelihood(double smoothingParam) {
		String wd;
		int wdNum;
		double probWord;
		double logProbWord;
		double logLikelihood = 0;
		
		while (this.testReader.hasNextItem()) {
			wd = this.testReader.readWord();
			wdNum = this.ut.getCount(wd);
			probWord = ((double)wdNum + smoothingParam) / (double)(this.wordCount + this.vocabSize * smoothingParam);
			logProbWord = Math.log(probWord);
			logLikelihood = logLikelihood + logProbWord;
		}
		// reset the testReader to the beginning of the file
		this.testReader.closeFile();
		try {
			this.testReader.openFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.resetFile();
		
		return logLikelihood;
	}
	
	public void shutter() {
		this.ut.closeFile();	// redundant
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
