package ru.homework.dao;

import ru.homework.common.TestUnit;

public interface ITestBoxDao {

	boolean EOF(); 
	int Count();  
	TestUnit Next();
	TestUnit Get();
	
}
