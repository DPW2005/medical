package org.example.medical.crud;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudOperations<T> {
    T create(T entity) ;
    Optional<T> findById(long id) ;
    List<T> findAll() ;
    T update(T entity) ;
    void delete(long id) ;
    T mapResultToEntity(ResultSet rs) throws SQLException;
}
