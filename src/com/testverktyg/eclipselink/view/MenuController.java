package com.testverktyg.eclipselink.view;

import com.testverktyg.eclipselink.service.user.UpdateUser;
import com.testverktyg.eclipselink.view.admin.AdminController;
import com.testverktyg.eclipselink.view.main.MainController;
import com.testverktyg.eclipselink.view.student.StudentController;
import com.testverktyg.eclipselink.view.teacher.TeacherController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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

    private int activeLoggedInUserId;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("student/layout/showStudentTests.fxml"));
        BorderPane borderPane = loader.load();
        this.borderPane.setCenter(borderPane);
        StudentController studentController= loader.getController();
        studentController.setUserId(getUserId());
        studentController.getStudentTests();
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
    private void getResults() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main/layout/resultsForAdminAndTeacherLayout.fxml"));
        BorderPane borderPane = loader.load();
        this.borderPane.setCenter(borderPane);
        AdminController adminController = loader.getController();
        adminController.getTestsToShowResults();
    }

    @FXML
    private void updateAccount(){

        editUserPane.getChildren().clear();

        //Label emailLabel = new Label();
        Label newPasswordLabel = new Label();
        Label verifyPasswordLabel = new Label();
        //TextField emailField = new TextField();
        TextField newPasswordField = new TextField();
        TextField verifyPasswordField = new TextField();
        Label updateUserMessageLabel = new Label();
        Button updateUserButton = new Button();

        //emailLabel.setText("Email:");
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
                System.out.println(uu.getUserId());
                //uu.setNewEmail(emailField.getText());
                uu.setNewPassword(newPasswordField.getText());
                uu.UpdateOnlyPassword();
                updateUserMessageLabel.setText("Lösenordet har ändrats");
                verifyPasswordField.clear();
                newPasswordField.clear();
            }
        });

        //editUserPane.add(emailLabel, 0, 4);
        //editUserPane.add(emailField, 0, 5);
        editUserPane.add(newPasswordLabel, 0, 1);
        editUserPane.add(newPasswordField, 0, 2);
        editUserPane.add(verifyPasswordLabel, 0, 3);
        editUserPane.add(verifyPasswordField, 0, 4);
        editUserPane.add(updateUserButton, 0, 5);
        editUserPane.add(updateUserMessageLabel, 0, 6);

    }

    @FXML
    public void activeLoggedInUser(String nameOnActiveUser, int loggedInUserId){
        activeLoggedInUserId = loggedInUserId;
        loggedInUser.setText("Inloggad som: " + nameOnActiveUser);
    }

}
