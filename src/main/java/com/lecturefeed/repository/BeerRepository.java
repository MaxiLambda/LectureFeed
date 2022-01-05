package com.lecturefeed.repository;

import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class BeerRepository extends SQLiteRepository<BeerSpringRepository> {

    public BeerRepository(BeerSpringRepository delegate, JdbcTemplate jdbcTemplate) {
        super(delegate, jdbcTemplate);
    }

    @Override
    protected String getTableName() {
        return "Beer";
    }
}
