package com.ibcs.hr.repo;

import com.ibcs.hr.entity.Dept;
import com.ibcs.hr.entity.Emp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepo extends JpaRepository<Dept, Long> {

    @Query("SELECT m FROM Dept m WHERE :sText is null or lower(m.code||m.name) LIKE '%lower(:sText)%' ORDER BY m.code")
    Page<Dept> findAllCustom(Pageable pageable, @Param("sText") String sText);

}
