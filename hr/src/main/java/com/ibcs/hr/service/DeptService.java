package com.ibcs.hr.service;

import com.ibcs.hr.dto.DeptDto;
import com.ibcs.hr.dto.DesgDto;
import com.ibcs.hr.dto.EmpDto;
import com.ibcs.hr.dto.ResponseDto;
import com.ibcs.hr.entity.Dept;
import com.ibcs.hr.entity.Desg;
import com.ibcs.hr.entity.Emp;
import com.ibcs.hr.repo.DeptRepo;
import com.ibcs.hr.repo.EmpRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeptService {

    Logger logger = LoggerFactory.getLogger(DeptService.class);
    @Autowired
    private DeptRepo deptRepo;
    @Autowired
    private EmpRepo empRepo;

    private DeptDto conv(Dept dept) {
        DeptDto deptDto = new DeptDto();
        BeanUtils.copyProperties(dept, deptDto, "headOfId");
        deptDto.setHeadOfId(dept.getHeadOfId().getId());
        return deptDto;
    }
    
    public Page<DeptDto> findAll(Pageable pageable, String sText) {
        logger.trace("findAll executed");
        Page<Dept> dept = deptRepo.findAllCustom(pageable, sText);

        List<DeptDto> sss = new ArrayList(pageable.getPageSize());
        for (Dept pp : dept.getContent()) {
            sss.add(conv(pp));
        }

        Page<DeptDto> deptDtos = new PageImpl(sss, pageable, dept.getTotalElements());

        return deptDtos;
    }


    public List<DeptDto> findAllWithoutPage(){

        List<DeptDto> deptDtoList = new ArrayList<>();
        List<Dept> deptList = deptRepo.findAll();

        for(Dept dept: deptList){
            DeptDto deptDto=new DeptDto();
            BeanUtils.copyProperties(dept, deptDto, "headOfId");
            deptDto.setHeadOfId(dept.getHeadOfId().getId());
            deptDtoList.add(deptDto);
        }
        return deptDtoList;
    }
    public Object findById(Long id) {
       /* Dept dept = deptRepo.getById(id);

        DeptDto deptDto = new DeptDto();
        BeanUtils.copyProperties(dept, deptDto);


        return deptDto;*/
        logger.trace("findById executed");
        DeptDto deptDto = new DeptDto();

        if(!deptRepo.existsById(id)){
            logger.trace("id not found");
           // deptDto.setResponseDto(responseDto);
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Dept not found",id);
        }

        Dept dept = deptRepo.getById(id);
        BeanUtils.copyProperties(dept, deptDto);
        deptDto.setHeadOfId(dept.getHeadOfId().getId());

//
       // return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Dept retrieved",deptDto);
        return deptDto;
        
    }

    public Object save(DeptDto deptDto) {

      /*  Dept  dept = new Dept();
        BeanUtils.copyProperties(deptDto, dept, "headOfId");
        dept.setHeadOfId(empRepo.getById(deptDto.getHeadOfId()));
        return conv(deptRepo.save(dept));*/


        Dept dept=new Dept();
        try {
            BeanUtils.copyProperties(deptDto, dept,"headOfId");
            dept.setHeadOfId(empRepo.getById(deptDto.getHeadOfId()));
            dept=deptRepo.save(dept);
            BeanUtils.copyProperties(dept,deptDto);
            deptDto.setHeadOfId(dept.getHeadOfId().getId());
            return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Department created", deptDto);
        }
        catch(Exception e) {

            return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Department creation is not possible",e);
        }
    }
    
    
    
    
    
    
    public Object update(DeptDto deptDto, Long id) {

       /* Dept dept = deptRepo.getById(id);
        BeanUtils.copyProperties(deptDto, dept, "id","headOfId");

        dept.setHeadOfId(empRepo.getById(deptDto.getHeadOfId()));

        return conv(deptRepo.save(dept));*/

        logger.trace(" update executed from service");
        if(!deptRepo.existsById(id)){
            logger.trace("id not found");

            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Dept not found",id);
        }

        Dept dept = deptRepo.getById(id);
        BeanUtils.copyProperties(deptDto, dept, "id","headOfId");

        dept.setHeadOfId(empRepo.getById(deptDto.getHeadOfId()));

        dept= deptRepo.save(dept);


        BeanUtils.copyProperties(dept, deptDto);
        deptDto.setHeadOfId(dept.getHeadOfId().getId());

        return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Dept data updated",deptDto);
       
    }



    public ResponseDto deleteById(Long id) { //delete<--------------
        logger.trace(" deleteById executed from service");

        if(!deptRepo.existsById(id)) {
            logger.trace(" id is not found");
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Delete is not possible",id);
        }

        return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Deletion complete",id);
    }


}
