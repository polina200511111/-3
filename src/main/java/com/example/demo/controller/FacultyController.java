package com.example.demo.controller;

import com.example.demo.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.*;

@Controller
public class FacultyController {
    
    @Autowired
    private FacultyService facultyService;
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("faculties", facultyService.getAllFaculties());
        model.addAttribute("count", facultyService.getCount());
        return "index";
    }
    
    @GetMapping("/add-faculty")
    public String showAddFacultyForm() {
        return "add-faculty";
    }
    
    @PostMapping("/add-faculty")
    public String addFaculty(
            @RequestParam String name,
            @RequestParam String аудитория,
            @RequestParam int корпус,
            @RequestParam String телефон,
            @RequestParam String декан) {
        
        facultyService.addFaculty(name, аудитория, корпус, телефон, декан);
        return "redirect:/";
    }
    
    @GetMapping("/add-department")
    public String showAddDepartmentForm(Model model) {
        model.addAttribute("faculties", facultyService.getAllFaculties());
        return "add-department";
    }
    
    @PostMapping("/add-department")
    public String addDepartment(
            @RequestParam int facultyIndex,
            @RequestParam String название,
            @RequestParam String направление) {
        
        facultyService.addDepartment(facultyIndex - 1, название, направление);
        return "redirect:/";
    }
    
    @GetMapping("/add-teacher")
    public String showAddTeacherForm(Model model) {
        model.addAttribute("faculties", facultyService.getAllFaculties());
        return "add-teacher";
    }
    
    @PostMapping("/add-teacher")
    public String addTeacher(
            @RequestParam int facultyIndex,
            @RequestParam String фио,
            @RequestParam String кафедра,
            @RequestParam String должность,
            @RequestParam int стаж,
            @RequestParam String дисциплины) {
        
        facultyService.addTeacher(facultyIndex - 1, фио, кафедра, должность, стаж, дисциплины);
        return "redirect:/";
    }
    
    @GetMapping("/add-staff")
    public String showAddStaffForm(Model model) {
        model.addAttribute("faculties", facultyService.getAllFaculties());
        return "add-staff";
    }
    
    @PostMapping("/add-staff")
    public String addStaff(
            @RequestParam int facultyIndex,
            @RequestParam String фио,
            @RequestParam String должность) {
        
        facultyService.addStaff(facultyIndex - 1, фио, должность);
        return "redirect:/";
    }
    
    @GetMapping("/sort-by-name")
    public String sortByName() {
        facultyService.sortByName();
        return "redirect:/";
    }
    
    @GetMapping("/sort-by-corps")
    public String sortByCorps() {
        facultyService.sortByCorps();
        return "redirect:/";
    }
    
    @GetMapping("/simple")
    public String simple() {
        return "simple";
    }
    
    @GetMapping("/save")
    public String save() {
        facultyService.saveToFile("faculties.txt");
        return "redirect:/";
    }

    @GetMapping("/load")
    public String load() {
        facultyService.loadFromFile("faculties.txt");
        return "redirect:/";
    }
    
    // ========== ПОДДЕРЖКА ==========
    
    @GetMapping("/support")
    public String support(Model model) {
        return "support";
    }
    
    @PostMapping("/support/send")
    public String sendSupport(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String subject,
            @RequestParam String message,
            Model model) {
        
        // 1. Сохраняем в файл
        saveSupportToFile(name, email, subject, message);
        
        // 2. Отправляем уведомление (в консоль)
        sendNotification(name, email, subject, message);
        
        model.addAttribute("message", "✅ Спасибо! Ваше сообщение отправлено. Мы ответим вам в ближайшее время.");
        model.addAttribute("success", true);
        
        return "support";
    }
    
    @GetMapping("/support/history")
    public String supportHistory(Model model) {
        List<String> messages = readSupportHistory();
        model.addAttribute("messages", messages);
        return "support-history";
    }
    
    // Сохранить обращение в файл
    private void saveSupportToFile(String name, String email, String subject, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("support_messages.txt", true))) {
            writer.write("=== " + new Date() + " ===");
            writer.newLine();
            writer.write("От: " + name + " (" + email + ")");
            writer.newLine();
            writer.write("Тема: " + subject);
            writer.newLine();
            writer.write("Сообщение: " + message);
            writer.newLine();
            writer.write("---");
            writer.newLine();
            writer.newLine();
            System.out.println("✅ Сохранено в файл support_messages.txt");
        } catch (IOException e) {
            System.out.println("❌ Ошибка сохранения: " + e.getMessage());
        }
    }
    
    // Отправить уведомление (пока в консоль)
    private void sendNotification(String name, String email, String subject, String message) {
        System.out.println("\n📧 НОВОЕ ОБРАЩЕНИЕ В ПОДДЕРЖКУ!");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("👤 Имя: " + name);
        System.out.println("📧 Email: " + email);
        System.out.println("📌 Тема: " + subject);
        System.out.println("💬 Сообщение: " + message);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
    }
    
    // Прочитать историю обращений из файла
    private List<String> readSupportHistory() {
        List<String> messages = new ArrayList<>();
        File file = new File("support_messages.txt");
        
        if (!file.exists()) {
            messages.add("Пока нет обращений.");
            return messages;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder message = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.equals("---")) {
                    messages.add(message.toString());
                    message = new StringBuilder();
                } else {
                    if (message.length() > 0) message.append("\n");
                    message.append(line);
                }
            }
            if (message.length() > 0) {
                messages.add(message.toString());
            }
        } catch (IOException e) {
            messages.add("Ошибка чтения файла: " + e.getMessage());
        }
        
        return messages;
    }
}