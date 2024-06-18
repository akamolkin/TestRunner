package ru.javapro.task1;

public class MainApp {
    public static void main(String[] args) {
        TestRunner testRunner = new TestRunner();
        try {
            testRunner.runTests(AnnotatedClass.class);
        } catch (Throwable ex) {
            System.out.println(ex.getMessage());
        }
    }
}
