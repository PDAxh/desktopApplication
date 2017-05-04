package com.testverktyg.eclipselink.view;

import com.testverktyg.eclipselink.view.main.MainController;
import com.testverktyg.eclipselink.view.teacher.TeacherController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Jonas Johansson, Java2 on 2017-05-01.
 *
 */
public class MenuController {

    private Stage stage;
    @FXML private BorderPane borderPane;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void logout() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main/layout/login.fxml"));
        GridPane gridPane = loader.load();
        Scene scene = new Scene(gridPane);
        getStage().setScene(scene);
        MainController mainController = loader.getController();
        mainController.setStage(getStage());
    }

    @FXML
    private void addUser() throws IOException{
        GridPane gridPane = FXMLLoader.load(getClass().getResource("admin/layout/addUser.fxml"));
        borderPane.setCenter(gridPane);
    }
    @FXML
    private void addClass() throws IOException{
        GridPane gridPane = FXMLLoader.load(getClass().getResource("admin/layout/addClass.fxml"));
        borderPane.setCenter(gridPane);
    }
    @FXML
    private void removeClass() throws IOException{
        GridPane gridPane = FXMLLoader.load(getClass().getResource("admin/layout/removeClass.fxml"));
        borderPane.setCenter(gridPane);
    }

    @FXML
    private void createNewTest() throws IOException{
        BorderPane bp = FXMLLoader.load(getClass().getResource("teacher/layout/createNewTestRoot.fxml"));
        borderPane.setCenter(bp);
    }

    @FXML
    private void getExitProgram(){
        System.exit(0);
    }

}
