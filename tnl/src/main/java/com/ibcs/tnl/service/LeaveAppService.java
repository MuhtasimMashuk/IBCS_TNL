package com.ibcs.tnl.service;

import com.ibcs.tnl.api.LeaveAppApi;
import com.ibcs.tnl.dto.EmpDto;
import com.ibcs.tnl.dto.FeignResponseDto;
import com.ibcs.tnl.dto.LeaveAppDto;
import com.ibcs.tnl.dto.ResponseDto;
import com.ibcs.tnl.entity.LeaveApp;
import com.ibcs.tnl.repo.LeaveAppRepo;
import com.ibcs.tnl.repo.LeaveTypeRepo;
import com.ibcs.tnl.consume.EmpConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaveAppService {
    Logger logger = LoggerFactory.getLogger(LeaveAppApi.class);
    @Autowired
    private LeaveAppRepo leaveAppRepo;

    @Autowired
    private LeaveTypeRepo leaveTypeRepo;

    @Autowired
    private EmpConsumer empConsumer; // have to be active for feign client

    @Autowired
    private WebClient.Builder webClientBuilder;
    //WebClient webClient = WebClient.create("http://localhost:9091"); //another way of webclient

    private LeaveAppDto conv(LeaveApp leaveApp) {//converting<-------------
        LeaveAppDto leaveAppDto = new LeaveAppDto();
        BeanUtils.copyProperties(leaveApp, leaveAppDto, "leaveTypeId");
        leaveAppDto.setLeaveTypeId(leaveApp.getLeaveTypeId().getId());
        return leaveAppDto;
    }

    private LeaveApp conv(LeaveAppDto leaveAppDto) {//converting<--------------

        LeaveApp leaveApp = new LeaveApp();

        BeanUtils.copyProperties(leaveAppDto, leaveApp, "leaveTypeId");
        leaveApp.setLeaveTypeId(leaveTypeRepo.getById(leaveAppDto.getLeaveTypeId()));
        return leaveApp;
    }


    public Page<LeaveAppDto> findAll(Pageable pageable, String sText) {//---------------all
        logger.trace("find all executed from service");
        Page<LeaveApp> leaveApp = leaveAppRepo.findAllCustom(pageable, sText);
        //PageRequest<LeaveAppDto> deptDtos= PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        List<LeaveAppDto> ss = new ArrayList(pageable.getPageSize());
        for (LeaveApp pp : leaveApp.getContent()) {
            ss.add(conv(pp));
        }
        Page<LeaveAppDto> deptDtos = new PageImpl(ss, pageable, leaveApp.getTotalElements());
        return deptDtos;
    }
    public List<LeaveAppDto> findAllList() {//---------------all
        logger.trace("find all executed from service");
        List<LeaveApp> leaveApp = leaveAppRepo.findAll();
        //PageRequest<LeaveAppDto> deptDtos= PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        List<LeaveAppDto> ss = new ArrayList();
        for (LeaveApp pp : leaveApp) {
            ss.add(conv(pp));
        }
        //List<LeaveAppDto> deptDtos = new PageImpl(ss, pageable, leaveApp.getTotalElements());
        return ss;
    }

/*    public LeaveAppDto findById(Long id) { //byId
        LeaveApp leaveApp = leaveAppRepo.getById(id);

        if(leaveApp==null)
        {
            throw new Exception();
        }

        return conv(leaveApp);

    }*/

    public FeignResponseDto findLeaveAppWithEmp(Long id) {//-------------------findLeaveAppWithEmp
        logger.trace(" findLeaveAppWithEmp executed from service");

        FeignResponseDto feignResponseDto = new FeignResponseDto();

        if (!leaveAppRepo.existsById(id)) {
            logger.trace("id not found");
            ResponseDto responseDto = new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Leave Application not found", id);
            feignResponseDto.setResponseDto(responseDto);
            return feignResponseDto;

        }
        LeaveApp leaveApp = leaveAppRepo.getById(id);
        feignResponseDto.setLeaveAppDto(conv(leaveApp));

        EmpDto empDto = webClientBuilder.build()
                .get()
                .uri("http://localhost:9090/hr/empApi/" + leaveApp.getEmployeeId())
                .retrieve()
                .bodyToMono(EmpDto.class)
                .block();


//        EmpDto empDto = empConsumer.getEmp(leaveApp.getEmployeeId()); //for feign client

//

        feignResponseDto.setEmpDto(empDto);

        ResponseDto responseDto = new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Data retrieved", empDto.getUserId());
        feignResponseDto.setResponseDto(responseDto);

        return feignResponseDto;



    /*    Mono<EmpDto> empDto =  webClient.get()
                .uri("/hr/empApi/" + leaveApp.getEmployeeId())
                .retrieve().bodyToMono(EmpDto.class);
*/

    }


    public Object save(LeaveAppDto leaveAppDto) {//-------------------post
        logger.trace(" findLeaveAppWithEmp executed from service");
        try {
                 leaveAppDto =conv(leaveAppRepo.save(conv(leaveAppDto)));;
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Application created", leaveAppDto);
        } catch (Exception e) {
           // ResponseDto responseDto = new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Application creation is not possible", e);
            //leaveAppDto.setResponseDto(responseDto);
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Application creation is not possible", e);
        }

    }

    /*
    public LeaveAppDto update(LeaveAppDto leaveAppDto) {
           return conv(leaveAppRepo.saveAndFlush(conv(leaveAppDto)));
       }
   */
    public Object update(LeaveAppDto leaveAppDto, Long id) {//----------------------update
        logger.trace("update executed from service");
        if (!leaveAppRepo.existsById(id)) {

            //leaveAppDto.setResponseDto(responseDto);
            return  new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Update is not possible", id);
        }
        LeaveApp leaveApp = leaveAppRepo.getById(id);
        BeanUtils.copyProperties(leaveAppDto, leaveApp, "id", "responseDto");
        leaveApp.setLeaveTypeId(leaveTypeRepo.getById(leaveAppDto.getLeaveTypeId()));

        leaveApp = leaveAppRepo.save(leaveApp);

        BeanUtils.copyProperties(leaveApp, leaveAppDto, "leaveTypeId", "responseDto");
        leaveAppDto.setLeaveTypeId(leaveApp.getLeaveTypeId().getId());
      //  ResponseDto responseDto = new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Update done", leaveAppDto.getEmployeeId());
       // leaveAppDto.setResponseDto(responseDto);

        return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Update done", leaveAppDto);
    }


    public ResponseDto deleteById(Long id) {//delete
        logger.trace(" deleteById executed from service");

        if (!leaveAppRepo.existsById(id)) {
            logger.trace(" id is not found");
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Delete is not possible", id);
        }
        return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Deletion complete", id);
    }


}
