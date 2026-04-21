package projectEngineering;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Reader {

	public static String readFile(String fileName) {
		
		String c = "";
		int i = 0;
		String todo = "";
		File file = new File(fileName + ".csv");
		Scanner s = null;
		
		if (!file.exists()) {
			return "The file doesn't exist";
		}
		try {
			s = new Scanner(file);
			while (s.hasNextLine()) {
				i++;
				c = s.nextLine(); 
				todo += i  + ":"  + c + "\n";		
			}
			s.close();
		}catch (IOException err) {
				return "error has been detected : " + err;
				
			}return todo;
	}
	
	
	
	public static void main(String[] args) {
		String result = "";
		Scanner sc = new Scanner(System.in);
		System.out.print("enter your file name : ");
		String fileName = sc.nextLine();
		if (fileName.isEmpty()) {
		System.out.println("empty file name");
		}
		sc.close();
		result = readFile(fileName);
		

	
		System.out.println("Reading file content : ");
		System.out.println(result);
		System.out.println("DONE");
		}



	}


