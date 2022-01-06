package controller.Item;


import bo.BoFactory;
import bo.custom.ItemBO;
import dto.ItemDTO;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.ItemTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static bo.BoFactory.getBOFactory;


public class ManageItemFormController {

    private final ItemBO itemBO = (ItemBO) getBOFactory().getBO(BoFactory.BoTypes.ITEM);

    public AnchorPane ManageItemFormContext;
    public TableView<ItemTm> tblManageItem;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colQtyOnHand;
    public TableColumn colUnitPrice;
    public TableColumn colUpdate;
    public TableColumn colDelete;


    public void initialize() {

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("update"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));

        tblManageItem.getColumns().setAll(colItemCode, colDescription, colPackSize, colQtyOnHand, colUnitPrice, colUpdate, colDelete);


        loadAllItems();
        UpdateItemFormController.ManageItemFormContext = ManageItemFormContext;
    }


    private void loadAllItems() {
        tblManageItem.getItems().clear();
        try {
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO item : allItems) {

                Button btnUpdate = new Button("Update");
                Button btnDelete = new Button("Delete");

                tblManageItem.getItems().add(new ItemTm(item.getItemCode(), item.getDescription(), item.getPackSize(),
                        item.getQtyOnHand(), item.getUnitPrice(), btnUpdate, btnDelete));

                btnDelete.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are you sure you want to delete this Supplier?", yes, no);
                    alert.setTitle("Conformation Alert");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.orElse(no) == yes) {

                        deleteCustomer(item);
                        allItems.remove(item);
                        loadAllItems();
                    }

                });

                btnUpdate.setOnAction((e) -> {
                    try {
                        showUpdateForm(item);


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                });

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
    }


    private void showUpdateForm(ItemDTO table) throws SQLException, ClassNotFoundException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/UpdateItemForm.fxml"));
            Parent parent = loader.load();
            UpdateItemFormController controller = loader.<UpdateItemFormController>getController();
            controller.txtUpdateDescription.setText(table.getDescription());
            controller.txtUpdatePackSize.setText(table.getPackSize());
            controller.txtUpdateQtyOnHand.setText(String.valueOf(table.getQtyOnHand()));
            controller.txtUpdateUnitPrice.setText(String.valueOf(table.getUnitPrice()));
            controller.itemCode = table.getItemCode();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean existItem(String id) throws SQLException, ClassNotFoundException {
        return itemBO.ifItemExist(id);
    }


    private void deleteCustomer(ItemDTO temp) {
        String description = tblManageItem.getSelectionModel().getSelectedItem().getItemCode();
        try {
            if (!existItem(description)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + description).show();
            }
            itemBO.deleteItem(description);
            tblManageItem.getItems().remove(tblManageItem.getSelectionModel().getSelectedItem());
            tblManageItem.getSelectionModel().clearSelection();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + description).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void ManageItemLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) ManageItemFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdministratorForm.fxml"))));
    }
}
