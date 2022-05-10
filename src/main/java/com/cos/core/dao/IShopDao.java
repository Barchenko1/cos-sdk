package com.cos.core.dao;

import java.util.List;
import java.util.Optional;

public interface IShopDao<E> {

    List<E> getAllShops();

    Optional<E> getShopByName(String name);

    void saveShop(E shop);

    void updateShop(E shop);

    void deleteShop(E shop);

}
