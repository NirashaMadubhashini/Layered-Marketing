package controller.Customer;

import bo.BoFactory;
import bo.custom.CustomerBO;
import dto.CustomerDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;


import static bo.BoFactory.getBOFactory;

public class UpdateCustomerFormController {

    private final CustomerBO customerBO = (CustomerBO) getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);

    public AnchorPane UpdateCustomerFormContext;
    public static AnchorPane ManageCustomerFormContext;
    public Button btnUpdateCustomer;
    public TextField txtUpdateTitle;
    public TextField txtUpdateCustomerName;
    public TextField txtUpdateAddress;
    public TextField txtUpdateCity;
    public TextField txtUpdatePostalCode;
    public TextField txtUpdateProvince;

    public String customerId;
    


    public void UpdateCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        try {
            CustomerDTO customerDTO = new CustomerDTO(customerId, txtUpdateTitle.getText(), txtUpdateCustomerName.getText(), txtUpdateAddress.getText(),
                    txtUpdateCity.getText(), txtUpdatePostalCode.getText(), txtUpdateProvince.getText());
            boolean updateCustomer = customerBO.updateCustomer(customerDTO);

            Stage stage = (Stage) btnUpdateCustomer.getScene().getWindow();
            stage.close();

            refreshCustomerUpdate(updateCustomer);

        } catch (SQLException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void refreshCustomerUpdate(boolean updateCustomer) throws IOException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Updated..", yes, no);
        Optional<ButtonType> result = alert.showAndWait();
        if (updateCustomer) {
            if (result.orElse(no) == yes) {
                Stage window = (Stage) ManageCustomerFormContext.getScene().getWindow();
                window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ManageCustomerForm.fxml"))));
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update the Item ", ButtonType.CLOSE).show();
        }
    }

}
