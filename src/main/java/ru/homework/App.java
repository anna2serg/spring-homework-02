package ru.homework;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import ru.homework.service.TestService;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class App 
{
    @SuppressWarnings("resource")
	public static void main( String[] args )
    {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
    	TestService t = context.getBean(TestService.class);
    	t.startTest();
    }
    
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
    	return new PropertySourcesPlaceholderConfigurer();
    }  
    
    @Bean
    public MessageSource messageSource() {
    	ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    	ms.setBasename("/i18n/bundle");
    	ms.setDefaultEncoding("UTF-8");
    	return ms;
    }
}
