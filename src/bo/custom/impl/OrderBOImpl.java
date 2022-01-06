package bo.custom.impl;

import bo.custom.OrderBO;
import dao.DAOFactory;
import dao.custom.*;
import db.DbConnection;
import dto.*;
import entity.*;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);


    @Override
    public boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        try {


            Connection connection = null;

            connection = DbConnection.getInstance().getConnection();
            boolean orderAvailable = orderDAO.ifOrderExist(dto.getOrderId());
            if (orderAvailable) {
                return false;
            }

            connection.setAutoCommit(false);
            Order order = new Order(dto.getOrderId(), dto.getItemCode(), dto.getCustomerId(), dto.getCost(), dto.getDate(), dto.getTime());
            boolean orderAdded = orderDAO.add(order);
            if (!orderAdded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailDTO detail : dto.getOrderDetail()) {
                OrderDetail orderDetailDTO = new OrderDetail(dto.getOrderId(), detail.getOrderDetailItemCode(), detail.getOrderDetailQty(), detail.getPrice());
                boolean orderDetailsAdded = orderDetailDAO.add(orderDetailDTO);
                if (!orderDetailsAdded) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

                Item search = itemDAO.search(detail.getOrderDetailItemCode());
                search.setQtyOnHand(search.getQtyOnHand() - detail.getOrderDetailQty());
                boolean update = itemDAO.update(search);
                if (!update) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }

            connection.commit();
            connection.setAutoCommit(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewOrderId();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer c : all) {
            allCustomers.add(new CustomerDTO(c.getCustomerId(), c.getTitle(), c.getName(), c.getAddress(), c.getCity()
                    , c.getPostalCode(), c.getProvince()));
        }
        return allCustomers;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll();
        for (Item item : all) {
            allItems.add(new ItemDTO(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getQtyOnHand(), item.getUnitPrice()));
        }
        return allItems;
    }

    @Override
    public List<ItemDTO> getItems() throws SQLException, ClassNotFoundException {
        List<ItemDTO> allItems = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll();
        for (Item item : all) {
            allItems.add(new ItemDTO(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getQtyOnHand(), item.getUnitPrice()));
        }
        return allItems;
    }

    @Override
    public ItemDTO searchItem(String itemCode) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(itemCode);
        return new ItemDTO(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getQtyOnHand(), item.getUnitPrice());
    }

    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.ifItemExist(code);
    }

    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.ifCustomerExist(id);
    }

    @Override
    public CustomerDTO searchCustomer(String customerId) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(customerId);
        return new CustomerDTO(c.getCustomerId(), c.getTitle(), c.getName(), c.getAddress(), c.getCity()
                , c.getPostalCode(), c.getProvince());
    }

    @Override
    public List<CustomerOrderDTO> searchOrderDetail(String value) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<CustomerOrderDTO> getAllCustomerDetailHistory() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerOrderDTO> allItems = new ArrayList<>();
        ArrayList<CustomerOrder> all = orderDetailDAO.getAllCustomerDetailHistory();
        for (CustomerOrder orderDetail : all) {
            allItems.add(new CustomerOrderDTO(orderDetail.getName(), orderDetail.getOrderId(), orderDetail.getItemCode(), orderDetail.getDescription()
                    , orderDetail.getPackSize(), orderDetail.getQty(), orderDetail.getPrice(), orderDetail.getDate(), orderDetail.getTime()));
        }
        return allItems;
    }
}
