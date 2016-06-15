package hr.tvz.crm.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
	
	public void setDijalogStage(Stage dijalogStage) {
        this.dijalogStage = dijalogStage;
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
    	PageSize ps = PageSize.A4;
    	Document document = new Document(pdf, ps);
    	
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
    	vlasnik.add(new Paragraph("www.TVZ.hr").setFont(font));
    	vlasnik.add(new Paragraph("01/3451-617, 098/9000-440").setFont(font));
    	
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
        Paragraph naslov = new Paragraph("Raèun br. 11");
        naslov.setTextAlignment(TextAlignment.CENTER);
        naslov.setFont(font);
        naslov.setFontSize(25);
        
        document.add(naslov);
        
        
        // PODACI O POPRAVKU
    	
    	
    	document.close();
    	
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
	
	@FXML
    private void handleCancel() {
        dijalogStage.close();
    }

}
