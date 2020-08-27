package com.myorg.dummy.pos.dao.custom;

import com.myorg.dummy.pos.dao.CrudDAO;
import com.myorg.dummy.pos.entity.Order;

public interface OrderDAO extends CrudDAO<Order, String> {

    String getLastOrderId() throws Exception;

}
