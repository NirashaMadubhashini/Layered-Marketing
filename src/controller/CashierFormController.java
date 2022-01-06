package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class CashierFormController {
    public AnchorPane CashierFormContext;
    public Label lblNewDate;
    public Label lblNewTime;


    public void initialize() throws IOException, SQLException, ClassNotFoundException {

        loadDateAndTime();

    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblNewDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblNewTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }


    public void AddCustomerOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddCustomerForm.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("GoldenFresh SuperMarket");
        stage.getIcons().add(new Image("assets/1.png"));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public void ManageCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) CashierFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageCustomerForm.fxml"))));
    }

    public void PlaceNewOrderOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) CashierFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml"))));
    }


    public void ManageCustomerOrderOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) CashierFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageOrderForm.fxml"))));
    }

    public void CashierLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) CashierFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
    }
}
