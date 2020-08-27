package com.myorg.dummy.pos.dao.custom.impl;

import com.myorg.dummy.pos.dao.custom.QueryDAO;
import com.myorg.dummy.pos.entity.CustomEntity;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

@Component
public class QueryDAOImpl implements QueryDAO {

    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public CustomEntity getOrderDetail(String orderId) throws Exception {
        return (CustomEntity) session.createNativeQuery("SELECT  o.id AS orderId," +
                " c.name AS customerName, o.date AS orderDate FROM `Order` o\n" +
                "INNER JOIN Customer c on o.customerId = c.id\n" +
                "WHERE o.id=?1").setParameter(1, orderId).
                setResultTransformer(Transformers.aliasToBean(CustomEntity.class)).uniqueResult();

    }

    @Override
    public CustomEntity getOrderDetail2(String orderId) throws Exception {
        return (CustomEntity) session.createQuery("SELECT NEW entity.CustomEntity(C.id, C.name, O.id) " +
                "FROM Order O INNER JOIN O.customer C WHERE O.id=:id")
                .setParameter("id", orderId).uniqueResult();
    }
}
