package ru.javapro.task1;

import ru.javapro.task1.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRunner {


    public static void runTests(Class c) throws Throwable{
        List<Method> methodsBeforeSuite = new ArrayList<>();
        List<Method> methodsAfterSuite  = new ArrayList<>();
        Map<Integer, List<Source>> methodsTest = new HashMap<>();
        List<Method> methodsBeforeTest = new ArrayList<>();
        List<Method> methodsAfterTest = new ArrayList<>();

        Object object = c.getDeclaredConstructor().newInstance();

        for (Method method : c.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                methodsBeforeSuite.add(method);
            }
            if (method.isAnnotationPresent(AfterSuite.class)) {
                methodsAfterSuite.add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                List<Source> sourceList = new ArrayList<>();
                int priority = method.getAnnotation(Test.class).priority();
                if (priority < 1 && priority > 10) {
                    throw new IllegalArgumentException("Priority should be in the range from 1 to 10");
                }
                if (methodsTest.containsKey(priority)) {
                    sourceList = methodsTest.get(priority);
                }
                String csv = new String();
                if (method.isAnnotationPresent(CsvSource.class)) {
                    csv = method.getAnnotation(CsvSource.class).value();
                }
                Source source = new Source(method,csv);
                sourceList.add(source);
                methodsTest.put(priority, sourceList);
            }
            if (method.isAnnotationPresent(BeforeTest.class)) {
                methodsBeforeTest.add(method);
            }
            if (method.isAnnotationPresent(AfterTest.class)) {
                methodsAfterTest.add(method);
            }

        }

        // BeforeSuite
        if (!methodsBeforeSuite.isEmpty()) {
            if (methodsBeforeSuite.size() > 1) {
                throw new RuntimeException("Annotated BeforeSuite methods more then 1!");
            }
            Method method = methodsBeforeSuite.getFirst();
            method.invoke(object);
        }

        // BeforeTest Test AfterTest
        if (!methodsTest.isEmpty()) {
            for (int i = 10; i > 0; i--) {
                if (methodsTest.containsKey(i)) {
                    List<Source> sourceList = new ArrayList<>();
                    sourceList = methodsTest.get(i);
                    for (Source source : sourceList) {
                        // BeforeTest
                        if (!methodsBeforeTest.isEmpty()) {
                            for (Method methodBeforeTest : methodsBeforeTest) {
                                methodBeforeTest.invoke(object);
                            }
                        }

                        // Test
                        String csv = source.getCsv();
                        Method method = source.getMethod();
                     //   String str = "10, Java, 20, true";

                        Class[] paramTypes = method.getParameterTypes();
                        // Если есть параметры
                        if (paramTypes.length != 0) {
                            String[] strArr = csv.split(",");
                            Object[] args = new Object[paramTypes.length];
                            for (int j = 0; j < paramTypes.length; j++) {
                                if (paramTypes[j] == int.class | paramTypes[j] == byte.class | paramTypes[j] == char.class | paramTypes[j] == long.class | paramTypes[j] == Integer.class ) {
                                    args[j] = Integer.parseInt(strArr[j].trim());
                                } else if (paramTypes[j] == String.class) {
                                    args[j] = strArr[j].trim();
                                } else if (paramTypes[j] == boolean.class) {
                                    args[j] = Boolean.valueOf(strArr[j].trim());
                                } else if (paramTypes[j] == float.class) {
                                    args[j] = Float.parseFloat(strArr[j].trim());
                                } else if (paramTypes[j] == double.class) {
                                    args[j] = Double.parseDouble(strArr[j].trim());
                                }
                            }
                          //  objects[0] = Integer.parseInt(strArr[0]);
                          //  objects[1] = "Java";
                          //  objects[2] = 20;
                          //  objects[3] = true;
                            method.invoke(object, args);
                       //     method.invoke(object, 10, "Java", 20, true);

                        } else {
                            method.invoke(object);
                        }

                        // AfterTest
                        if (!methodsAfterTest.isEmpty()) {
                            for (Method methodAfterTest : methodsAfterTest) {
                                methodAfterTest.invoke(object);
                            }
                        }

                    }

                }

            }
        }

        if (!methodsAfterSuite.isEmpty()) {
            if (methodsAfterSuite.size() > 1) {
                throw new RuntimeException("Annotated BeforeSuite methods more then 1!");
            }
            Method method = methodsAfterSuite.getFirst();
            method.invoke(object);
        }



//            Annotation annotation = method.getAnnotation(Test.class);
//            Test test = (Test) annotation;
//
//            // If the annotation is not null
//            if (test != null) {
//            }


    }
}