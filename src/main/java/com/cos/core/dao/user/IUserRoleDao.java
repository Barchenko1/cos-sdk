package com.cos.core.dao.user;

import com.cos.core.dao.IEntityDao;

import java.util.Optional;

public interface IUserRoleDao<E> extends IEntityDao<E> {

    Optional<E> getRoleByName(String name);
}
