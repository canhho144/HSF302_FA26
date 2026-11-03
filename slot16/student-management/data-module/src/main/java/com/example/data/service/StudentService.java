package com.example.data.service;

import com.example.data.pojo.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {
    Student create(Student s);
    Student findById(Long id);
    List<Student> findAll();
    Student update(Student s);
    void delete(Long id);
    Object search(String keyword);
    Page<Student> findPage(int page, int size);
}