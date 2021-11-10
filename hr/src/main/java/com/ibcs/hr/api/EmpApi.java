package com.ibcs.hr.api;

import com.ibcs.hr.dto.DeptDto;
import com.ibcs.hr.dto.EmpDto;
import com.ibcs.hr.dto.ResponseDto;
import com.ibcs.hr.entity.Emp;
import com.ibcs.hr.service.EmpService;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/empApi")
public class EmpApi {



    Logger logger = LoggerFactory.getLogger(EmpApi.class);
    @Autowired
    private EmpService empService;

    @GetMapping("/")
    public Page<EmpDto> getAll() {
        logger.trace("getAll executed from api");
        return empService.findAll(PageRequest.of(0, 10), null);
    }
    @GetMapping("/list/")
    public List<EmpDto> getAllWithoutPage() {
        logger.trace("getAll executed from api");
        return empService.findAllWithoutPage();
    }

    @GetMapping("/{id}")
    public Object getOne(@PathVariable Long id) {
        logger.trace("getOne executed from api");
        return empService.findById(id);
    }

    @GetMapping("/withUser/{id}")
    public Object getEmpWithUser(@PathVariable Long id) {
        logger.trace("getEmpWithUser executed from api");
        return empService.empWithUser(id);
    }

    @PostMapping("/")
    public Object createEmp(@RequestBody EmpDto newEmp) {
        logger.trace("createEmp executed from api");
        return empService.save(newEmp);
    }



    @PutMapping("/{id}")
    public Object updateEmp(@RequestBody EmpDto newEmp, @PathVariable Long id) {
        logger.trace("updateEmp executed from api");
        return empService.update(newEmp, id);
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteEmp(@PathVariable Long id) {

        logger.trace("delteEmp executed from api");
        return empService.deleteById(id);
    }

    //------------------------------------------------------------
//    @GetMapping("/reportDept")
//    public ResponseEntity generateReportDept(@QueryParam("deptId") Integer deptId, @QueryParam("type") String type) throws FileNotFoundException, JRException, SQLException {
//        return empService.exportReportWithDepartment(type, deptId);
//    }
}
