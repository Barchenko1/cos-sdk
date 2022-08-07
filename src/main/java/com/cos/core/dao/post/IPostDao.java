package com.cos.core.dao.post;

import com.cos.core.dao.IEntityDao;

import java.util.List;
import java.util.Optional;

public interface IPostDao<E> extends IEntityDao<E> {

    List<E> getAllPosts();

    Optional<E> getPostByName(String name);
}
