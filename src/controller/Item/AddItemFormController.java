package controller.Item;

import bo.BoFactory;
import bo.custom.ItemBO;
import dto.ItemDTO;
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

public class AddItemFormController {

    private final ItemBO itemBO = (ItemBO) getBOFactory().getBO(BoFactory.BoTypes.ITEM);


    public AnchorPane AddItemFormContext;
    public Button btnAddItem;
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern itemCodePattern = Pattern.compile("^(I)[0-9]{3}$");
    Pattern descriptionPattern = Pattern.compile("^[A-z ]{2,}$");
    Pattern packSizePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern qtyOnHandPattern = Pattern.compile("^[1-9][0-9]*$");
    Pattern unitPricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");


    public void initialize() {
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtItemCode, itemCodePattern);
        map.put(txtDescription, descriptionPattern);
        map.put(txtPackSize, packSizePattern);
        map.put(txtQtyOnHand, qtyOnHandPattern);
        map.put(txtUnitPrice, unitPricePattern);
    }

    boolean existItem(String id) throws SQLException, ClassNotFoundException {
        return itemBO.ifItemExist(id);
    }


    public void AddItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemCode = txtItemCode.getText();
        String description = txtDescription.getText();
        String packSize = txtPackSize.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        try {
            if (existItem(itemCode)) {
                new Alert(Alert.AlertType.ERROR, itemCode + " already exists").show();

            } else {

                ItemDTO itemDTO = new ItemDTO(itemCode, description, packSize, qtyOnHand, unitPrice);
                itemBO.addItem(itemDTO);

                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();

                Stage stage = (Stage) btnAddItem.getScene().getWindow();
                stage.close();

            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void textFieldsKeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAddItem);

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
