package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Преподаватель {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String фио;
    private String кафедра;
    private String должность;
    private int стаж;
    private String дисциплины;

    public Преподаватель() {}

    public Преподаватель(String фио, String кафедра, String должность, int стаж, String дисциплины) {
        this.фио = фио;
        this.кафедра = кафедра;
        this.должность = должность;
        this.стаж = стаж;
        this.дисциплины = дисциплины;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getФио() { return фио; }
    public void setФио(String фио) { this.фио = фио; }

    public String getКафедра() { return кафедра; }
    public void setКафедра(String кафедра) { this.кафедра = кафедра; }

    public String getДолжность() { return должность; }
    public void setДолжность(String должность) { this.должность = должность; }

    public int getСтаж() { return стаж; }
    public void setСтаж(int стаж) { this.стаж = стаж; }

    public String getДисциплины() { return дисциплины; }
    public void setДисциплины(String дисциплины) { this.дисциплины = дисциплины; }

    @Override
    public String toString() {
        return фио + " (" + кафедра + ", " + должность + ", стаж: " + стаж + " лет, дисциплины: " + дисциплины + ")";
    }
}