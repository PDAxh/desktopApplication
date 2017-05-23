package com.testverktyg.eclipselink.view;

import com.testverktyg.eclipselink.service.user.UpdateUser;
import com.testverktyg.eclipselink.view.admin.AdminController;
import com.testverktyg.eclipselink.view.main.MainController;
import com.testverktyg.eclipselink.view.teacher.TeacherController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Jonas Johansson, Java2 on 2017-05-01.
 *
 */
public class MenuController {

    @FXML
    private GridPane editUserPane;
    @FXML
    private TableView userTable;

    @FXML
    private Label loggedInUser;


    private ObservableList<AdminController.User> data = FXCollections.observableArrayList();
    int selectedID;

    private Stage stage;
    @FXML private BorderPane borderPane;
    private int userId;

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
    private void removeUser() throws IOException{
        GridPane gridPane = FXMLLoader.load(getClass().getResource("admin/layout/removeUser.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("teacher/layout/testTestLayout.fxml"));
        BorderPane borderPane = loader.load();
        this.borderPane.setCenter(borderPane);
        TeacherController teacherController = loader.getController();
        teacherController.setUserId(getUserId());
    }
    @FXML
    private void showTestToStudent() throws IOException{
        BorderPane bp = FXMLLoader.load(getClass().getResource("student/layout/showTestToStudentRoot.fxml"));
        borderPane.setCenter(bp);
    }

    @FXML
    private void showTests() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("teacher/layout/teacherShowTestsToPublishLayout.fxml"));
        BorderPane borderPane = loader.load();
        this.borderPane.setCenter(borderPane);
        TeacherController teacherController = loader.getController();
        teacherController.setUserId(getUserId());
        teacherController.getTeacherTest();
    }

    @FXML
    private void getExitProgram(){
        System.exit(0);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @FXML
    private void getHelpMenueForTeacher() throws IOException{
        BorderPane bP = FXMLLoader.load(getClass().getResource("main/layout/helpForTeacher.fxml"));
        borderPane.setCenter(bP);
    }

    @FXML
    private void getHelpMenueForStudent() throws IOException{
        BorderPane bP = FXMLLoader.load(getClass().getResource("main/layout/helpForStudent.fxml"));
        borderPane.setCenter(bP);
    }

    @FXML
    private void getHelpMenueForAdmin() throws IOException{
        BorderPane bP = FXMLLoader.load(getClass().getResource("main/layout/helpForAdmin.fxml"));
        borderPane.setCenter(bP);
    }

    @FXML
    private void getAboutUs() throws IOException{
        BorderPane bP = FXMLLoader.load(getClass().getResource("main/layout/aboutUs.fxml"));
        borderPane.setCenter(bP);
    }


    @FXML
    private void updateAccount(){

        //int selectedUserIndex = 2;
        //selectedID = data.get(selectedUserIndex).getID();

        Label fnameLabel = new Label();
        Label lnameLabel = new Label();
        Label emailLabel = new Label();
        Label newPasswordLabel = new Label();
        Label verifyPasswordLabel = new Label();
        TextField fnameField = new TextField();
        TextField lnameField = new TextField();
        TextField emailField = new TextField();
        TextField newPasswordField = new TextField();
        TextField verifyPasswordField = new TextField();
        Label updateUserMessageLabel = new Label();
        Button updateUserButton = new Button();

        fnameLabel.setText("Förnamn:");
        lnameLabel.setText("Efternamn");
        emailLabel.setText("Email:");
        newPasswordLabel.setText("Nytt lösenord");
        verifyPasswordLabel.setText("Repetera lösenord");
        updateUserButton.setText("Uppdatera");
        updateUserButton.setOnAction(event -> {
            if (newPasswordField.getText().equals(verifyPasswordField.getText())) {
                UpdateUser uu = new UpdateUser();
                uu.setNewfirstname(fnameField.getText());
                uu.setNewLastname(lnameField.getText());
                uu.setNewEmail(emailField.getText());
                uu.setNewPassword(newPasswordField.getText());
                uu.UpdateUser();
                updateUserMessageLabel.setText("Användaren har ändrats");
            } else {
                updateUserMessageLabel.setText("Lösenord stämmer inte överens");
            }
        });

       /* editUserPane.add(fnameLabel, 0, 0);
        editUserPane.add(fnameField, 0, 1);
        editUserPane.add(lnameLabel, 0, 2);
        editUserPane.add(lnameField, 0, 3);
        editUserPane.add(emailLabel, 0, 4);
        editUserPane.add(emailField, 0, 5);
        editUserPane.add(newPasswordLabel, 0, 6);
        editUserPane.add(newPasswordField, 0, 7);
        editUserPane.add(verifyPasswordLabel, 0, 8);
        editUserPane.add(verifyPasswordField, 0, 9);
        editUserPane.add(updateUserMessageLabel, 0, 10);
        editUserPane.add(updateUserButton, 0, 11);

        */

        //fnameField.setText(data.get(selectedUserIndex).fname);
        //lnameField.setText(data.get(selectedUserIndex).lname);
        //emailField.setText(data.get(selectedUserIndex).email);

    }

    @FXML
    public void activeLoggedInUser(String nameOnActiveUser){
        loggedInUser.setText("Inloggad som: " + nameOnActiveUser);
    }

}
