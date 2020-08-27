package com.myorg.dummy.pos.db;

import com.myorg.dummy.pos.entity.Customer;
import com.myorg.dummy.pos.entity.Item;
import com.myorg.dummy.pos.entity.Order;
import com.myorg.dummy.pos.entity.OrderDetail;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;

public class HibernateUtil {

    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

        File propFile = new File("resources/application.properties");

        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .loadProperties(propFile)
                .build();

        Metadata metadata = new MetadataSources( standardRegistry )
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderDetail.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy( ImplicitNamingStrategyJpaCompliantImpl.INSTANCE )
                .build();

        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder()
                .build();
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

}
