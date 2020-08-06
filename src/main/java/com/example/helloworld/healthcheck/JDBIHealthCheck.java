package com.example.helloworld.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

/**
 * Health check to ensure a connection to the database exists
 * and that the 'sayings' table exists.
 */
public class JDBIHealthCheck extends HealthCheck {

    private final Jdbi jdbi;

    public JDBIHealthCheck(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    protected Result check() throws Exception {
        try (Handle h = this.jdbi.open()) {
            h.createQuery("SELECT * FROM saying LIMIT 1").mapToMap().list();
            return Result.healthy();
        } catch (Exception e) {
            return Result.unhealthy("Cannot Query Database!");
        }
    }
}
