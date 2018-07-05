package ru.homework.spring01.dao;

import ru.homework.spring01.common.TestUnit;

public interface ITestBoxDao {

	boolean EOF(); 
	int Count();  
	TestUnit Next();
	TestUnit Get();
	
}
