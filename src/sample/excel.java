package sample;

import java.io.*;
import java.util.Date;

import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import java.util.Date;


public class excel {
    private int inicio = 1;
    private int fin = 6;
    private int inicio2 = 7;
    private int fin2 = 8;
    private int cabezeraLista = 1;
    private int inicioLista = 16;
    private int lengthList = 32;
    private int  cont = 0;

    public void datos_factura(HSSFSheet worksheet, String codicliente, int nfactura, String fecha,HSSFCellStyle total,String ncliente,String cifcliente, String direccioncliente, String poblacioncli){
        for (int r = 10; r < 14; ++r){
            HSSFRow row1 = worksheet.createRow((short) r);
            HSSFCell cell1 = row1.createCell(1);
            if(r!=13)cell1.setCellStyle(total);
            worksheet.addMergedRegion(new CellRangeAddress(r, r, 2,3));
            HSSFCell cell2 = row1.createCell(2);
            worksheet.addMergedRegion(new CellRangeAddress(r, r, 5, fin2));
            HSSFCell cell3 = row1.createCell(5);
            if (r == 10) {
                cell1.setCellValue("Nº Factura");
                cell2.setCellValue(nfactura);
                cell3.setCellValue(ncliente);
            }
            else if (r == 11) {
                cell1.setCellValue("Fecha");
                cell2.setCellValue(fecha);
                cell3.setCellValue(direccioncliente);
            }
            else if (r == 12) {
                cell1.setCellValue("Cod.Clietne");
                cell2.setCellValue(codicliente);
                cell3.setCellValue(poblacioncli);
            }
            else {
                cell3.setCellValue(cifcliente);
            }
        }
    }

    public void setWidth(HSSFSheet worksheet){
        worksheet.setColumnWidth(0, 1500);
        worksheet.setColumnWidth(1, 3000);
        worksheet.setColumnWidth(2, 3000);
        worksheet.setColumnWidth(3, 3000);
        worksheet.setColumnWidth(4, 3000);
        worksheet.setColumnWidth(5, 3000);
        worksheet.setColumnWidth(6, 3000);
        worksheet.setColumnWidth(7, 3000);
        worksheet.setColumnWidth(8, 3000);
    }

    public void setImage(HSSFWorkbook workbook, HSSFSheet worksheet) throws IOException{
        InputStream inputStream = new FileInputStream("C:\\Users\\Maria\\Desktop\\foto2.png");
        byte[] bytes = IOUtils.toByteArray(inputStream);
        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
        inputStream.close();
        CreationHelper helper = workbook.getCreationHelper();
        Drawing drawing = worksheet.createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(0);
        anchor.setRow1(0);
        Picture pict = drawing.createPicture(anchor, pictureIdx);
        pict.resize();
    }

    public HSSFCellStyle right_style(HSSFWorkbook workbook){
        HSSFCellStyle right = workbook.createCellStyle();
        HSSFFont ff = workbook.createFont();
        ff.setFontName("Calibri");
        ff.setFontHeightInPoints((short) 11);
        right.setBorderRight(HSSFCellStyle.BORDER_THIN);
        right.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        right.setFont(ff);
        return right;
    }

    public HSSFCellStyle top_style(HSSFWorkbook workbook){
        HSSFCellStyle top = workbook.createCellStyle();
        HSSFFont ff = workbook.createFont();
        ff.setFontName("Calibri");
        ff.setFontHeightInPoints((short) 11);
        top.setBorderTop(HSSFCellStyle.BORDER_THIN);
        top.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        top.setFont(ff);
        return top;
    }

    public HSSFCellStyle no_left_style(HSSFWorkbook workbook){
        HSSFCellStyle especifico = workbook.createCellStyle();
        HSSFFont especificof = workbook.createFont();
        especificof.setFontName("Calibri");
        especificof.setFontHeightInPoints((short) 11);
        especifico.setBorderTop(HSSFCellStyle.BORDER_THIN);
        especifico.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        especifico.setBorderRight(HSSFCellStyle.BORDER_THIN);
        especifico.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        especifico.setFont(especificof);
        return especifico;
    }

    public HSSFCellStyle total_style(HSSFWorkbook workbook){
        HSSFCellStyle especifico = workbook.createCellStyle();
        HSSFFont especificof = workbook.createFont();
        especificof.setFontName("Calibri");
        especificof.setFontHeightInPoints((short) 11);
        especifico.setBorderTop(HSSFCellStyle.BORDER_THIN);
        especifico.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        especifico.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        especifico.setBorderRight(HSSFCellStyle.BORDER_THIN);
        especifico.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        especifico.setFont(especificof);
        return especifico;
    }

    public HSSFCellStyle left_bottom__style(HSSFWorkbook workbook){
        HSSFCellStyle especifico = workbook.createCellStyle();
        HSSFFont especificof = workbook.createFont();
        especificof.setFontName("Calibri");
        especificof.setFontHeightInPoints((short) 11);
        especifico.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        especifico.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        especifico.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        especifico.setFont(especificof);
        return especifico;
    }

