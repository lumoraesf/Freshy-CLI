package projectEngineering;

import java.util.Scanner;
	import java.io.File;
	import java.io.IOException;
	import java.io.FileWriter;
	import java.io.PrintWriter;
	public class export {
		



		public static String reader(File file) {
			String c;
			String todo = "";
			Scanner s = null;
			try {
			s = new Scanner(file);
			while (s.hasNextLine()) {
				c = s.nextLine();
				todo = todo + c+ " ";
			}}
			catch(IOException er) {
				er.printStackTrace();
			}finally {
				s.close();
			}
			return todo;

			}
		public static void writer(FileWriter fw, String todo) {
			String[] a = todo.split("[,\\s.;!]+");
			int length = a.length;
			try {
				PrintWriter writer = new PrintWriter(fw);
				for (int i = 0 ;i <length;i++) {
					writer.print(a[i] + "\n");
				}
				writer.close();
				
			}catch(Exception err) {
				err.printStackTrace();
			}
				
			
			
		}
		
		public static void main(String[] args) {
			boolean booleanChoice;
			Scanner sc = new Scanner(System.in);
			System.out.println("enter your text file's name : ");
			String fileName = sc.nextLine();
			try {
			File file = new File(fileName+ ".txt");
			if (file.exists()) {
				System.out.println("Enter name for new file: ");
				String newFileName = sc.nextLine();
				System.out.println("Select format (csv/txt): ");
				String choice = sc.nextLine();
				if (choice.equals("csv")) {
					booleanChoice = true;
				}
				else {
					 booleanChoice = false;
				}
				if (booleanChoice) {
				File file1 = new File(newFileName +".csv");
				FileWriter fw1 = new FileWriter(file1);
				String todo = reader(file);
				writer(fw1,todo);
				System.out.println("DONE");
				}
				else {
					File file1 = new File(newFileName +".txt");
					FileWriter fw1 = new FileWriter(file1);
					String todo = reader(file);
					writer(fw1,todo);
					System.out.println("DONE");
				}
			}
			else if (!file.exists()) {
			
			System.out.println("the file doesn't exist.");
			
			}
			}catch (Exception e) {
				System.out.println("an error has occured : " + e);
			}finally {
				sc.close();
				
			}
		
		}
	}



