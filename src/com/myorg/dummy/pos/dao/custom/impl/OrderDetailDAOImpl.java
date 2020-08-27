package com.myorg.dummy.pos.dao.custom.impl;

import com.myorg.dummy.pos.dao.CrudDAOImpl;
import com.myorg.dummy.pos.dao.custom.OrderDetailDAO;
import com.myorg.dummy.pos.entity.OrderDetail;
import com.myorg.dummy.pos.entity.OrderDetailPK;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDetailDAOImpl extends CrudDAOImpl<OrderDetail, OrderDetailPK> implements OrderDetailDAO {

    @Override
    public List<OrderDetail> findAll() throws Exception {
        return session.createQuery("FROM OrderDetail", OrderDetail.class).list();
    }
}
