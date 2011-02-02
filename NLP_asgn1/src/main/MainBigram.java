package main;

public class MainBigram {

	public static void main(String[] args) {
		
		String corpusName = "data/trainingEx.txt";
		String testName = "data/testEx.txt";			

		
		LogLikeBigram ll = new LogLikeBigram(corpusName, testName);
		
		double logLike = ll.getLogLikelihood(1.0, 1.0);
		
		System.out.println(Double.toString(logLike));
		
		
		
		
		
	}
	
	
	
}
