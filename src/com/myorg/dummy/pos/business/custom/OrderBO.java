package com.myorg.dummy.pos.business.custom;

import com.myorg.dummy.pos.business.SuperBO;
import com.myorg.dummy.pos.util.OrderDetailTM;
import com.myorg.dummy.pos.util.OrderTM;

import java.util.List;

public interface OrderBO extends SuperBO {

    public String getNewOrderId() throws Exception;

    public void placeOrder(OrderTM order, List<OrderDetailTM> orderDetails)throws Exception;
}
