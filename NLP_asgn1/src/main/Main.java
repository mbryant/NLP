package main;
import java.io.IOException;


// basic main method without using the LogLike class to get the log likelihood
// in general: do not use this method for anything meaningful. this is the "scratch" method

public class Main {

	public static void main(String[] args) throws IOException {
		
		// training and test filenames
		String trainingCorpus = "data/trainingEx.txt";
		String testCorpus = "data/testEx.txt";
		
		BigramTrainer bt = new BigramTrainer(trainingCorpus);
		bt.buildCounts();
		
		int i = bt.getTotalDistinctBigrams();
		
		System.out.println(Integer.toString(i) + " total distinct bigrams");
		

		
	}
		
}
