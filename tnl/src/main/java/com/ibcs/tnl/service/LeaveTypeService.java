package com.ibcs.tnl.service;

import com.ibcs.tnl.api.LeaveAppApi;
import com.ibcs.tnl.dto.LeaveTypeDto;
import com.ibcs.tnl.dto.ResponseDto;
import com.ibcs.tnl.entity.LeaveApp;
import com.ibcs.tnl.entity.LeaveType;
import com.ibcs.tnl.repo.LeaveTypeRepo;
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
public class LeaveTypeService {

    Logger logger = LoggerFactory.getLogger(LeaveTypeService.class);
    @Autowired
    private LeaveTypeRepo leaveTypeRepo;

    private LeaveTypeDto conv(LeaveType leaveType) { //converting entity to dto<-----------
        LeaveTypeDto leaveTypeDto = new LeaveTypeDto();
        BeanUtils.copyProperties(leaveType, leaveTypeDto);
        return leaveTypeDto;
    }

    private LeaveType conv(LeaveTypeDto leaveTypeDto) { // converting dto to entity <---------
        LeaveType   leaveType = new LeaveType();
        BeanUtils.copyProperties(leaveTypeDto, leaveType);

        return leaveType;
    }

    public Page<LeaveTypeDto> findAll(Pageable pageable, String sText) { //get all<----------
        logger.trace(" getAll executed");
        Page<LeaveType> leaveType = leaveTypeRepo.findAllCustom(pageable, sText);
        //PageRequest<LeaveTypeDto> leaveTypeDtos= PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        List<LeaveTypeDto> ss = new ArrayList(pageable.getPageSize());
        for (LeaveType pp : leaveType.getContent()) {
            ss.add(conv(pp));
        }

        Page<LeaveTypeDto> leaveTypeDtos = new PageImpl(ss, pageable, leaveType.getTotalElements());

        return leaveTypeDtos;
    }

    public List<LeaveTypeDto> findAllList() { //get all<----------
        logger.trace(" getAll executed");
        List<LeaveType> leaveType = leaveTypeRepo.findAll();
        //PageRequest<LeaveTypeDto> leaveTypeDtos= PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        List<LeaveTypeDto> ss = new ArrayList();
        for (LeaveType pp : leaveType) {
            ss.add(conv(pp));
        }



        return ss;
    }

    public Object findById(Long id) { //get by id<------------
        logger.trace("findById executed");
        LeaveTypeDto leaveTypeDto = new LeaveTypeDto();

        if(!leaveTypeRepo.existsById(id)){
            logger.trace("id not found");
//            ResponseDto responseDto=new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Leave Type not found",id);
//            leaveTypeDto.setResponseDto(responseDto);
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Leave Type not found",id);
        }

        LeaveType leaveType = leaveTypeRepo.getById(id);
        BeanUtils.copyProperties(leaveType, leaveTypeDto);
        ResponseDto responseDto=new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Leave Type retrieved",leaveTypeDto);
       // leaveTypeDto.setResponseDto(responseDto);
        return responseDto ;

    }


    public Object save(LeaveTypeDto leaveTypeDto) {//post or save<-----------

        try {
            leaveTypeDto=conv(leaveTypeRepo.save(conv(leaveTypeDto)));
            return  new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "leave type created",  leaveTypeDto);

        }
        catch(Exception e) {
            return  new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Application creation is not possible", e);
        }

    }

//    public LeaveTypeDto update(LeaveTypeDto leaveTypeDto) {//put or update<------------
//        return conv(leaveTypeRepo.saveAndFlush(conv(leaveTypeDto)));
//    }

    public Object update(LeaveTypeDto leaveTypeDto, Long id) {//put or update<------------
        logger.trace(" update executed from service");
        if(!leaveTypeRepo.existsById(id)) {
            logger.trace(" id not found");
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Update is not possible",id);
        }
        LeaveType leaveType = leaveTypeRepo.getById(id);
        BeanUtils.copyProperties(leaveTypeDto, leaveType,"id","responseDto");
        leaveType.setId(id);

        leaveType=leaveTypeRepo.save(leaveType);

        BeanUtils.copyProperties(leaveType, leaveTypeDto,"responseDto");
        ResponseDto responseDto=new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Update done",leaveTypeDto);
       // leaveTypeDto.setResponseDto(responseDto);

        return responseDto;
    }


    public Object deleteById(Long id) { //delete<--------------
        logger.trace(" deleteById executed from service");

        if(!leaveTypeRepo.existsById(id)) {
            logger.trace(" id is not found");
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Delete is not possible",id);
        }

        return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Deletion complete",id);
    }
    }
    

