package com.testverktyg.eclipselink.view.main;

import com.testverktyg.eclipselink.service.user.loginUser;
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
        primaryStage.setTitle("NEWTON™ Testverktyg 0.5");
        MainController mainController = loader.getController();
        mainController.setStage(primaryStage);
        primaryStage.show();

       /* loginUser user = new loginUser("admin","admin", "admin");
        loginUser student = new loginUser("student", "student", "student");
        loginUser teacher = new loginUser("teacher", "teacher", "teacher");*/
    }
}
