package com.ibcs.hr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="HR_DEPT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dept extends BaseEntity{

    @Column(unique=true, nullable=false, length=10)
    private String code;

    @Column(unique=true, nullable=false, length=35)
    private String name;

    @ManyToOne
    @JoinColumn(name = "HR_EMP_HOD_ID")
    private Emp headOfId;

    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean active;
}