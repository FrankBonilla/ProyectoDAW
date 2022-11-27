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

public class ProjectReportExcell {

    private XSSFWorkbook libro;
    private XSSFSheet hoja;
    private Proyecto proyecto;

    /**Constructor con parámetros**/
    public ProjectReportExcell(Proyecto proyecto) {
        libro = new XSSFWorkbook();
        hoja = libro.createSheet("Proyecto");
        this.proyecto = proyecto;
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
        Row titulo = hoja.createRow(0);
        //fusión de celdas para el titulo
        hoja.addMergedRegion(new CellRangeAddress(0,0,0,5));

        Cell tituloCell = titulo.createCell(0);
        tituloCell.setCellValue("Reporte de Proyecto");
        tituloCell.setCellStyle(estiloTitulo);
        //se crea la fila
        Row fila = hoja.createRow(1);

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

    /**Cabeceras para la tabla empleados**/
    private void createHeadersOfEmployees(){
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
        Row titulo = hoja.createRow(3);
        //fusión de celdas para el titulo
        hoja.addMergedRegion(new CellRangeAddress(3,3,0,5));

        Cell tituloCell = titulo.createCell(0);
        tituloCell.setCellValue("Empleados asignados");
        tituloCell.setCellStyle(estiloTitulo);
        //se crea la fila
        Row fila = hoja.createRow(4);

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
        celda.setCellValue("TELÉFONO");
        celda.setCellStyle(estiloCabecera);

        celda = fila.createCell(5);
        celda.setCellValue("EMAIL");
        celda.setCellStyle(estiloCabecera);
    }

    private void writeDataOFProject(){
        int numFilas = 2;

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);

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
    }

    /**Escribimos los datos de los empleados**/
    private void writeDataOfEmployees(){
        int numFilas = 5;
        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);

        for(Empleado empleado : proyecto.getEmployees()){
            Row fila = hoja.createRow(numFilas);

            Cell celda = fila.createCell(0);
            celda.setCellValue(empleado.getNif());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(empleado.getNombre());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(empleado.getApellido1());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(empleado.getApellido2() != null ? empleado.getApellido2() : "-");
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(empleado.getTelefono1());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(5);
            celda.setCellValue(empleado.getEmail());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            numFilas++;
        }
    }

    /**formatear fecha**/
    public String dateFormater(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String finalDate = format.format(date);

        return  finalDate;
    }

    /**Método para escribir y exportar el documento**/
    public void export(HttpServletResponse response) throws IOException {
        createHeadersOfProject();
        writeDataOFProject();
        createHeadersOfEmployees();
        writeDataOfEmployees();

        ServletOutputStream outputStream = response.getOutputStream();
        libro.write(outputStream);
        libro.close();
        outputStream.close();
    }
}
