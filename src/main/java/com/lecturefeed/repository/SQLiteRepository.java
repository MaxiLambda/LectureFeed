package com.lecturefeed.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;

@AllArgsConstructor
public abstract class SQLiteRepository<T extends JpaRepository> {

    @Getter
    private final T delegate;

    @Getter
    private final JdbcTemplate jdbcTemplate;

    protected abstract String getTableName();

    public int getNextId(){
        return jdbcTemplate.queryForObject(String.format("select max(s.id) from %s s", getTableName()), (resultSet, rowNum) -> resultSet.getInt(1))+1;
    }

    public T getDelegation(){
        return delegate;
    }

}
