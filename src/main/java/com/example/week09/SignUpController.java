package com.example.week09;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller class for the Registration Application.
 *
 * Within the GUI, each data field is validated after the focus is swapped. If the data doesn't match the regular expression,
 * the user is forced to enter valid data to proceed with the registration process. Once all data fields have been verified,
 * the user is able to register.
 *
 * @author guzmjo
 */
public class SignUpController extends App{
    @FXML
    private TextField fNameField, LNameField, emailField, dobField, zipField;
    @FXML
    private Label fNameErr, LNameErr, emailErr, dobErr, zipErr;
    @FXML
    private Label fNameCheck, LNameCheck, emailCheck, dobCheck, zipCheck;
    @FXML
    private Button createBtn;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private boolean flag;


    @FXML
    public void initialize() {

        dataVerification(fNameField,fNameErr, fNameCheck,"[A-Za-z]{2,25}");
        dataVerification(LNameField, LNameErr, LNameCheck, "[A-Za-z]{2,25}");
        dataVerification(emailField, emailErr, emailCheck, "[a-z]+@farmingdale.edu");
        dataVerification(dobField, dobErr, dobCheck,"\\d{2}/\\d{2}/\\d{4}");
        dataVerification(zipField, zipErr, zipCheck,"\\d{5}");

    }

    /**
     * Method verifies the data within the specified text field. Depending on whether the data matches the regular
     * expression passed as a parameter, an error message will appear and the user is forced to enter data
     * that matches the regular expression or the user is able to traverse to the following text field to continue
     * the registration process.
     *
     * @param txtField where the data will be entered
     * @param error error message to be displayed
     * @param check check mark that appears when data is valid
     * @param regEx regular expression
     */
    private void dataVerification(TextField txtField, Label error, Label check, String regEx) {
        txtField.setOnKeyPressed(event -> {

            if (event.getCode() != KeyCode.TAB && flag) {
                txtField.setStyle("-fx-border-color: none;");
                flag = false;
            }

        });

        txtField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {

        if (!newValue) {
                if (txtField.getText().matches(regEx)) {
                    txtField.setEditable(false);
                    txtField.setBorder(null);
                    error.setVisible(false);
                    check.setVisible(true);
                    validateData();

                } else {

                    txtField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                    txtField.setVisible(true);
                    txtField.requestFocus();
                    error.setVisible(true);
                    flag = true;
                }

            }
        });
    }

    /**
     * Method will enable the button to register only when all fields of data have been validated
     */
    @FXML
    private void validateData() {
        //use isVisible() b/c check will only appear after data has been validated
        boolean isFirstNameValid = fNameCheck.isVisible();
        boolean isLastNameValid = LNameCheck.isVisible();
        boolean isEmailValid = emailCheck.isVisible();
        boolean isDobValid = dobCheck.isVisible();
        boolean isZipValid = zipCheck.isVisible();

        //if all data fields have been validated, button will be enabled
        createBtn.setDisable(!(isFirstNameValid && isLastNameValid && isEmailValid && isDobValid && isZipValid));
    }

    /**
     * Swaps to another fxml file
     * @param actionEvent button click
     * @throws IOException
     */
    @FXML
    private void swapScene(ActionEvent actionEvent) throws IOException {
        System.out.println("CLICKED");
        root = FXMLLoader.load(getClass().getResource("Congrats.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}