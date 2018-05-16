package com.brahma.processor;

import com.brahma.hibernateutils.processor.HibernateUtilsProcessor;
import com.brahma.utils.TestUtils;
import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;
import java.util.Collections;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class HibernateUtilsTest {
    @Test
    public void hibernateUtilsTest() {

        final JavaFileObject input = JavaFileObjects.forSourceString(
                "com.example.EntityClass",
                Joiner.on('\n').join(Collections.singleton(TestUtils.readLineByLineJava8
                        ("testfiles/hibernateutils_input.txt"))

                )
        );

        final JavaFileObject output = JavaFileObjects.forSourceString(
                "com.brahma.utils.Brahma_HibernateUtils",
                Joiner.on('\n').join(Collections.singleton(TestUtils.readLineByLineJava8
                        ("testfiles/hibernateutils_output.txt"))

                )
        );

        assertAbout(javaSource())
                .that(input)
                .processedWith(new HibernateUtilsProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(output);
    }

}
