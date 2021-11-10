package com.ibcs.hr.api;

import com.ibcs.hr.dto.EmpDto;
import com.ibcs.hr.dto.ReportDto;

import com.ibcs.hr.dto.UserDto;
import com.ibcs.hr.service.EmpService;
import com.ibcs.hr.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.io.FileNotFoundException;
import java.sql.SQLException;


@RestController
@RequestMapping("/reportApi")
public class ReportApi {

    @Autowired
    private ReportService reportService;

//   http://localhost:9090/hr/empApi/reportDept?deptId=1&type=pdf
    @PostMapping("/report")
    public ResponseEntity exportReport( @RequestBody ReportDto reportDto) throws FileNotFoundException, JRException, SQLException {
        System.out.println("reportDto"+ reportDto);

        return reportService.exportReport(reportDto);
    }




}
