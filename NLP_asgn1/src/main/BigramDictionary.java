package main;
import java.util.HashMap;
import java.util.Iterator;

// Support class for bigram training. 
// Meant to store bigram and unigram counts for
// a training set.

public class BigramDictionary {

	// declare the dictionary
	HashMap<String, Pair<Integer, HashMap<String, Integer>>> hm;
	
	// default constructor
	public BigramDictionary() {
		this.hm = new HashMap<String, Pair<Integer, HashMap<String, Integer>>>();
	}
	
	
	////// Instance Methods ///////
	
	// GET methods
	
	// total number of words in the vocabulary
	public int getSize() {
		return this.hm.keySet().size();
	}
	
	// total count of unigram wd in the text (context-free)
	public int getWordCount(String wd) {
		try {
		return this.hm.get(wd).first; }
		catch (NullPointerException e) {	
		}
		return 0;
	}
	
	// total count of bigram wd1 wd2
	public int getBigramCount(String wd1, String wd2) {
		if (!this.hm.containsKey(wd1)) {
			return 0;
		} else {
			HashMap<String, Integer> existingBigrams = this.hm.get(wd1).second;
			if (!existingBigrams.containsKey(wd2)) {
				return 0;
			} else {
				return existingBigrams.get(wd2);
			}
		}
	}
	
	// PRINT methods
	
	// print all the bigrams in the dictionary
	public void printBigrams() {
		
		for (Iterator<String> it = this.hm.keySet().iterator(); it.hasNext();) {
			String key = it.next().toString();
			for (Iterator<String> it2 = this.hm.get(key).second.keySet().iterator(); it2.hasNext();) {
				System.out.println(key + " " + it2.next());
			}
		}	
	}
	
	public void printBigramsAndCounts() {
		
		for (Iterator<String> it = this.hm.keySet().iterator(); it.hasNext();) {
			String key = it.next().toString();
			for (Iterator<String> it2 = this.hm.get(key).second.keySet().iterator(); it2.hasNext();) {
				String wd2 = it2.next();
				String count = Integer.toString(this.hm.get(key).second.get(wd2));
				System.out.println(key + " " + wd2 + " : " + count + " appearance(s)");
			}
		}
		
		
	}
	
	// INSERT method
	
	public void insertBigram(String wd1, String wd2) {
		// if the dictionary doesn't even have the first word in the bigram
		// then add it and the bigram as the first
		if (!this.hm.containsKey(wd1)) {
			HashMap<String, Integer> innerHM = new HashMap<String, Integer>(); 
			innerHM.put(wd2, 1); // contains the bigram and count of that bigram
			Pair<Integer, HashMap<String, Integer>> innerPair; 
			// the pair contains the total document count of the first word
			// and the inner HashMap of bigrams
			innerPair = new Pair<Integer, HashMap<String, Integer>>(1,innerHM); 
			this.hm.put(wd1,innerPair);
		}
		// otherwise, add the bigram to the word's existing entry
		else {
			// grab the existing bigram array and wd1's count
			HashMap<String, Integer> existingBigrams = 
				this.hm.get(wd1).second;
			int wordCount = this.hm.get(wd1).first;
			// if the bigram doesn't exist
			if (!existingBigrams.containsKey(wd2)) {
				// put it in
				existingBigrams.put(wd2,1);
			}
			// if it does exist
			else {
				existingBigrams.put(wd2, existingBigrams.get(wd2) + 1);
			}
			Pair<Integer, HashMap<String, Integer>> newPair = 
				new Pair<Integer, HashMap<String, Integer>>(wordCount + 1, existingBigrams);
			this.hm.put(wd1, newPair);

		}
		
		
	}
	
}
