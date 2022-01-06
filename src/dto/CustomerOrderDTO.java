package dto;

public class CustomerOrderDTO {
    private String name;
    private String orderId;
    private String itemCode;
    private String description;
    private String packSize;
    private int qty;
    private double price;
    private String date;
    private String time;

    public CustomerOrderDTO(String orderId, int qty) {
        this.setOrderId(orderId);
        this.setQty(qty);
    }

    public CustomerOrderDTO(String name, String orderId, String itemCode, String description,
                            String packSize, int qty, double price, String date, String time) {
        this.setName(name);
        this.setOrderId(orderId);
        this.setItemCode(itemCode);
        this.setDescription(description);
        this.setPackSize(packSize);
        this.setQty(qty);
        this.setPrice(price);
        this.setDate(date);
        this.setTime(time);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
}
