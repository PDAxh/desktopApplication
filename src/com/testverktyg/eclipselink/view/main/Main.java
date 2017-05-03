package com.testverktyg.eclipselink.view.main;

import com.testverktyg.eclipselink.service.user.CreateUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Jonas Johansson, Java2 on 2017-05-01.
 * Startar upp programmet.
 */
public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Quiz");
        MainController mainController = loader.getController();
        mainController.setStage(primaryStage);
        primaryStage.show();

        CreateUser user = new CreateUser("admin","admin", "admin");
        CreateUser student = new CreateUser("student", "student", "student");
        CreateUser teacher = new CreateUser("teacher", "teacher", "teacher");
    }
}
