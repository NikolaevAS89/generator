package ru.timestop.generator.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 04.09.2018
 */
@Component
public class JournalPropertiesImpl implements JournalProperties {
    @Value("${journal.fail.log:false}")
    private boolean isFailLogEnabled;

    @Value("${journal.fail.output:failed.sql}")
    private String failOutput;

    @Value("${journal.succeeded.log:false}")
    private boolean isSuccessLogEnabled;

    @Value("${journal.succeeded.output:succeeded.sql}")
    private String successOutput;

    @Override
    public boolean isFailLogEnabled() {
        return isFailLogEnabled;
    }

    @Override
    public String getFailOutput() {
        return failOutput;
    }

    @Override
    public boolean isSuccessLogEnabled() {
        return isSuccessLogEnabled;
    }

    @Override
    public String getSuccessOutput() {
        return successOutput;
    }
}
