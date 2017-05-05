package com.testverktyg.eclipselink.view.admin;

import com.testverktyg.eclipselink.service.user.loginUser;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    @FXML private ComboBox classList;
    @FXML private Button removeClassButton;
    @FXML private ComboBox editUserUsertypeList;
    @FXML private Label editUserClassLabel;
    @FXML private ComboBox editUserClassList;
    @FXML private Label editUserUserLabel;
    @FXML private ComboBox editUserUserList;
    @FXML private Button editUserRemoveButton;
    @FXML private Button editUserEditButton;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;


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
    @FXML
    private void checkUserTypeToEdit(){
        if(getEditUserUserList().getValue().equals("Välj en användare")) {
            System.out.println("User not selected");
            if (getEditUserUsertypeList().getValue().equals("Välj en typ")) {
                System.out.println("Type not selected");
                editUserClassLabel.setTextFill(Color.web("#d3d3d3"));
                getEditUserClassList().setDisable(true);
                editUserUserLabel.setTextFill(Color.web("#d3d3d3"));
                getEditUserUserList().setDisable(true);
                editUserClassList.setValue("Välj en klass");
                editUserUserList.setValue("Välj en användare");
            } else if (getEditUserUsertypeList().getValue().equals("Student")) {
                System.out.println("Type is student");
                editUserClassLabel.setTextFill(Color.web("#000000"));
                getEditUserClassList().setDisable(false);
                editUserUserLabel.setTextFill(Color.web("#000000"));
                getEditUserUserList().setDisable(false);
                editUserClassLabel.setTextFill(Color.web("#000000"));
                getEditUserClassList().setDisable(false);
            } else {
                editUserUserLabel.setTextFill(Color.web("#000000"));
                getEditUserUserList().setDisable(false);
                editUserClassLabel.setTextFill(Color.web("#d3d3d3"));
                getEditUserClassList().setDisable(true);
            }
        }else{
            getEditUserEditButton().setDisable(false);
            getEditUserRemoveButton().setDisable(false);
        }
    }
    @FXML
    private void checkClassChoiceToRemove(){
        if(getClassList().getValue().equals("Välj en klass")){
            removeClassButton.setDisable(true);
        }else{
            removeClassButton.setDisable(false);
        }
    }


    @FXML
    private void createNewUser(){

        //CreateUser newUser = new CreateUser();
        
    }

    //Getters for createUser
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
    //Getters for editUser
    private ComboBox getEditUserUsertypeList(){
        return editUserUsertypeList;
    }
    private ComboBox getEditUserClassList(){
        return editUserClassList;
    }
    private ComboBox getEditUserUserList() {
        return editUserUserList;
    }
    private Button getEditUserRemoveButton() {
        return editUserRemoveButton;
    }
    private Button getEditUserEditButton() {
        return editUserEditButton;
    }
    private Label getEditUserClassLabel(){
        return editUserClassLabel;
    }


    //Getters for add/remove Class
    private ComboBox getClassList() {
        return classList;
    }
    private Button getRemoveClassButton() {
        return removeClassButton;
    }
    public TextField getFirstName() {
        return firstName;
    }

    public void setFirstName(TextField firstName) {
        this.firstName = firstName;
    }

    public TextField getLastName() {
        return lastName;
    }

    public void setLastName(TextField lastName) {
        this.lastName = lastName;
    }

    public TextField getEmail() {
        return email;
    }

    public void setEmail(TextField email) {
        this.email = email;
    }
}
