package com.cos.core.dao.shop;

import com.cos.core.dao.IEntityDao;

import java.util.List;
import java.util.Optional;

public interface IShopDao<E> extends IEntityDao<E> {

    List<E> getAllShops();

    Optional<E> getShopByName(String name);
}
