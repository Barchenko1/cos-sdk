package com.cos.core.annotation;

import java.lang.annotation.Annotation;
import java.util.List;

public interface IAnnotationChecker {
    public <T> boolean areAllElementsHaveAnnotation(List<T> list, Class<? extends Annotation> annotationClass);

}
