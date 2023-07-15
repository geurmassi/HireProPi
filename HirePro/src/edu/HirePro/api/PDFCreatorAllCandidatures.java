package edu.HirePro.api;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.HirePro.entities.Condidature;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PDFCreatorAllCandidatures {

    public Path createPDF(TableView<Condidature> tableView, String fileName) throws DocumentException, IOException {
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
            Path destinationPath = Paths.get(fileName);
            Files.move(Paths.get(tempFilePath), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            return destinationPath;

        } catch (DocumentException | IOException e) {
            // Log the error
            System.err.println("Error: " + e.getMessage());
            throw e;
        } finally {
            // Delete the temporary file in case of any exceptions
            tempFile.delete();
        }
    }

    private List<List<String>> convertTableData(TableView<Condidature> tableView) {
        List<List<String>> tableData = new ArrayList<>();

        // Get column names
        List<String> columnNames = new ArrayList<>();
        List<TableColumn<Condidature, ?>> columns = tableView.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            TableColumn<Condidature, ?> column = columns.get(i);
            String columnName = column.getText();
            if (!columnName.equals("id Condidature") && !columnName.equals("Offre") && !columnName.equals("Utilisateur")) {
                columnNames.add(columnName);
            }
        }
        tableData.add(columnNames);

        // Get cell data
        for (int i = 0; i < tableView.getItems().size(); i++) {
            List<String> rowData = new ArrayList<>();
            for (int j = 0; j < columns.size(); j++) {
                TableColumn<Condidature, ?> column = columns.get(j);
                String columnName = column.getText();
                if (!columnName.equals("id Condidature") && !columnName.equals("Offre") && !columnName.equals("Utilisateur")) {
                    Object cellData = column.getCellData(i);
                    if (cellData != null) {
                        rowData.add(cellData.toString());
                    } else {
                        rowData.add("");
                    }
                }
            }
            tableData.add(rowData);
        }

        return tableData;
    }

    private PdfPTable createPdfTable(List<List<String>> tableData) {
        PdfPTable pdfTable = new PdfPTable(tableData.get(0).size());
         pdfTable.setWidthPercentage(100);

        // Add data rows
        for (List<String> rowData : tableData) {
            for (String cellData : rowData) {
                PdfPCell cell = new PdfPCell(new Paragraph(cellData));
                pdfTable.addCell(cell);
            }
        }

        return pdfTable;
    }
}
