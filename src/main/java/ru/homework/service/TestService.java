package ru.homework.service;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ru.homework.common.Answer;
import ru.homework.common.TestUnit;
import ru.homework.dao.TestBoxDao;

@Service
public class TestService {
	
	final private TestBoxDao dao;
	
	@Autowired
	private MessageSource messageSource;

    public TestService(TestBoxDao dao) {
    	this.dao = dao;	
    }	
     
    @SuppressWarnings("resource")
	public void startTest() {
     if (dao.isEOF()) return;	
     
     Scanner in = new Scanner(System.in);
          
     System.out.println(messageSource.getMessage("hello.user", new String[] {"Anna"}, new Locale("ru") ));
  	
   	 System.out.println( "в Нью-Йорке свет погас давно и молоко прокисло,\r\n" + 
      		"а мне плевать, ведь все равно\r\n" + 
      		"жизнь не имеет смысла.\r\n" + 
      		"Фиби Буфе (c)\r\n" );    	
   	 System.out.println("Тест \"Хорошо ли ты знаешь сериал \"Друзья\"?\"");
   	 System.out.println("");
   	 int maxScore = 0;
   	 int userScore = 0;
   	 String question = "";
   	 String answers = "";
   	 boolean multi = false;
   	 String hint = "(Введите цифру, соответствующую выбранному Вами ответу)";
   	 String multiHint = "(На вопрос имеется несколько правильных ответов, введите их через запятую)";
     ArrayList<Integer> userAnswers = null; 
     ArrayList<Integer> rightAnswers = null;
     while (!dao.isEOF()) {     	 
    	 TestUnit tu = dao.getTest();
		 question = tu.getQuestion();
		 multi = tu.isMultiChoice();
		 answers = "";
		 rightAnswers = new ArrayList<Integer>();
		 for (int i = 0; i < tu.getAnswerCount(); i +=1) {
			 Answer a = tu.getAnswer(i);
			 answers += a.getId() + " - " + a + "\r\n";
			 if (a.getRight()) rightAnswers.add(a.getId());
		 }	 
		 System.out.println(question);
		 if (multi) System.out.println(multiHint);
		 else System.out.println(hint);
		 System.out.println(answers);
		 System.out.print("Ваш ответ: ");
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
		 if (userAnswers.containsAll(rightAnswers) 
		 && rightAnswers.containsAll(userAnswers)) {
			 userScore += 10;
		 }
		 dao.nextTest();
     }
     
     if (maxScore > 0) {
    	 String scoreResult = "Поздравляю! Вы набрали %d очков из %d возможных ";
    	 System.out.println(String.format(scoreResult, userScore, maxScore));
    	 String rankResult = userScore>50 ? "Браво! Вы фанат сериала \"Друзья\"!" :
    		                 userScore>20 ? "Вы только что получили почетное звание \"Друг Друзей\"!" :
    		                 "Вы не смотрели сериал \"Друзья\"? Вы многое потеряли :)"; 	 	
    	 System.out.println(rankResult);
     }
   }    

    
}
