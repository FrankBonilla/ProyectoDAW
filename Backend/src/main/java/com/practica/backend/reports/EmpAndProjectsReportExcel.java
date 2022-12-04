package com.practica.backend.reports;

import com.practica.backend.entities.Empleado;
import com.practica.backend.entities.Proyecto;
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

public class EmpAndProjectsReportExcel {

    private XSSFWorkbook libro;
    private XSSFSheet hoja;
    private Empleado empleado;
    private List<Proyecto> projectsList;

    /**Constructor con parámetros**/
    public EmpAndProjectsReportExcel(Empleado empleado, List<Proyecto> projectsList) {
        this.empleado = empleado;
        this.projectsList = projectsList;
        libro = new XSSFWorkbook();
        hoja = libro.createSheet("Empleado");
    }

    /**Cabeceras del empleado **/
    private void createHeadersOfEmployee(){
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
        tituloCell.setCellValue("Informe empleado");
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

    /**Cabecera para datos del proyecto**/
    private void createHeadersOfProject(){
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
        Row titulo = hoja.createRow(6);
        //fusión de celdas para el titulo
        hoja.addMergedRegion(new CellRangeAddress(6,6,0,5));

        Cell tituloCell = titulo.createCell(0);
        tituloCell.setCellValue("Proyectos asignados");
        tituloCell.setCellStyle(estiloTitulo);
        //se crea la fila
        Row fila = hoja.createRow(7);

        Cell celda = fila.createCell(0);
        celda.setCellValue("CÓDIGO");
        celda.setCellStyle(estiloCabecera);

        celda = fila.createCell(1);
        celda.setCellValue("DESCRIPCIÓN");
        celda.setCellStyle(estiloCabecera);

        celda = fila.createCell(2);
        celda.setCellValue("FECHA INICIO");
        celda.setCellStyle(estiloCabecera);

        celda = fila.createCell(3);
        celda.setCellValue("FECHA FIN");
        celda.setCellStyle(estiloCabecera);

        celda = fila.createCell(4);
        celda.setCellValue("LUGAR");
        celda.setCellStyle(estiloCabecera);

        celda = fila.createCell(5);
        celda.setCellValue("OBSERVACIONES");
        celda.setCellStyle(estiloCabecera);

        //ancho de columnas
        hoja.setColumnWidth(0,1000);
        hoja.setColumnWidth(1,8000);
        hoja.setColumnWidth(2,5000);
        hoja.setColumnWidth(3,5000);
        hoja.setColumnWidth(4,8000);
        hoja.setColumnWidth(5,14000);
    }

    /**Creamos y rellenamos las celdas para el empleado**/
    private void writeDataOnTableEmployee(){

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);
        Row fila = hoja.createRow(2);

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
    /**Creamos y rellenamos las celdas para los proyectos**/
    private void writeDataOFProject(){
        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);

        int numFilas = 8;

        for(Proyecto proyecto : projectsList){
            Row fila = hoja.createRow(numFilas);

            Cell celda = fila.createCell(0);
            celda.setCellValue(proyecto.getId_proyecto());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(proyecto.getDescripcion());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(dateFormater(proyecto.getFechaInicio()));
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(proyecto.getFechaFin() != null ? dateFormater(proyecto.getFechaFin()) : "*");
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(proyecto.getLugar() != null ? proyecto.getLugar() : "*");
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(5);
            celda.setCellValue(proyecto.getObservaciones());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            numFilas++;
        }
    }

    /**Método para escribir y exportar el documento**/
    public void export(HttpServletResponse response) throws IOException {
        createHeadersOfEmployee();
        writeDataOnTableEmployee();
        createHeadersOfProject();
        writeDataOFProject();

        ServletOutputStream outputStream = response.getOutputStream();
        libro.write(outputStream);
        libro.close();
        outputStream.close();
    }
    /** Formateador de fecha **/
    public String dateFormater(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String finalDate = format.format(date);

        return  finalDate;
    }
}
