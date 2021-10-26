package com.wildcodeschool.challengeJDBCIntroduction.repository;

import java.util.List;

public interface CrudDao<School> {

    School save(School entity);

    School findById(Long id);

    List<School> findAll();

    School update(School entity);

    void deleteById(Long id);
}