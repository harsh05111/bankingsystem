package com.example.bankingsystem.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankingsystem.entity.transaction;
import com.example.bankingsystem.entity.user;
import com.example.bankingsystem.repository.transactionrepository;
import com.example.bankingsystem.repository.userrepository;

@Service
// @Slf4js
public class excelservice {

    @Autowired
    private userrepository userrepository;

    @Autowired
    private transactionrepository transactionrepository;

    @Autowired
    private userservice userservice;

    public ByteArrayOutputStream generateexcel () throws Exception{
        List<user> a = userrepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees");


       Row header = sheet.createRow(0);
       header.createCell(0).setCellValue("Id");
       header.createCell(1).setCellValue("full name");
       header.createCell(2).setCellValue("email");
       header.createCell(3).setCellValue("Phone number");
       header.createCell(4).setCellValue("Bank balance");
       header.createCell(5).setCellValue("Roles");

       int rownum = 1;
       for(user user :a){
        
        Row row = sheet.createRow(rownum++);
        String ObjectIdString = user.getId().toString();
        String bank = user.getBankbalance().toString();
        String roles = user.getRoles().toString();

        row.createCell(0).setCellValue(ObjectIdString);
        row.createCell(1).setCellValue(user.getFullname());
        row.createCell(2).setCellValue(user.getEmail());
        row.createCell(3).setCellValue(user.getPhonenumber());
        row.createCell(4).setCellValue(bank);
        row.createCell(5).setCellValue(roles);
       }
       
       ByteArrayOutputStream outputscreen = new ByteArrayOutputStream();
       workbook.write(outputscreen);
       workbook.close();

        
        return outputscreen;
    }

    public ByteArrayOutputStream usertrasactionexcel() throws Exception{

        List<transaction> a = transactionrepository.findAll();
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Trasaction history");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Id");
        header.createCell(1).setCellValue("Amount");
        header.createCell(2).setCellValue("Type");
        header.createCell(3).setCellValue("Time");
        header.createCell(4).setCellValue("Description");
        int rownum = 1;
        for(transaction  user :a){
        Row row = sheet.createRow(rownum++);

        String id = user.getId().toString();
        String amount = user.getAmount().toString();
        String time = user.getTime().toString();
        row.createCell(0).setCellValue(id);
        row.createCell(1).setCellValue(amount);
        row.createCell(1).setCellValue(user.getType());
        row.createCell(1).setCellValue(time);
        row.createCell(1).setCellValue(user.getDescription());
        }

        ByteArrayOutputStream outputscreen = new ByteArrayOutputStream();
        workbook.write(outputscreen);
        workbook.close();
        return outputscreen;

    }
}
