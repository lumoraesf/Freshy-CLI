package projectEngineering;
	import java.util.Scanner;
	import java.io.*;
	import java.time.LocalDate;

	
public class Writer {
	public static String eraseFile (boolean delete , String name) {
		String okay = "";
		try {
			FileWriter fw = new FileWriter(name+".csv", false);
			okay = "File cleared successfully.";
			fw.close();
		}catch (IOException err) {
			return "An error has occured : " + err;
		}
			return okay;
		
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean save = false;
		String name ="";
		double  quantity = 0;
		String fileName = "";
		int YYYY = 0;
		int MM = 0;
		int DD  = 0;
			System.out.println("Enter your file name : ");
			fileName = sc.nextLine();
			
			
			
				File file = new File(fileName + ".csv");
				if (file.exists()) {
					System.out.println("File already exists, do you want to erase the existing content? (True/False)");
					boolean eraser = sc.nextBoolean();
					sc.nextLine();
					if (eraser) {
						System.out.println(eraseFile(eraser,fileName));
					}}
					try {
					FileWriter fw = new FileWriter(fileName+".csv");
					fw.write("Name | Quantity |  Expiration Date  ." + "\n");
					fw.close();
				
			}catch(Exception er ) {
					System.out.println("An error has occured : " + er);
				}
					System.out.println("Enter the name of your product : ");
					name = sc.nextLine();
					System.out.println("Enter the quantity of your product : ");
					quantity = sc.nextInt();
					sc.nextLine();
					System.out.println("Enter the expiration date of your product (DD/MM/YYYY): ");
					System.out.println("Day");
					DD = sc.nextInt();
					System.out.println("Month");
					MM = sc.nextInt();
					System.out.println("Year");
					YYYY = sc.nextInt();
					LocalDate now = LocalDate.now();
					LocalDate myTime = LocalDate.of(YYYY,MM,DD);

					 if (now.isAfter(myTime)) {
					    System.out.println("Food is already expired.");
					 }
					 else {
						 save = true;
					 }
					 if (save) {
						 
					 }
	}
	

}
