package com.myorg.dummy.pos.dao.custom;

import com.myorg.dummy.pos.dao.CrudDAO;
import com.myorg.dummy.pos.entity.Customer;

public interface CustomerDAO extends CrudDAO<Customer,String> {

    String getLastCustomerId() throws Exception;

}
