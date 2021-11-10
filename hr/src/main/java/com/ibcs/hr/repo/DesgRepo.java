package com.ibcs.hr.repo;

import com.ibcs.hr.entity.Dept;
import com.ibcs.hr.entity.Desg;
import com.ibcs.hr.entity.Emp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DesgRepo extends JpaRepository<Desg, Long> {

    @Query("SELECT m FROM Desg m WHERE :sText is null or lower(m.name) LIKE '%lower(:sText)%'")
    Page<Desg> findAllCustom(Pageable pageable, @Param("sText") String sText);

}
