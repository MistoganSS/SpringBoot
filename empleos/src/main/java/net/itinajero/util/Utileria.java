package net.itinajero.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class Utileria {

	public static String saveFile(MultipartFile multipart, String route) {
		String nameOriginal=multipart.getOriginalFilename();
		nameOriginal=nameOriginal.replace(" ", "-");
		String nameEnd=randomAlphaNumeric(8)+nameOriginal;
		try {
			File imgFile= new File(route+nameEnd);
			System.out.println(imgFile.getAbsolutePath());
			multipart.transferTo(imgFile);
			return nameEnd;
		}catch(IOException e) {
			System.out.println("Error: "+e.getMessage());
			return null;
		}
		
	}
	
	public static String randomAlphaNumeric(int count) {
		String CARACTERES= "ABCDFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder=new StringBuilder();
		while(count--!=0) {
			int character= (int) (Math.random()*CARACTERES.length());
			builder.append(CARACTERES.charAt(character));
		}
		return builder.toString();
	}
}
