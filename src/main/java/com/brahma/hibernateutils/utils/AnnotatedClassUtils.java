package com.brahma.hibernateutils.utils;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class AnnotatedClassUtils {
    public static String getQualifiedClassName(TypeElement typeElement, Elements elementUtils) {
        return elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
    }


    public static String getSimpleName(TypeElement typeElement) {
        return typeElement.getSimpleName().toString();
    }


}
