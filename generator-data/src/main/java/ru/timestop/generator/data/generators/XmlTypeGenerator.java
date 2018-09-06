package ru.timestop.generator.data.generators;

/**
 * Генератор случайных SQLXML объектов
 *
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class XmlTypeGenerator extends StringGenerator {

    private static final int TEXT_LENGTH = 10;

    public XmlTypeGenerator() {
        super(TEXT_LENGTH);
    }

    /**
     * New sqlxml object
     *
     * @return new random xml object linked with connection
     */
    @Override
    public Object next() {
        return "<xml><value>" + super.next().toString() + "</value></xml>";
    }
}
