package main;


import java.io.IOException;
import java.util.Iterator;
import java.util.HashMap;

public class BigramTrainer {

	BigramDictionary dict;
	HashMap<String,Integer> precedingWords;
	WordReader wr;
	int totalNumWords;
	
	// constructs a new Bigram Trainer on corpus fileName
	public BigramTrainer(String fileName) {
		this.wr = new WordReader(fileName);
		this.dict = new BigramDictionary();
		this.precedingWords = new HashMap<String,Integer>();
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
		this.incrementPrecedingWordCount(wd1);
		
		// read the file and insert all the bigrams
		while (this.wr.hasNextItem()) {
			wd2 = this.wr.readWord();
			this.totalNumWords++;
			this.dict.insertBigram(wd1, wd2);
			if (this.dict.getBigramCount(wd1, wd2) == 1) {
				this.incrementPrecedingWordCount(wd2);
			}
			wd1 = wd2;
		}
		
		// insert the last bigram
		this.dict.insertBigram(wd1, "**STOP**");
		this.incrementPrecedingWordCount("**STOP**");
		
		this.wr.closeFile();
	}
	
	public void incrementPrecedingWordCount(String wd1) {
		try {
			int prevCount = this.precedingWords.get(wd1);
			this.precedingWords.put(wd1, prevCount + 1);	
		} catch (NullPointerException e) {
			this.precedingWords.put(wd1, 1);
		}
		
	}
	
	
	// GET methods //
	
	public int getUnigramCount(String wd) {
		return this.dict.getWordCount(wd);
	}
	
	public int getBigramCount(String wd1, String wd2) {
		return this.dict.getBigramCount(wd1, wd2);
	}
	
	public int getDistinctBigrams(String wd) {
		try {
			return this.dict.hm.get(wd).second.keySet().size();
		} catch (NullPointerException e) {
			return 0;
		}
	}
	
	public int getTotalDistinctBigrams() {
		int count = 0;
		for (Iterator<String> it = this.dict.hm.keySet().iterator(); it.hasNext();) {
			count = count + getDistinctBigrams(it.next());
		}
		return count;
	}
	
	public int getVocabSize() {
		return this.dict.getSize();
	}
	
	public int getTotalWordCount() {
		return this.totalNumWords;
	}
	
	public int getPrecedingWords(String wd) {
		try {
			return this.precedingWords.get(wd);
		} catch (NullPointerException e) {
			return 0;
		}
	}

	
	public void shutter() {
		this.wr.closeFile();
	}
	
	
	
}
