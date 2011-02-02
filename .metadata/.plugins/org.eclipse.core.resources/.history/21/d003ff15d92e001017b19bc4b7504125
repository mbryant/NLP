import java.io.*;
import java.util.Scanner;

public class WordReader {

	String fileName;
	FileReader fr;
	Scanner in;
	boolean ignoreCase = true;

	// constructor
	public WordReader(String fileName) {
		this.fileName = fileName;
	}
	
	public void openFile() throws IOException {
		this.fr = new FileReader(fileName);
		this.in = new Scanner(this.fr);
	}
	
	public void closeFile() {
		this.in.close();
	}
	
	public boolean hasNextItem() {
		return this.in.hasNext();
	}
	
	public String readWord() {
		if (!this.ignoreCase) {
			return this.in.next();
		} else {
			return this.in.next().toUpperCase();
		}
		
	}
}

