package controller.Order;

import bo.BoFactory;
import bo.custom.impl.OrderBOImpl;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;


import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.CartTm;


import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class PlaceOrderFormController {

    private final OrderBOImpl orderBOImpl = (OrderBOImpl) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDER);

    public AnchorPane PlaceOrderFormContext;
    public TextField txtCustomerOrderId;
    public TextField txtCustomerOrderDate;
    public TextField txtCustomerOrderTime;
    public TextField txtCustomerOrderTitle;
    public TextField txtCustomerOrderAddress;
    public ComboBox<String> cmbCustomerOrderID;
    public TextField txtCustomerOrderName;
    public TextField txtCustomerOrderCity;
    public TextField txtCustomerOrderPostalCode;
    public TextField txtCustomerOrderProvince;
    public ComboBox<String> cmbCustomerOrderItemCode;
    public TextField txtCustomerOrderItemPackSize;
    public TextField txtCustomerOrderUnitPrice;
    public TextField txtCustomerOrderDescription;
    public TextField txtCustomerOrderItemQty;
    public TextField txtCustomerOrderItemQtyOnHand;
    public Label lblCustomerOrderTotal;
    public Label lblCustomerOrderDiscount;
    public TableView<CartTm> tblPlaceOrder;
    public TableColumn colPlaceOrderId;
    public TableColumn colPlaceCustomerName;
    public TableColumn colPlaceItemCode;
    public TableColumn colPlaceDescription;
    public TableColumn colPlacePackSize;
    public TableColumn colPlaceQty;
    public TableColumn colPlaceUnitPrice;
    public TableColumn colPlaceTotal;
    private String orderId;

    int cartSelectedRowForRemove = -1;


    public void initialize() throws SQLException, ClassNotFoundException {
        colPlaceOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colPlaceCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPlaceItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colPlaceDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPlacePackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colPlaceQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPlaceUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colPlaceTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


        cmbCustomerOrderID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                CustomerDTO customerDTO = orderBOImpl.searchCustomer(newValue + "");
                txtCustomerOrderTitle.setText(customerDTO.getTitle());
                txtCustomerOrderName.setText(customerDTO.getName());
                txtCustomerOrderAddress.setText(customerDTO.getAddress());
                txtCustomerOrderCity.setText(customerDTO.getCity());
                txtCustomerOrderPostalCode.setText(customerDTO.getPostalCode());
                txtCustomerOrderProvince.setText(customerDTO.getProvince());

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        cmbCustomerOrderItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                ItemDTO itemDTO = orderBOImpl.searchItem(newValue + "");
                txtCustomerOrderDescription.setText(itemDTO.getDescription());
                txtCustomerOrderItemPackSize.setText(itemDTO.getPackSize());
                txtCustomerOrderItemQtyOnHand.setText(String.valueOf(itemDTO.getQtyOnHand()));
                txtCustomerOrderUnitPrice.setText(String.valueOf(itemDTO.getUnitPrice()));


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        tblPlaceOrder.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });

        orderId = generateOrderId();
        txtCustomerOrderId.setText(orderId);


        loadCustomerIds();
        loadItemIds();
        loadDateTime();


    }


    private String generateOrderId() {
        try {
            return orderBOImpl.generateNewOrderId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadDateTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        txtCustomerOrderDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            txtCustomerOrderTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void loadCustomerIds() throws SQLException, ClassNotFoundException {
        try {
            ArrayList<CustomerDTO> all = orderBOImpl.getAllCustomers();
            for (CustomerDTO customerDTO : all) {
                cmbCustomerOrderID.getItems().add(customerDTO.getCustomerId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        try {
            ArrayList<ItemDTO> all = orderBOImpl.getAllItems();
            for (ItemDTO dto : all) {
                cmbCustomerOrderItemCode.getItems().add(dto.getItemCode());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int isExists(CartTm tm) {
        for (int i = 0; i < obList.size(); i++) {

            if (tm.getItemCode() == obList.get(i).getItemCode()) {
                return i;
            }
        }
        return -1;
    }

    ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public void AddToCartOnAction(ActionEvent actionEvent) {
        try {
            String orderId = txtCustomerOrderId.getText();
            String name = txtCustomerOrderName.getText();
            String itemCode = String.valueOf(cmbCustomerOrderItemCode.getValue());
            String description = txtCustomerOrderDescription.getText();
            String packSize = txtCustomerOrderItemPackSize.getText();
            int qtyOnHand = Integer.parseInt(txtCustomerOrderItemQtyOnHand.getText());
            double unitPrice = Double.parseDouble(txtCustomerOrderUnitPrice.getText());
            int qtyForCustomer = Integer.parseInt(txtCustomerOrderItemQty.getText());
            double total = qtyForCustomer * unitPrice;

            if (qtyOnHand < qtyForCustomer) {
                new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
                return;
            }

            CartTm tm = new CartTm(
                    orderId,
                    name,
                    itemCode,
                    description,
                    packSize,
                    qtyForCustomer,
                    unitPrice,
                    total
            );
            int rowNumber = isExists(tm);

            if (rowNumber == -1) {
                obList.add(tm);
            } else {
                CartTm temp = obList.get(rowNumber);
                CartTm newTm = new CartTm(
                        temp.getItemCode(),
                        temp.getName(),
                        temp.getItemCode(),
                        temp.getDescription(),
                        temp.getPackSize(),
                        temp.getQty() + qtyForCustomer,
                        unitPrice,
                        total + temp.getTotal()
                );

                obList.remove(rowNumber);
                obList.add(newTm);
            }
            tblPlaceOrder.setItems(obList);
            calculateCost();


        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
            e.printStackTrace();
        }
    }


    public void CustomerPlaceOrderOnAction(ActionEvent actionEvent) {

        boolean b = saveOrder(txtCustomerOrderId.getText(), String.valueOf(cmbCustomerOrderItemCode.getValue()), String.valueOf(cmbCustomerOrderID.getValue()),
                Double.parseDouble(txtCustomerOrderUnitPrice.getText()), txtCustomerOrderDate.getText(), txtCustomerOrderTime.getText(),
                tblPlaceOrder.getItems().stream().map(tm ->
                        new OrderDetailDTO(tm.getOrderId(), tm.getItemCode(), tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));


        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
            printBill();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        orderId = generateOrderId();
        txtCustomerOrderId.setText(generateOrderId());
        tblPlaceOrder.getItems().clear();
        txtCustomerOrderItemQty.clear();
        clearText();
        calculateCost();
    }

    private void printBill() {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/PlaceBill.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            ObservableList<CartTm> items = tblPlaceOrder.getItems();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JRBeanArrayDataSource(items.toArray()));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public boolean saveOrder(String orderId, String itemCode, String customerId, double cost, String date, String time, List<OrderDetailDTO> orderDetails) {
        try {
            OrderDTO orderDTO = new OrderDTO(orderId, itemCode, customerId, cost, date, time, orderDetails);
            return orderBOImpl.purchaseOrder(orderDTO);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void clearText() {
        txtCustomerOrderTitle.setText("");
        txtCustomerOrderName.setText("");
        txtCustomerOrderAddress.setText("");
        txtCustomerOrderCity.setText("");
        txtCustomerOrderPostalCode.setText("");
        txtCustomerOrderProvince.setText("");
        txtCustomerOrderDescription.setText("");
        txtCustomerOrderItemPackSize.setText("");
        txtCustomerOrderItemQtyOnHand.setText("");
        txtCustomerOrderUnitPrice.setText("");
        txtCustomerOrderItemQty.setText("");
        lblCustomerOrderDiscount.setText("");
        lblCustomerOrderTotal.setText("");

        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            tblPlaceOrder.getItems().clear();
        }

    }

    public void CustomerPlaceOrderRemoveOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        } else {
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblPlaceOrder.refresh();
        }
    }

    private void calculateCost() {
        double total = 0;
        for (CartTm tm : tblPlaceOrder.getItems()) {
            total += tm.getTotal();
        }
        lblCustomerOrderTotal.setText(total + " /=");

        double discount = 0;

        for (CartTm tm : tblPlaceOrder.getItems()
        ) {
            discount += tm.getTotal();

            if (total >= 1000) {
                lblCustomerOrderDiscount.setText(String.valueOf(total - (total / 10)));
            } else if (total <= 1000) {
                total = total;
            }
        }
    }

    public void EnterQtyOnAction(ActionEvent actionEvent) {
        try {
            int qtyOnHand = Integer.parseInt(txtCustomerOrderItemQtyOnHand.getText());
            double unitPrice = Double.parseDouble(txtCustomerOrderUnitPrice.getText());
            int enterQty = Integer.parseInt(txtCustomerOrderItemQty.getText());

            double total = enterQty * unitPrice;
            if (enterQty > qtyOnHand) {

                new Alert(Alert.AlertType.WARNING, "Qty is Not Enough Please Re Try").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Value Is Empty Try Again").show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void PlaceOrderLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) PlaceOrderFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CashierForm.fxml"))));
    }
}
