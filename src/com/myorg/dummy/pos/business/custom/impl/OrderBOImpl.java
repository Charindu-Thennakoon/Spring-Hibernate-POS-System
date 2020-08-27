package com.myorg.dummy.pos.business.custom.impl;

import com.myorg.dummy.pos.business.custom.OrderBO;
import com.myorg.dummy.pos.dao.custom.CustomerDAO;
import com.myorg.dummy.pos.dao.custom.ItemDAO;
import com.myorg.dummy.pos.dao.custom.OrderDAO;
import com.myorg.dummy.pos.dao.custom.OrderDetailDAO;
import com.myorg.dummy.pos.db.HibernateUtil;
import com.myorg.dummy.pos.entity.Item;
import com.myorg.dummy.pos.entity.Order;
import com.myorg.dummy.pos.entity.OrderDetail;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.myorg.dummy.pos.util.OrderDetailTM;
import com.myorg.dummy.pos.util.OrderTM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Component
public class OrderBOImpl implements OrderBO {

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private CustomerDAO customerDAO;



    public String getNewOrderId() throws Exception {

        Session session = HibernateUtil.getSessionFactory().openSession();
        orderDAO.setSession(session);
        Transaction transaction = null;
        String lastOrderId = null;
        try {
            transaction = session.beginTransaction();
            lastOrderId = orderDAO.getLastOrderId();
            transaction.commit();
        }catch (Throwable t){
            transaction.rollback();
            throw t;
        }
        session.close();

        if (lastOrderId == null) {
            return "OD001";
        } else {
            int maxId = Integer.parseInt(lastOrderId.replace("OD", ""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "OD00" + maxId;
            } else if (maxId < 100) {
                id = "OD0" + maxId;
            } else {
                id = "OD" + maxId;
            }
            return id;
        }
    }

    public void placeOrder(OrderTM order, List<OrderDetailTM> orderDetails) throws Exception {

        Session session = HibernateUtil.getSessionFactory().openSession();
        orderDAO.setSession(session);
        orderDetailDAO.setSession(session);
        itemDAO.setSession(session);
        customerDAO.setSession(session);

        try {
            session.getTransaction().begin();
            orderDAO.save(new Order(order.getOrderId(),
                    Date.valueOf(order.getOrderDate()),
                    customerDAO.find(order.getCustomerId())));

            for (OrderDetailTM orderDetail : orderDetails) {
                orderDetailDAO.save(new OrderDetail(
                        order.getOrderId(), orderDetail.getCode(),
                        orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())
                ));

                Item item = itemDAO.find(orderDetail.getCode());
                item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());

            }
            session.getTransaction().commit();
        } catch (Throwable t) {
            session.getTransaction().rollback();
            throw t;
        } finally {
            session.close();
        }
    }
}
