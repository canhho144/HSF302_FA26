// data-module/src/main/java/com/example/data/exception/StudentNotFoundException.java
package com.example.data.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String id) { super("Student not found: " + id); }
}