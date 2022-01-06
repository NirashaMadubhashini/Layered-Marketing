package controller.Customer;

import bo.BoFactory;
import bo.custom.impl.CustomerBOImpl;
import dto.CustomerDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.CustomerTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;;
import java.util.Optional;


public class ManageCustomerFormController {

    private final CustomerBOImpl customerBOImpl = (CustomerBOImpl) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);

    public AnchorPane ManageCustomerFormContext;
    public TableView<CustomerTM> tblManageCustomer;
    public TableColumn colCustomerID;
    public TableColumn colTitle;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colPostalCode;
    public TableColumn colProvince;
    public TableColumn colUpdate;
    public TableColumn colDelete;

    public void initialize() {

        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("update"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));


        tblManageCustomer.getColumns().setAll(colCustomerID, colTitle, colName, colAddress, colCity, colPostalCode, colProvince, colUpdate, colDelete);


        loadAllCustomers();
        UpdateCustomerFormController.ManageCustomerFormContext = ManageCustomerFormContext;
    }

    private void loadAllCustomers() {
        tblManageCustomer.getItems().clear();
        try {
            ArrayList<CustomerDTO> allCustomers = customerBOImpl.getAllCustomer();
            for (CustomerDTO temp : allCustomers) {

                Button btnUpdate = new Button("Update");
                Button btnDelete = new Button("Delete");

                tblManageCustomer.getItems().add(new CustomerTM(temp.getCustomerId(), temp.getTitle(), temp.getName(), temp.getAddress(), temp.getCity(),
                        temp.getPostalCode(), temp.getProvince(), btnUpdate, btnDelete));

                btnDelete.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are you sure you want to delete this Supplier?", yes, no);
                    alert.setTitle("Conformation Alert");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.orElse(no) == yes) {

                        deleteCustomer(temp);
                        allCustomers.remove(temp);
                        loadAllCustomers();
                    }

                });

                btnUpdate.setOnAction((e) -> {
                    try {

                        showUpdateForm(temp);


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                });

            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    private void showUpdateForm(CustomerDTO table) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/UpdateCustomerForm.fxml"));
        Parent parent = loader.load();
        UpdateCustomerFormController controller = loader.<UpdateCustomerFormController>getController();
        controller.txtUpdateTitle.setText(table.getTitle());
        controller.txtUpdateCustomerName.setText(table.getName());
        controller.txtUpdateAddress.setText(table.getAddress());
        controller.txtUpdateCity.setText(table.getCity());
        controller.txtUpdatePostalCode.setText(table.getPostalCode());
        controller.txtUpdateProvince.setText(table.getProvince());
        controller.customerId = table.getCustomerId();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBOImpl.ifCustomerExist(id);
    }

    private void deleteCustomer(CustomerDTO temp) {
        String name = tblManageCustomer.getSelectionModel().getSelectedItem().getCustomerId();
        try {
            if (!existCustomer(name)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + name).show();
            }
            customerBOImpl.deleteCustomer(name);
            tblManageCustomer.getItems().remove(tblManageCustomer.getSelectionModel().getSelectedItem());
            tblManageCustomer.getSelectionModel().clearSelection();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + name).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void ManageCustomerLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) ManageCustomerFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CashierForm.fxml"))));
    }

}
