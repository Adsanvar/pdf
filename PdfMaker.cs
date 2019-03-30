using iTextSharp.text;
using iTextSharp.text.pdf;
using iTextSharp.text.pdf.draw;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using Zen.Barcode;

namespace MVLandscaping.Classes
{
    class PdfMaker
    {
        public PdfMaker()
        {

        }

        public void MakePDF(string fileName, Invoice invoice)
        {
             
            Document doc = new Document(PageSize.LETTER, 1f,1f,15f,1f);

            int[] width = { 25, 20, 110 };
            int[] widthII = { 4,1,5 };
            int[] widthIII = { 25,200 ,75 };
            Font TIMES_ROMAN_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Font TIMES_ROMAN_14 = new Font(Font.FontFamily.TIMES_ROMAN, 14);
            Font TIMES_ROMAN_12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
            Font SMALL_TIMES_ROMAN_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
            Font TOTAL_TIMES_ROMAN_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Font SMALL_TIMES_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 8);

            //FileStream fs = new FileStream(fileName + ".pdf", FileMode.Create, FileAccess.Write, FileShare.None);

            FileStream fs = new FileStream(Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.Desktop), fileName) + ".pdf", FileMode.Create, FileAccess.Write);
            //  FileStream fs = new FileStream(fileName + ".pdf", FileMode.Append, FileAccess.Write);

            PdfWriter writer = PdfWriter.GetInstance(doc, fs);

            doc.Open();
            /*
            Code128BarcodeDraw barcode128 = BarcodeDrawFactory.Code128WithChecksum;
            // pictureBox1.Image = barcode128.Draw(x, 40);

            System.Drawing.Image img = barcode128.Draw(orderID, 40);

            iTextSharp.text.Image barcode = iTextSharp.text.Image.GetInstance(img, BaseColor.BLACK);

            pOrderID.Alignment = Element.ALIGN_RIGHT;
            barcode.Alignment = Element.ALIGN_RIGHT;

            var directory = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location);
            var filename = Path.Combine(directory, "Nucerity", "Nucerity.jpg");

            iTextSharp.text.Image logo = iTextSharp.text.Image.GetInstance(filename);

            //  iTextSharp.text.Image logo = iTextSharp.text.Image.GetInstance("Nucerity.jpg");
            //   iTextSharp.text.Image logo = iTextSharp.text.Image.GetInstance(@"C:\Users\Adrian Sandoval\documents\visual studio 2017\Projects\PrintGenerator\PrintGenerator\Nucerity\Nucerity.jpg");

            logo.ScaleAbsolute(150, 100);




            doc.Add(logo);
            doc.Add(barcode);*/

            // var directory = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location);
            // var filename = Path.Combine(directory, "fullMVLogo.PNG");
            //iTextSharp.text.Image logo = iTextSharp.text.Image.GetInstance(filename);
            Paragraph spacer = new Paragraph("\n");
            PdfPTable headerTable = new PdfPTable(3);
            headerTable.SetWidths(widthII);
            headerTable.DefaultCell.Border = Rectangle.NO_BORDER;
            iTextSharp.text.Image logo = iTextSharp.text.Image.GetInstance(@"C:\Users\Adrian Sandoval\documents\visual studio 2017\Projects\MVLandscaping\MVLandscaping\GrayMVLogo.PNG");
            logo.ScaleAbsolute(175, 125);
            logo.Alignment = Image.ALIGN_CENTER;// | Image.TEXTWRAP;
            Paragraph to = new Paragraph();
            Chunk invoiceInfo = new Chunk("Invoice Number: " + invoice.getInvoiceNumber() + "\n\nInvoice Date: " + invoice.getDate() +
                "\n\n\n\n" + invoice.getBuilding().billTo + "\n" + invoice.getBuilding().address + "\n" + invoice.getBuilding().city + ", " + invoice.getBuilding().state + " " + invoice.getBuilding().zip
                , TIMES_ROMAN_14);
            PdfPCell img = new PdfPCell()
            {
                Border = Rectangle.NO_BORDER
            };
            Paragraph pFrom = new Paragraph();
            Chunk contact = new Chunk("Contact: Juan Manuel Vargas\nEmail: mvlandscape@yahoo.com", TIMES_ROMAN_14);
            pFrom.Add(contact);
            pFrom.Alignment = Element.ALIGN_CENTER;   
            img.AddElement(logo);
            img.AddElement(pFrom); 
            to.Add(invoiceInfo);
            to.Alignment = Element.ALIGN_LEFT;
            headerTable.AddCell(to);
            headerTable.AddCell(new Paragraph("\t\t"));
            headerTable.AddCell(img);
            headerTable.HorizontalAlignment = Element.ALIGN_CENTER;
                    
            doc.Add(headerTable);
            doc.Add(spacer);
            //Creates Services Table:
            PdfPTable servicesTable = new PdfPTable(3);
            servicesTable.SetWidths(widthIII);
            Chunk header = new Chunk("Service(s) Description", TIMES_ROMAN_BOLD );
            Paragraph headerP = new Paragraph(header);
            PdfPCell servicesHeaderCell = new PdfPCell();
            headerP.Alignment = Element.ALIGN_CENTER;
            servicesHeaderCell.AddElement(headerP);
            servicesHeaderCell.Colspan = 3;
            servicesHeaderCell.Border = Rectangle.TOP_BORDER | Rectangle.RIGHT_BORDER | Rectangle.LEFT_BORDER;
            servicesTable.AddCell(servicesHeaderCell);
            //
            //Objects Inside Table
            PdfPCell qty = new PdfPCell();
            qty.Border = Rectangle.LEFT_BORDER;
            PdfPCell description = new PdfPCell();
            description.Border = Rectangle.NO_BORDER;
            PdfPCell price = new PdfPCell();
            price.Border = Rectangle.RIGHT_BORDER;
            Chunk qtyChunk = new Chunk("QTY", SMALL_TIMES_ROMAN_BOLD);
            Chunk descriptionChunk = new Chunk("DESCRIPTION", SMALL_TIMES_ROMAN_BOLD);
            Chunk priceChunk = new Chunk("PRICE", SMALL_TIMES_ROMAN_BOLD);
            Paragraph qty1 = new Paragraph(qtyChunk);
            Paragraph description1 = new Paragraph(descriptionChunk);
            Paragraph price1 = new Paragraph(priceChunk);
            qty1.Alignment = Element.ALIGN_CENTER;
            description1.Alignment = Element.ALIGN_CENTER;
            price1.Alignment = Element.ALIGN_CENTER;
            qty.AddElement(qty1);
            description.AddElement(description1);
            price.AddElement(price1);
            servicesTable.AddCell(qty);
            servicesTable.AddCell(description);
            servicesTable.AddCell(price);
            //Table for all services
            foreach (InvoiceService service in invoice.getInvoiceServices())
            {
                Chunk quan = new Chunk(service.qty.ToString(), SMALL_TIMES_ROMAN);
                Chunk des = new Chunk(service.serviceName, SMALL_TIMES_ROMAN);
                string servicePrice = String.Format("{0, 0:C2}", service.price);
                Chunk pr = new Chunk(servicePrice, SMALL_TIMES_ROMAN);
                Paragraph q = new Paragraph(quan);
                Paragraph d = new Paragraph(des);
                Paragraph p = new Paragraph(pr);
                q.Alignment = Element.ALIGN_CENTER;
                d.Alignment = Element.ALIGN_CENTER;
                p.Alignment = Element.ALIGN_CENTER;
                PdfPCell qCell = new PdfPCell();
                PdfPCell dCell = new PdfPCell();
                PdfPCell pCell = new PdfPCell();
                qCell.FixedHeight = 14f;
                dCell.FixedHeight = 14f;
                pCell.FixedHeight = 14f;
                qCell.AddElement(q);
                dCell.AddElement(d);
                pCell.AddElement(p);
                qCell.PaddingTop = -1;
                dCell.PaddingTop = -1;
                pCell.PaddingTop = -1;
                servicesTable.AddCell(qCell);
                servicesTable.AddCell(dCell);
                servicesTable.AddCell(pCell);
            }
            //Maintians 25 objects Max in table
            if(invoice.getInvoiceServices().Count < 25)
            {
                PdfPCell qCell = new PdfPCell();
                qCell.FixedHeight = 14f;
                qCell.PaddingTop = -1;
                int trip = 0;
                for (int i = (invoice.getInvoiceServices().Count*3); i < (25*3); i++)
                {
                    trip++;
                    if(trip == 1)
                    {
                        qCell.Border = Rectangle.LEFT_BORDER;
                        servicesTable.AddCell(qCell);
                    }
                    else if(trip == 2)
                    {
                        qCell.Border = Rectangle.NO_BORDER;
                        servicesTable.AddCell(qCell);
                    }
                    else if(trip == 3)
                    {
                        trip = 0;
                        qCell.Border = Rectangle.RIGHT_BORDER;
                        servicesTable.AddCell(qCell);
                    }
                   
                }
            }
            //Final Price
            PdfPCell blank = new PdfPCell(new Phrase(""));
            PdfPCell blank1 = new PdfPCell();
            blank.Border = Rectangle.BOTTOM_BORDER | Rectangle.LEFT_BORDER;
            blank1.Border = Rectangle.BOTTOM_BORDER;
            servicesTable.AddCell(blank);
            PdfPCell totalCell = new PdfPCell();
            totalCell.Border = Rectangle.BOTTOM_BORDER | Rectangle.RIGHT_BORDER;
            string totalString = String.Format("{0, 0:C2}", invoice.getTotalPrice());
            Chunk total = new Chunk(totalString, TOTAL_TIMES_ROMAN_BOLD);
            Chunk totalChunk = new Chunk("Total", TOTAL_TIMES_ROMAN_BOLD);
            Paragraph totalPString = new Paragraph(totalChunk);
            totalPString.Alignment = Element.ALIGN_RIGHT;
            blank1.AddElement(totalPString);
            servicesTable.AddCell(blank1);
            Paragraph totalPrice = new Paragraph(total);
            totalPrice.Alignment = Element.ALIGN_CENTER;
            totalCell.AddElement(totalPrice);
            totalCell.VerticalAlignment = PdfPCell.ALIGN_CENTER;
            servicesTable.AddCell(totalCell);
            doc.Add(servicesTable);
            //Notice
            Chunk nChunk = new Chunk("*Payment is due upon receiving this invoice. If payment is not received within 10 days of this invoice a $25.00 late fee will apply.\n", SMALL_TIMES_ROMAN_BOLD);
            Chunk tChunk = new Chunk("[Please ensure to return the slip below with your payment]", TIMES_ROMAN_12);
            Paragraph notice = new Paragraph();
            notice.Add(nChunk);
            notice.Add(tChunk);
            notice.Alignment = Element.ALIGN_CENTER;
            doc.Add(notice);
            //spacer
            doc.Add(spacer);
            //Line
            PdfPTable line = new PdfPTable(1);
            line.TotalWidth = doc.PageSize.Width;
            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.Border = Rectangle.TOP_BORDER;
            line.AddCell(pdfPCell);
            doc.Add(line);

            //Footer
            Chunk toChunk = new Chunk("Payment slip for " + invoice.getBuilding().billTo + "\nPlease make all checks payable to:\nM.V. Landscaping, LLC\n54404 Bartram Drive\nMacomb, MI 48042", TIMES_ROMAN_12);
            Chunk inChunk = new Chunk("Invoice Number: " + invoice.getInvoiceNumber().ToString() + "\nTotal Enclosed: $_____________________"+
                "\nEmail Invoice to this address:\n__________________________________________", TIMES_ROMAN_12);
            Chunk glue = new Chunk(new VerticalPositionMark());
            Code128BarcodeDraw barcode128 = BarcodeDrawFactory.Code128WithChecksum;
            System.Drawing.Image temp = barcode128.Draw(invoice.invoiceNumber.ToString(), 4);
            iTextSharp.text.Image barcode = iTextSharp.text.Image.GetInstance(temp, BaseColor.BLACK);
            barcode.Alignment = Image.ALIGN_RIGHT | Image.ALIGN_BOTTOM;
          //  Paragraph rightP = new Paragraph(inChunk);
            
            //Paragraph footer = new Paragraph(toChunk);
            //footer.Add(glue);
            //footer.Add(rightP);
           // footer.Add(barcode);


            PdfPTable footerTable = new PdfPTable(2);
            PdfPCell leftCell = new PdfPCell();
            PdfPCell rightCell = new PdfPCell();
            leftCell.Border = Rectangle.NO_BORDER;
            rightCell.Border = Rectangle.NO_BORDER;

            leftCell.AddElement(toChunk);
            rightCell.AddElement(inChunk);
            rightCell.AddElement(barcode);

            footerTable.AddCell(leftCell);
            footerTable.AddCell(rightCell);
            doc.Add(footerTable);
           
            //to be used when creating multiple invoices
            // doc.NewPage();

            doc.Close();

        }
    }
}
