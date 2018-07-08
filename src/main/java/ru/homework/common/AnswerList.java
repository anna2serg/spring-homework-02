package ru.homework.common;

import java.util.ArrayList;

public final class AnswerList {
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
	
	public void add(Answer answer) {
		answers.add(answer);
	}
}
