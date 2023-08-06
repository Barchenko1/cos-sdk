package com.cos.core.dao.bucket;

import com.cos.core.dao.IEntityDao;

public interface IBucketDao<E> extends IEntityDao<E> {
    void saveBucket();

    void findBucket();

    void updateBucket();

    void deleteBucket();

}
