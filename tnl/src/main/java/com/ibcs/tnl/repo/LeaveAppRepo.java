package com.ibcs.tnl.repo;

import com.ibcs.tnl.entity.LeaveApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveAppRepo extends JpaRepository<LeaveApp,Long> {
    @Query("SELECT m FROM LeaveApp m WHERE :sText is null or lower(m.entry) LIKE '%lower(:sText)%'")
    Page<LeaveApp> findAllCustom(Pageable pageable, @Param("sText") String sText);
}
