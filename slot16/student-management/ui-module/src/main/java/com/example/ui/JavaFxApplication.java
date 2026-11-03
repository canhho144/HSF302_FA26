package com.example.ui;

import com.example.StudentManagementApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        // TODO 4.3a: context = new SpringApplicationBuilder(StudentManagementApplication.class)
        //              .web(WebApplicationType.NONE).headless(false).run(...)
        context = new SpringApplicationBuilder(StudentManagementApplication.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage stage) throws Exception {
        // TODO 4.3b: FXMLLoader "/student-view.fxml" + loader.setControllerFactory(context::getBean)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/student-view.fxml"));
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();

        // TODO 4.3c: Scene 1000x600 + "/style.css"; title "Student Management System"; min 900x500; show
        Scene scene = new Scene(root, 1000, 600);

        var styleResource = getClass().getResource("/style.css");
        if (styleResource != null) {
            scene.getStylesheets().add(styleResource.toExternalForm());
        }

        stage.setTitle("Student Management System");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(500);
        stage.show();
    }

    @Override
    public void stop() {
        // TODO 4.3d: context.close(); Platform.exit();
        if (context != null) {
            context.close();
        }
        Platform.exit();
    }
}