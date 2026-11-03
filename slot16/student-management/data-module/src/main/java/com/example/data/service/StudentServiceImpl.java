package com.example.data.service;

import com.example.data.exception.StudentNotFoundException;
import com.example.data.exception.ValidationException;
import com.example.data.pojo.Student;
import com.example.data.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

// TODO 3.1: @Service + @Transactional(readOnly = true)
@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[\\w.-]+$");

    // TODO 3.2: field final StudentRepository + constructor injection (KHÔNG dùng @Autowired trên field)
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    // TODO 3.3: @Transactional — normalize → validate → existsByEmailIgnoreCase → save
    @Transactional
    public Student create(Student s) {
        normalize(s);
        validate(s);
        if (studentRepository.existsByEmailIgnoreCase(s.getEmail())) {
            throw new ValidationException("Email already exists: " + s.getEmail());
        }
        return studentRepository.save(s);
    }

    @Override
    // TODO 3.4: findById(...).orElseThrow(() -> new StudentNotFoundException(id))
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
    }

    @Override
    // TODO 3.5: findAllByOrderByIdAsc()
    public List<Student> findAll() {
        return studentRepository.findAllByOrderByIdAsc();
    }

    @Override
    // TODO 3.6: @Transactional — findById (entity managed) → normalize → validate
    //           → existsByEmailIgnoreCaseAndIdNot → copy field → save
    @Transactional
    public Student update(Student s) {
        Student existing = findById(s.getId());
        normalize(s);
        validate(s);

        if (studentRepository.existsByEmailIgnoreCaseAndIdNot(s.getEmail(), s.getId())) {
            throw new ValidationException("Email already exists: " + s.getEmail());
        }

        existing.setName(s.getName());
        existing.setEmail(s.getEmail());
        // Cập nhật thêm các trường khác nếu có trong Student pojo của bạn

        return studentRepository.save(existing);
    }

    @Override
    // TODO 3.7: @Transactional — !existsById → StudentNotFoundException; ngược lại deleteById
    @Transactional
    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    // TODO 3.8
    public Object search(String keyword) {
        // Giả sử repository có phương thức tìm kiếm theo tên hoặc email chứa keyword
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        return studentRepository.findByEmailIgnoreCase(keyword.trim(), keyword.trim());
    }


    private void normalize(Student s) {
        // TODO 3.9
        if (s != null) {
            if (s.getName() != null) {
                s.setName(s.getName().trim().replaceAll("\\s+", " "));
            }
            if (s.getEmail() != null) {
                s.setEmail(s.getEmail().trim().toLowerCase());
            }
        }
    }
    @Override
    public Page<Student> findPage(int page, int size) {
        return studentRepository.findAll(PageRequest.of(page, size, Sort.by("id")));
    }
    private void validate(Student s) {
        // TODO 3.10
        if (s == null) {
            throw new ValidationException("Student object cannot be null");
        }
        if (s.getName() == null || s.getName().isEmpty()) {
            throw new ValidationException("Student name cannot be empty");
        }
        if (s.getEmail() == null || !EMAIL.matcher(s.getEmail()).matches()) {
            throw new ValidationException("Invalid email format: " + s.getEmail());
        }
    }
}