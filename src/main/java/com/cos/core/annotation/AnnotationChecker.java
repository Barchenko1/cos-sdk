package com.cos.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;

public class AnnotationChecker implements IAnnotationChecker {
    public <T> boolean areAllElementsHaveAnnotation(List<T> list, Class<? extends Annotation> annotationClass) {
        return list.stream().allMatch(element -> hasAnnotation((AnnotatedElement) element, annotationClass));
    }

    private boolean hasAnnotation(AnnotatedElement element, Class<? extends Annotation> annotationClass) {
        return element.isAnnotationPresent(annotationClass);
    }
}
