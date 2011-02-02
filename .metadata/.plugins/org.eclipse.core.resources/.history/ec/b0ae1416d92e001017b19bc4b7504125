

import java.io.IOException;

public class BigramTrainer {

	BigramDictionary dict;
	WordReader wr;
	
	// constructs a new Bigram Trainer on corpus fileName
	public BigramTrainer(String fileName) {
		this.wr = new WordReader(fileName);
		this.dict = new BigramDictionary();
	}
	
	// go through the training file and add all the bigrams
	// to the bigram dictionary
	public void buildCounts() throws IOException {
		this.wr.openFile();
		String wd1, wd2;
		
		// read the first word and insert the first bigram
		if (this.wr.hasNextItem()) {
			wd1 = this.wr.readWord();
		} else {
			throw new IOException("there is just nothing in the darn file");
		}
		
		this.dict.insertBigram("**STOP**", wd1);
		
		// read the file and insert all the bigrams
		while (this.wr.hasNextItem()) {
			wd2 = this.wr.readWord();
			this.dict.insertBigram(wd1, wd2);
			wd1 = wd2;
		}
		
		// insert the last bigram
		this.dict.insertBigram(wd1, "**STOP**");
		
		this.wr.closeFile();
	}
	
	public void shutter() {
		this.wr.closeFile();
	}
	
	
}
