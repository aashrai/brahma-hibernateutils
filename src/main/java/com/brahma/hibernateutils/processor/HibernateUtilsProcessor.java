package com.brahma.hibernateutils.processor;

import com.brahma.hibernateutils.utils.AnnotatedClassUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.persistence.Entity;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

@AutoService(Processor.class)
public class HibernateUtilsProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

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
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        generateEntityArray(roundEnv);
        return false;
    }

    private void generateJavaFile(TypeSpec generatedDaoClass, String packageName) {
        JavaFile javaFile = JavaFile.builder(packageName, generatedDaoClass).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {

        }
    }

    private void generateEntityArray(RoundEnvironment roundEnv) {
        CodeBlock.Builder builder = CodeBlock.builder().beginControlFlow("new Class[]");
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Entity.class)) {
            TypeElement typeElement = (TypeElement) annotatedElement;

            ClassName className = ClassName.get(AnnotatedClassUtils.getQualifiedClassName(typeElement, elementUtils),
                    AnnotatedClassUtils
                            .getSimpleName(typeElement));
            builder.add("$T.class,\n", className);
        }
        FieldSpec entityAnnotatedClasses = FieldSpec.builder(Class[].class, "entityAnnotatedClasses")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer(builder.endControlFlow().build())
                .build();

        TypeSpec Zefo_HibernateUtils = TypeSpec.classBuilder("Zefo_HibernateUtils")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(entityAnnotatedClasses).build();

        generateJavaFile(Zefo_HibernateUtils, "com.zefo.utils");
    }
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }


}
