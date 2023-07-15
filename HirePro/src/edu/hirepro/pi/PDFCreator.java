/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hirepro.pi;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.hirepro.entities.Reclamation;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PDFCreator {

    /**
     * This function takes a TableView object and creates a PDF file from it.
     *
     * @param tableView The TableView object to be converted to PDF
     * @param fileName The name of the PDF file to be created
     * @return The path of the generated PDF file
     * @throws DocumentException
     * @throws IOException
     */
    public Path createPDF(TableView<Reclamation> tableView, String fileName) throws DocumentException, IOException {
        File tempFile = File.createTempFile("temp_", "_" + fileName);
        String tempFilePath = tempFile.getAbsolutePath();

        try {
            // Create a new PDF document
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(tempFile));
            document.open();

            // Convert TableView data to a list
            List<List<String>> tableData = convertTableData(tableView);

            // Create a PDF table from the TableView data
            PdfPTable pdfTable = createPdfTable(tableData);

            // Add the PDF table to the document
            document.add(pdfTable);

            // Close the document
            document.close();

            // Move the temporary file to the desired location
            java.nio.file.Path destinationPath = java.nio.file.Paths.get(fileName);
            Files.move(java.nio.file.Paths.get(tempFilePath), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            return (Path) destinationPath;

        } catch (DocumentException | IOException e) {
            // Log the error
            System.err.println("Error: " + e.getMessage());
            throw e;
        } finally {
            // Delete the temporary file in case of any exceptions
            tempFile.delete();
        }
    }

    private List<List<String>> convertTableData(TableView<Reclamation> tableView) {
        List<List<String>> tableData = new ArrayList<>();

        // Get column names
        List<String> columnNames = new ArrayList<>();
        for (TableColumn<?, ?> column : tableView.getColumns()) {
            columnNames.add(column.getText());
        }
        tableData.add(columnNames);

        // Get cell data
        for (int i = 0; i < tableView.getItems().size(); i++) {
            List<String> rowData = new ArrayList<>();
            for (TableColumn<?, ?> column : tableView.getColumns()) {
                Object cellData = column.getCellData(i);
                if (cellData != null) {
                    rowData.add(cellData.toString());
                } else {
                    rowData.add("");
                }
            }
            tableData.add(rowData);
        }

        return tableData;
    }

    private PdfPTable createPdfTable(List<List<String>> tableData) {
        PdfPTable pdfTable = new PdfPTable(tableData.get(0).size());
        pdfTable.setWidthPercentage(100);

        // Add column names as header
        for (String columnName : tableData.get(0)) {
            PdfPCell cell = new PdfPCell(new Paragraph(columnName));
            pdfTable.addCell(cell);
        }

        // Add data rows
        for (int i = 1; i < tableData.size(); i++) {
            for (String cellData : tableData.get(i)) {
                PdfPCell cell = new PdfPCell(new Paragraph(cellData));
                pdfTable.addCell(cell);
            }
        }

        return pdfTable;
    }
}
