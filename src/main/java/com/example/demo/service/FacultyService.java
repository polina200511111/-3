package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.ПреподавательRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;

@Service
public class FacultyService {
    
    @Autowired
    private ПреподавательRepository преподавательRepository;
    
    private List<УправлениеФакультетами> всеФакультеты = new ArrayList<>();
    
    // Получить все факультеты
    public List<УправлениеФакультетами> getAllFaculties() {
        return всеФакультеты;
    }
    
    // Добавить новый факультет
    public void addFaculty(String название, String аудитория, int корпус, String телефон, String декан) {
        Факультет факультет = new Факультет(аудитория, корпус, телефон, декан) {};
        УправлениеФакультетами упр = new УправлениеФакультетами(название, факультет);
        всеФакультеты.add(упр);
    }
    
    // Добавить кафедру на факультет
    public void addDepartment(int facultyIndex, String название, String направление) {
        if (facultyIndex >= 0 && facultyIndex < всеФакультеты.size()) {
            Кафедра кафедра = new Кафедра(название, направление);
            всеФакультеты.get(facultyIndex).добавитьКафедру(кафедра);
        }
    }
    
    // Добавить преподавателя (с сохранением в БД)
    public void addTeacher(int facultyIndex, String фио, String кафедра, String должность, int стаж, String дисциплины) {
        // 1. Создаем преподавателя
        Преподаватель преп = new Преподаватель(фио, кафедра, должность, стаж, дисциплины);
        
        // 2. Сохраняем в базу данных через репозиторий!
        преподавательRepository.save(преп);
        
        // 3. Также добавляем его в список факультета (для отображения на сайте)
        if (facultyIndex >= 0 && facultyIndex < всеФакультеты.size()) {
            всеФакультеты.get(facultyIndex).добавитьПреподавателя(преп);
        }
    }
    
    // Добавить сотрудника в состав факультета
    public void addStaff(int facultyIndex, String фио, String должность) {
        if (facultyIndex >= 0 && facultyIndex < всеФакультеты.size()) {
            СоставФакультета сотрудник = new СоставФакультета(фио, должность);
            всеФакультеты.get(facultyIndex).добавитьВСостав(сотрудник);
        }
    }
    
    // Сортировка по названию факультета
    public void sortByName() {
        Collections.sort(всеФакультеты, new Comparator<УправлениеФакультетами>() {
            @Override
            public int compare(УправлениеФакультетами f1, УправлениеФакультетами f2) {
                return f1.getНазваниеФакультета().compareToIgnoreCase(f2.getНазваниеФакультета());
            }
        });
    }
    
    // Сортировка по корпусу
    public void sortByCorps() {
        Collections.sort(всеФакультеты, new Comparator<УправлениеФакультетами>() {
            @Override
            public int compare(УправлениеФакультетами f1, УправлениеФакультетами f2) {
                return Integer.compare(
                    f1.getФакультет().getКорпус(), 
                    f2.getФакультет().getКорпус()
                );
            }
        });
    }
    
    // Получить количество факультетов
    public int getCount() {
        return всеФакультеты.size();
    }
    
    // НОВЫЙ МЕТОД: поиск преподавателей по части ФИО
    public List<Преподаватель> searchTeachersByName(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return преподавательRepository.findByФиоContainingIgnoreCase(query);
    }
    
    // Сохранить все факультеты в файл
    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (УправлениеФакультетами faculty : всеФакультеты) {
                writer.write(faculty.toFileString());
                writer.newLine();
            }
            System.out.println(" Сохранено в файл: " + filename);
        } catch (IOException e) {
            System.out.println(" Ошибка сохранения: " + e.getMessage());
        }
    }

    // Загрузить факультеты из файла
    public void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println(" Файл не найден: " + filename);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            всеФакультеты.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    УправлениеФакультетами faculty = УправлениеФакультетами.fromFileString(line);
                    всеФакультеты.add(faculty);
                }
            }
            System.out.println(" Загружено из файла: " + filename);
        } catch (IOException e) {
            System.out.println(" Ошибка загрузки: " + e.getMessage());
        }
    }
}