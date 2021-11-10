package com.ibcs.hr.service;

import com.ibcs.hr.consume.UserConsume;
import com.ibcs.hr.dto.EmpDto;
import com.ibcs.hr.dto.FeignResponseDto;
import com.ibcs.hr.dto.ResponseDto;
import com.ibcs.hr.dto.UserDto;
import com.ibcs.hr.entity.Emp;
import com.ibcs.hr.repo.DeptRepo;
import com.ibcs.hr.repo.DesgRepo;
import com.ibcs.hr.repo.EmpRepo;
import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmpService {
    Logger logger = LoggerFactory.getLogger(EmpService.class);
    @Autowired
    private EmpRepo empRepo;
    @Autowired
    private DeptRepo deptRepo;
    @Autowired
    private DesgRepo desgRepo;

    @Autowired
    private UserConsume userConsume;
    @Autowired
    DataSource dataSource;

    private EmpDto conv(Emp emp) {
        EmpDto empDto = new EmpDto();
        BeanUtils.copyProperties(emp, empDto, "deptId", "desgId", "supervisorId","gender");
        empDto.setDeptId(emp.getDeptId().getId());
        empDto.setDesgId(emp.getDesgId().getId());
        empDto.setSupervisorId(emp.getSupervisorId().getId());
        empDto.setGender(emp.getGender().name());

        return empDto;
    }




    public Page<EmpDto> findAll(Pageable pageable, String sText) {
        Page<Emp> emp = empRepo.findAllCustom(pageable, sText);
        logger.trace("findAll executed");
        //PageRequest<EmpDto> empDtos= PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        List<EmpDto> ss = new ArrayList(pageable.getPageSize());
        for (Emp pp : emp.getContent()) {
            ss.add(conv(pp));
        }

        Page<EmpDto> empDtos = new PageImpl(ss, pageable, emp.getTotalElements());

        return empDtos;
    }

    public List<EmpDto> findAllWithoutPage(){

        List<EmpDto> empDtoList = new ArrayList<>();
        List<Emp> empList = empRepo.findAll();

        for(Emp emp: empList){
            EmpDto empDto=new EmpDto();
            BeanUtils.copyProperties(emp, empDto, "deptId", "desgId", "supervisorId","gender","responseDto");
            empDto.setDeptId(emp.getDeptId().getId());
            empDto.setDesgId(emp.getDesgId().getId());
            empDto.setSupervisorId(emp.getSupervisorId().getId());
            empDto.setGender(emp.getGender().name());
            empDtoList.add(empDto);
        }
       return empDtoList;
    }

    public Object findById(Long id) {
        logger.trace("findById executed");
        EmpDto empDto = new EmpDto();

        if(!empRepo.existsById(id)){
            logger.trace("id not found");

            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Employee not found",id);
        }
        Emp emp= empRepo.getById(id);
        BeanUtils.copyProperties(emp, empDto, "deptId", "desgId", "supervisorId","gender","responseDto");
        empDto.setDeptId(emp.getDeptId().getId());
        empDto.setDesgId(emp.getDesgId().getId());
        empDto.setSupervisorId(emp.getSupervisorId().getId());
        empDto.setGender(emp.getGender().name());

     //   return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Employee found",empDto);
        return empDto;

    }

    public Object empWithUser(Long id){

        logger.trace("empWithUser executed");
        if(!empRepo.existsById(id)){
            logger.trace("id not found");
            ;
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Employee not found",id);
        }
        Emp emp= empRepo.getById(id);
        EmpDto empDto = new EmpDto();
        BeanUtils.copyProperties(emp, empDto, "deptId", "desgId", "supervisorId","gender","responseDto");
        empDto.setDeptId(emp.getDeptId().getId());
        empDto.setDesgId(emp.getDesgId().getId());
        empDto.setSupervisorId(emp.getSupervisorId().getId());
        empDto.setGender(emp.getGender().name());

        FeignResponseDto feignResponseDto=new FeignResponseDto();
        feignResponseDto.setEmpDto(empDto);
        UserDto userDto = userConsume.getUserFromAdmin(emp.getUserId()); //for feign client
        feignResponseDto.setUserDto(userDto);
        return feignResponseDto;



    }

    public Object save(EmpDto empDto) {
        logger.trace(" save executed from service");
        Emp emp = new Emp();
        try{
            BeanUtils.copyProperties(empDto, emp, "deptId", "desgId", "supervisorId","gender");
            emp.setDeptId(deptRepo.getById(empDto.getDeptId()));
            emp.setDesgId(desgRepo.getById(empDto.getDesgId()));
            emp.setSupervisorId(empRepo.getById(empDto.getSupervisorId()));
            emp.setGender(Emp.Gender.valueOf(empDto.getGender()));
            emp=empRepo.save(emp);

            BeanUtils.copyProperties(emp, empDto);
            return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Employee created", empDto);

        }
        catch (Exception e)
        {

            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Employee creation is not possible", e);
        }

    }


    public Object update(EmpDto empDto, Long id) {
        logger.trace(" updated executed from service");
        if(!empRepo.existsById(id)){
            logger.trace("id not found");

            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Employee not found",empDto);
        }
        Emp emp = empRepo.getById(id);
        BeanUtils.copyProperties(empDto, emp, "id", "deptId", "desgId", "supervisorId","gender");
        emp.setDeptId(deptRepo.getById(empDto.getDeptId()));
        emp.setDesgId(desgRepo.getById(empDto.getDesgId()));
        emp.setSupervisorId(empRepo.getById(empDto.getSupervisorId()));
        emp.setGender(Emp.Gender.valueOf(empDto.getGender()));
        emp= empRepo.save(emp);


        BeanUtils.copyProperties(emp, empDto, "deptId", "desgId", "supervisorId","gender");
        empDto.setDeptId(emp.getDeptId().getId());
        empDto.setDesgId(emp.getDesgId().getId());
        empDto.setSupervisorId(emp.getSupervisorId().getId());
        empDto.setGender(emp.getGender().name());

        return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Employee data updated",empDto);
       // return conv(empRepo.save(emp));
    }

    public ResponseDto deleteById(Long id) { //delete<--------------
        logger.trace(" deleteById executed from service");

        if(!empRepo.existsById(id)) {
            logger.trace(" id is not found");
            return new ResponseDto(ResponseDto.ResponseStatus.ERROR, "Delete is not possible",id);
        }

        return new ResponseDto(ResponseDto.ResponseStatus.SUCCESS, "Deletion complete",id);
    }


//    //--------------------------------Report Part-------------------------//
//    ///@@ Service Part////
//    // Export Report With Department
//    public ResponseEntity exportReportWithDepartment(String reportFormat, Integer deptId) throws FileNotFoundException, JRException, SQLException {
//        String path = "D:\\project\\IBCS\\IBCS-TNL\\hr\\src\\main\\resources";
////        Connection connection = null;
////        try {
////            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Qwer123#");
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
//        //load file and compile it
//        File file = ResourceUtils.getFile("classpath:no1.jrxml");
//        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("P_DEPT", deptId);
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
//        int i = (int) Math.random();
//
//        System.out.println("Random::"+i);
//        if (reportFormat.equalsIgnoreCase("html")) {
//            path = path + "\\employeeDept"+i+".html";
//            JasperExportManager.exportReportToHtmlFile(jasperPrint, path);
//        }else {
//            path = path + "\\employeeDept"+i+".pdf";
//            JasperExportManager.exportReportToPdfFile(jasperPrint, path);
//        }
//        File file1 = new File(path);
//        Path fPath1 = Paths.get(file1.getAbsolutePath());
//        ByteArrayResource resource = null;
//        try {
//            resource = new ByteArrayResource(Files.readAllBytes(fPath1));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("content-disposition", "attachment; filename=" + "employeeDept.pdf");
//        dataSource.getConnection().close();
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentLength(file1.length())
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(resource);
//    }
}
