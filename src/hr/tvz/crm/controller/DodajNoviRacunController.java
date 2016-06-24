package hr.tvz.crm.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import hr.tvz.crm.main.Klijent;
import hr.tvz.crm.main.Popravak;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class DodajNoviRacunController {
	
	@FXML
	public ComboBox<Klijent> klijentComboBox;
	@FXML
	public ComboBox<Popravak> popravakComboBox;
	@FXML
	public Button dodajButton;
	@FXML
	private Button odustaniButton;
	
	private Stage dijalogStage;
	
	private static PrivateKey privateKey;
	private static Certificate[] certificateChain;
	
	public void setDijalogStage(Stage dijalogStage) {
        this.dijalogStage = dijalogStage;
    }
	
	@FXML
    private void handleCancel() {
        dijalogStage.close();
    }
	
	@FXML
	private void handleOk() {
    	Klijent klijent = klijentComboBox.getSelectionModel().getSelectedItem();
    	Popravak popravak = popravakComboBox.getSelectionModel().getSelectedItem();
    	
    	String dest = "output/pdf/" + klijent.getIme() + ".pdf";	
    	OutputStream fos = null;
		try {
			fos = new FileOutputStream(dest);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	PdfWriter writer = new PdfWriter(fos);
    	PdfDocument pdf = new PdfDocument(writer);
    	Document document = new Document(pdf, PageSize.A4);
    	document.setMargins(36, 36, 36, 55);
    	
    	final String FONT = "resources/font/times.ttf";
    	PdfFont font = null;
		try {
			font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// HEADER TABLICA
    	Table table = new Table(new float[]{1, 1});
    	table.setWidthPercent(100);
    	table.setBorder(Border.NO_BORDER);
    	
    	Cell vlasnik = new Cell();
    	vlasnik.setBorder(Border.NO_BORDER);
    	vlasnik.add(new Paragraph("TVZ Car Mechanic").setFont(font));
    	vlasnik.add(new Paragraph("www.car-mechanic.hr").setFont(font));
    	vlasnik.add(new Paragraph("01/3451-617, 098/9000-440").setFont(font));
    	vlasnik.add(new Paragraph("info@car-mechanic.hr").setFont(font));
    	
    	Cell stranka = new Cell();
    	stranka.setBorder(Border.NO_BORDER);
		stranka.add(new Paragraph(klijent.getIme() + " " + klijent.getPrezime()).setTextAlignment(TextAlignment.RIGHT).setFont(font));
    	stranka.add(new Paragraph(klijent.getAdresa()).setTextAlignment(TextAlignment.RIGHT).setFont(font));
    	stranka.add(new Paragraph(klijent.getKontakt()).setTextAlignment(TextAlignment.RIGHT).setFont(font));
    	stranka.add(new Paragraph(klijent.getEmail()).setTextAlignment(TextAlignment.RIGHT).setFont(font));
    	
    	table.addCell(vlasnik);
    	table.addCell(stranka);
    	
        document.add(table);
        
        // NASLOV
        // TODO dodavanje raèuna u bazu i print id-ja
        Paragraph naslov = new Paragraph("Raèun br. 11")
        		.setTextAlignment(TextAlignment.CENTER)
        		.setFont(font)
        		.setFontSize(25)
        		.setMargins(40, 0, 30, 0);
        
        document.add(naslov);
        
        
        // PODACI O POPRAVKU
        document.add(new Paragraph("Detalji o popravku:").setFont(font).setFontSize(15).setMargins(20, 0, 0, 10));
        // POPRAVAK TABLICA
    	Table table2 = new Table(new float[]{1, 2});
    	table2.setWidthPercent(100);
    	table2.setBorder(Border.NO_BORDER);
    	
    	table2.addCell(new Cell().add(
    			new Paragraph("Naziv")
    			.setTextAlignment(TextAlignment.RIGHT)
    			.setFont(font))
    			.setBorder(Border.NO_BORDER)
    			.setMarginRight(15)
		);
    	table2.addCell(new Cell().add(
    			new Paragraph(popravak.getNaziv())
    			.setTextAlignment(TextAlignment.LEFT)
    			.setFont(font))
    			.setBorder(Border.NO_BORDER)
		);
    	
    	table2.addCell(new Cell().add(
    			new Paragraph("Opis")
    			.setTextAlignment(TextAlignment.RIGHT)
    			.setFont(font))
    			.setBorder(Border.NO_BORDER)
    			.setMarginRight(15)
		);
    	table2.addCell(new Cell().add(
    			new Paragraph(popravak.getOpis())
    			.setTextAlignment(TextAlignment.LEFT)
    			.setFont(font))
    			.setBorder(Border.NO_BORDER)
		);
    	
    	table2.addCell(new Cell().add(
    			new Paragraph("Vozilo")
    			.setTextAlignment(TextAlignment.RIGHT)
    			.setFont(font))
    			.setBorder(Border.NO_BORDER)
    			.setMarginRight(15)
		);
    	table2.addCell(new Cell().add(
    			new Paragraph(popravak.getVozilo())
    			.setTextAlignment(TextAlignment.LEFT)
    			.setFont(font))
    			.setBorder(Border.NO_BORDER)
		);
    	
    	table2.addCell(new Cell().add(
    			new Paragraph("Cijena")
    			.setTextAlignment(TextAlignment.RIGHT)
    			.setFont(font))
    			.setBorder(Border.NO_BORDER)
    			.setMarginRight(15)
		);
    	table2.addCell(new Cell().add(
    			new Paragraph(popravak.getCijena().toString() + " kn")
    			.setTextAlignment(TextAlignment.LEFT)
    			.setFont(font))
    			.setBorder(Border.NO_BORDER)
		);
    	
        document.add(table2);
        
        // FOOTER
        Table footer = new Table(new float[]{1, 1});
        footer.setWidthPercent(100);
        
        LocalDateTime currentTime = LocalDateTime.now();		
        
        Cell c = new Cell().add(
    			new Paragraph("Ovaj dokument je digitalno potpisan.")
    			.setTextAlignment(TextAlignment.LEFT)
    			.setFont(font))
    			.setBorder(Border.NO_BORDER)
    			.setMarginRight(15);
        c.add(new Paragraph(currentTime.toLocalDate().toString())
    			.setTextAlignment(TextAlignment.LEFT)
    			.setFont(font));
		
        
        footer.addCell(c);
        footer.addCell(new Cell().add(
    			new Paragraph("Preuzeo: __________________________")
    			.setTextAlignment(TextAlignment.RIGHT)
    			.setFont(font))
    			.setBorder(Border.NO_BORDER)
		);
        footer.setFixedPosition(document.getLeftMargin(), 50, footer.getWidth());
        
        document.add(footer);    	
    	
        // CLOSE DOCUMENT
    	document.close();
    	
    	//signPDF("resources/cert/tomo-cert.p12", "output/pdf/" + klijent.getIme() + ".pdf", "output/pdf/signed/" + klijent.getIme() + ".pdf");
    	
    	// GENERATE CONFIRMATION POPUP
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("PDF generiran!");
    	alert.setHeaderText(null);
    	alert.setContentText("Novi raèun je uspješno generiran!");
    	alert.showAndWait();
    	
        dijalogStage.close();
		
    	/*try {
			//BazaPodataka.dodajNoviPopravak(noviPopravak);
			
			Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Popravak unesen!");
        	alert.setHeaderText(null);
        	alert.setContentText("Novi popravak je uspješno dodan u bazu!");
        	alert.showAndWait();
        	
            dijalogStage.close();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Unos novog popravka");
    		alert.setHeaderText(null);
    		alert.setContentText("Unos novog popravka nije uspjelo!");
    		alert.showAndWait();
    		
			e.printStackTrace();
		}*/
    }
	
	private void signPDF(String pkcs12fnm, String pdfInput, String pdfOutput){
		try {
	        String pkcs12FileName = pkcs12fnm;
	        String pdfInputFileName = pdfInput;
	        String pdfOutputFileName = pdfOutput;
	        
            readPrivateKeyFromPKCS12(pkcs12FileName);

	        com.lowagie.text.pdf.PdfReader reader = null;
	        try {
	            reader = new com.lowagie.text.pdf.PdfReader(pdfInputFileName);
	        } catch (IOException e) {
	            System.err
	                    .println("An unknown error accoured while opening the input PDF file: \""
	                            + pdfInputFileName + "\"");
	            e.printStackTrace();
	            System.exit(-1);
	        }
	        FileOutputStream fout = null;
	        try {
	            fout = new FileOutputStream(pdfOutputFileName);
	        } catch (FileNotFoundException e) {
	            System.err
	                    .println("An unknown error accoured while opening the output PDF file: \""
	                            + pdfOutputFileName + "\"");
	            e.printStackTrace();
	            System.exit(-1);
	        }
	        
	        com.lowagie.text.pdf.PdfStamper stp = null;
	        
	        try {
	            stp = com.lowagie.text.pdf.PdfStamper.createSignature(reader, fout, '\0');
	            com.lowagie.text.pdf.PdfSignatureAppearance sap = stp.getSignatureAppearance();
	            sap.setCrypto(privateKey, certificateChain, null, com.lowagie.text.pdf.PdfSignatureAppearance.WINCER_SIGNED);
	            // sap.setReason("I'm the author");
	            // sap.setLocation("Lisbon");
	            // sap.setVisibleSignature(new Rectangle(100, 100, 200, 200), 1,
	            // null);
	            stp.close();
	        } catch (Exception e) {
	            System.err
	                    .println("An unknown error accoured while signing the PDF file:");
	            e.printStackTrace();
	            System.exit(-1);
	        }
	    } catch (KeyStoreException kse) {
	        System.err
	                .println("An unknown error accoured while initializing the KeyStore instance:");
	        kse.printStackTrace();
	        System.exit(-1);
	    }
	}
	
	protected static void readPrivateKeyFromPKCS12(String pkcs12FileName)
	        throws KeyStoreException {
	    String pkcs12Password = "tvz123";
	    KeyStore ks = null;

	    try {
	        ks = KeyStore.getInstance("pkcs12");
	        ks.load(new FileInputStream(pkcs12FileName), pkcs12Password
	                .toCharArray());
	    } catch (NoSuchAlgorithmException e) {
	        System.err
	                .println("An unknown error accoured while reading the PKCS#12 file:");
	        e.printStackTrace();
	        System.exit(-1);
	    } catch (CertificateException e) {
	        System.err
	                .println("An unknown error accoured while reading the PKCS#12 file:");
	        e.printStackTrace();
	        System.exit(-1);
	    } catch (FileNotFoundException e) {
	        System.err.println("Unable to open the PKCS#12 keystore file \""
	                + pkcs12FileName + "\":");
	        System.err
	                .println("The file does not exists or missing read permission.");
	        System.exit(-1);
	    } catch (IOException e) {
	        System.err
	                .println("An unknown error accoured while reading the PKCS#12 file:");
	        e.printStackTrace();
	        System.exit(-1);
	    }
	    String alias = "";
	    try {
	        alias = (String) ks.aliases().nextElement();
	        privateKey = (PrivateKey) ks.getKey(alias, pkcs12Password
	                .toCharArray());
	    } catch (NoSuchElementException e) {
	        System.err
	                .println("An unknown error accoured while retrieving the private key:");
	        System.err
	                .println("The selected PKCS#12 file does not contain any private keys.");
	        e.printStackTrace();
	        System.exit(-1);
	    } catch (NoSuchAlgorithmException e) {
	        System.err
	                .println("An unknown error accoured while retrieving the private key:");
	        e.printStackTrace();
	        System.exit(-1);
	    } catch (UnrecoverableKeyException e) {
	        System.err
	                .println("An unknown error accoured while retrieving the private key:");
	        e.printStackTrace();
	        System.exit(-1);
	    }
	    certificateChain = ks.getCertificateChain(alias);
	}

}
