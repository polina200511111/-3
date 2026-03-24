package com.example.demo.controller;

import com.example.demo.model.Преподаватель;
import com.example.demo.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class TeacherSearchController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping("/test-search")
    public List<Преподаватель> searchTeachers(@RequestParam String query) {
        return facultyService.searchTeachersByName(query);
    }
}
