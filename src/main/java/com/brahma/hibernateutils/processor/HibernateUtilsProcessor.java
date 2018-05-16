/*
 * Copyright (c) 2018 gozefo.
 *
 * Licensed under the MIT License;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/MIT
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *SOFTWARE
 */

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
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.persistence.Entity;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * An {@linkplain javax.annotation.processing.Processor annotation processor} to generate list of Entity classes.
 *
 * @author sarthak
 * @version 1.0
 */

@AutoService(Processor.class)
public final class HibernateUtilsProcessor extends AbstractProcessor {

    /**
     * A utils class to fetch details about the annotated element.
     * @see {@link com.brahma.hibernateutils.utils.AnnotatedClassUtils}
     */
    private Elements elementUtils;
    /**
     * Writes the generated java class.
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
     * Generates java file with list of Entity Classes. {@linkplain #generateEntityArray(RoundEnvironment)}
     *
     * @param generatedDaoClass Generated java class
     */
    private void generateJavaFile(final TypeSpec generatedDaoClass) {
        JavaFile javaFile = JavaFile.builder("com.brahma.utils", generatedDaoClass).build();
        /**
         * Writing java class.
         */
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {

        }
    }

    /**
     * Processes all the elements annotated with @Entity annotation.
     * The Entity classes are added to a list of classes
     *
     * @param roundEnv roundEnv contains all the classes/elements of the project
     * @see "/brahma-hibernateutils/testfiles/hibernateutils_output.txt"
     */
    private void generateEntityArray(final RoundEnvironment roundEnv) {
        CodeBlock.Builder builder = CodeBlock.builder().beginControlFlow("new Class[]");
        roundEnv.getElementsAnnotatedWith(Entity.class).forEach(annotatedElement -> {
            TypeElement typeElement = (TypeElement) annotatedElement;

            ClassName className = ClassName.get(AnnotatedClassUtils.getQualifiedClassName(typeElement, elementUtils),
                    AnnotatedClassUtils.getSimpleName(typeElement));
            builder.add("$T.class,\n", className);

        });

        FieldSpec entityAnnotatedClasses = FieldSpec.builder(Class[].class, "entityAnnotatedClasses")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer(builder.endControlFlow().build())
                .build();

        TypeSpec brahmaHibernateUtils = TypeSpec.classBuilder("Brahma_HibernateUtils")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(entityAnnotatedClasses).build();

        generateJavaFile(brahmaHibernateUtils);
    }

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }


}
