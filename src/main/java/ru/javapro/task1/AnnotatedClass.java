package ru.javapro.task1;

import ru.javapro.task1.annotations.*;

import java.util.List;

public class AnnotatedClass {
    @BeforeSuite
    static void methodBeforeSuite(){
        System.out.println("Run methodBeforeSuite");
    }

    @Test(priority = 1)
    void methodTest1(){
        System.out.println("Run methodTest1 with 1 priority");
    }

    @Test
    @CsvSource("10, Java, 20, true")
    void methodTest2(int a, String b, int c, boolean d){
        System.out.println("Run methodTest2 with default priority and args: " + a +" "+ b +" "+ c +" "+ d);
    }

    @Test(priority = 10)
    void methodTest3(){
        System.out.println("Run methodTest3 with 10 priority");
    }
    @AfterSuite
    static void methodAfterSuite(){
        System.out.println("Run methodAfterSuite");
    }

    @BeforeTest
    void methodBeforeTest(){
        System.out.println("Run methodBeforeTest");
    }

    @AfterTest
    void methodAfterTest(){
        System.out.println("Run methodAfterTest");
    }


}
