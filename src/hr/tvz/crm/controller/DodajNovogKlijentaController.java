package hr.tvz.crm.controller;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import hr.tvz.crm.baza.BazaPodataka;
import hr.tvz.crm.main.Klijent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodajNovogKlijentaController {
	
	@FXML
	private TextField imeField;
	@FXML
	private TextField prezimeField;
	@FXML
	private TextField adresaField;
	@FXML
	private TextField mobitelField;
	@FXML
	private TextField emailField;
	@FXML
	private Label errorLabel;
	@FXML
	private Button dodajButton;
	@FXML
	private Button odustaniButton;
	
	private Stage dijalogStage;
	
	public void setDijalogStage(Stage dijalogStage) {
        this.dijalogStage = dijalogStage;
    }
	
	@FXML
	private void handleOk() {
        if (isInputValid()) {
        	/*
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(CalendarUtil.parse(birthdayField.getText()));
            */
        	
        	Klijent noviKlijent = new Klijent(imeField.getText(), prezimeField.getText(), adresaField.getText(), mobitelField.getText(), emailField.getText());
        	try {
				BazaPodataka.dodajNovogKlijenta(noviKlijent);
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
        		alert.setTitle("Unos novog klijenta");
        		alert.setHeaderText(null);
        		alert.setContentText("Unos novog klijenta nije uspjelo!");
        		alert.showAndWait();
        		
				e.printStackTrace();
			}
        	
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Klijent unesen!");
        	alert.setHeaderText(null);
        	alert.setContentText("Novi klijent je uspješno dodan u bazu!");
        	alert.showAndWait();
        	
            dijalogStage.close();
        }		
    }
	
	@FXML
    private void handleCancel() {		
        dijalogStage.close();
    }
	
	public static boolean isValidEmailAddress(String email) {
	   boolean result = true;
	   try {
	      InternetAddress emailAddr = new InternetAddress(email);
	      emailAddr.validate();
	   } catch (AddressException ex) {
	      result = false;
	   }
	   return result;
	}
	
	private boolean isInputValid() {
        String errorMessage = "";
        
        
        if (imeField.getText() == null || imeField.getText().length() == 0) {
            errorMessage += "Ime nije ispravno!\n"; 
        }
        if (prezimeField.getText() == null || prezimeField.getText().length() == 0) {
            errorMessage += "Prezime nije ispravno!\n"; 
        }
        if (adresaField.getText() == null || adresaField.getText().length() == 0) {
            errorMessage += "Adresa nije ispravna!\n"; 
        }
        if (mobitelField.getText() == null || mobitelField.getText().length() == 0) {
            errorMessage += "Kontakt broj nije ispravan!\n"; 
        }
        if (emailField.getText() == null || emailField.getText().length() == 0 || !isValidEmailAddress(emailField.getText())) {
            errorMessage += "E-mail nije ispravan!\n"; 
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.

    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Greška");
    		alert.setHeaderText("Uneseni podaci nisu ispravni");
    		alert.setContentText(errorMessage);

    		alert.showAndWait();
    		
            return false;
        }
    }

}
