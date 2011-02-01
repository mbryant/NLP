import java.io.IOException;

public class LogLikeBigram {
	
	BigramTrainer bt;
	WordReader testReader;
	
	public LogLikeBigram(String corpusName, String heldoutName) {
		try {
		this.bt = new BigramTrainer(corpusName);	// initialize the unigram trainer for the corpus
		this.testReader = new WordReader(heldoutName);	// initialize a reader on the heldout data
		this.testReader.openFile();	// open the heldout data file
		this.bt.buildCounts();	// build the word counts from the trainer
		
		} catch (Exception e) {e.printStackTrace();}
	}
	
	// this is the function that needs some doing
	public double getLogLikelihood(double smoothingParam) {
		String wd;
		int wdNum;
		double probWord;
		double logProbWord;
		double logLikelihood = 0;
		
		while (this.testReader.hasNextItem()) {
			wd = this.testReader.readWord();
			//wdNum = this.bt.getCount(wd);
			//probWord = ((double)wdNum + smoothingParam) / (double)(this.wordCount + this.vocabSize * smoothingParam);
			//logProbWord = Math.log(probWord);
			//logLikelihood = logLikelihood + logProbWord;
		}
		// reset the testReader to the beginning of the file
		this.testReader.closeFile();
		try {
			this.testReader.openFile();
		} catch (IOException e) {
			e.printStackTrace();
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