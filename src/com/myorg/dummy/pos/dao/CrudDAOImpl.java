package com.myorg.dummy.pos.dao;

import com.myorg.dummy.pos.entity.SuperEntity;
import org.hibernate.Session;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class CrudDAOImpl<T extends SuperEntity,ID extends Serializable> implements CrudDAO<T, ID>{

    protected Session session;
    private Class<T> entity;

    public CrudDAOImpl(){
        entity = (Class<T>)(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void save(T entity) throws Exception {
        session.save(entity);
    }

    @Override
    public void update(T entity) throws Exception {
        session.update(entity);
    }

    @Override
    public void delete(ID key) throws Exception {
        session.delete(session.load(entity,key));
    }

    @Override
    public List<T> findAll() throws Exception {
        return session.createQuery("FROM " + entity.getName()).list();
    }

    @Override
    public T find(ID key) throws Exception {
        return session.get(entity,key);
    }
}
