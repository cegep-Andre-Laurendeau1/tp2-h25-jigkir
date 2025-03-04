package ca.cal.tp2.repository;

import java.util.List;

public interface IBaseRepository<T> {
    boolean save(T entity);
    T get(Long id);
    List<T> findAll();
    boolean update(T entity);
}