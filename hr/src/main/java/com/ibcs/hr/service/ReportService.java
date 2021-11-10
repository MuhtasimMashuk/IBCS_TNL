package com.ibcs.hr.service;


import com.ibcs.hr.dto.EmpDto;
import com.ibcs.hr.dto.ReportDto;

import com.ibcs.hr.dto.UserDto;
import com.ibcs.hr.entity.Emp;
import com.ibcs.hr.repo.DeptRepo;
import com.ibcs.hr.repo.DesgRepo;
import com.ibcs.hr.repo.EmpRepo;

import lombok.var;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;


import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ReportService {

    @Autowired
    private DataSource dataSource;

    public ResponseEntity exportReport(ReportDto reportDto) throws FileNotFoundException, JRException, SQLException {
        // String path = "D:\\Projects\\JavaProject\\IBCS-TNL\\hr\\src\\main\\resources";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Qwer123#");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //load file and compile it
        File file = ResourceUtils.getFile("classpath:" + reportDto.getName());
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, reportDto.getParams(), connection);

        String fileName = System.currentTimeMillis() + ".pdf";

        // if (reportDto.getFormat().equals("html")) {
        //     JasperExportManager.exportReportToHtmlFile(jasperPrint, fileName);
        // } else {
        JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);
        // }
        File file1 = new File(fileName);

        try {
            return generateResponse(Files.readAllBytes(Paths.get(file1.getAbsolutePath())), fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public ResponseEntity<byte[]> generateResponse(byte[] bytes, String filename) {

        var contentDisposition = ContentDisposition.builder("attachment").filename(filename).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        return ResponseEntity.ok()
//                .header("Content-Type", "application/pdf; charset=UTF-8")
                .headers(headers)
                .body(bytes);
    }
}