    public void fill_not_empty(HSSFSheet worksheet,HSSFCellStyle right){
        int n = inicioLista;
        while (n <= lengthList+inicioLista) {
            HSSFRow rowprueba2 = worksheet.createRow(n);
            CellRangeAddress tres = new CellRangeAddress(n, n, inicio, fin);
            CellRangeAddress cuatro = new CellRangeAddress(n, n, inicio2, fin2);
            worksheet.addMergedRegion(cuatro);
            worksheet.addMergedRegion(tres);
            for (int j = 0; j <= fin2; ++j) {
                HSSFCell cell = rowprueba2.createCell(j);
                cell.setCellStyle(right);
            }
            ++n;
        }
    }

    public void setSpaceFinal(HSSFSheet worksheet){
        worksheet.addMergedRegion(new CellRangeAddress(49, 49, 1, 2));
        worksheet.addMergedRegion(new CellRangeAddress(50, 51, 1, 2));
        worksheet.addMergedRegion(new CellRangeAddress(49, 49, 3, 4));
        worksheet.addMergedRegion(new CellRangeAddress(50, 51, 3, 4));
        worksheet.addMergedRegion(new CellRangeAddress(49, 49, 5, 6));
        worksheet.addMergedRegion(new CellRangeAddress(50, 51, 5, 6));
        worksheet.addMergedRegion(new CellRangeAddress(49, 49, 7, 8));
        worksheet.addMergedRegion(new CellRangeAddress(50, 53, 7, 8));
        worksheet.addMergedRegion(new CellRangeAddress(52, 52, 1, 4));
        worksheet.addMergedRegion(new CellRangeAddress(52, 52, 5, 6));
        worksheet.addMergedRegion(new CellRangeAddress(53, 53, 1, 4));
        worksheet.addMergedRegion(new CellRangeAddress(53, 53, 5, 6));
    }

    public void setMargin(HSSFSheet worksheet){
        worksheet.setMargin(Sheet.LeftMargin, 0.5 /* inches */);
        worksheet.setMargin(Sheet.TopMargin, 0.5 /* inches */);
        worksheet.setMargin(Sheet.BottomMargin, 0.5 /* inches */);
        worksheet.setMargin(Sheet.RightMargin, 0.5 /* inches */);
    }
    public void setFila52(HSSFSheet worksheet,double precioiva, double preciototal,HSSFCellStyle especifico, HSSFCellStyle right,HSSFCellStyle top){
        HSSFRow rowfinal = worksheet.createRow(52);
        for (int j = 0; j <= fin2; ++j) {
            HSSFCell cellf = rowfinal.createCell(j);
            if (j == 1) cellf.setCellValue("FORMA DE PAGO");
            else if (j == 5) cellf.setCellValue("VENCIMIENTO");
            if (j == 0) cellf.setCellStyle(right);
            else cellf.setCellStyle(especifico);
        }
    }

    public void setFila53(HSSFSheet worksheet,String pago, String vencimiento,HSSFCellStyle especifico, HSSFCellStyle right,HSSFCellStyle top){
        HSSFRow rowfinal = worksheet.createRow(53);
        for (int j = 0; j <= fin2; ++j) {
            HSSFCell cellf = rowfinal.createCell(j);
            if (j == 1) cellf.setCellValue(pago);
            else if (j == 5) cellf.setCellValue(vencimiento);
            if (j == 0) cellf.setCellStyle(right);
            else cellf.setCellStyle(especifico);
        }
    }


    public void setFila50 (HSSFSheet worksheet,double precioiva, double preciototal,HSSFCellStyle especifico, HSSFCellStyle right,HSSFCellStyle total){
        HSSFRow rowfinal = worksheet.createRow(50);
        for (int j = 0; j <= fin2; ++j) {
            HSSFCell cellf = rowfinal.createCell(j);
            if (j == 1) cellf.setCellValue(preciototal);
            else if (j == 3) cellf.setCellValue("21%");
            else if (j == 5) cellf.setCellValue(precioiva);
            else if (j == 7) cellf.setCellValue(preciototal);
            if (j == 0) cellf.setCellStyle(right);
            else cellf.setCellStyle(especifico);
        }
        HSSFRow rowfinal2 = worksheet.createRow(51);
        for (int j = 0; j <= fin2; ++j) {
            HSSFCell cellf = rowfinal2.createCell(j);
            if (j == 0) cellf.setCellStyle(right);
            else if (j == 2 || j == 4 ||j == 6) cellf.setCellStyle(total);
            else cellf.setCellStyle(especifico);
        }
    }
    public void setFila49 (HSSFSheet worksheet,HSSFCellStyle especifico, HSSFCellStyle right){
        HSSFRow rowfinal = worksheet.createRow(49);
        for (int j = 0; j <= fin2; ++j) {
            HSSFCell cellf = rowfinal.createCell(j);
            if (j == 1) cellf.setCellValue("BASE IMPONIBLE");
            else if (j == 3) cellf.setCellValue("% I.V.A");
            else if (j == 5) cellf.setCellValue("IMPORTE I.V.A");
            else if (j == 7) cellf.setCellValue("TOTAL FACTURA");
            if(j == 0) cellf.setCellStyle(right);
            else cellf.setCellStyle(especifico);

        }
    }

