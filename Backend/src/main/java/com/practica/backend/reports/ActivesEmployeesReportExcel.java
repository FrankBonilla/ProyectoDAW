package com.practica.backend.reports;

import com.mysql.cj.util.StringUtils;
import com.practica.backend.entities.Empleado;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class ActivesEmployeesReportExcel {

    private XSSFWorkbook libro;
    private XSSFSheet hoja;
    private List<Empleado> empleadoList;

    /**Métodos**/
    private void createHeaders(){
        /**Estilos y fuentes**/
        //estilo cabecera
        CellStyle estiloCabecera = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setFontHeight(16);
        estiloCabecera.setFont(fuente);
        estiloCabecera.setFillBackgroundColor(IndexedColors.GOLD.getIndex());
        estiloCabecera.setFillPattern(FillPatternType.BIG_SPOTS);
        estiloCabecera.setFillForegroundColor(IndexedColors.GOLD.getIndex());
        //bordes
        estiloCabecera.setBorderLeft(BorderStyle.THIN);
        estiloCabecera.setBorderTop(BorderStyle.THIN);
        estiloCabecera.setBorderRight(BorderStyle.THIN);
        estiloCabecera.setBorderBottom(BorderStyle.THIN);
        //estilo titulo
        CellStyle estiloTitulo = libro.createCellStyle();
        XSSFFont fuenteTitulo = libro.createFont();
        fuenteTitulo.setBold(true);
        fuenteTitulo.setFontHeight(18);
        fuenteTitulo.setColor(IndexedColors.WHITE.getIndex());
        estiloTitulo.setFont(fuenteTitulo);
        estiloTitulo.setFillBackgroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
        estiloTitulo.setFillPattern(FillPatternType.BIG_SPOTS);
        estiloTitulo.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
        estiloTitulo.setAlignment(HorizontalAlignment.CENTER);

        //titulo en excell
        Row titulo = hoja.createRow(0);
        //fusión de celdas para el titulo
        hoja.addMergedRegion(new CellRangeAddress(0,0,0,10));

        Cell tituloCell = titulo.createCell(0);
        tituloCell.setCellValue("Lista de Empleados");
        tituloCell.setCellStyle(estiloTitulo);
        //se crea la fila
        Row fila = hoja.createRow(1);

        Cell celda = fila.createCell(0);
        celda.setCellValue("NIF");
        celda.setCellStyle(estiloCabecera);

        celda = fila.createCell(1);
        celda.setCellValue("NOMBRE");
        celda.setCellStyle(estiloCabecera);

        celda = fila.createCell(2);
        celda.setCellValue("PRIMER APELLIDO");
        celda.setCellStyle(estiloCabecera);

        celda = fila.createCell(3);
        celda.setCellValue("SEGUNDO APELLIDO");
        celda.setCellStyle(estiloCabecera);

        celda = fila.createCell(4);
        celda.setCellValue("FECHA NACIMIENTO");
        celda.setCellStyle(estiloCabecera);

        celda = fila.createCell(5);
        celda.setCellValue("TELEFONO");
        celda.setCellStyle(estiloCabecera);

        celda = fila.createCell(6);
        celda.setCellValue("TELEFONO 2");
        celda.setCellStyle(estiloCabecera);

        celda = fila.createCell(7);
        celda.setCellValue("EMAIL");
        celda.setCellStyle(estiloCabecera);

        celda = fila.createCell(8);
        celda.setCellValue("FECHA ALTA");
        celda.setCellStyle(estiloCabecera);

        celda = fila.createCell(9);
        celda.setCellValue("ESTADO CIVIL");
        celda.setCellStyle(estiloCabecera);

        celda = fila.createCell(10);
        celda.setCellValue("CARNET CONDUCIR");
        celda.setCellStyle(estiloCabecera);

        //ancho de columnas
        hoja.setColumnWidth(0,3000);
        hoja.setColumnWidth(1,6000);
        hoja.setColumnWidth(2,7000);
        hoja.setColumnWidth(3,7000);
        hoja.setColumnWidth(4,7000);
        hoja.setColumnWidth(5,5000);
        hoja.setColumnWidth(6,5000);
        hoja.setColumnWidth(7,7000);
        hoja.setColumnWidth(8,4000);
        hoja.setColumnWidth(9,5000);
        hoja.setColumnWidth(10,6000);
    }


    private void writeDataOnTable(){
        int numFilas = 2;

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);
        //Recorremos la lista de EMPLEADOS y creando y rellenando las celdas
        for(Empleado empleado : empleadoList){
            Row fila = hoja.createRow(numFilas++);

            Cell celda = fila.createCell(0);
            celda.setCellValue(empleado.getNif());
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(empleado.getNombre());
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(empleado.getApellido1());
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(empleado.getApellido2() != null ? empleado.getApellido2() : "*");
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(dateFormater(empleado.getNacimiento()));
            celda.setCellStyle(estilo);

            celda = fila.createCell(5);
            celda.setCellValue(empleado.getTelefono1());
            celda.setCellStyle(estilo);

            celda = fila.createCell(6);
            celda.setCellValue(empleado.getTelefono2() != null ? empleado.getTelefono2() : "*");
            celda.setCellStyle(estilo);

            celda = fila.createCell(7);
            celda.setCellValue(empleado.getEmail());
            celda.setCellStyle(estilo);

            celda = fila.createCell(8);
            celda.setCellValue(dateFormater(empleado.getFechaAlta()));
            celda.setCellStyle(estilo);

            celda = fila.createCell(9);
            celda.setCellValue("S".equalsIgnoreCase(empleado.getEdoCivil()) ? "Soltero/a" : "Casado/a");
            celda.setCellStyle(estilo);

            celda = fila.createCell(10);
            celda.setCellValue("S".equalsIgnoreCase(empleado.getSerMilitar()) ? "Si" : "No");
            celda.setCellStyle(estilo);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        createHeaders();
        writeDataOnTable();

        ServletOutputStream outputStream = response.getOutputStream();
        libro.write(outputStream);
        libro.close();
        outputStream.close();
    }
    public String dateFormater(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String finalDate = format.format(date);

        return  finalDate;
    }

    /**Constructor con parámetros**/
    public ActivesEmployeesReportExcel(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
        libro = new XSSFWorkbook();
        hoja = libro.createSheet("Empleados");
    }
}
