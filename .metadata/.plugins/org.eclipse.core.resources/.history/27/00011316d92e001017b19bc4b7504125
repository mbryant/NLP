import java.io.IOException;


// basic main method without using the LogLike class to get the log likelihood
// in general: do not use this method for anything meaningful. this is the "scratch" method

public class Main {

	public static void main(String[] args) throws IOException {
		
		String fileName = "data/testEx.txt";
		
		BigramTrainer bt = new BigramTrainer(fileName);
		bt.buildCounts();
		
		//String ct = Integer.toString(bt.dict.getWordCount("and"));
		
		bt.dict.printBigramsAndCounts();
		
		System.out.println();
		//System.out.println("'and' appears : " + ct + " times");
		
	}
		
}
