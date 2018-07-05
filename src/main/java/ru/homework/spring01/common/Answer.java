package ru.homework.spring01.common;

public class Answer {
	private int id;
    private String variant;
    private boolean right;

    public Answer(int id, String variant, boolean right) {
    	this.id = id;
        this.variant = variant;
        this.right = right;
    }

    public int getId() {
        return id;
    }    
    
    public String getVariant() {
        return variant;
    }

    public boolean getRight() {
        return right;
    }
    
    public String toString() { 
    	return variant;  
    }     
    
}
