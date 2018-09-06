package ru.timestop.generator.database.query;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class TypeNotSupportedException extends Exception {

    public TypeNotSupportedException(String typeName, String className) {
        super("Type " + typeName + " mapped on not supported java class " + className);
    }
}
