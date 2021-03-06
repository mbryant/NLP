package main;
import java.io.IOException;
import java.util.Random;

public class MainOptimalUnigram {

	public static void main(String[] args) throws IOException {
		
		String corpusName = "data/english-senate-0.txt";
		String heldoutName = "data/english-senate-1.txt";
		String testName = "data/english-senate-2.txt";
		
			// to find the optimal smoothing parameter
			Random rng = new Random();
			LogLikeUnigram ll =  new LogLikeUnigram(corpusName, heldoutName);
			// three initial smoothing param values for golden section search and their log likelihoods
			// this is somewhat ad hoc for this situation, wouldn't necessarily be optimal for other corpora
			double a1 = 0.001;
			double a2 = 5.0;
			double a3 = 5000.0;
			double LLa1 = ll.getLogLikelihood(a1);
			double LLa2 = ll.getLogLikelihood(a2);
			double LLa3 = ll.getLogLikelihood(a3);
			// perform the recursive search
			double optimalSmooth = gs(0,a1, a2, a3, LLa1, LLa2, LLa3, ll, rng);
			System.out.println("Optimal smoothing parameter is " + Double.toString(optimalSmooth));
			// close it all up
			ll.shutter();
		
		LogLikeUnigram llTest = new LogLikeUnigram(corpusName, testName);
		double logLTest = llTest.getLogLikelihood(optimalSmooth);
		System.out.println("Log likelihood of the test data is " + Double.toString(logLTest));
		
		llTest.shutter();
		
	}
	
	// Golden Section search method to return the optimal smoothing parameter value
	public static double gs(int count, double a1, double a2, double a3, double LLa1, double LLa2, double LLa3, LogLikeUnigram ll, Random rng) {
		
		double newAlpha = rng.nextDouble() * (a3 - a1) + a1;
		double LLnew = ll.getLogLikelihood(newAlpha);
		// simple stopping condition
		if (count > 500) {return newAlpha;}
		
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
