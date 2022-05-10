package com.cos.core.dao;

import java.util.Optional;

public interface IRoleDao<E> {

    Optional<E> getRoleByName(String name);

    void saveRole(E role);

    void updateRole(E role);

    void deleteRole(E role);
}
