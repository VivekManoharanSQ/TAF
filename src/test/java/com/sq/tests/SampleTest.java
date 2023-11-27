package com.sq.tests;

import com.google.common.reflect.ClassPath;
import com.opencsv.CSVWriter;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SampleTest {


    @BeforeSuite
    public void beforeSuite(ITestContext context) {
        System.out.println("in beforeSuite of NewTestngClass");
        ITestNGMethod[] a = context.getAllTestMethods();
        for (ITestNGMethod b : a) {
            System.out.println(b.getConstructorOrMethod().getName());
        }
    }

    public static void main(String[] a) throws IOException {
        Set<? extends Class<?>> collect = ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .filter(clazz -> clazz.getPackageName()
                        .equalsIgnoreCase("com.sq.tests"))
                .map(clazz -> clazz.load())
                .collect(Collectors.toSet());
        List<String[]> arrList = new ArrayList<>();
        for (Class<?> aClass : collect) {
            Arrays.stream(aClass.getMethods()).filter(m -> m.isAnnotationPresent(Test.class))
                    .forEach(method -> {
                        List<String> lines = new ArrayList<>();
                        lines.add(aClass.toString());
                        lines.add(method.getName());
                        arrList.add(lines.toArray(new String[0]));
                    });
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter("D:\\Automation\\Intellij\\Framework\\TestAutomationFramework\\TAF\\test-output\\sample.csv"))) {
            for (String[] a1 : arrList) {
                writer.writeNext(a1);
            }

        }
        System.out.println(collect);
    }
}
