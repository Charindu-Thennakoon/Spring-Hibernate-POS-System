package com.myorg.dummy.pos.dao.custom.impl;

import com.myorg.dummy.pos.dao.CrudDAOImpl;
import com.myorg.dummy.pos.dao.custom.OrderDAO;
import com.myorg.dummy.pos.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDAOImpl extends CrudDAOImpl<Order, String> implements OrderDAO {

    public String getLastOrderId() throws Exception {
        List list =  session.createQuery("SELECT o.id FROM Order o ORDER BY o.id DESC")
                .setMaxResults(1).list();
        return (list.size()> 0)? (String) list.get(0): null;
    }
}
