package com.myorg.dummy.pos.dao.custom;

import com.myorg.dummy.pos.dao.SuperDAO;
import com.myorg.dummy.pos.entity.CustomEntity;

public interface QueryDAO extends SuperDAO {

    CustomEntity getOrderDetail(String orderId) throws Exception;

    CustomEntity getOrderDetail2(String orderId) throws Exception;
}
