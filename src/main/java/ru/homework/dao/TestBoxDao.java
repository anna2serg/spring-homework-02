package ru.homework.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import ru.homework.common.Answer;
import ru.homework.common.TestUnit;
import ru.homework.reader.CSVReader;

public class TestBoxDao implements ITestBoxDao {
	
	private ArrayList<TestUnit> TestList;
	private int index = 0; 
	
	private void _TestListLoad(String file) {
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream  = classloader.getResourceAsStream(file);	
		InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(streamReader);
		
		/*File f = new File(file);
		if (!f.exists()) {
			System.out.println("Упс! Файл с тестом не найден. Надо бы проверить путь к файлу в настройках программы");
			return;
		}*/
		ArrayList<ArrayList<String>> raw = CSVReader.ParseString(reader);
		for (ArrayList<String> rows : raw) {
			TestUnit tu = new TestUnit();
			String var = "";
			Boolean varRight = false; 
			int i = 0;
			int c = 0;
			for (String col : rows) {
				String s = col.trim();
				if (i==0) tu.setQuestion(s);
				else {
					if (i%2==0) {
						varRight = s.equals("1");
						tu.addAnswer(new Answer(c+1, var, varRight));
						c += 1;
					}
					else {
						var = s;
					}
				}
				i++;
			}		
			TestList.add(tu);
		}
	}
	
    public TestBoxDao(String file) {
        //парсим
    	index = 0;
    	TestList = new ArrayList<TestUnit>();
    	_TestListLoad(file);
    }

	@Override
	public boolean EOF() {
		return ((TestList.size() <= 0)||(index == TestList.size()));
	}

	@Override
	public int Count() {
		return TestList.size();
	}

	@Override
	public TestUnit Next() {
		TestUnit result = TestList.get(index);
		index += 1;
		return result;
	}
	
	@Override
	public TestUnit Get() {
		return TestList.get(index);
	}	

}
