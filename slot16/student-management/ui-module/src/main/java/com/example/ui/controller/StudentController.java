package com.example.ui.controller;

import com.example.data.exception.StudentNotFoundException;
import com.example.data.exception.ValidationException;
import com.example.data.pojo.Student;
import com.example.data.service.StudentService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentController {

    private final StudentService studentService;

    @FXML private TextField searchField;
    @FXML private TableView<Student> tableView;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField ageField;
    @FXML private Label messageLabel;
    @FXML private Label statusLabel;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @FXML
    public void initialize() {
        // Lắng nghe sự kiện chọn dòng trên bảng để đổ dữ liệu lên form
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                fillForm(newVal);
            }
        });
        loadData(null);
    }

    // TODO 6.1: readForm()
    private Student readForm() {
        String name = nameField.getText();
        String email = emailField.getText();
        String ageStr = ageField.getText();

        if (ageStr == null || ageStr.trim().isEmpty()) {
            throw new ValidationException("Age is required");
        }

        int age;
        try {
            age = Integer.parseInt(ageStr.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("Age must be a number");
        }

        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setAge(age);
        return student;
    }

    // TODO 6.2: handleAdd()
    @FXML
    public void handleAdd() {
        executeSafely(() -> {
            Student s = readForm();
            Student created = studentService.create(s);
            handleClear();
            loadData(null);
            showSuccess("Added: " + created.getName());
        });
    }

    // TODO 6.3: handleUpdate()
    @FXML
    public void handleUpdate() {
        executeSafely(() -> {
            Student selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                throw new ValidationException("Please select a student to update");
            }
            Student edit = readForm();
            edit.setId(selected.getId());
            Student updated = studentService.update(edit);
            handleClear();
            loadData(null);
            showSuccess("Updated: " + updated.getName());
        });
    }

    // TODO 6.4: handleDelete()
    @FXML
    public void handleDelete() {
        executeSafely(() -> {
            Student selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                throw new ValidationException("Please select a student to delete");
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Delete student \"" + selected.getName() + "\"?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                studentService.delete(selected.getId());
                handleClear();
                loadData(null);
                showSuccess("Deleted: " + selected.getName());
            }
        });
    }

    // TODO 6.5: handleClear()
    @FXML
    public void handleClear() {
        idField.clear();
        nameField.clear();
        emailField.clear();
        ageField.clear();
        tableView.getSelectionModel().clearSelection();
        nameField.requestFocus();
        messageLabel.setText("");
    }

    @FXML
    public void handleSearch() {
        executeSafely(() -> {
            String keyword = searchField.getText();
            List<Student> results = studentService.search(keyword);
            tableView.getItems().setAll(results);
            statusLabel.setText("Found " + results.size() + " student(s)");
        });
    }

    @FXML
    public void handleRefresh() {
        searchField.clear();
        loadData(null);
    }

    private void loadData(String keyword) {
        try {
            List<Student> students = (keyword == null || keyword.isEmpty()) ?
                    studentService.findAll() : studentService.search(keyword);
            tableView.getItems().setAll(students);
            statusLabel.setText("Ready");
        } catch (Exception e) {
            showError(e);
        }
    }

    private void fillForm(Student s) {
        idField.setText(String.valueOf(s.getId()));
        nameField.setText(s.getName());
        emailField.setText(s.getEmail());
        ageField.setText(String.valueOf(s.getAge()));
        messageLabel.setText("");
    }

    // TODO 6.6: Bắt lỗi ValidationException | StudentNotFoundException và Exception khác
    private void executeSafely(Runnable action) {
        try {
            messageLabel.setStyle("-fx-text-fill: green;");
            action.run();
        } catch (ValidationException | StudentNotFoundException e) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText(e.getMessage());
            statusLabel.setText("Error");
        } catch (Exception e) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("System error: " + e.getMessage());
            statusLabel.setText("System Error");
            e.printStackTrace();
        }
    }

    private void showSuccess(String message) {
        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText(message);
        statusLabel.setText("Success");
    }
}