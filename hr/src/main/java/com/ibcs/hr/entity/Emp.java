package com.ibcs.hr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="HR_EMP")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emp extends BaseEntity{

    public enum Gender {
        MALE, FEMALE, OTHERS
    }

    @Column()
    private String photo;

    @Column(unique=true, nullable=false, length=6)
    private String code;

    @Column(nullable=false, length=35)
    private String name;

    @Column(name="FATHER_NAME" , nullable=false, length=35)
    private String fatherName;

    @Column(nullable=false)
	private LocalDate dob;

    @Column(nullable=false)
	private LocalDate doj;

    @Column(unique = true, nullable=false, length=17)
    private String nid;

    @Enumerated(EnumType.STRING)
    @Column(length = 6, nullable = false)
    private Gender gender;

    //@Email
    @Column(unique = true, length=50)
    private String email;

    @Column(name="MOBILE_NO", nullable = false, unique = true, length=14)
    private String mobileNo;

    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean active;

    @Column(unique = true,name = "ADM_USER_ID")
    private Long userId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "HR_DEPT_ID", nullable = false)
    private Dept deptId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "HR_DESG_ID", nullable = false)
    private Desg desgId;

    @ManyToOne()
    @JoinColumn(name = "HR_EMP_SUPERVISOR_ID")
    private Emp supervisorId;
}