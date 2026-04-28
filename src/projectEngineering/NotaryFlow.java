package projectEngineering;

import java.util.Scanner;
import java.io.*;
import java.time.LocalTime;

class userL{


public static void userLog (String userName) {
	File file = new File("logs.csv");
	
	try {
		FileWriter fw = new FileWriter(file,true);
		PrintWriter writer = new PrintWriter(fw);
		LocalTime time = LocalTime.now();
		writer.println(userName + "logged on at :" + time);
		writer.close();
}catch (IOException e) {
	System.out.println(e);
}
	}}
 class users {
	String userName;
	String userLevel;
	String userPassword;
	String userID;
	
	
	public void userInfo(String userName,String userLevel,String userPassword , String userID) {
		this.userName = userName;
		this.userLevel = userLevel;
		this.userPassword = userPassword;
		this.userID = userID;
	}
	public static String userExist(String userInFile) {

		String c = "";
		String found="";
		File file = new File("Users.csv");
		Scanner s = null;
		
		if (!file.exists()) {
			return "The file doesn't exist";
		}
		try {
			s = new Scanner(file);
			while (s.hasNextLine()) {
				c = s.nextLine();
				String[] parts = c.split("!");
				if (parts.length > 0 && parts[0].equalsIgnoreCase(userInFile)) {
					return "";
				}
				else if (!c.toLowerCase().contains(userInFile.toLowerCase()) && !s.hasNextLine()) {
					return"User does not exist";
				}
				//depending on how the user info are created we will decide on how to split the data
				//all data will be stored in one line and then to be separated and added into the method user info 
			}
			s.close();
		}catch (IOException err) {
				return "error has been detected : " + err;
				
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return found;
		
	}
	public String userCreate(String userName,String userLevel,String userPassword , String userID) {
		FileWriter fw = null;
		Scanner sc = new Scanner(System.in);
		try {
		File file = new File("Users.csv");
		fw = new FileWriter(file, true);
		PrintWriter writer = new PrintWriter(fw);
		String S1 = userName + "!" + userLevel + "!" + userPassword + "!" + userID + "!" ;
		//add a info spliter
		writer.println(S1);
		sc.close();
		writer.close();
		
		}catch (IOException er) { System.out.print("An error has occured : " + er);
		}finally {
			try {
			if (fw != null) fw.close();
			}catch (IOException er2) {
				System.out.print("an error has occured : " + er2);
			}
		}
		return "User has been registered successfully.";
	}
}



public class NotaryFlow {
	public static void main(String[] args) {
		//a user reader is needed
		System.out.println("Enter your User Name, Password and ID:");
		boolean work = true;
		String username = "";
		String password = "";
		String userID = "";
		Scanner sc = new Scanner(System.in);
		System.out.print("User Name:");
		while (work) {
			 username = sc.nextLine();
			 String result = users.userExist(username);
			 System.out.println(result);
			 if (result.equals("")) {
				 work = false;
			 }
		}
		work = true;
		
		System.out.print("Password:");
		 password = sc.nextLine();
		System.out.print("User ID:");
		 userID = sc.nextLine();
		userL.userLog(username);
	
	
	
	}

}
