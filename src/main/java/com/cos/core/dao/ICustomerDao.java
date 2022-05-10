package com.cos.core.dao;

import java.util.List;
import java.util.Optional;

public interface ICustomerDao<E> {

    List<E> getAllCustomers();

    Optional<E> getCustomerByName(String name);

    void saveCustomer(E customer);

    void updateCustomer(E customer);

    void deleteCustomer(E customer);
}
