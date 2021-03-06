package misc;



import java.util.HashMap;
import java.util.Iterator;

import main.Pair;

// Alternate implementation with Object instead of String as the
// dictionary keys, so that a STOP object can be included for the
// stop characters. I don't know if I'll have to end up using this
// or not. In any case, it's definitely not done. Don't attempt to
// run it before you fix it up. You'll have to do some type casting
// at the very least.

public class BigramDictionary2 {

	// declare the dictionary
	HashMap<Object, Pair<Integer, HashMap<Object, Integer>>> hm;
	
	// default constructor
	public BigramDictionary2() {
		this.hm = new HashMap<Object, Pair<Integer, HashMap<Object, Integer>>>();
	}
	
	
	// Instance Methods
	
	// total count of unigram wd in the text (context-free)
	public int getWordCount(String wd) {
		return this.hm.get(wd).first;
	}
	
	// total count of bigram wd1 wd2
	public int getBigramCount(String wd1, String wd2) {
		if (!this.hm.containsKey(wd1)) {
			return 0;
		} else {
			HashMap<Object, Integer> existingBigrams = this.hm.get(wd1).second;
			if (!existingBigrams.containsKey(wd2)) {
				return 0;
			} else {
				return existingBigrams.get(wd2);
			}
		}
	}
	
	// print all the bigrams in the dictionary
	public void printBigrams() {
		
		for (Iterator<Object> it = this.hm.keySet().iterator(); it.hasNext();) {
			String key = it.next().toString();
			for (Iterator<Object> it2 = this.hm.get(key).second.keySet().iterator(); it2.hasNext();) {
				System.out.println(key + " " + it2.next());
			}
		}	
	}
	
	// total number of words in the vocabulary
	public int getSize() {
		return this.hm.keySet().size();
	}
	
	// insert a new bigram
	public void insertBigram(String wd1, String wd2) {
		// if the dictionary doesn't even have the first word in the bigram
		// then add it and the bigram as the first
		if (!this.hm.containsKey(wd1)) {
			HashMap<Object, Integer> innerHM = new HashMap<Object, Integer>(); 
			innerHM.put(wd2, 1); // contains the bigram and count of that bigram
			Pair<Integer, HashMap<Object, Integer>> innerPair; 
			// the pair contains the total document count of the first word
			// and the inner HashMap of bigrams
			innerPair = new Pair<Integer, HashMap<Object, Integer>>(1,innerHM); 
			this.hm.put(wd1,innerPair);
		}
		// otherwise, add the bigram to the word's existing entry
		else {
			// grab the existing bigram array and wd1's count
			HashMap<Object, Integer> existingBigrams = 
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
			Pair<Integer, HashMap<Object, Integer>> newPair = 
				new Pair<Integer, HashMap<Object, Integer>>(wordCount + 1, existingBigrams);
			this.hm.put(wd1, newPair);

		}
		
		
	}
	
}
