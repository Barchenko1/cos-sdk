package com.cos.core.dao;

import java.util.List;
import java.util.Optional;

public interface IStorageDao<E> {

    List<E> getAllStorages();

    Optional<E> getStorageByName(String name);

    void saveStorage(E storage);

    void updateStorage(E storage);

    void deleteStorage(E storage);

}
