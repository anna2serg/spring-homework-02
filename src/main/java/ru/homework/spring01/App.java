package ru.homework.spring01;

import java.net.URL;
import java.net.URLClassLoader;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.homework.spring01.service.TestService;


public class App 
{
    @SuppressWarnings("resource")
	public static void main( String[] args )
    {
    	
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
        	System.out.println(url.getFile());
        }    	
    	
    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
    	TestService t = context.getBean(TestService.class);
    	t.startTest();
    }
}
