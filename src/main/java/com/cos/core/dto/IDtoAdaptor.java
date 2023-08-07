package com.cos.core.dto;

import org.hibernate.type.BasicTypeReference;

import java.util.Map;

public interface IDtoAdaptor {
    Map<String, BasicTypeReference<?>> getMetadata(Class<?> clazz);
}
