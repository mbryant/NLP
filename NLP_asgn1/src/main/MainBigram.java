package main;

import java.util.Random;

public class MainBigram {

	public static void main(String[] args) {
		
		String corpusName = "data/english-senate-0.txt";
//		String heldoutName = "data/english-senate-1.txt";
		String testName = "data/english-senate-2.txt";			

//		LogLikeBigram ll = new LogLikeBigram(corpusName, heldoutName);
		
		double mu1 = 0.7634;
		double lambda1 = 0.6824;
		
		double mu2 = 0.8279;
		double lambda2 = 0.6959;
		
//		double beta = 1;
//		Random rng = new Random();
//		double a1 = 0.01;
//		double a2 = 1;
//		double a3 = 1000;
//		double LLa1 = ll.getLogLikelihood(a1, beta);
//		double LLa2 = ll.getLogLikelihood(a2, beta);
//		double LLa3 = ll.getLogLikelihood(a3, beta);
//		
//		// This strategy produces the optimal alpha *for a given beta*. Will need some sort
//		// of expectation maximization algorithm to overcome this problem. 
//		
//		double optimalAlpha = gs(0,a1,a2,a3,LLa1,LLa2,LLa3,ll,beta,rng);
//		
//		System.out.println("Optimal alpha given beta=1 is " + Double.toString(optimalAlpha));
//		
//		ll.shutter();
//		
		LogLikeBigram llTest = new LogLikeBigram(corpusName, testName);
		
		// generates the LL the old way
//		double logLikelihood = llTest.getLogLikelihood(optimalAlpha, beta);
//		System.out.println(Double.toString(logLikelihood));
		// generates the LL with a type of mixture model
		double logLikelihood = llTest.getLogLikelihoodKN(mu2, lambda2);
		System.out.println(Double.toString(logLikelihood));
		logLikelihood = llTest.getLogLikelihood2(mu1,lambda1);
		System.out.println(Double.toString(logLikelihood));
		
		
		
		llTest.shutter();
		
		
	}
	
	public static double gs(int count, double a1, double a2, double a3, double LLa1, double LLa2, double LLa3, LogLikeBigram ll, double beta, Random rng) {
			
			double newAlpha = rng.nextDouble() * (a3 - a1) + a1;
			double LLnew = ll.getLogLikelihood(newAlpha, beta);
			// simple stopping condition
			if (count > 200) {return newAlpha;}
			
			if (LLnew > LLa2) {
				if (newAlpha < a2) {
					return gs(count + 1, a1, newAlpha, a2, LLa1, LLnew, LLa2, ll, beta, rng);
				} else {
					return gs(count + 1, a2, newAlpha, a3, LLa2, LLnew, LLa3, ll, beta, rng);
				}
			} else {
				if (newAlpha < a2) {
					return gs(count + 1, newAlpha, a2, a3, LLnew, LLa2, LLa3, ll, beta, rng);
				} else {
					return gs(count + 1, a1, a2, newAlpha, LLa1, LLa2, LLnew, ll, beta, rng);
				}
			}
		}
	
	
	
}
