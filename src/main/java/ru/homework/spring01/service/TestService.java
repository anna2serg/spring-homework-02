package ru.homework.spring01.service;

import java.util.ArrayList;
import java.util.Scanner;

import ru.homework.spring01.common.Answer;
import ru.homework.spring01.common.TestUnit;
import ru.homework.spring01.dao.ITestBoxDao;

public class TestService {
	
	private ITestBoxDao dao;
	private Scanner in = new Scanner(System.in);
	
    public TestService(ITestBoxDao dao) {
    	this.dao = dao;	
    }	
     
    public void startTest() {
     if (dao.EOF()) return;	
  	
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
     while (!dao.EOF()) {     	 
    	 TestUnit tu = dao.Get();
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
		 dao.Next();
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
