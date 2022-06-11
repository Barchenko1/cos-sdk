package com.cos.core.dao;

import java.util.Optional;

public interface IProductTypeDao<E> {

    Optional<E> getProductTypeByName(String name);

    void saveProductType(E productType);

    void updateProductType(E productType);

    void deleteProductType(E productType);
}
