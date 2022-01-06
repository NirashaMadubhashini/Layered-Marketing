package controller.Customer;

import bo.BoFactory;
import bo.custom.CustomerBO;
import dto.CustomerDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import validation.ValidationUtil;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import static bo.BoFactory.getBOFactory;

public class AddCustomerFormController {

    private final CustomerBO customerBO = (CustomerBO) getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);


    public AnchorPane AddCustomerFormContext;
    public Button btnAddCustomer;
    public TextField txtCustomerID;
    public TextField txtTitle;
    public TextField txtCustomerName;
    public TextField txtAddress;
    public TextField txtCity;
    public TextField txtPostalCode;
    public TextField txtProvince;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern customerIDPattern = Pattern.compile("^(C)[0-9]{3,4}$");
    Pattern titlePattern = Pattern.compile("^(Miss|Mrs|Mr)(.)?$");
    Pattern namePattern = Pattern.compile("^[A-z ]{2,}$");
    Pattern addressPattern = Pattern.compile("^[A-z ]{3,30}([0-9]{1,2})?$");
    Pattern cityPattern = Pattern.compile("^[A-z ]{3,30}([0-9]{1,2})?$");
    Pattern postalCodePattern = Pattern.compile("^[0-9 A-z]*$");
    Pattern provincePattern = Pattern.compile("^[A-z ]{2,}$");

    public void initialize() {
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtCustomerID, customerIDPattern);
        map.put(txtTitle, titlePattern);
        map.put(txtCustomerName, namePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtCity, cityPattern);
        map.put(txtPostalCode, postalCodePattern);
        map.put(txtProvince, provincePattern);
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.ifCustomerExist(id);
    }

    public void AddCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String customerID = txtCustomerID.getText();
        String title = txtTitle.getText();
        String name = txtCustomerName.getText();
        String address = txtAddress.getText();
        String city = txtCity.getText();
        String postalCode = txtPostalCode.getText();
        String province = txtProvince.getText();


        try {
            if (existCustomer(customerID)) {
                new Alert(Alert.AlertType.ERROR, customerID + " already exists").show();

            } else {

                CustomerDTO customerDTO = new CustomerDTO(customerID, title, name, address, city, postalCode, province);
                customerBO.addCustomer(customerDTO);

                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();


                Stage stage = (Stage) btnAddCustomer.getScene().getWindow();
                stage.close();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void textFieldsKeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAddCustomer);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.WARNING, "Empty Result Set").showAndWait();
            }
        }
    }

}