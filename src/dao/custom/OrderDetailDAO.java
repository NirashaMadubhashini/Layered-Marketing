package dao.custom;

import dao.CrudDAO;
import dto.CustomerOrderDTO;
import entity.CustomerOrder;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDetail, String> {
    public List<CustomerOrderDTO> searchOrderDetail(String value) throws SQLException, ClassNotFoundException;

    public ArrayList<CustomerOrder> getAllCustomerDetailHistory() throws SQLException, ClassNotFoundException;
}
