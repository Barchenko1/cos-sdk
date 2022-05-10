package com.cos.core.dao;

import java.util.List;
import java.util.Optional;

public interface IPostDao<E> {

    List<E> getAllPosts();

    Optional<E> getPostByName(String name);

    void savePost(E post);

    void updatePost(E post);

    void deletePost(E post);
}
