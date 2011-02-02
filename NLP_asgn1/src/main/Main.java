package main;
import java.io.IOException;


// basic main method without using the LogLike class to get the log likelihood
// in general: do not use this method for anything meaningful. this is the "scratch" method

public class Main {

	public static void main(String[] args) throws IOException {
		
		// training and test filenames
		String trainingCorpus = "data/english-senate-0.txt";
		String testCorpus = "data/english-senate-1.txt";
		
		/* make the bigram trainer, which does these things:
		 * - initializes a wordReader on the arg
		 * - initializes a bigramDictionary
		 * - opens the training file
		 * - goes through the file word by word and inserts every bigram into the bigramDict
		 * - closes the file
		 */		
		BigramTrainer bt = new BigramTrainer(trainingCorpus);
		bt.buildCounts();
		
		int vocabSize = bt.getVocabSize();
		int wordCount = bt.getTotalWordCount();
		
		WordReader wrTest = new WordReader(testCorpus);
		wrTest.openFile();
		
		String wd1 = null;
		String wd2 = null;
		double LogLikelihood = 0;
		double beta = 100;
		double alpha = 1;
		double probBigram;
		double probUnigram2;
		
		// includes temporary fixes for counting the **STOP** character. 
		// still not entirely sure how to do this.
		
		if (wrTest.hasNextItem()) {
			wd1 = wrTest.readWord();
		} else {System.out.println("oh no!");}
		
		probUnigram2 = ((bt.getUnigramCount(wd1) + alpha) / (wordCount + alpha * vocabSize));
		probBigram = (bt.getBigramCount("**STOP**",wd1) + beta * probUnigram2) / 
					 (1 + beta);
		//System.out.println(Double.toString(probBigram));
		LogLikelihood = LogLikelihood + Math.log(probBigram);
		
		while (wrTest.hasNextItem()) {
			wd2 = wrTest.readWord();
			probUnigram2 = ((bt.getUnigramCount(wd2) + alpha) / (wordCount + alpha * vocabSize));
			//System.out.println("Unigram " + Double.toString(probUnigram2));
			probBigram = (bt.getBigramCount(wd1, wd2) + beta * probUnigram2) / 
			 			 (bt.getUnigramCount(wd1) + beta);
			//System.out.println(Double.toString(probBigram));
			LogLikelihood = LogLikelihood + Math.log(probBigram);
			wd1 = wd2;
		}
		
		probUnigram2 = ((0 + alpha) / (wordCount + alpha * vocabSize));
		probBigram = (bt.getBigramCount(wd1,"**STOP**") + beta * probUnigram2) / 
		 			 (bt.getUnigramCount(wd1) + beta);
		//System.out.println(Double.toString(probBigram));
		LogLikelihood = LogLikelihood + Math.log(probBigram);
		

		bt.shutter();
		wrTest.closeFile();
		
		System.out.println("The whole big log likelihood is " + Double.toString(LogLikelihood));
		

		
	}
		
}
