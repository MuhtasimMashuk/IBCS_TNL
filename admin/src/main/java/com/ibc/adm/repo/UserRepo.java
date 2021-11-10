package com.ibc.adm.repo;

import com.ibc.adm.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT m FROM User m WHERE m.code=:code OR  m.email=:email OR m.mobileNo=:mobileNo ")
    User loadSingleUser(@Param("code") String code, @Param("email") String email, @Param("mobileNo") String mobileNo);

    @Query("SELECT m FROM User m WHERE m.code=:loginId OR  m.email=:loginId OR m.mobileNo=:loginId  ")
    User loadSingleUser(@Param("loginId") String loginId);

    @Query("SELECT m FROM User m WHERE :sText is null or lower(m.code||m.name) LIKE '%lower(:sText)%' ORDER BY m.code")
    Page<User> findAllCustom(Pageable pageable, @Param("sText") String sText);

 }
