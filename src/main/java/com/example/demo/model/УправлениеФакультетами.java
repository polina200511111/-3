package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class УправлениеФакультетами {
    private String названиеФакультета;
    private Факультет факультет;
    private List<СоставФакультета> состав = new ArrayList<>();
    private List<Кафедра> кафедры = new ArrayList<>();
    private List<Преподаватель> преподаватели = new ArrayList<>();
    
    public УправлениеФакультетами(String названиеФакультета, Факультет факультет) {
        this.названиеФакультета = названиеФакультета;
        this.факультет = факультет;
    }
    
    // Методы добавления
    public void добавитьВСостав(СоставФакультета сотрудник) {
        состав.add(сотрудник);
    }
    
    public void добавитьКафедру(Кафедра кафедра) {
        кафедры.add(кафедра);
    }
    
    public void добавитьПреподавателя(Преподаватель преподаватель) {
        преподаватели.add(преподаватель);
    }
    
    // Геттеры
    public String getНазваниеФакультета() { 
        return названиеФакультета; 
    }
    
    public Факультет getФакультет() { 
        return факультет; 
    }
    
    public List<СоставФакультета> getСостав() { 
        return состав; 
    }
    
    public List<Кафедра> getКафедры() { 
        return кафедры; 
    }
    
    public List<Преподаватель> getПреподаватели() { 
        return преподаватели; 
    }
    // Превратить объект в строку для файла
public String toFileString() {
    StringBuilder sb = new StringBuilder();
    sb.append(названиеФакультета).append(";");
    sb.append(факультет.getАудитория()).append(";");
    sb.append(факультет.getКорпус()).append(";");
    sb.append(факультет.getТелефон()).append(";");
    sb.append(факультет.getФамилияДекана()).append(";");

    // Сохраняем сотрудников
    for (СоставФакультета с : состав) {
        sb.append(с.getФио()).append(",").append(с.getДолжность()).append("|");
    }
    sb.append(";");

    // Сохраняем кафедры
    for (Кафедра к : кафедры) {
        sb.append(к.getНазвание()).append(",").append(к.getНаправлениеПодготовки()).append("|");
    }
    sb.append(";");

    // Сохраняем преподавателей
    for (Преподаватель п : преподаватели) {
        sb.append(п.getФио()).append(",").append(п.getКафедра()).append(",")
          .append(п.getДолжность()).append(",").append(п.getСтаж()).append(",")
          .append(п.getДисциплины()).append("|");
    }

    return sb.toString();
}

// Восстановить объект из строки
public static УправлениеФакультетами fromFileString(String line) {
    String[] parts = line.split(";");
    String название = parts[0];
    String аудитория = parts[1];
    int корпус = Integer.parseInt(parts[2]);
    String телефон = parts[3];
    String декан = parts[4];

    Факультет факультет = new Факультет(аудитория, корпус, телефон, декан) {};
    УправлениеФакультетами упр = new УправлениеФакультетами(название, факультет);

    // Загружаем сотрудников (часть 5)
    if (parts.length > 5 && !parts[5].isEmpty()) {
        String[] staffList = parts[5].split("\\|");
        for (String s : staffList) {
            String[] data = s.split(",");
            if (data.length == 2) {
                упр.добавитьВСостав(new СоставФакультета(data[0], data[1]));
            }
        }
    }

    // Загружаем кафедры (часть 6)
    if (parts.length > 6 && !parts[6].isEmpty()) {
        String[] deptList = parts[6].split("\\|");
        for (String d : deptList) {
            String[] data = d.split(",");
            if (data.length == 2) {
                упр.добавитьКафедру(new Кафедра(data[0], data[1]));
            }
        }
    }

    // Загружаем преподавателей (часть 7)
    if (parts.length > 7 && !parts[7].isEmpty()) {
        String[] teacherList = parts[7].split("\\|");
        for (String t : teacherList) {
            String[] data = t.split(",");
            if (data.length == 5) {
                упр.добавитьПреподавателя(new Преподаватель(
                    data[0], data[1], data[2], 
                    Integer.parseInt(data[3]), data[4]
                ));
            }
        }
    }

    return упр;
}
}