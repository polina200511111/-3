package com.example.demo.model;

public class СоставФакультета {
    private String фио;
    private String должность;
    
    public СоставФакультета(String фио, String должность) {
        this.фио = фио;
        this.должность = должность;
    }
    
    // Геттеры
    public String getФио() { 
        return фио; 
    }
    
    public String getДолжность() { 
        return должность; 
    }
    
    @Override
    public String toString() {
        return фио + " - " + должность;
    }
}