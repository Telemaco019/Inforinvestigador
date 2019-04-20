package com.unibs.zanotti.inforinvestigador.data.source;

import java.util.List;
import java.util.Optional;

public interface IDao<T> {
    void save(T t);

    void update(T t, String[] params);

    void delete(T t);

    List<T> getAll();

    Optional<T> get(long id);
}
