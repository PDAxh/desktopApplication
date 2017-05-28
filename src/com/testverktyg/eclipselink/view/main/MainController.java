package com.testverktyg.eclipselink.view.main;

import com.testverktyg.eclipselink.service.user.ReadUser;
import com.testverktyg.eclipselink.view.MenuController;
import com.testverktyg.eclipselink.view.admin.AdminController;
import com.testverktyg.eclipselink.view.student.StudentController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Jonas Johansson, Java2 on 2017-05-01.
 *
 */
public class MainController {

    private Stage stage;
    @FXML private Text loginFailed;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordTextField;



    private Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private String getUsernameTextField() {
        return usernameTextField.getText();
    }

    private void setUsernameTextField(String usernameTextField) {
        this.usernameTextField.setText(usernameTextField);
    }

    private String getPasswordTextField() {
        return passwordTextField.getText();
    }

    private void setPasswordTextField(String passwordTextField) {
        this.passwordTextField.setText(passwordTextField);
    }

    private void setLoginFailed() {
        this.loginFailed.setText("Try again!");
    }

    private void setNewUserLayout(String layout, int userId) throws IOException{
        ReadUser ru = new ReadUser();
        ru.setLoggedInUser(userId);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(layout));
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        getStage().setScene(scene);
        MenuController menuController = loader.getController();
        menuController.setStage(getStage());
        menuController.setUserId(userId);
        setUsernameTextField("");
        setPasswordTextField("");
        menuController.activeLoggedInUser(ru.getLoggedInUser().get(0).getFirstname() + " " + ru.getLoggedInUser().get(0).getLastname(), userId);
        System.out.println(ru.getLoggedInUser().get(0).getFirstname());
    }

    @FXML
    private void logInButton() throws IOException{
        ReadUser readUser = new ReadUser(getUsernameTextField(), getPasswordTextField());
        int userid = readUser.getUserId();

        if(readUser.getLoginStatus()){
            if(readUser.getGroup().equals("admin")){
               setNewUserLayout("../admin/layout/adminMainLayout.fxml", userid);
            }
            else if(readUser.getGroup().equals("student")){
                setNewUserLayout("../student/layout/studentMainLayout.fxml", userid);
            }
            else if(readUser.getGroup().equals("teacher")){
                setNewUserLayout("../teacher/layout/teacherMainLayout.fxml", userid);
            }
        }
        else{
            setLoginFailed();
        }
    }
}
