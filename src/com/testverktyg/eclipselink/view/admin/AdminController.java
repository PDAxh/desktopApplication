package com.testverktyg.eclipselink.view.admin;


import com.testverktyg.eclipselink.service.Class.CreateClass;
import com.testverktyg.eclipselink.service.Class.DeleteClass;
import com.testverktyg.eclipselink.service.Class.ReadClass;
import com.testverktyg.eclipselink.service.user.CreateUser;
import com.testverktyg.eclipselink.service.user.DeleteUser;
import com.testverktyg.eclipselink.service.user.ReadUser;
import com.testverktyg.eclipselink.service.user.UpdateUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Created by Andreas
 */
public class AdminController {

    @FXML
    private ComboBox<String> userType;
    @FXML
    private ComboBox<String> studentClass;
    @FXML
    private Label studentClassLabel;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordRepeat;
    @FXML
    private Label passwordMessageLabel;
    @FXML
    private ComboBox<String> classList;
    @FXML
    private Button removeClassButton;
    @FXML
    private ComboBox editUserUsertypeList;
    @FXML
    private Label editUserClassLabel;
    @FXML
    private ComboBox<String> editUserClassList;
    @FXML
    private Label editUserUserLabel;
    @FXML
    private ComboBox<String> editUserUserList;
    @FXML
    private Button editUserRemoveButton;
    @FXML
    private Button editUserEditButton;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField studentClassName;
    @FXML
    private Label addClassMessageLabel;
    @FXML
    private Label removeClassMessageLabel;
    @FXML
    private TableView userTable;
    @FXML
    private ComboBox userTypeList;
    @FXML
    private TableColumn IDCol;
    @FXML
    private TableColumn fnameCol;
    @FXML
    private TableColumn lnameCol;
    @FXML
    private TableColumn emailCol;
    @FXML
    private TableColumn klassCol;
    @FXML
    private TableColumn typeCol;
    @FXML
    private TextField searchField;
    @FXML
    private GridPane editUserPane;
    @FXML
    private GridPane newUserPane;


    private ObservableList<User> data = FXCollections.observableArrayList();
    int selectedID;

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

