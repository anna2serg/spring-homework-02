package ru.homework.spring01.common;

public class TestUnit {
	
    private String question;
    private AnswerList answerList;

    public TestUnit() {
    	answerList = new AnswerList();
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
    
    public boolean isMultiChoice() {
        return answerList.isMultiChoice();
    }	       
       
}
