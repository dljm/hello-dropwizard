package com.example.helloworld.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thinkon.common.database.mysql.JDBCConfiguration;
import io.dropwizard.Configuration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Configuration class mapped from config.yml by Jackson.
 */
public class HelloWorldConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("jdbc")
    private JDBCConfiguration jdbcConfiguration;

    @NotEmpty
    private String defaultName;

    public JDBCConfiguration getJdbcConfiguration() {
        return jdbcConfiguration;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }
}
