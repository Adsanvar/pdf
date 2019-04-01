package sample;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

import static com.itextpdf.io.font.constants.StandardFonts.TIMES_BOLD;
import static com.itextpdf.io.font.constants.StandardFonts.TIMES_ROMAN;

import com.itextpdf.layout.renderer.IRenderer;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PdfMaker
{
    public PdfMaker()
    {

    }

    /**
     * This will feed in fileName, an invoice, and a list of Services that are linked to that invoice
     * @param fileName
     */
    public void MakePDF(String fileName)
    {
       ArrayList<Invoice> list = new ArrayList<Invoice>();
       list.add(new Invoice(5,"Tree",25.52f));
       list.add(new Invoice(5,"Shurb",25.00f));
       list.add(new Invoice(5,"Plant",25.00f));

        try
        {
            String home = System.getProperty("user.home");
            Path path = Paths.get(home,"Desktop", fileName+".pdf");
            float[] width = { 25, 20, 110 };
            float[] widthII = { 4,1,2 };
            float[] widthIII = { 25,200 ,75 };
            //URL url = getClass().getClassLoader().getResource("/orderProcessing.png");
            //System.out.println(url);
            //Creating a writer
            PdfWriter writer = new PdfWriter(path.toString());
            //Creating a PdfDocument
            PdfDocument pdfDocument = new PdfDocument(writer);
            //adding new page
            pdfDocument.addNewPage();
            //creating a doc
            Document document = new Document(pdfDocument);
           // PdfFont TIMES_ROMAN_BOLD = PdfFontFactory.createFont(TIMES_BOLD);
           // PdfFont TIMES_ROMAN = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            // Creating a table
            Table table = new Table(widthII);
            table.setBorder(Border.NO_BORDER);
            table.setWidth(UnitValue.createPercentValue(100f));
            // Adding cells to the table
            Paragraph spacer = new Paragraph("\n");
           // Image image = new Image(ImageDataFactory.create("tierra.png")).scaleAbsolute(150, 150);
           // image.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            Cell img = new Cell();
           // img.add(image);
            img.setBorder(Border.NO_BORDER);
          /* Paragraph to = new Paragraph(new Text("Invoice Number: " + invoice.getInvoiceNumber() + "\n\nInvoice Date: " + invoice.getDate() +
                    "\n\n\n\n" + invoice.getBuilding().billTo + "\n" + invoice.getBuilding().address + "\n" + invoice.getBuilding().city + ", " + invoice.getBuilding().state + " " + invoice.getBuilding().zip
                    ).setFont(TIMES_ROMAN).setFontSize(14));*/
            Paragraph to = new Paragraph(new Text("Invoice Number: " + "\nDate: ").setFontSize(14));
            Paragraph from = new Paragraph(new Text("Contact: Some Company\nEmail: service@somecompany.com").setFontSize(14));
            to.setVerticalAlignment(VerticalAlignment.MIDDLE);
            to.setHorizontalAlignment(HorizontalAlignment.CENTER);
            from.setTextAlignment(TextAlignment.RIGHT);
            from.setVerticalAlignment(VerticalAlignment.BOTTOM);
            img.setVerticalAlignment(VerticalAlignment.MIDDLE);
            img.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            img.add(from);
            Cell toCell = new Cell();
            toCell.add(to);
            toCell.setBorder(Border.NO_BORDER);
            Cell spacerCell = new Cell();
            spacerCell.setBorder(Border.NO_BORDER);
            spacerCell.add(new Paragraph(new Text("\t\t\t")));
            table.addCell(toCell);
            table.addCell(spacerCell);
            table.addCell(img);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            document.add(table);
            document.add(spacer);

            /**
             * Creating service Table
             */
            Table servicesTable = new Table(1);
            servicesTable.setWidth(UnitValue.createPercentValue(100f));

            //Header content
            Paragraph servicesHeader = new Paragraph(new Text("Service(s) Invoiced").setFontSize(18));
            servicesHeader.setTextAlignment(TextAlignment.CENTER);
            servicesHeader.setVerticalAlignment(VerticalAlignment.MIDDLE);
            servicesHeader.setHorizontalAlignment(HorizontalAlignment.CENTER);
            Cell serviceCell = new Cell();
            serviceCell.add(servicesHeader);
            serviceCell.setBorderBottom(Border.NO_BORDER);
            serviceCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
            servicesTable.addCell(serviceCell);
            document.add(servicesTable);
            //mainContent
            Table mainContentTable = new Table(3);
            mainContentTable.setWidth(UnitValue.createPercentValue(100f));
            Cell qtyCell = new Cell();
            qtyCell.setBorderBottom(Border.NO_BORDER);
            qtyCell.setBorderRight(Border.NO_BORDER);
            qtyCell.setBorderTop(Border.NO_BORDER);
            qtyCell.setHeight(14f);

            Cell serviceDes = new Cell();
            serviceDes.setBorder(Border.NO_BORDER);
            serviceDes.setHeight(14f);

            Cell servicePrice = new Cell();
            servicePrice.setBorderTop(Border.NO_BORDER);
            servicePrice.setBorderLeft(Border.NO_BORDER);
            servicePrice.setBorderBottom(Border.NO_BORDER);
            servicePrice.setHeight(14f);

            Paragraph qtyHeader = new Paragraph(new Text("QTY").setFontSize(10));
            Paragraph serviceDesHeader = new Paragraph(new Text("DESCRIPTION").setFontSize(10));
            Paragraph priceHeader = new Paragraph(new Text("PRICE").setFontSize(10));
            qtyHeader.setTextAlignment(TextAlignment.CENTER);
            serviceDesHeader.setTextAlignment(TextAlignment.CENTER);
            priceHeader.setTextAlignment(TextAlignment.CENTER);
            qtyCell.add(qtyHeader);
            serviceDes.add(serviceDesHeader);
            servicePrice.add(priceHeader);

            mainContentTable.addCell(qtyCell);
            mainContentTable.addCell(serviceDes);
            mainContentTable.addCell(servicePrice);

            //Gets all information from invoices

            for(Invoice i : list)
            {
                Paragraph qty = new Paragraph(new Text(Integer.toString(i.getQty())).setFontSize(10));
                qty.setTextAlignment(TextAlignment.CENTER);
                Paragraph des = new Paragraph(new Text(i.getDes()).setFontSize(10));
                des.setTextAlignment(TextAlignment.CENTER);
                Paragraph price = new Paragraph(new Text(Float.toString(i.getPrice())).setFontSize(10));
                price.setTextAlignment(TextAlignment.CENTER);

                Cell qtyCellI = new Cell();
                qtyCellI.setHorizontalAlignment(HorizontalAlignment.CENTER);
                qtyCellI.setBorderBottom(Border.NO_BORDER);
                qtyCellI.setBorderRight(Border.NO_BORDER);
                qtyCellI.setBorderTop(Border.NO_BORDER);
                qtyCellI.setHeight(14f);

                Cell serviceDesI = new Cell();
                serviceDesI.setHorizontalAlignment(HorizontalAlignment.CENTER);
                serviceDesI.setBorder(Border.NO_BORDER);
                serviceDesI.setHeight(14f);

                Cell servicePriceI = new Cell();
                servicePriceI.setHorizontalAlignment(HorizontalAlignment.CENTER);
                servicePriceI.setBorderTop(Border.NO_BORDER);
                servicePriceI.setBorderLeft(Border.NO_BORDER);
                servicePriceI.setBorderBottom(Border.NO_BORDER);
                servicePriceI.setHeight(14f);

                qtyCellI.add(qty);
                serviceDesI.add(des);
                servicePriceI.add(price);

                mainContentTable.addCell(qtyCellI);
                mainContentTable.addCell(serviceDesI);
                mainContentTable.addCell(servicePriceI);

            }

            document.add(mainContentTable);

            //Total Price Table
            Table finalPriceTable = new Table(1);
            finalPriceTable.setWidth(UnitValue.createPercentValue(100f));
            Cell totalPriceCell = new Cell();
            totalPriceCell.setMarginRight(10f);
            totalPriceCell.setBorderTop(Border.NO_BORDER);
            totalPriceCell.setHorizontalAlignment(HorizontalAlignment.RIGHT);
           // String invoicePrice = null;
            //Test Values
            float total = 0.00f;
            for(Invoice i : list)
                total += i.getPrice();
            //Test Values
            String totalPrice = String.format("%.2f", total );
            Paragraph finalPrice = new Paragraph(new Text("Total $" + totalPrice).setFontSize(18));
            finalPrice.setTextAlignment(TextAlignment.RIGHT);
            totalPriceCell.add(finalPrice);
            finalPriceTable.addCell(totalPriceCell);

            document.add(finalPriceTable);

            //closing document
            document.close();
        }
        catch (Exception ex){
            System.out.println("Exception Happened: " + ex.getMessage());
        }



    }
}
