package ru.homework.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import ru.homework.common.Answer;
import ru.homework.common.TestUnit;
import ru.homework.reader.CSVReader;

@Repository
public class TestBoxDaoImpl implements TestBoxDao {
	
	private ArrayList<TestUnit> testList;
	private int index = 0; 
	
	private void testListLoad(String file) {
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream  = classloader.getResourceAsStream(file);
		if (inputStream==null) {
			System.out.println("Упс! Файл с тестом не найден. Надо бы проверить имя и путь к файлу в настройках программы");
			return;			
		}	
		InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(streamReader);

		ArrayList<ArrayList<String>> raw = CSVReader.ParseString(reader);
		for (ArrayList<String> rows : raw) {
			TestUnit testUnit = new TestUnit();
			String var = "";
			ArrayList<Integer> rightAnswers = new ArrayList<Integer>();
			Boolean varRight = false; 
			int iCol = 0;
			int idAnswer = 1;
			for (String col : rows) {
				String s = col.trim();
				if (iCol==0) testUnit.setQuestion(s);
				else {
					if (iCol%2==0) {
						varRight = s.equals("1");
						if (varRight) rightAnswers.add(idAnswer);
						testUnit.addAnswer(new Answer(idAnswer, var));
						idAnswer += 1;
					}
					else {
						var = s;
					}
				}
				iCol++;
			}	
			testUnit.setRightAnswers(rightAnswers);
			testList.add(testUnit);
		}
	}
	
	@Autowired
    public TestBoxDaoImpl(@Value("${testFile}") String file) {
        //парсим
    	index = 0;
    	testList = new ArrayList<TestUnit>();
    	testListLoad(file);
    }

	@Override
	public boolean isEOF() {
		return ((testList.size() <= 0)||(index == testList.size()));
	}

	@Override
	public int count() {
		return testList.size();
	}

	@Override
	public TestUnit nextTest() {
		TestUnit result = testList.get(index);
		index += 1;
		return result;
	}
	
	@Override
	public TestUnit getTest() {
		return testList.get(index);
	}	

}
