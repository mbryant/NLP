import java.util.HashMap;
import java.io.IOException;

public class UnigramTrainer {

	HashMap<String,Integer> hm;
	WordReader wr;
	int vocabSize;
	int wordCount;
	
	// constructor
	public UnigramTrainer(String fileName) {
		this.hm = new HashMap<String, Integer>();
		this.wr = new WordReader(fileName);
	}
	
	// get the counts of the words from the corpus
	// and store them in the HashMap
	public void buildCounts() throws IOException {
		this.wr.openFile();
		String wd;
		
		while (this.wr.hasNextItem()) {
			wd = this.wr.readWord();
			this.wordCount = this.wordCount + 1;
			if (!this.hm.containsKey(wd)) {
				this.hm.put(wd, 1);
			} else {
				this.hm.put(wd, this.hm.get(wd) + 1);
			}
		}
		
		this.wr.closeFile();
	}
	
	public int getCount(String wd) {
		try {return this.hm.get(wd);}
		catch (NullPointerException e) {return 0;}
	}
	
	public int getVocabSize() {
		return this.hm.size();
	}
	
	public int getWordCount() {
		return this.wordCount;
	}
	
	public void closeFile() {
		this.wr.closeFile();
	}
	
}
	
	

