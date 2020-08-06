package com.example.helloworld.dao;

import com.example.helloworld.models.Saying;
import java.util.List;
import java.util.Optional;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterFieldMapper(Saying.class)
public interface SayingDao {

    /**
     * Gets all stored saying records
     *
     * @return all saying records
     */
    @SqlQuery("SELECT * FROM saying")
    List<Saying> getSayings();

    /**
     * Get saying by id
     * @param id Saying record id
     * @return a saying record
     */
    @SqlQuery("SELECT id, content, active FROM saying WHERE id = :id")
    Optional<Saying> getSaying(@Bind("id") long id);

    /**
     * Get a single random Saying record
     * @return a saying record
     */
    @SqlQuery("SELECT id, content, active FROM saying WHERE active = true ORDER BY RAND() LIMIT 1")
    Optional<Saying> getRandomSaying();

    /**
     * Creates a new saying.
     *
     * @param saying saying
     * @return generated id
     */
    @SqlUpdate("INSERT INTO saying(content, active) VALUES (:content, :active)")
    @GetGeneratedKeys
    long createSaying(@BindBean Saying saying);

    /**
     * Update an existing saying
     *
     * @param saying Saying object
     * @param id id of the record to update
     */
    @SqlUpdate("UPDATE saying SET content = :content, active = :active WHERE id = :id")
    void updateSaying(@Bind("id") long id, @BindBean Saying saying);

    /**
     * Delete an existing saying
     * @param id the long id
     */
    @SqlUpdate("DELETE FROM saying WHERE id = :id")
    void deleteSaying(@Bind("id") long id);
}
