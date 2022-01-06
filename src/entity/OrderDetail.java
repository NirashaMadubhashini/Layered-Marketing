package entity;

public class OrderDetail {
    private String orderDetailId;
    private String orderDetailItemCode;
    private int orderDetailQty;
    private double price;


    public OrderDetail() {
    }

    public OrderDetail(String orderDetailId, String orderDetailItemCode, int orderDetailQty, double price) {
        this.setOrderDetailId(orderDetailId);
        this.setOrderDetailItemCode(orderDetailItemCode);
        this.setOrderDetailQty(orderDetailQty);
        this.setPrice(price);

    }

    public OrderDetail(String orderId, String itemCode, String description, String packSize, int qtyOnHand, double unitPrice) {
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getOrderDetailItemCode() {
        return orderDetailItemCode;
    }

    public void setOrderDetailItemCode(String orderDetailItemCode) {
        this.orderDetailItemCode = orderDetailItemCode;
    }

    public int getOrderDetailQty() {
        return orderDetailQty;
    }

    public void setOrderDetailQty(int orderDetailQty) {
        this.orderDetailQty = orderDetailQty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
