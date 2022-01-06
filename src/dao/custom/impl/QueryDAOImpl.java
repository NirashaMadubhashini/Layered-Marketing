package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.QueryDAO;
import dto.CustomerOrderDTO;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<CustomerOrderDTO> getOrderDetailsFromOrderID(String oid) throws SQLException, ClassNotFoundException {
        ArrayList<CustomerOrderDTO> allData = new ArrayList();
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
                " LEFT JOIN `Order` o ON cod.orderDetailId = o.orderId" +
                " LEFT JOIN Item i ON cod.orderDetailItemCode =i.itemCode" +
                " LEFT JOIN customer c ON o.customerId = c.customerId");
        while (rst.next()) {
            allData.add(new CustomerOrderDTO(
                    rst.getString("name"), rst.getString("orderId"), rst.getString("itemCode"),
                    rst.getString("description"), rst.getString("packSize"),
                    rst.getInt("qty"), rst.getDouble("price"), rst.getString("date"), rst.getString("time")));
        }
        return allData;
    }
}
