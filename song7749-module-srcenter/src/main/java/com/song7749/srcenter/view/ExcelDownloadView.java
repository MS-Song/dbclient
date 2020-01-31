package com.song7749.srcenter.view;


import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.song7749.util.LogMessageFormatter.format;

/**
 * <pre>
 * Class Name : ExcelDownloadView
 * Description :
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier				Comment
 *  -----------------------------------------------
 *  15/01/2020		song7749@gmail.com		    NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 15/01/2020
 */

public class ExcelDownloadView extends AbstractXlsxStreamingView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = "sr_data_request_" + format.format(new Date()) + ".xlsx";

        Sheet sheet = workbook.createSheet();
        // 행 생성
        Row row;
        // 쎌 생성
        Cell cell;
        //스타일 설정 -- header
        CellStyle styleOfColorHeader = workbook.createCellStyle();
        //정렬
        styleOfColorHeader.setAlignment(HorizontalAlignment.CENTER);
        styleOfColorHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        //테두리 라인
        styleOfColorHeader.setBorderRight(BorderStyle.THIN);
        styleOfColorHeader.setBorderLeft(BorderStyle.THIN);
        styleOfColorHeader.setBorderTop(BorderStyle.THIN);
        styleOfColorHeader.setBorderBottom(BorderStyle.THIN);
        styleOfColorHeader.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
        styleOfColorHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //스타일 설정 -- header
        CellStyle styleOfColorBody = workbook.createCellStyle();
        //정렬
        styleOfColorBody.setAlignment(HorizontalAlignment.CENTER);
        styleOfColorBody.setVerticalAlignment(VerticalAlignment.CENTER);
        //테두리 라인
        styleOfColorBody.setBorderRight(BorderStyle.THIN);
        styleOfColorBody.setBorderLeft(BorderStyle.THIN);
        styleOfColorBody.setBorderTop(BorderStyle.THIN);
        styleOfColorBody.setBorderBottom(BorderStyle.THIN);
        styleOfColorBody.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        styleOfColorBody.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 엑셀 생성 시작
        int listLoop=0;
        int mapLoop=0;
        for(Map<?, ?> data : (List<Map<String, String>>)model.get("rows")) {
            // 첫번째 loop 인 경우에는 header 를 만든다.
            if(listLoop==0) {
                row = sheet.createRow(listLoop);
                // 색상 지정
                for(String key : ((Map<String, String>)data).keySet()) {
                    cell = row.createCell(mapLoop);
                    cell.setCellValue(key == null ? "" : key);
                    cell.setCellStyle(styleOfColorHeader);
                    mapLoop++;
                }
                mapLoop=0;
            }
            // 색상 지정
            // 그 외에는 body 를 만든다.
            row = sheet.createRow(listLoop+1);
            for(String key : ((Map<String, String>)data).keySet()) {
                cell = row.createCell(mapLoop);
                cell.setCellValue(null== data.get(key) ? "" : (String)data.get(key));
                cell.setCellStyle(styleOfColorBody);
                //sheet.autoSizeColumn(mapLoop);
                mapLoop++;
            }
            listLoop++;
            mapLoop=0;
        }

        try {
            response.setHeader("Content-Disposition", "attachement; filename=\""
                    + java.net.URLEncoder.encode(fileName, "UTF-8") + "\";charset=\"UTF-8\"");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("엑셀 파일 생성에 실패 했습니다.");
        }
    }
}
