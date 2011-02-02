package main;
import java.io.IOException;
import java.util.Random;

public class MainUnigram {

	public static void main(String[] args) throws IOException {
		
		String corpusName = "/home/mbryant/workspace/NLP_asgn1/data/english-senate-0.txt";
		String testName = "/home/mbryant/workspace/NLP_asgn1/data/testEx.txt";			
		
		double smoothingParam = 1.0;
		System.out.println("The smoothing parameter in use is " + Double.toString(smoothingParam));
		
		
		LogLikeUnigram llTest = new LogLikeUnigram(corpusName, testName);
		
		System.out.println("Case is being ignored: " + Boolean.toString(llTest.testReader.ignoreCase));
		
		double logLTest = llTest.getLogLikelihood(smoothingParam);
		System.out.println("Log likelihood of the test data is " + Double.toString(logLTest));
		
		llTest.shutter();
		
	}
	
	// Golden Section search method to return the optimal smoothing parameter value
	public static double gs(int count, double a1, double a2, double a3, double LLa1, double LLa2, double LLa3, LogLikeUnigram ll, Random rng) {
		
		double newAlpha = rng.nextDouble() * (a3 - a1) + a1;
		double LLnew = ll.getLogLikelihood(newAlpha);
		ll.resetFile();
		// simple stopping condition
		if (count > 200) {return newAlpha;}
		
		if (LLnew > LLa2) {
			if (newAlpha < a2) {
				return gs(count + 1, a1, newAlpha, a2, LLa1, LLnew, LLa2, ll, rng);
			} else {
				return gs(count + 1, a2, newAlpha, a3, LLa2, LLnew, LLa3, ll, rng);
			}
		} else {
			if (newAlpha < a2) {
				return gs(count + 1, newAlpha, a2, a3, LLnew, LLa2, LLa3, ll, rng);
			} else {
				return gs(count + 1, a1, a2, newAlpha, LLa1, LLa2, LLnew, ll, rng);
			}
		}
	}
		
}
