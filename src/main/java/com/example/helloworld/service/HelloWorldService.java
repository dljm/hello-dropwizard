package com.example.helloworld.service;

import com.example.helloworld.dao.SayingDao;
import com.example.helloworld.healthcheck.JDBIHealthCheck;
import com.example.helloworld.models.Saying;
import com.thinkon.common.database.mysql.JDBCSession;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.example.helloworld.resources.HelloWorldResource;
import javax.sql.DataSource;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class HelloWorldService extends Application<HelloWorldConfiguration> {

    public static void main(String[] args) throws Exception {
        new HelloWorldService().run(args);
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) throws Exception {
        // initialize the database connection
        JDBCSession jdbcSession = new JDBCSession(configuration.getJdbcConfiguration());
        jdbcSession.open();

        // Setup JDBI database access
        DataSource dataSource = jdbcSession.getDataSource();
        Jdbi jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin());

        // Initialize JDBI DAOs
        SayingDao sayingDao = jdbi.onDemand(SayingDao.class);
        // Bind DAO
        environment.jersey().register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(sayingDao).to(SayingDao.class);
            }
        });

        // Health checks, check them out at http://localhost:8081/healthcheck
        JDBIHealthCheck jdbiHealthCheck = new JDBIHealthCheck(dataSource);
        environment.healthChecks().register("Database Health Check", jdbiHealthCheck);

        // Bind resources
        HelloWorldResource helloWorldResource = new HelloWorldResource(sayingDao, configuration.getDefaultName());
        environment.jersey().register(helloWorldResource);
    }

    @Override
    public String getName() {
        return "hello-dropwizard";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        // Manage custom environment variables
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
    }
}
