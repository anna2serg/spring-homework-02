package ru.homework.service;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ru.homework.common.Answer;
import ru.homework.common.TestUnit;
import ru.homework.dao.TestBoxDao;

@Service
public class TestService {
	
	private Locale locale = Locale.ENGLISH;
	
	final private TestBoxDao dao;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
    public TestService(@Value("${locale}") String localeName, TestBoxDao dao) {
		this.locale = new Locale(localeName);
    	this.dao = dao;	
    }	
     
	private boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}	
	
	private String getLocalizedValue(String value) {
		return messageSource.getMessage(value, null, locale);
	}
	
	private String getLocalizedValue(String value, Object[] args) {
		return messageSource.getMessage(value, args, locale);
	}	
	
    @SuppressWarnings("resource")
	public void startTest() {
     if (dao.isEOF()) return;	
     
     Scanner in = new Scanner(System.in);
     System.out.println(getLocalizedValue("test.name"));
     System.out.println();     
     System.out.println(getLocalizedValue("test.description"));
     System.out.println();
	 System.out.println(getLocalizedValue("input.name"));
	 String username = in.nextLine();      
	 System.out.println(getLocalizedValue("hello.user", new String[] {username}));
	 System.out.println();
   	 System.out.println("");
   	 int maxScore = 0;
   	 int userScore = 0;
   	 String question = "";
   	 String answers = "";
   	 boolean multi = false;
   	 String hint = getLocalizedValue("hint.answer.single");
   	 String multiHint = getLocalizedValue("hint.answer.multi");
     ArrayList<Integer> userAnswers = null; 
     while (!dao.isEOF()) {     	 
    	 TestUnit tu = dao.getTest();
		 question = getLocalizedValue(tu.getQuestion());
		 multi = tu.isMultiChoice();
		 answers = "";
		 for (int i = 0; i < tu.getAnswerCount(); i+=1) {
			 Answer ans = tu.getAnswer(i); 
			 String ansStr = tryParseInt(ans.toString()) ? ans.toString() : getLocalizedValue(ans.toString());
			 answers += ans.getId() + " - " + ansStr + "\r\n";
		 }	 
		 System.out.println(question);
		 if (multi) System.out.println(multiHint);
		 else System.out.println(hint);
		 System.out.println(answers);
		 System.out.print(getLocalizedValue("user.answer"));
		 String[] userInput = in.nextLine().split(","); 
		 userAnswers = new ArrayList<Integer>();
		 for (String ui : userInput) {
			 int uiId = -1;
			 try {
			 	 uiId = Integer.parseInt(ui.trim());	 
			 } catch (NumberFormatException e) {
				 uiId = -1;
			 }	
			 if (uiId != -1) {
				 userAnswers.add(uiId);
			 }	
			 if (!multi) break;
		 }
		 maxScore += 10;
		 
		 if (tu.isRightAnswers(userAnswers)) {
			 userScore += 10;
		 }
		 dao.nextTest();
     }
     
     if (maxScore > 0) {
    	 System.out.println();
    	 String scoreResult = getLocalizedValue("score.result",  new Integer[]{userScore, maxScore});
    	 System.out.println(scoreResult);
    	 
    	 int userScorePercent = 100 * userScore / maxScore;
    	 String rankResult = userScorePercent>80 ? getLocalizedValue("score.result.excellent") :
    		 				 userScorePercent>55 ? getLocalizedValue("score.result.good") :
    		 				 userScorePercent>20 ? getLocalizedValue("score.result.notbad") :	 
    		                 					   getLocalizedValue("score.result.soso"); 	 	
    	 System.out.println(rankResult);
     }
   }    

    
}
