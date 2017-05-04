package com.testverktyg.eclipselink.view.admin;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * Created by Grodfan on 2017-05-01.
 * Edited by Andreas on 2017-05-04
 */
public class AdminController {

    @FXML private ComboBox userType;
    @FXML private ComboBox studentClass;
    @FXML private Label studentClassLabel;
    @FXML private PasswordField password;
    @FXML private PasswordField passwordRepeat;
    @FXML private Label passwordMessageLabel;


    @FXML
    private void setStudentClassOption(){
        if(getUserType().getValue().equals("Student")){
            getStudentClass().setDisable(false);
            studentClassLabel.setTextFill(Color.web("#000000"));
        }
        else{
            getStudentClass().setDisable(true);
            studentClassLabel.setTextFill(Color.web("#d3d3d3"));
        }
    }
    @FXML
    private void comparePassword(){
        if(getPassword().getText().equals(getPasswordRepeat().getText())) {
            passwordMessageLabel.setText("");
        }else{
            passwordMessageLabel.setText("Lösenorden är inte samma.");
            getPassword().setText("");
            getPasswordRepeat().setText("");
            password.requestFocus();
        }
    }

    private ComboBox getUserType() {
        return userType;
    }
    private ComboBox getStudentClass() {
        return studentClass;
    }
    private PasswordField getPassword() {
        return password;
    }
    private PasswordField getPasswordRepeat() {
        return passwordRepeat;
    }
}
