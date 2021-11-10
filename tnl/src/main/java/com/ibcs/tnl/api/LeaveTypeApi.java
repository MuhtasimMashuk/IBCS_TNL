package com.ibcs.tnl.api;

import com.ibcs.tnl.dto.LeaveTypeDto;
import com.ibcs.tnl.dto.ResponseDto;
import com.ibcs.tnl.service.LeaveTypeService;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaveTypeApi")
public class LeaveTypeApi {

    Logger logger = LoggerFactory.getLogger(LeaveTypeApi.class);
    @Autowired
    private LeaveTypeService leaveTypeService;

    @GetMapping("/")
    Page<LeaveTypeDto> getAll() {
        logger.trace("get all executed");
        return leaveTypeService.findAll(PageRequest.of(0, 10), null);
    }
    @GetMapping("/list/")
    List<LeaveTypeDto> getAllList() {
        logger.trace("get all executed");
        return leaveTypeService.findAllList();
    }


    @GetMapping("/{id}")
    Object getOne(@PathVariable Long id) {
        logger.trace("get one executed");
        return leaveTypeService.findById(id);
    }

    @PostMapping("/")
    Object createLeaveType(@RequestBody LeaveTypeDto newLeaveTypeDto) {
        logger.trace("create leave type executed");
        return leaveTypeService.save(newLeaveTypeDto);
    }


    @PutMapping("/{id}")
    Object updateLeaveType(@RequestBody LeaveTypeDto newLeaveTypeDto, @PathVariable Long id) {
//    LeaveTypeDto replaceDesg(@RequestBody Desg newDesg, @PathVariable Long id) {

        //return leaveTypeService.update(newLeaveTypeDto);
        logger.trace("update leave type executed");
        return leaveTypeService.update(newLeaveTypeDto, id);

    }

    @DeleteMapping("/{id}")
    Object deleteLeaveType(@PathVariable Long id) {
        logger.trace("Delete method executed");
        return leaveTypeService.deleteById(id);
    }
}
