package sample;

import java.io.*;
import java.util.Date;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;

/**
 * Created by Maria on 30/07/2015.
 */
public class recibo {
    public static void main(String[] args, Integer nrecibo, String locexp, Double importe, String fechaexp, String vencimiento, Double euros, String concepto, String persona, String domicilio, Integer ncuenta, String personapag, String domiciliopag, String nombreRecibo) {
        try {
            HSSFDataFormatter formatter = new HSSFDataFormatter();
            FileOutputStream fileOut = new FileOutputStream(nombreRecibo +  ".xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Recibo");
            HSSFFont font = workbook.createFont();

            //ESTILO EUROS Y CONCEPTO
            HSSFCellStyle b = workbook.createCellStyle();
            HSSFFont f = workbook.createFont();
            f.setFontName("Calibri");
            f.setFontHeightInPoints((short) 11);
            b.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            b.setBorderTop(HSSFCellStyle.BORDER_THIN);
            b.setFont(f);

            HSSFCellStyle especifico2 = workbook.createCellStyle();
            HSSFFont especificof2 = workbook.createFont();
            especificof2.setFontName("Calibri");
            especificof2.setFontHeightInPoints((short) 11);
            especifico2.setBorderTop(HSSFCellStyle.BORDER_THIN);
            especifico2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            especifico2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            especifico2.setBorderRight(HSSFCellStyle.BORDER_THIN);
            especifico2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            especifico2.setFont(especificof2);

            HSSFCellStyle lateraliz = workbook.createCellStyle();
            HSSFFont flatealiz = workbook.createFont();
            flatealiz.setFontName("Calibri");
            flatealiz.setFontHeightInPoints((short) 11);
            lateraliz.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            lateraliz.setFont(flatealiz);

            HSSFCellStyle lateralde = workbook.createCellStyle();
            HSSFFont flateralde = workbook.createFont();
            flateralde.setFontName("Calibri");
            flateralde.setFontHeightInPoints((short) 11);
            lateralde.setBorderRight(HSSFCellStyle.BORDER_THIN);
            lateralde.setFont(flateralde);

            HSSFCellStyle dieciochodos = workbook.createCellStyle();
            HSSFFont fdieciochodos = workbook.createFont();
            fdieciochodos.setFontName("Calibri");
            fdieciochodos.setFontHeightInPoints((short) 11);
            dieciochodos.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            dieciochodos.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            dieciochodos.setFont(fdieciochodos);

            HSSFCellStyle dieciochodoce = workbook.createCellStyle();
            HSSFFont fdieciochodoce = workbook.createFont();
            fdieciochodoce.setFontName("Calibri");
            fdieciochodoce.setFontHeightInPoints((short) 11);
            dieciochodoce.setBorderRight(HSSFCellStyle.BORDER_THIN);
            dieciochodoce.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            dieciochodoce.setFont(fdieciochodoce);

            HSSFCellStyle quncedos = workbook.createCellStyle();
            HSSFFont fquncedos = workbook.createFont();
            fquncedos.setFontName("Calibri");
            fquncedos.setFontHeightInPoints((short) 11);
            quncedos.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            quncedos.setBorderTop(HSSFCellStyle.BORDER_THIN);
            quncedos.setFont(fquncedos);

            HSSFCellStyle quincedoce = workbook.createCellStyle();
            HSSFFont fquincedoce = workbook.createFont();
            fquincedoce.setFontName("Calibri");
            fquincedoce.setFontHeightInPoints((short) 11);
            quincedoce.setBorderRight(HSSFCellStyle.BORDER_THIN);
            quincedoce.setBorderTop(HSSFCellStyle.BORDER_THIN);
            quincedoce.setFont(fquincedoce);

            HSSFCellStyle bajo = workbook.createCellStyle();
            HSSFFont fbajo = workbook.createFont();
            fbajo.setFontName("Calibri");
            fbajo.setFontHeightInPoints((short) 11);
            bajo.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            bajo.setFont(fbajo);

            HSSFCellStyle arriba = workbook.createCellStyle();
            HSSFFont farriba = workbook.createFont();
            farriba.setFontName("Calibri");
            farriba.setFontHeightInPoints((short) 11);
            arriba.setBorderTop(HSSFCellStyle.BORDER_THIN);
            arriba.setFont(farriba);

            //2/3 lineas
            for( int i=2; i<=3;++i){
                HSSFRow rowprueba4 = worksheet.createRow(i);
                CellRangeAddress uno = new CellRangeAddress(i, i, 2, 4);
                worksheet.addMergedRegion(uno);
                CellRangeAddress dos = new CellRangeAddress(i, i, 5, 8);
                worksheet.addMergedRegion(dos);
                CellRangeAddress tres = new CellRangeAddress(i, i, 9, 10);
                worksheet.addMergedRegion(tres);
                for (int j = 2; j <= 10; ++j) {
                    HSSFCell cell = rowprueba4.createCell(j);
                    if(i==2){
                        if(j==2)    cell.setCellValue("RECIBO NÚMERO");
                        else if (j==5)  cell.setCellValue("LOCALIDAD DE EXPEDICIÓN");
                        else if (j==9)  cell.setCellValue("IMPORTE");
                    }
                    else if(i==3){
                        if(j==2) cell.setCellValue(nrecibo);
                        if(j==5) cell.setCellValue(locexp);
                        if(j==9) cell.setCellValue(importe);
                        cell.setCellStyle(especifico2);
                    }
                }
            }
            //4/5linea
            for( int i=5; i<=6;++i){
                HSSFRow rowprueba4 = worksheet.createRow(i);
                CellRangeAddress cuatro = new CellRangeAddress(i, i, 2, 4);
                worksheet.addMergedRegion(cuatro);
                CellRangeAddress cinco = new CellRangeAddress(i, i, 5, 10);
                worksheet.addMergedRegion(cinco);
                for (int j = 2; j <= 10; ++j) {
                    HSSFCell cell = rowprueba4.createCell(j);
                    if(i==5){
                        if(j==2)    cell.setCellValue("FECHA DE EXPEDICIÓN");
                        else if (j==5)  cell.setCellValue("VENCIMIENTO");
                    }
                    else if(i==6){
                        if(j==2)   cell.setCellValue(fechaexp);
                        else if(j==5)  cell.setCellValue(vencimiento);
                        cell.setCellStyle(especifico2);
                    }
                }
            }

            //EUROS Y SU LINEA
            for( int i=8; i<=9;++i){
                HSSFRow rowprueba2 = worksheet.createRow(i);
                CellRangeAddress uno = new CellRangeAddress(i, i, 2, 10);
                worksheet.addMergedRegion(uno);
                CellRangeAddress dos = new CellRangeAddress(i, i, 2, 10);
                worksheet.addMergedRegion(dos);
                for (int j = 2; j <= 10; ++j) {
                    HSSFCell cell = rowprueba2.createCell(j);
                    if(i==8 && j ==2)   cell.setCellValue("EUROS");
                    else if (i==9) {
                        if(j==2) cell.setCellValue(euros);
                        cell.setCellStyle(b);    //Para hacer las rallas
                    }
                }
            }
            //CONCEPTO Y SU LINEA
            for( int i=11; i<=12;++i){
                HSSFRow rowprueba3 = worksheet.createRow(i);
                CellRangeAddress tres = new CellRangeAddress(i, i, 2, 10);
                worksheet.addMergedRegion(tres);
                CellRangeAddress cuatro = new CellRangeAddress(i, i, 2, 10);
                worksheet.addMergedRegion(cuatro);
                for (int j = 2; j <= 10; ++j) {
                    HSSFCell cell = rowprueba3.createCell(j);
                    if (i == 11 && j == 2) cell.setCellValue("CONCEPTO");
                    else if (i==12) {
                        cell.setCellValue(concepto);
                        cell.setCellStyle(b);    //Para hacer las rallas
                    }
                }
            }
            //Linea pagadero
            HSSFRow rowprueba4 = worksheet.createRow(14);
            worksheet.addMergedRegion(new CellRangeAddress(14, 14, 2, 10));
            worksheet.addMergedRegion(new CellRangeAddress(20, 20, 11, 12));
            for (int j = 2; j <= 12; ++j) {
                HSSFCell cell = rowprueba4.createCell(j);
                if (j == 2) cell.setCellValue("PAGADERO EN: (persona o entidad y domicilio");
                else if(j==11)  cell.setCellValue("C.C.C");
            }

            //Recuadro gigante
            for( int i=15; i<=16;++i){
                HSSFRow rowprueba3 = worksheet.createRow(i);
                CellRangeAddress tres = new CellRangeAddress(i, i, 2, 6);
                worksheet.addMergedRegion(tres);
                CellRangeAddress cinco = new CellRangeAddress(i, i, 7, 8);
                worksheet.addMergedRegion(cinco);
                CellRangeAddress cuatro = new CellRangeAddress(i, i, 9, 10);
                worksheet.addMergedRegion(cuatro);
                CellRangeAddress seis = new CellRangeAddress(i, i, 11, 12);
                worksheet.addMergedRegion(seis);
                for (int j = 2; j <= 12; ++j) {
                    HSSFCell cell = rowprueba3.createCell(j);
                    if (i == 15 && j == 7) cell.setCellValue("ENT.");
                    else if (i == 15 && j == 9) cell.setCellValue("OF.");
                    else if (i == 15 && j == 11) cell.setCellValue("DC.");
                    if(i==15)   cell.setCellStyle(arriba);
                    if(j==2)    cell.setCellStyle(lateraliz);
                    if(j==12)   cell.setCellStyle(lateralde);
                    if(i==15 && j==2)   cell.setCellStyle(quncedos);
                    else if(i==15 && j==12) cell.setCellStyle(quincedoce);
                }

            }

            for( int i=17; i<=18;++i){
                HSSFRow rowprueba3 = worksheet.createRow(i);
                CellRangeAddress tres = new CellRangeAddress(i, i, 2, 6);
                worksheet.addMergedRegion(tres);
                CellRangeAddress cinco = new CellRangeAddress(i, i, 7, 12);
                worksheet.addMergedRegion(cinco);
                for (int j = 2; j <= 12; ++j) {
                    HSSFCell cell = rowprueba3.createCell(j);
                    if (i == 17 && j == 7) cell.setCellValue("Nº DE CUENTA");
                    if(i==18)   cell.setCellStyle(bajo);
                    if(j==2)    cell.setCellStyle(lateraliz);
                    if(j==12)   cell.setCellStyle(lateralde);
                    if(i==18 && j==2)   cell.setCellStyle(dieciochodos);
                    else if(i==18 && j==7)    cell.setCellValue(ncuenta);
                    else if(i==18 && j==12) cell.setCellStyle(dieciochodoce);
                }
            }

            HSSFRow rowprueba6 = worksheet.createRow(20);
            worksheet.addMergedRegion(new CellRangeAddress(20, 20, 2, 7));
            worksheet.addMergedRegion(new CellRangeAddress(20, 20, 8, 12));
            for (int j = 2; j <= 12; ++j) {
                HSSFCell cell = rowprueba6.createCell(j);
                if (j == 2) cell.setCellValue("NOMBRE Y DOMICILIO DEL PAGADOR");
                if(j==8)    cell.setCellValue("ALEJOGALANTE, S.L.");
            }

            for( int i=21; i<=26;++i){
                HSSFRow rowprueba7 = worksheet.createRow(i);
                CellRangeAddress tres = new CellRangeAddress(i, i, 2, 6);
                worksheet.addMergedRegion(tres);
                CellRangeAddress cinco = new CellRangeAddress(22, 22, 8, 12);
                worksheet.addMergedRegion(cinco);
                for (int j = 2; j <= 12; ++j) {
                    HSSFCell cell = rowprueba7.createCell(j);
                    if(i==22 && j==8)   cell.setCellValue("p.p.");
                    if(i==26 && j <= 6)   cell.setCellStyle(bajo);
                    if(i==21 && j <= 6)   cell.setCellStyle(arriba);
                    if(j==2)    cell.setCellStyle(lateraliz);
                    if(j==6)   cell.setCellStyle(lateralde);
                    if(i==21 && j==2)   cell.setCellStyle(quncedos);
                    else if(i==21 && j==6)   cell.setCellStyle(quincedoce);
                    if(i==26 && j==2)   cell.setCellStyle(dieciochodos);
                    else if(i==26 && j==6) cell.setCellStyle(dieciochodoce);
                }
            }
            //worksheet.autoSizeColumn(0);

            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
