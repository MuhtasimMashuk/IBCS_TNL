package com.ibcs.hr.api;

import com.ibcs.hr.dto.DeptDto;
import com.ibcs.hr.dto.EmpDto;
import com.ibcs.hr.dto.ResponseDto;
import com.ibcs.hr.service.DeptService;
import com.ibcs.hr.service.DesgService;
import com.ibcs.hr.service.EmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deptApi")

public class DeptApi {

    Logger logger = LoggerFactory.getLogger(DeptApi.class);
    @Autowired
    private DeptService deptService;

    @GetMapping("/")
    Page<DeptDto> getAll() {
        logger.trace(" getAll executed from dept Api");
        return deptService.findAll(PageRequest.of(0, 10), null);
    }
    @GetMapping("/list/")
    List<DeptDto> getAllWithoutPage() {
        logger.trace(" getAll executed from dept Api");
        return deptService.findAllWithoutPage();
    }
    @PostMapping("/")
    Object createDept(@RequestBody DeptDto newDept) {
        logger.trace(" createDept executed");
        return deptService.save(newDept);
    }

    @GetMapping("/{id}")
    Object getOne(@PathVariable Long id) {
        logger.trace(" getOne executed");
        return deptService.findById(id);
    }

    @PutMapping("/{id}")
    Object updateDept(@RequestBody DeptDto newDept, @PathVariable Long id) {
        logger.trace(" update dept executed");
        return deptService.update(newDept, id);
    }

    @DeleteMapping("/{id}")
    ResponseDto deleteDept(@PathVariable Long id) {
        logger.trace("delete dept executed");
        return deptService.deleteById(id);
    }

}
