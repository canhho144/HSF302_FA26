package com.fu.ch08.b4;

import com.fu.ch08.common.AlertUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class LoginController {

    @FXML private TextField tfUsername;
    @FXML private TextField tfPasswordVisible;
    @FXML private PasswordField pfPassword;
    @FXML private CheckBox chkShow;
    @FXML private ComboBox<String> cbRole;
    @FXML private Label lblError;
    @FXML private Button btnLogin;

    private static final Map<String, String[]> ACCOUNTS = Map.of(
            "student", new String[]{"Student@123", "Sinh viên"},
            "teacher", new String[]{"Teacher@123", "Giảng viên"}
    );
    private int failedCount = 0;

    @FXML
    public void initialize() {
        // Đồng bộ 2 chiều dữ liệu giữa PasswordField và TextField hiển thị mật khẩu
        tfPasswordVisible.textProperty().bindBidirectional(pfPassword.textProperty());

        // Hiện/ẩn: visible + managed (managed=false để node không chiếm chỗ bố cục)
        tfPasswordVisible.visibleProperty().bind(chkShow.selectedProperty());
        tfPasswordVisible.managedProperty().bind(chkShow.selectedProperty());
        pfPassword.visibleProperty().bind(chkShow.selectedProperty().not());
        pfPassword.managedProperty().bind(chkShow.selectedProperty().not());

        // Nút Đăng nhập chỉ bật khi cả 3 trường đều có dữ liệu
        btnLogin.disableProperty().bind(
                tfUsername.textProperty().isEmpty()
                        .or(pfPassword.textProperty().isEmpty())
                        .or(cbRole.valueProperty().isNull())
        );

        Platform.runLater(tfUsername::requestFocus);
    }

    @FXML
    private void handleLogin(ActionEvent e) {
        String user = tfUsername.getText().trim().toLowerCase();
        String pass = pfPassword.getText();
        String role = cbRole.getValue();

        if (failedCount >= 3) {
            lblError.setText("Sai quá 3 lần. Vui lòng khởi động lại ứng dụng.");
            return;
        }

        if (!user.matches("[a-z0-9_]{3,20}")) {
            lblError.setText("Username 3–20 ký tự, chỉ chữ thường/số/_");
            return;
        }

        String[] acc = ACCOUNTS.get(user);
        if (acc == null || !acc[0].equals(pass)) {
            failedCount++;
            int remaining = 3 - failedCount;
            if (remaining > 0) {
                lblError.setText("Sai username hoặc mật khẩu (còn %d lần)".formatted(remaining));
            } else {
                lblError.setText("Sai quá 3 lần. Vui lòng khởi động lại ứng dụng.");
            }
            pfPassword.clear();
            return;
        }

        if (!acc[1].equals(role)) {
            lblError.setText("Vai trò không đúng với tài khoản");
            return;
        }

        lblError.setText("");
        openWelcome(user, role, (Stage) btnLogin.getScene().getWindow());
    }

    private void openWelcome(String user, String role, Stage loginStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome-view.fxml"));
            Parent root = loader.load();

            // Truyền dữ liệu sang Controller thứ hai
            WelcomeController ctrl = loader.getController();
            ctrl.setUser(user, role);

            Stage stage = new Stage();
            stage.setTitle("Chào mừng");
            stage.setScene(new Scene(root, 360, 200));
            stage.show();

            loginStage.close();
        } catch (IOException ex) {
            AlertUtil.error("Không mở được màn hình chào mừng: " + ex.getMessage());
        }
    }

    @FXML
    private void handleForgot() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Quên mật khẩu");
        dialog.setHeaderText("Nhập email để lấy lại mật khẩu");
        dialog.setContentText("Email:");

        dialog.showAndWait().ifPresent(email -> {
            if (email.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
                AlertUtil.info("Đã gửi hướng dẫn tới " + email);
            } else {
                AlertUtil.warn("Email không hợp lệ");
            }
        });
    }
}