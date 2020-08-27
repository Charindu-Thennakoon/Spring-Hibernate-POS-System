package com.myorg.dummy.pos.dao.custom.impl;

import com.myorg.dummy.pos.dao.CrudDAOImpl;
import com.myorg.dummy.pos.dao.custom.CustomerDAO;
import com.myorg.dummy.pos.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDAOImpl extends CrudDAOImpl<Customer, String> implements CustomerDAO {

    @Override
    public String getLastCustomerId() throws Exception {
        return (String) session.createNativeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1").uniqueResult();
    }
}