    public void prepareteList (HSSFSheet worksheet,HSSFCellStyle especifico,HSSFCellStyle right){
        HSSFRow rowprueba1 = worksheet.createRow(cabezeraLista);
        CellRangeAddress uno = new CellRangeAddress(cabezeraLista, cabezeraLista, inicio, fin);
        worksheet.addMergedRegion(uno);
        CellRangeAddress dos = new CellRangeAddress(cabezeraLista, cabezeraLista, inicio2, fin2);
        worksheet.addMergedRegion(dos);
        for (int j = 0; j <= fin2; ++j) {
            HSSFCell cell = rowprueba1.createCell(j);
            cell.setCellStyle(especifico);
            if (j == 0) cell.setCellStyle(right);
            if (j == inicio) cell.setCellValue("CONCEPTO");
            else if (j == 7) cell.setCellValue("PRECIO");
        }
    }

    public void fillConceptoPrecioList(int contx,HSSFSheet worksheet, int n, HSSFCellStyle right, ObservableList<ConceptoTable> L){
        for(int i = contx; i < cont; ++i){
            HSSFRow rowprueba2 = worksheet.createRow(n);
            CellRangeAddress tres = new CellRangeAddress(n, n, inicio, fin);
            worksheet.addMergedRegion(tres);
            CellRangeAddress cuatro = new CellRangeAddress(n, n, inicio2, fin2);
            worksheet.addMergedRegion(cuatro);
            for (int j = 0; j <= fin2; ++j) {
                HSSFCell cell = rowprueba2.createCell(j);
                cell.setCellStyle(right);
                if (j == 1) cell.setCellValue(L.get(i).getConsulta());
                else if (j == 7) cell.setCellValue(L.get(i).getPrecio());
            }
            ++n;
        }
    }

    public void fillList( ObservableList<ConceptoTable> L, HSSFSheet worksheet, HSSFCellStyle right){
        int n = inicioLista;
        int contx = cont;
        if(cont+lengthList > L.size()) cont = L.size();
        else cont += lengthList;
        fill_not_empty(worksheet, right);
        fillConceptoPrecioList(contx,worksheet,n,right,L);
    }

    private void setFinalPre(HSSFSheet worksheet, HSSFCellStyle top) {
        HSSFRow rowfinal3 = worksheet.createRow(54);
        for (int j = 1; j <= fin2; ++j) {
            HSSFCell cell = rowfinal3.createCell(j);
            cell.setCellStyle(top);
        }
    }


    public void main(String[] args, Integer nfact, String fecha, String codecliente, ObservableList<ConceptoTable> L, String formapago, String vencimiento, String ncliente, String cifcliente, String direccioncliente, String poblacioncli, String nombreFactura)  {
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(nombreFactura + ".xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        int tot = 0;
        if (L.size() > lengthList) {
            int aux = L.size()-lengthList;
            lengthList = 36;
            if(L.size() > 86){
                int r = L.size()-86;
                while (r <= 0){
                    if(r-47 > 0) ++tot;
                    r -=50;
                }
                tot += 3;
            }
            else tot = 2;
        }
        else tot = 1;
        for (int x = 0; x < tot; ++x) {
            HSSFSheet worksheet = workbook.createSheet("Factura " + (x+1));
            HSSFCellStyle right = right_style(workbook);
            HSSFCellStyle especifico = no_left_style(workbook);
            HSSFCellStyle todos = total_style(workbook);
            HSSFCellStyle top = top_style(workbook);
            setWidth(worksheet);
            cabezeraLista = 1;
            if (x == 0) {
                try {
                    setImage(workbook,worksheet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                cabezeraLista = 15;
                datos_factura(worksheet,codecliente,nfact,fecha,todos,ncliente, cifcliente, direccioncliente,poblacioncli);
            }
            else if(x == tot-1){
                lengthList = 46;

            }
            else {
                lengthList = 50;
            }
            inicioLista = cabezeraLista+1;
            prepareteList(worksheet, especifico, right);
            fillList(L, worksheet, right);
            double precio = 0;
            for (ConceptoTable t : L) {
                precio = precio + t.getPrecio();
            }
            double iva = precio*0.21;
            double total = (precio*0.21) + precio;
            if(x == tot-1) {
                setSpaceFinal(worksheet);
                setFila49(worksheet, especifico, right);
                setFila50(worksheet, iva, total, especifico, right, top);
                setFila52(worksheet, iva, total, especifico, right, top);
                setFila53(worksheet, formapago, vencimiento, especifico, right, top);
                setFinalPre(worksheet, top);
            }
            else setFinalPre(worksheet,top);
            setMargin(worksheet);
        }
        try {
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}