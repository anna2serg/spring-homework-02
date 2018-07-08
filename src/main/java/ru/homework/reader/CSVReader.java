package ru.homework.reader;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class CSVReader {
	
    public ArrayList<ArrayList<String>> ParseString(BufferedReader file) {
    	ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
    	
    	String row = "";
        String splitBy = ";";
        
        try {
			while ((row = file.readLine()) != null) {
				String[] columns = row.split(splitBy);  
				
				result.add(new ArrayList<>(Arrays.asList(columns)));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    	return result;
    }
    
}
