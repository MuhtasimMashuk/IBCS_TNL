package com.ibcs.tnl.repo;

import com.ibcs.tnl.entity.LeaveType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveTypeRepo extends JpaRepository<LeaveType,Long> {
    @Query("SELECT m FROM LeaveType m WHERE :sText is null or lower(m.name) LIKE '%lower(:sText)%'")
    Page<LeaveType> findAllCustom(Pageable pageable, @Param("sText") String sText);
}
