package ru.homework.common;

import java.util.ArrayList;
import java.util.List;

public class TestUnit {
	
    private String question;
    private AnswerList answerList;
    private ArrayList<Integer> rightAnswers;

    public TestUnit() {
    	answerList = new AnswerList();
    	rightAnswers = new ArrayList<Integer>();
    }
    
    public String getQuestion() {
        return question;
    }    
    
    public void setQuestion(String question) {
        this.question = question;
    }    
    
    public int getAnswerCount() {
        return answerList.getCount();
    }	
    
    public Answer getAnswer(int Index) {
        return answerList.get(Index);
    }	    
    
    public void addAnswer(Answer answer) {
        answerList.add(answer);
    }	 
    
    public void setRightAnswers(List<Integer> rightAnswers) {
    	this.rightAnswers.clear();
    	this.rightAnswers.addAll(rightAnswers);
    }	    
    
	public boolean isMultiChoice() {
		return rightAnswers.size()>1;
	}	   
	
	public boolean isRightAnswers(ArrayList<Integer> userAnswers) {
		return (userAnswers.containsAll(rightAnswers) 
				&& rightAnswers.containsAll(userAnswers));		
	}
       
}
