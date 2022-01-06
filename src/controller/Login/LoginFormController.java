package controller.Login;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;

public class LoginFormController {

    public AnchorPane loginFormContext;
    public JFXTextField txtUserName;
    public JFXTextField txtPassWord;
    public Button btnAdmin;

    public void openAdminFormOnAction(ActionEvent actionEvent) {

        if (txtUserName.getText().equalsIgnoreCase("Admin") && txtPassWord.getText().equals("1234")) {
            Stage window = (Stage) loginFormContext.getScene().getWindow();
            try {
                window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdministratorForm.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (txtUserName.getText().equalsIgnoreCase("User") && txtPassWord.getText().equals("123")) {
            Stage window = (Stage) loginFormContext.getScene().getWindow();
            try {
                window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CashierForm.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Incorrect User Name, Password. Try again..", ButtonType.CLOSE).show();
        }
    }

    public void moveToPassword(ActionEvent actionEvent) {
        txtPassWord.requestFocus();
    }

}
