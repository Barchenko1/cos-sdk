package com.cos.core.util.converter;

import java.util.List;
import java.util.Map;

public interface ISqlParamsConverter {
    Map<Integer, Object> getObjectParamsMap(List<Object> params);
}
