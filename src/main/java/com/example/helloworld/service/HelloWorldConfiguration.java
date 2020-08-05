package com.example.helloworld.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thinkon.common.database.mysql.JDBCConfiguration;
import io.dropwizard.Configuration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

public class HelloWorldConfiguration extends Configuration {
    // Jackson is used to map config.yml to this configuration class
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
