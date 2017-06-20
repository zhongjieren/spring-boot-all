package com.lance.email;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SimpleApplication.class)
public class EmailSenderTest {
	private Logger logger = LogManager.getLogger(getClass());
	@Autowired
	private EmailSender emailSender;

	@Test
	public void sender() {
		String to = "zhongjieren@shanghaionstar.com";
		String subject = "Test subject";
		String content = "Hello Spring boot Email.";
		
		boolean result = emailSender.sender(to, subject, content);
		logger.info("-------------======---------------"+result);
	}
	
	
	@Test
	public void sendAttachmentsMail() {
		String toMail = "zhongjieren@shanghaionstar.com";
        String subject="prdexperienceapplyemail";
        String fileName="prdexperienceapplyemail.xls";
        String content= "prdexperienceapplyemail";
        

		HSSFWorkbook workBook = this.createHssf();
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        try {
			workBook.write(baos);
	        baos.flush();  
	        byte[] bt = baos.toByteArray();  
	        InputStream is = new ByteArrayInputStream(bt, 0, bt.length);  
	        baos.close();  

//	        emailSender.sendAttachmentsMail(subject, sendEmailAddress, content, is, fileName);
	        
	        boolean result = emailSender.sendAttachmentsMail(subject, toMail, content, is, fileName);
			
//			boolean result = emailSender.sender(to, subject, content);
			logger.info("-------------======---------------"+result);
	        
		} catch (IOException e) {
//			log.info("apper error Message scheduler.prdexperienceapplyemail processBatch************************************");
//			log.info(e.getMessage()); 
		}
        
		
		
	}
	
	private HSSFWorkbook createHssf() {
//		List<PrdExperienceApplyDomain> list = new 
		String[] excelHeader= {"用户名","电话号码","申请时间"};
		int[] excelHeaderWidths = {150,150,150}; 
		HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        HSSFRow row = sheet.createRow((int) 0);
        HSSFCellStyle style = wb.createCellStyle();
        
        HSSFFont font = wb.createFont();  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        HSSFCellStyle rowstyle = wb.createCellStyle();
        rowstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        for (int i = 0; i < excelHeader.length; i++) {    
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, 32 * excelHeaderWidths[i]);
        }
        
//        if(!CollectionUtils.isEmpty(list)){
//			for (int i = 0; i < list.size(); i++) {
//				row = sheet.createRow(i + 1);
//				PrdExperienceApplyDomain obj = list.get(i);
//				row.createCell(0).setCellValue(obj.getUsername()!=null?obj.getUsername().toString():"");
//				row.createCell(1).setCellValue(obj.getPhone()!=null?obj.getPhone().toString():"");
//				row.createCell(2).setCellValue(obj.getLast_update_date()!=null?obj.getLast_update_date().toString():"");
//			}
//        }
		return wb; 
	}
	
}
