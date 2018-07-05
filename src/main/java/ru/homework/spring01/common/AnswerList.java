package ru.homework.spring01.common;

import java.util.ArrayList;

public class AnswerList {
	private ArrayList<Answer> answers;
	
	public AnswerList() {
		answers = new ArrayList<Answer>();
	}
	
    public Answer get(int Index) {
        return answers.get(Index);
    }	    	
	
    public int getCount() {
        return answers.size();
    }		
	
	public boolean isMultiChoice() {
    	boolean result = false; 
    	int rightCount = 0;
    	for (Answer a : answers) {
    		if (a.getRight()) rightCount += 1;
    		if (rightCount>1) {
    			result = true;
    			return result;
    		}
    	}	
    	return result;
	}
	
	public void add(Answer answer) {
		answers.add(answer);
	}
}
