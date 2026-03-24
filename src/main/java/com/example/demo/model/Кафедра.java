package com.example.demo.model;

public class Кафедра {
    private String название;
    private String направлениеПодготовки;
    
    public Кафедра(String название, String направлениеПодготовки) {
        this.название = название;
        this.направлениеПодготовки = направлениеПодготовки;
    }
    
    // Геттеры
    public String getНазвание() { 
        return название; 
    }
    
    public String getНаправлениеПодготовки() { 
        return направлениеПодготовки; 
    }
    
    @Override
    public String toString() {
        return "Кафедра '" + название + "' (" + направлениеПодготовки + ")";
    }
}