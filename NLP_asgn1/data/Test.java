import java.util.Scanner;
import java.io.*;

public class Test {

	public static void main(String[] args) throws IOException {

		FileReader fr = new FileReader("testEx.txt");
		Scanner in = new Scanner(fr);
		String wd;

		while (in.hasNext()) {
			wd = in.next().toUpperCase();
			System.out.println(wd);
		}


	}
}
