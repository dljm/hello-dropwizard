package com.example.helloworld.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import javax.sql.DataSource;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

public class JDBIHealthCheck extends HealthCheck {
    private final DBI dbi;

    // Ensure database is connected and sayings table exists
    public JDBIHealthCheck(DataSource dataSource){
        this.dbi = new DBI(dataSource);
    }

    @Override
    protected Result check() throws Exception {
        try(Handle h = this.dbi.open()){
            h.createQuery("SELECT * FROM saying LIMIT 1");
            return Result.healthy();
        }catch(Exception e){
            return Result.unhealthy("Cannot Query Database!");
        }
    }
}
