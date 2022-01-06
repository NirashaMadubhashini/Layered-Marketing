package dto;


import java.util.List;

public class OrderDTO {
    private String orderId;
    private String itemCode;
    private String customerId;
    private double cost;
    private String date;
    private String time;
    private List<OrderDetailDTO> orderDetail;


    public OrderDTO(String orderId, String itemCode, String customerId, double cost, String date, String time, List<OrderDetailDTO> orderDetails) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.customerId = (customerId);
        this.cost = cost;
        this.date = date;
        this.time = time;
        this.orderDetail = orderDetails;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public List<OrderDetailDTO> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", customerId='" + customerId + '\'' +
                ", cost=" + cost +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", orderDetailDTOS=" + orderDetail +
                '}';
    }


}
