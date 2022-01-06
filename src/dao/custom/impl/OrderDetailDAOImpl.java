package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailDAO;
import dto.CustomerOrderDTO;
import entity.CustomerOrder;
import entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public List<CustomerOrderDTO> searchOrderDetail(String value) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<CustomerOrder> getAllCustomerDetailHistory() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerOrder> allItems = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT c.name," +
                "cod.orderDetailId," +
                "cod.orderDetailItemCode," +
                "i.description," +
                "i.packSize," +
                "cod.orderDetailQty," +
                "cod.price," +
                "o.orderDate," +
                "o.orderTime" +
                " FROM orderdetail cod" +
                "  LEFT JOIN `Order` o ON cod.orderDetailId = o.orderId" +
                "  LEFT JOIN Item i ON cod.orderDetailItemCode =i.itemCode" +
                "  LEFT JOIN customer c ON o.customerId = c.customerId");
        while (rst.next()) {
            allItems.add(new CustomerOrder(rst.getString("name"), rst.getString("orderDetailId"), rst.getString("orderDetailItemCode"),
                    rst.getString("description"), rst.getString("packSize"), rst.getInt("orderDetailQty"), rst.getDouble("price"),
                    rst.getString("orderDate"), rst.getString("orderTime")));
        }
        return allItems;
    }

    @Override
    public boolean add(OrderDetail dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO OrderDetail (orderDetailId, orderDetailItemCode,orderDetailQty, price) VALUES (?,?,?,?)",
                dto.getOrderDetailId(), dto.getOrderDetailItemCode(), dto.getOrderDetailQty(), dto.getPrice());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public OrderDetail search(String orderDetailId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM OrderDetail WHERE orderDetailId=?", orderDetailId);
        rst.next();
        return new OrderDetail(orderDetailId, rst.getString("orderDetailItemCode"), rst.getInt("orderDetailQty"),
                rst.getDouble("price"));
    }


    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

}
