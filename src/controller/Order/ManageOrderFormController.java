package controller.Order;

import bo.BoFactory;
import bo.custom.OrderBO;
import dto.CustomerDTO;
import dto.CustomerOrderDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.CustomerOrderDetailTm;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class ManageOrderFormController {
    private final OrderBO orderBO = (OrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDER);

    public AnchorPane ManageOrderFormContext;
    public TableView<CustomerOrderDetailTm> tblManageOrders;
    public TableColumn colOrderCustomerName;
    public TableColumn colOrderOrderID;
    public TableColumn colOrderItemCode;
    public TableColumn colOrderDescription;
    public TableColumn colOrderPackSize;
    public TableColumn colOrderQty;
    public TableColumn colOrderUnitPrice;
    public TableColumn colOrderDate;
    public TableColumn colOrderTime;


    public void initialize() {
        colOrderCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOrderOrderID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colOrderDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colOrderPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colOrderQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colOrderUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrderTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        tblManageOrders.getColumns().setAll(colOrderCustomerName, colOrderOrderID, colOrderItemCode,
                colOrderDescription, colOrderPackSize, colOrderQty, colOrderUnitPrice,
                colOrderDate, colOrderTime);

        loadAllOrders();

    }


    private void loadAllOrders() {
        tblManageOrders.getItems().clear();
        try {
            ArrayList<CustomerOrderDTO> allCustomers = orderBO.getAllCustomerDetailHistory();
            for (CustomerOrderDTO temp : allCustomers) {

                tblManageOrders.getItems().add(new CustomerOrderDetailTm(temp.getName(), temp.getOrderId(), temp.getItemCode(),
                        temp.getDescription(), temp.getPackSize(), temp.getQty(), temp.getPrice(),
                        temp.getDate(), temp.getTime()));

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        tblManageOrders.getItems();


    }


    public void ManageOrderLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) ManageOrderFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CashierForm.fxml"))));
    }

}
