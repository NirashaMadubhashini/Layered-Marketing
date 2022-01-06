package entity;


public class Order {
    private String orderId;
    private String itemCode;
    private String customerId;
    private double cost;
    private String date;
    private String time;


    public Order(String orderId, String itemCode, String customerId, double cost, String date, String time) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.setCustomerId(customerId);
        this.cost = cost;
        this.date = date;
        this.time = time;
    }

    public Order() {
    }

    public Order(String orderId, String itemCode, double cost, String date, String time) {
        this.setOrderId(orderId);
        this.setItemCode(itemCode);
        this.setCost(cost);
        this.setDate(date);
        this.setTime(time);
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

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", customerId='" + customerId + '\'' +
                ", cost=" + cost +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }


}
