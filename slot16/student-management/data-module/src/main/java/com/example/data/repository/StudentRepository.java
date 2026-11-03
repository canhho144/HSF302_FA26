package com.example.data.repository;

import com.example.data.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// TODO 2.1: @Repository, extends JpaRepository<Student, Long>
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // TODO 2.2: Tìm sinh viên theo email chính xác
    Optional<Student> findByEmail(String email);

    // TODO 2.3: Kiểm tra tồn tại theo email
    boolean existsByEmail(String email);

    // TODO 2.4: Tìm danh sách sinh viên theo độ tuổi
    List<Student> findByAge(Integer age);

    // TODO 2.5: Tìm danh sách sinh viên theo khoảng tuổi (từ minAge đến maxAge)
    List<Student> findByAgeBetween(Integer minAge, Integer maxAge);

    // TODO 2.6: Tìm kiếm không phân biệt hoa thường theo tên hoặc email chứa từ khóa
    @Query("SELECT s FROM Student s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :kw, '%')) OR LOWER(s.email) LIKE LOWER(CONCAT('%', :kw, '%'))")
    List<Student> searchByKeyword(@Param("kw") String kw);

    Optional<Object> findByEmailIgnoreCase(String mail, String trim);

    List<Student> findAllByOrderByIdAsc();

    boolean existsByEmailIgnoreCase(String mail);

    boolean existsByEmailIgnoreCaseAndIdNot(String mail, Long id);

    List<Student> search(String binh);


}