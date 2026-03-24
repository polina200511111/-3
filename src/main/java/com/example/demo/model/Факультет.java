package com.example.demo.model;

public abstract class Факультет {
    protected String аудитория;
    protected int корпус;
    protected String телефон;
    protected String фамилияДекана;

    public Факультет() {
        this.аудитория = "";
        this.корпус = 0;
        this.телефон = "";
        this.фамилияДекана = "";
    }

    public Факультет(String аудитория, int корпус, String телефон, String фамилияДекана) {
        this.аудитория = аудитория;
        this.корпус = корпус;
        this.телефон = телефон;
        this.фамилияДекана = фамилияДекана;
    }

    // Геттеры
    public String getАудитория() { return аудитория; }
    public int getКорпус() { return корпус; }
    public String getТелефон() { return телефон; }
    public String getФамилияДекана() { return фамилияДекана; }

    // Сеттеры
    public void setАудитория(String аудитория) { this.аудитория = аудитория; }
    public void setКорпус(int корпус) { this.корпус = корпус; }
    public void setТелефон(String телефон) { this.телефон = телефон; }
    public void setФамилияДекана(String фамилияДекана) { this.фамилияДекана = фамилияДекана; }

    @Override
    public String toString() {
        return "Корпус " + корпус + ", ауд. " + аудитория + ", тел. " + телефон + ", декан: " + фамилияДекана;
    }
}