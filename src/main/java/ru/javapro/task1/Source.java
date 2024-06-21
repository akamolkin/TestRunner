package ru.javapro.task1;

import java.lang.reflect.Method;

public class Source {
    private Method method;
    private String csv;

    public Source(Method method, String csv) {
        this.method = method;
        this.csv = csv;
    }

    public Method getMethod() {
        return method;
    }

    public String getCsv() {
        return csv;
    }
}
