package com.cos.core.dao.store;

import com.cos.core.dao.IEntityDao;

import java.util.List;
import java.util.Optional;

public interface IStoreDao<E> extends IEntityDao<E> {

    List<E> getAllStorages();

    Optional<E> getStorageByName(String name);
}
