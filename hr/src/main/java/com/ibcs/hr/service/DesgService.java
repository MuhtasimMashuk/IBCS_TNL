package com.ibcs.hr.service;

import com.ibcs.hr.dto.DeptDto;
import com.ibcs.hr.dto.DesgDto;
import com.ibcs.hr.dto.EmpDto;
import com.ibcs.hr.dto.ResponseDto;
import com.ibcs.hr.entity.Dept;
import com.ibcs.hr.entity.Desg;
import com.ibcs.hr.entity.Emp;
import com.ibcs.hr.repo.DesgRepo;
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
public class DesgService {

    Logger logger = LoggerFactory.getLogger(DesgService.class);
    @Autowired
    private DesgRepo desgRepo;

    private DesgDto conv(Desg desg) {
        DesgDto desgDto = new DesgDto();
        BeanUtils.copyProperties(desg, desgDto);
        return desgDto;
    }
    public Page<DesgDto> findAll(Pageable pageable, String sText) {
        logger.trace(" getAll executed");
        Page<Desg> desg = desgRepo.findAllCustom(pageable, sText);


        List<DesgDto> ssp = new ArrayList(pageable.getPageSize());
        for (Desg pp : desg.getContent()) {
            ssp.add(conv(pp));
        }

        Page<DesgDto> desgDtos = new PageImpl(ssp, pageable, desg.getTotalElements());

        return desgDtos;
    }


    public List<DesgDto> findAllwihoutPage() {
        logger.trace(" getAll executed");
        List<Desg> desgs= desgRepo.findAll();
        List<DesgDto> desgDtos=new ArrayList<>();

        for (Desg desg: desgs)
        {
            DesgDto desgDto= new DesgDto();
            BeanUtils.copyProperties(desg, desgDto);
            desgDtos.add(desgDto);
        }


return desgDtos;
    }


    public Object findById(Long id) {
        
//        Desg desg = desgRepo.getById(id);
//
//        DesgDto desgDto = new DesgDto();
//        BeanUtils.copyProperties(desg, desgDto);
//
//
//        return desgDto;


        logger.trace("findById executed");
        DesgDto desgDto = new DesgDto();

        if(!desgRepo.existsById(id)){
            logger.trace("id not found");
           // desgDto.setResponseDto(responseDto);
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Desg not found",id);
        }

        Desg desg = desgRepo.getById(id);
        BeanUtils.copyProperties(desg, desgDto);
        //ResponseDto responseDto=new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Desg retrieved",id);
       // desgDto.setResponseDto(responseDto);
        return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Desg retrieved",desgDto);
    }


    public Object save(DesgDto desgDto) {

        Desg desg=new Desg();
        try {
            BeanUtils.copyProperties(desgDto, desg);
            desgRepo.save(desg);
            BeanUtils.copyProperties(desg, desgDto);
            return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Desg created", desgDto);
        }
        catch(Exception e) {

           // desgDto.setResponseDto(responseDto);
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Desg creation is not possible", e);
        }
    }
    
    
    public Object update(DesgDto desgDto, Long id) {

        logger.trace(" update executed from service");
        if(!desgRepo.existsById(id)){
            logger.trace("id not found");
           // ResponseDto responseDto=
            //desgDto.setResponseDto(responseDto);
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Desg not found",id);
        }

        Desg desg = desgRepo.getById(id);
        BeanUtils.copyProperties(desgDto, desg,"id");
     
        desg= desgRepo.save(desg);


        BeanUtils.copyProperties(desg, desgDto);

        return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Desg data updated",desgDto);
    }


    public ResponseDto deleteById(Long id) { //delete<--------------
        logger.trace(" deleteById executed from service");

        if(!desgRepo.existsById(id)) {
            logger.trace(" id is not found");
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Delete is not possible",id);
        }

        return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Deletion complete",id);
    }

}