    public void addSearchListener() {
        FilteredList<User> filteredData = new FilteredList<>(data, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (user.getFname().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getLname().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getKlass().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(userTable.comparatorProperty());
        userTable.setItems(sortedData);
    }

    @FXML
    private void setStudentClassOption() {
        if (getUserType().getValue().equals("student")) {
            getStudentClass().setDisable(false);
            studentClassLabel.setTextFill(Color.web("#000000"));
            fillClasses(studentClass);
        } else {
            getStudentClass().setDisable(true);
            studentClassLabel.setTextFill(Color.web("#d3d3d3"));
        }
    }

    @FXML
    private void comparePassword() {
        if (getPassword().getText().equals(getPasswordRepeat().getText())) {
            passwordMessageLabel.setText("");
        } else {
            passwordMessageLabel.setText("Lösenorden stämmer inte överens. Var god försök igen.");
            getPassword().setText("");
            getPasswordRepeat().setText("");
            password.requestFocus();
        }
    }

    @FXML
    public void fillClassList() {
        //TEMP METHOD
        fillClasses(classList);
    }

    @FXML
    public void fillClasses(ComboBox cb) {
        cb.getItems().clear();
        cb.getItems().add("Välj en klass");
        cb.setValue("Välj en klass");
        ReadClass newReadClass = new ReadClass();
        newReadClass.readAllClasses();
        for (int i = 0; i < newReadClass.getClassNameList().size(); i++) {
            System.out.println(newReadClass.getClassNameList().get(i));
            cb.getItems().add(String.valueOf(newReadClass.getClassNameList().get(i)));
        }
    }

    @FXML
    private void showUsers() {
        System.out.println("showUser Started");
        String userType = String.valueOf(userTypeList.getValue());
        fillUserList(userType);
    }

    @FXML
    private void fillUserList(String type) {
        ReadUser newReadUser = new ReadUser();
        if (type.equals("Student")) {
            //addSearchListener();
            newReadUser.readOnlyStudents();
            data.clear();
            for (int i = 0; i < newReadUser.getStudentList().size(); i++) {
                int ID = newReadUser.getStudentList().get(i).getUserId();
                String fname = newReadUser.getStudentList().get(i).getFirstname();
                String lname = newReadUser.getStudentList().get(i).getLastname();
                String email = newReadUser.getStudentList().get(i).getEmail();
                String klass = newReadUser.getStudentList().get(i).getKlass();
                String usertype = newReadUser.getStudentList().get(i).getTypeOfUser();
                User newUser = new User();
                newUser.createNewUser(ID, fname, lname, email, klass, usertype);
                data.add(newUser);
            }
            userTable.setItems(data);
            userTable.getColumns().setAll(IDCol, fnameCol, lnameCol, emailCol, klassCol, typeCol);
        } else if (type.equals("Teacher")) {
            newReadUser.readOnlyTeacher();
            data.clear();
            for (int i = 0; i < newReadUser.getTeacherList().size(); i++) {
                int ID = newReadUser.getTeacherList().get(i).getUserId();
                String fname = newReadUser.getTeacherList().get(i).getFirstname();
                String lname = newReadUser.getTeacherList().get(i).getLastname();
                String email = newReadUser.getTeacherList().get(i).getEmail();
                String klass = newReadUser.getTeacherList().get(i).getKlass();
                String usertype = newReadUser.getTeacherList().get(i).getTypeOfUser();
                User newUser = new User();
                newUser.createNewUser(ID, fname, lname, email, klass, usertype);
                data.add(newUser);
            }
            userTable.setItems(data);
            userTable.getColumns().setAll(IDCol, fnameCol, lnameCol, emailCol, klassCol, typeCol);
        } else if (type.equals("Admin")) {
            newReadUser.readOnlyAdmin();
            data.clear();
            for (int i = 0; i < newReadUser.getAdminList().size(); i++) {
                int ID = newReadUser.getAdminList().get(i).getUserId();
                String fname = newReadUser.getAdminList().get(i).getFirstname();
                String lname = newReadUser.getAdminList().get(i).getLastname();
                String email = newReadUser.getAdminList().get(i).getEmail();
                String klass = newReadUser.getAdminList().get(i).getKlass();
                String usertype = newReadUser.getAdminList().get(i).getTypeOfUser();
                User newUser = new User();
                newUser.createNewUser(ID, fname, lname, email, klass, usertype);
                data.add(newUser);
            }
            userTable.setItems(data);
            userTable.getColumns().setAll(IDCol, fnameCol, lnameCol, emailCol, klassCol, typeCol);
        }
    }

    @FXML
    private void checkClassChoiceToRemove() {
        if (getClassList().getValue().equals("Välj en klass")) {
            removeClassButton.setDisable(true);
        } else {
            removeClassButton.setDisable(false);
        }
    }

    @FXML
    private void removeSelectedClass() {
        DeleteClass dc = new DeleteClass();
        String classToDelete = classList.getValue();
        dc.deleteClass(classToDelete);
        removeClassMessageLabel.setText(classToDelete + " är borttagen");
        fillClasses(classList);
    }

    @FXML
    private void createNewUser() {
        if (getPassword().getText().equals(getPasswordRepeat().getText())) {
            passwordMessageLabel.setText("");
            String fname = getFirstName().getText();
            System.out.println(fname);
            String lname = getLastName().getText();
            System.out.println(lname);
            String emailString = getEmail().getText();
            System.out.println(email);
            String Klass = getStudentClass().getValue().toString();
            System.out.println(Klass);
            String passwordString = getPassword().getText();
            System.out.println(password);
            String userType = getUserType().getValue().toString();
            System.out.println(userType);
            CreateUser newUser = new CreateUser(fname, lname, passwordString, emailString, Klass, userType);
            passwordMessageLabel.setText("Användare för " + fname + " " + lname + " " + " är tillagd");
            firstName.setText("");
            lastName.setText("");
            email.setText("");
            passwordRepeat.setText("");
            password.setText("");
        } else {
            passwordMessageLabel.setText("Lösenorden är inte samma.");
            getPassword().setText("");
            getPasswordRepeat().setText("");
            password.requestFocus();
        }
    }

    @FXML
    private void addClass() {
        String className = studentClassName.getText();
        boolean matchFound = false;
        ReadClass newReadClass = new ReadClass();
        newReadClass.readAllClasses();
        if (newReadClass.getClassNameList().size() == 0) {
            System.out.println("Class list is empty. 0 expcetion");
            CreateClass addClass = new CreateClass();
            addClass.CreateClass(className);
            addClassMessageLabel.setText("Klass är skapad");
            studentClassName.setText("");
        } else {
            for (int i = 0; i < newReadClass.getClassNameList().size(); i++) {
                System.out.println("Comparing: " + className + " and " + String.valueOf(newReadClass.getClassNameList().get(i)));
                if (className.equals(String.valueOf(newReadClass.getClassNameList().get(i)))) {
                    matchFound = true;
                } else {
                }
            }
            if (matchFound == true) {
                System.out.println("Match");
                addClassMessageLabel.setText(className + " finns redan");
                studentClassName.setText("");
            } else {
                CreateClass addClass = new CreateClass();
                addClass.CreateClass(className);
                addClassMessageLabel.setText("Klass är skapad");
                studentClassName.setText("");
            }
        }
    }

    @FXML
    private void deleteSelectedUser() {

        int selectedUserIndex = userTable.getSelectionModel().getFocusedIndex();
        int selectedID = data.get(selectedUserIndex).getID();
        DeleteUser du = new DeleteUser();
        du.setUserId(selectedID);
        du.DeleteUser();
        String userType = String.valueOf(userTypeList.getValue());
        fillUserList(userType);
    }

    @FXML
    private void editSelectedUser() {
        int selectedUserIndex = userTable.getSelectionModel().getFocusedIndex();
        selectedID = data.get(selectedUserIndex).getID();
        editUserPane.getChildren().clear();

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

        editUserPane.add(fnameLabel, 0, 0);
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

        fnameField.setText(data.get(selectedUserIndex).fname);
        lnameField.setText(data.get(selectedUserIndex).lname);
        emailField.setText(data.get(selectedUserIndex).email);

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
    private ComboBox getEditUserUsertypeList() {
        return editUserUsertypeList;
    }

    private ComboBox getEditUserClassList() {
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

    private Label getEditUserClassLabel() {
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

    public class User {
        int ID;
        String fname;
        String lname;
        String email;
        String klass;
        String type;

        public void createNewUser(int inID, String infname, String inlname, String inemail, String inklass, String intype) {
            ID = inID;
            fname = infname;
            lname = inlname;
            email = inemail;
            klass = inklass;
            type = intype;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getKlass() {
            return klass;
        }

        public void setKlass(String klass) {
            this.klass = klass;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
