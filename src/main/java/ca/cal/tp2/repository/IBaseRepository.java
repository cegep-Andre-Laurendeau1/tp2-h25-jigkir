package ca.cal.tp2.repository;

import java.util.List;

public interface IBaseRepository<T, ID> {
    boolean save(T entity);
    T findById(ID id);
    List<T> findAll();
    boolean update(T entity);
}