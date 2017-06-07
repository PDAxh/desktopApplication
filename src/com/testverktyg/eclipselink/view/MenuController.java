package com.testverktyg.eclipselink.view;

import com.testverktyg.eclipselink.service.user.UpdateUser;
import com.testverktyg.eclipselink.view.admin.AdminController;
import com.testverktyg.eclipselink.view.main.MainController;
import com.testverktyg.eclipselink.view.student.StudentController;
import com.testverktyg.eclipselink.view.teacher.TeacherController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Created by Jonas Johansson, Java2 on 2017-05-01.
 *
 */
public class MenuController {

    @FXML private BorderPane borderPane;
    @FXML private Label loggedInUser;
    @FXML private Text welcomeUser;
    private int activeLoggedInUserId;
    private int userId;
    private Stage stage;

    private Stage getStage() {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/layout/removeClass.fxml"));
        GridPane gridPane = loader.load();
        this.borderPane.setCenter(gridPane);
        AdminController adminController = loader.getController();
        adminController.fillClasses();
    }

    @FXML
    private void showTestsToAdmin() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/layout/showTests.fxml"));
        BorderPane borderPane = loader.load();
        this.borderPane.setCenter(borderPane);
        AdminController adminController = loader.getController();
        adminController.getAllTestsForAdminList();
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
    private void showTestsToStudentToDo() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("student/layout/showTestToStudentRoot.fxml"));
        BorderPane borderPane = loader.load();
        this.borderPane.setCenter(borderPane);
        StudentController studentController= loader.getController();
        studentController.setUserId(getUserId());
        studentController.getStudentTests();
    }

    @FXML
    private void showTests() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("teacher/layout/teacherShowTestsToPublishLayout.fxml"));
        BorderPane borderPane = loader.load();
        this.borderPane.setCenter(borderPane);
        TeacherController teacherController = loader.getController();
        teacherController.setUserId(getUserId());
        teacherController.getTeacherTestToEdit();
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
    private void getResultsAdmin() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/layout/resultAdminLayout.fxml"));
        BorderPane borderPane = loader.load();
        this.borderPane.setCenter(borderPane);
        AdminController adminController = loader.getController();
        adminController.getTestsToShowResults();
    }

    @FXML
    private void getResultsTeacher() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("teacher/layout/resultTeacherLayout.fxml"));
        BorderPane borderPane = loader.load();
        this.borderPane.setCenter(borderPane);
        TeacherController teacherController = loader.getController();
        teacherController.setUserId(getUserId());
        teacherController.getTeacherTestToShowResult();
    }

    @FXML
    private void updateAccount(){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        Label newPasswordLabel = new Label();
        Label verifyPasswordLabel = new Label();
        TextField newPasswordField = new TextField();
        TextField verifyPasswordField = new TextField();
        Label updateUserMessageLabel = new Label();
        Button updateUserButton = new Button();
        updateUserButton.setStyle("-fx-background-color: #EE7202; -fx-text-fill: white");

        newPasswordLabel.setText("Nytt lösenord");
        verifyPasswordLabel.setText("Repetera lösenord");
        updateUserButton.setText("Uppdatera");
        updateUserButton.setOnAction(event -> {
            if (newPasswordField.getText().isEmpty()){
                updateUserMessageLabel.setText("Minst ett av fälten saknar innehåll");
            }else if (!newPasswordField.getText().equals(verifyPasswordField.getText())){
                updateUserMessageLabel.setText("Lösenorden stämmer inte överrens");
            }else if (newPasswordField.getText().equals(verifyPasswordField.getText())) {
                UpdateUser uu = new UpdateUser();
                uu.setUserId(activeLoggedInUserId);
                uu.setNewPassword(newPasswordField.getText());
                uu.UpdateOnlyPassword();
                updateUserMessageLabel.setText("Lösenordet har ändrats");
                verifyPasswordField.clear();
                newPasswordField.clear();
            }
        });

        gridPane.add(newPasswordLabel, 0, 1);
        gridPane.add(newPasswordField, 0, 2);
        gridPane.add(verifyPasswordLabel, 0, 3);
        gridPane.add(verifyPasswordField, 0, 4);
        gridPane.add(updateUserButton, 0, 5);
        gridPane.add(updateUserMessageLabel, 0, 6);

        this.borderPane.setCenter(gridPane);
    }

    @FXML
    public void activeLoggedInUser(String nameOnActiveUser, int loggedInUserId){
        activeLoggedInUserId = loggedInUserId;
        loggedInUser.setText("Inloggad som: " + nameOnActiveUser+"       ");
        welcomeUser.setText("Välkommen " + nameOnActiveUser);
    }
}
