package com.myorg.dummy.pos.dao.custom.impl;

import com.myorg.dummy.pos.dao.CrudDAOImpl;
import com.myorg.dummy.pos.dao.custom.ItemDAO;
import com.myorg.dummy.pos.entity.Item;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemDAOImpl extends CrudDAOImpl<Item, String> implements ItemDAO {

    public String getLastItemCode() throws Exception {
        List list = session.createQuery("SELECT i.code FROM com.myorg.dummy.pos.entity.Item i ORDER BY i.code DESC")
                .setMaxResults(1).list();
        return list.size() > 0 ? (String) list.get(0) : null;
    }
}
