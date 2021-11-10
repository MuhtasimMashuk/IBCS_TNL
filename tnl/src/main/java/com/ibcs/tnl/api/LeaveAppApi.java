package com.ibcs.tnl.api;

import com.ibcs.tnl.dto.FeignResponseDto;
import com.ibcs.tnl.dto.LeaveAppDto;
import com.ibcs.tnl.dto.ResponseDto;
import com.ibcs.tnl.service.LeaveAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaveAppApi")
public class LeaveAppApi {

    @Autowired
    private LeaveAppService leaveAppService;
    Logger logger = LoggerFactory.getLogger(LeaveAppApi.class);

    @GetMapping("/all/")
    Page<LeaveAppDto> getAll() {
        return leaveAppService.findAll(PageRequest.of(0, 10), null);
    }

    @GetMapping("/all/list/")
    List<LeaveAppDto> getAllList() {
        return leaveAppService.findAllList();
    }
/*
    @GetMapping("/{id}")
    LeaveAppDto findById(@PathVariable Long id) {
        return leaveAppService.findById(id);
    }
*/

    @GetMapping("/{id}")
    FeignResponseDto findByIdWithemp(@PathVariable Long id) {
        logger.trace("Okay from findByIdWithemp ");


            return leaveAppService.findLeaveAppWithEmp(id);


    }


    @PostMapping("/create/")
    public Object createLeaveApp(@RequestBody LeaveAppDto newLeaveAppDto) {
        logger.trace("Okay from createLeaveApp ");
        return leaveAppService.save(newLeaveAppDto);
    }


    @PutMapping("/update/{id}")
    public Object updateLeaveApp(@RequestBody LeaveAppDto newLeaveAppDto, @PathVariable Long id) {

        logger.trace("Okay from updateLeaveApp");
        return leaveAppService.update(newLeaveAppDto, id);

    }

    @DeleteMapping("/delete/{id}")
    ResponseDto deleteLeaveApp(@PathVariable Long id) {
        logger.trace("Okay from deleteLeaveApp");
        return leaveAppService.deleteById(id);
    }



}
