package com.brahma.hibernateutils.processor;

import com.brahma.hibernateutils.utils.AnnotatedClassUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.persistence.Entity;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Annotation processor here takes Entity classes as in the processing env. Generates a class Brahma_HibernateUtils
 * Brahma_HibernateUtils has a array of @Entity annotated classes
 * @author sarthak
 * @version 1.0
 */

@AutoService(Processor.class)
public final class HibernateUtilsProcessor extends AbstractProcessor {

    /**
     * Util classes provided by the processing environment. A utils class to work with Element classes
     */
    private Elements elementUtils;
    /**
     * Filer you can create files.
     */
    private Filer filer;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Entity.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        generateEntityArray(roundEnv);
        return false;
    }

    /**
     * This method writes the java file.
     *
     * @param generatedDaoClass Java class to be generated
     * @param packageName       Package of the generated class
     */
    private void generateJavaFile(final TypeSpec generatedDaoClass, final String packageName) {
        JavaFile javaFile = JavaFile.builder(packageName, generatedDaoClass).build();
        /**
         * Writing java class.
         */
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {

        }
    }

    /**
     * Generates Brahma_HibernateUtils.
     *
     * @param roundEnv roundEnv contains all the Element annotated with @Entity
     */
    private void generateEntityArray(final RoundEnvironment roundEnv) {
        CodeBlock.Builder builder = CodeBlock.builder().beginControlFlow("new Class[]");
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Entity.class)) {
            TypeElement typeElement = (TypeElement) annotatedElement;

            ClassName className = ClassName.get(AnnotatedClassUtils.getQualifiedClassName(typeElement, elementUtils),
                    AnnotatedClassUtils.getSimpleName(typeElement));
            builder.add("$T.class,\n", className);
        }
        FieldSpec entityAnnotatedClasses = FieldSpec.builder(Class[].class, "entityAnnotatedClasses")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer(builder.endControlFlow().build())
                .build();

        TypeSpec brahmaHibernateUtils = TypeSpec.classBuilder("Brahma_HibernateUtils")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(entityAnnotatedClasses).build();

        generateJavaFile(brahmaHibernateUtils, "com.brahma.utils");
    }

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }


}
