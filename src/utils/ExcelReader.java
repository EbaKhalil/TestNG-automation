package utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    public static Object[][] readExcelData(String filePath, String sheetName) {
        Object[][] data = null;
        FileInputStream fis = null;
        Workbook workbook = null;

        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
            
            int rowCount = ((org.apache.poi.ss.usermodel.Sheet) sheet).getPhysicalNumberOfRows();
            data = new Object[rowCount - 1][2]; 

            for (int i = 1; i < rowCount; i++) {
                Row row = ((org.apache.poi.ss.usermodel.Sheet) sheet).getRow(i);
                data[i - 1][0] = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                data[i - 1][1] = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) workbook.close();
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return data;
    }
}


