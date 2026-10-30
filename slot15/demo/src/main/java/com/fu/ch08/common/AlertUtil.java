package com.fu.ch08.common;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertUtil {

    /**
     * Hiển thị hộp thoại xác nhận Yes/No (OK/Cancel)
     * @param message Nội dung thông báo
     * @return true nếu người dùng chọn OK, false nếu Cancel hoặc đóng cửa sổ
     */
    public static boolean confirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}