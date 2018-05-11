package com.brahma.hibernateutils.utils;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * This class is use to get name/package of the annotated element.
 * @author sarthak
 * @since 1.0
 */
public final class AnnotatedClassUtils {
    /**
     *
     */
    private AnnotatedClassUtils() {
    }

    /**
     * @param typeElement Represents a class or interface program element
     * @param elementUtils Utility methods for operating on program elements
     * @return Qualified class name
     */
    public static String getQualifiedClassName(final TypeElement typeElement, final Elements elementUtils) {
        return elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
    }

    /**
     * @param typeElement Represents a class or interface program element.
     * @return Simple class name
     */
    public static String getSimpleName(final TypeElement typeElement) {
        return typeElement.getSimpleName().toString();
    }


}
