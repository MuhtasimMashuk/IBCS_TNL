package com.ibcs.hr.api;

import com.ibcs.hr.dto.DeptDto;
import com.ibcs.hr.dto.DesgDto;
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
@RequestMapping("/desgApi")

public class DesgApi {
    Logger logger = LoggerFactory.getLogger(DesgApi.class);
    @Autowired
    private DesgService desgService;

    @GetMapping("/")
    Page<DesgDto> getAll() {

        logger.trace("getAll executed");
        return desgService.findAll(PageRequest.of(0, 10), null);
    }


    @GetMapping("/list/")
    List<DesgDto> getAllWithoutPage() {

        logger.trace("getAll executed");
        return desgService.findAllwihoutPage();
    }



    @PostMapping("/")
    Object createDesg(@RequestBody DesgDto newDesg) {
        logger.trace("getAll executed");
        return desgService.save(newDesg);
    }

    @GetMapping("/{id}")
    Object getOne(@PathVariable Long id) {
        logger.trace("getOne executed");
        return desgService.findById(id);
    }

    @PutMapping("/{id}")
    Object updateDesg(@RequestBody DesgDto newDesg, @PathVariable Long id) {
        logger.trace("update executed");
        return desgService.update(newDesg,id);
    }

    @DeleteMapping("/{id}")
    ResponseDto deleteDesg(@PathVariable Long id) {

        logger.trace("delete executed");

        return desgService.deleteById(id);
    }

}
