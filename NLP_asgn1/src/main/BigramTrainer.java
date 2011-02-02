package main;


import java.io.IOException;

public class BigramTrainer {

	BigramDictionary dict;
	WordReader wr;
	int totalNumWords;
	
	// constructs a new Bigram Trainer on corpus fileName
	public BigramTrainer(String fileName) {
		this.wr = new WordReader(fileName);
		this.dict = new BigramDictionary();
		this.totalNumWords = 0;
	}
	
	// go through the training file and add all the bigrams
	// to the bigram dictionary
	// This method is somewhat specialized, and will change depending on how I 
	// change the *STOP* signals. What will I do with periods and sentences?
	// Right now it only inserts the **STOP** token at the beginning and end of the
	// document, and preserves the punctuation and stuff for the rest.
	public void buildCounts() throws IOException {
		this.wr.openFile();
		String wd1, wd2;
		
		// read the first word and insert the first bigram
		if (this.wr.hasNextItem()) {
			wd1 = this.wr.readWord();
			this.totalNumWords++;
		} else {
			throw new IOException("there is just nothing in the darn file");
		}
		
		this.dict.insertBigram("**STOP**", wd1);
		
		// read the file and insert all the bigrams
		while (this.wr.hasNextItem()) {
			wd2 = this.wr.readWord();
			this.totalNumWords++;
			this.dict.insertBigram(wd1, wd2);
			wd1 = wd2;
		}
		
		// insert the last bigram
		this.dict.insertBigram(wd1, "**STOP**");
		
		this.wr.closeFile();
	}
	
	public int getUnigramCount(String wd) {
		return this.dict.getWordCount(wd);
	}
	
	public int getBigramCount(String wd1, String wd2) {
		return this.dict.getBigramCount(wd1, wd2);
	}
	
	public int getVocabSize() {
		return this.dict.getSize();
	}
	
	public int getTotalWordCount() {
		return this.totalNumWords;
	}

	
	public void shutter() {
		this.wr.closeFile();
	}
	
	
	
}