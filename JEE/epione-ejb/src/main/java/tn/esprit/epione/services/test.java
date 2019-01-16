package tn.esprit.epione.services;

import java.util.Scanner;

import tn.esprit.epione.util.Util;

public class test {

	String path = this.getClass().getClassLoader().getResource("").getPath();
	
	public static void main(String args[]) {
//		Scanner inputScanner = new Scanner(System.in);
//		System.out.print("String: ");
//		String input = inputScanner.next();
//
//		System.out.println("SHA-256: " + Util.hashPassword(input));
//		System.out.println("\nSHA-256: " + Util.hashPassword(input));

		
		String uploadedFilePath = System.getProperty("user.dir");// ...\Epione\JEE\epione-ejb
		for (int i = 0; i < 2; i++)
			uploadedFilePath = uploadedFilePath.substring(0, uploadedFilePath.lastIndexOf("\\"));

		uploadedFilePath = uploadedFilePath + "\\Files\\";// ...\Epione\Files\
		System.out.println(uploadedFilePath );

	}
}
