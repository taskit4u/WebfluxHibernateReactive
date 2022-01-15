package com.example.webfluxhibernatereactive.dao;

import com.example.webfluxhibernatereactive.entity.Customer;
import org.hibernate.reactive.stage.Stage;
import org.springframework.stereotype.Component;

import javax.persistence.Persistence;
import java.util.concurrent.CompletableFuture;

@Component
public class CustomerDao {

    private final Stage.SessionFactory sessionFactory;

    public CustomerDao() {
        // postgresql-example - name from persistence.xml
        this.sessionFactory = Persistence.createEntityManagerFactory( "postgresql-example" )
                        .unwrap(Stage.SessionFactory.class);
    }

    public CompletableFuture<Void> save(Customer customer) {
        // obtain a reactive session
        return sessionFactory.withTransaction(
                        // persist the Customer in a transaction
                        (session, tx) -> session.persist(customer)
                )
                // wait for it to finish
                .toCompletableFuture();
    }

    public CompletableFuture<Customer> getCustomer(String name) {
        return sessionFactory.withSession(
                        // retrieve Customer by name
                        session -> session.createQuery("from Customer where name=:name order by name", Customer.class)
                                    .setParameter("name", name)
                                    .getSingleResult()
                ).toCompletableFuture();
    }

    public CompletableFuture<Integer> update(Customer customer) {
        return sessionFactory.withTransaction(
                // retrieve Customer by name
                session -> session.createQuery("update Customer set name=:name, sex=:sex, age=:age where name=:name")
                        .setParameter("name", customer.getName())
                        .setParameter("sex", customer.getSex())
                        .setParameter("age", customer.getAge())
                        .executeUpdate()
        ).toCompletableFuture();
    }

    public CompletableFuture<Integer> delete(String name) {
        return sessionFactory.withTransaction(
                // retrieve Customer by name
                session -> session.createQuery("delete Customer where name=:name")
                        .setParameter("name", name)
                        .executeUpdate()
        ).toCompletableFuture();
    }

}
