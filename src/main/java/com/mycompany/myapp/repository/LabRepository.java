package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Lab;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Lab entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LabRepository extends JpaRepository<Lab, Long> {

    @Query(value = "select distinct lab from Lab lab left join fetch lab.courses",
        countQuery = "select count(distinct lab) from Lab lab")
    Page<Lab> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct lab from Lab lab left join fetch lab.courses")
    List<Lab> findAllWithEagerRelationships();

    @Query("select lab from Lab lab left join fetch lab.courses where lab.id =:id")
    Optional<Lab> findOneWithEagerRelationships(@Param("id") Long id);

}
