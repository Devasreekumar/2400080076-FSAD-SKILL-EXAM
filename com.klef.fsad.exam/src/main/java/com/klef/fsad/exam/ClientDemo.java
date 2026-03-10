package com.klef.fsad.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import com.klef.fsad.exam.entity.Transport;

import java.util.List;

public class ClientDemo
{
    public static void main(String[] args)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Transport t1 = new Transport("Bus", "Active", "Public", 5000);
        Transport t2 = new Transport("Truck", "Inactive", "Goods", 12000);
        session.persist(t1);
        session.persist(t2);

        tx.commit();

        String hql = "FROM Transport t WHERE t.name LIKE :nameParam";
        Query<Transport> query = session.createQuery(hql, Transport.class);
        query.setParameter("nameParam", "%"); // matches all names

        List<Transport> list = query.getResultList();

        System.out.println("Transport Records:");
        for(Transport t : list)
        {
            System.out.println(
                t.getId() + " | " +
                t.getName() + " | " +
                t.getStatus() + " | " +
                t.getType() + " | " +
                t.getCost()
            );
        }

        session.close();
        sessionFactory.close();
    }
}