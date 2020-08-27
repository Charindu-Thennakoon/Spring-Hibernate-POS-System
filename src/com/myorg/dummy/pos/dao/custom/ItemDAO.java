package com.myorg.dummy.pos.dao.custom;

import com.myorg.dummy.pos.dao.CrudDAO;
import com.myorg.dummy.pos.entity.Item;

public interface ItemDAO extends CrudDAO<Item, String> {

    String getLastItemCode() throws Exception;

}
