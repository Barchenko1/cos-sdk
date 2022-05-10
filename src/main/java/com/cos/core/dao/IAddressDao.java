package com.cos.core.dao;

import java.util.List;
import java.util.Optional;

public interface IAddressDao<E> {

    List<E> getAllAddresses();

    Optional<E> getAddressByCityName(String name);

    void saveAddress(E address);

    void updateAddress(E address);

    void deleteAddress(E address);

}
