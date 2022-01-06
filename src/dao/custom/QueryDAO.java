package dao.custom;

import dao.SuperDAO;
import dto.CustomerOrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<CustomerOrderDTO> getOrderDetailsFromOrderID(String oid) throws SQLException, ClassNotFoundException;
}
