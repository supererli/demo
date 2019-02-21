package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Grade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query(value = "select distinct grade from Grade grade left join fetch grade.teachers",
        countQuery = "select count(distinct grade) from Grade grade")
    Page<Grade> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct grade from Grade grade left join fetch grade.teachers")
    List<Grade> findAllWithEagerRelationships();

    @Query("select grade from Grade grade left join fetch grade.teachers where grade.id =:id")
    Optional<Grade> findOneWithEagerRelationships(@Param("id") Long id);

}
