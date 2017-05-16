package com.testverktyg.eclipselink.view.main;

import com.testverktyg.eclipselink.service.user.ReadUser;
import com.testverktyg.eclipselink.view.MenuController;
import com.testverktyg.eclipselink.view.admin.AdminController;
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

    private void setNewUserLayout(String layout) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(layout));
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        getStage().setScene(scene);
        MenuController menuController = loader.getController();
        menuController.setStage(getStage());
        setUsernameTextField("");
        setPasswordTextField("");
    }

    @FXML
    private void logInButton() throws IOException{
        ReadUser readUser = new ReadUser(getUsernameTextField(), getPasswordTextField());

        if(readUser.getLoginStatus()){
            if(readUser.getGroup().equals("admin")){
               setNewUserLayout("../admin/layout/adminMainLayout.fxml");
            }
            else if(readUser.getGroup().equals("student")){
                setNewUserLayout("../student/layout/studentMainLayout.fxml");
            }
            else if(readUser.getGroup().equals("teacher")){
                setNewUserLayout("../teacher/layout/teacherMainLayout.fxml");
            }
        }
        else{
            setLoginFailed();
        }
    }
}
