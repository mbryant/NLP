package main;

import java.util.Random;

public class MainBigram {

	public static void main(String[] args) {
		
		String corpusName = "data/english-senate-0.txt";
		String testName = "data/english-senate-2.txt";			

		LogLikeBigram ll = new LogLikeBigram(corpusName, testName);
		
		double mu = 0.7634;
		double lambda = 0.6824;
		
//		Random rng = new Random();
//		double b1 = 0.01;
//		double b2 = 1;
//		double b3 = 1000;
//		double LLb1 = ll.getLogLikelihood(alpha, b1);
//		double LLb2 = ll.getLogLikelihood(alpha, b2);
//		double LLb3 = ll.getLogLikelihood(alpha, b3);
		
		// This strategy produces the optimal beta *for a given alpha*. Will need some sort
		// of expectation maximization algorithm to overcome this problem. 
		
		//double optimalBeta = gs(0,b1,b2,b3,LLb1,LLb2,LLb3,ll,alpha,rng);
		
		// generates the LL the old way
		double logLikelihood = ll.getLogLikelihood(1.54, 180);
		System.out.println(Double.toString(logLikelihood));
		// generates the LL with a type of mixture model
		logLikelihood = ll.getLogLikelihood2(mu, lambda);
		System.out.println(Double.toString(logLikelihood));
		
		
		
		
	}
	
	public static double gs(int count, double a1, double a2, double a3, double LLa1, double LLa2, double LLa3, LogLikeBigram ll, double alpha, Random rng) {
			
			double newBeta = rng.nextDouble() * (a3 - a1) + a1;
			double LLnew = ll.getLogLikelihood(alpha, newBeta);
			// simple stopping condition
			if (count > 200) {return newBeta;}
			
			if (LLnew > LLa2) {
				if (newBeta < a2) {
					return gs(count + 1, a1, newBeta, a2, LLa1, LLnew, LLa2, ll, alpha, rng);
				} else {
					return gs(count + 1, a2, newBeta, a3, LLa2, LLnew, LLa3, ll, alpha, rng);
				}
			} else {
				if (newBeta < a2) {
					return gs(count + 1, newBeta, a2, a3, LLnew, LLa2, LLa3, ll, alpha, rng);
				} else {
					return gs(count + 1, a1, a2, newBeta, LLa1, LLa2, LLnew, ll, alpha, rng);
				}
			}
		}
	
	
	
}
