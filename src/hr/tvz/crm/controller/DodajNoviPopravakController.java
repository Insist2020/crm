package hr.tvz.crm.controller;

import hr.tvz.crm.baza.BazaPodataka;
import hr.tvz.crm.main.Klijent;
import hr.tvz.crm.main.Popravak;
import hr.tvz.crm.main.Tip;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodajNoviPopravakController {
	
	@FXML
	public ComboBox<Klijent> klijentComboBox;
	@FXML
	public ComboBox<Tip> tipComboBox;
	@FXML
	private TextField nazivField;
	@FXML
	private TextField opisField;
	@FXML
	private TextField voziloField;
	@FXML
	private TextField cijenaField;
	@FXML
	private Label errorLabel;
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
        if (isInputValid()) {
        	Klijent klijent = klijentComboBox.getSelectionModel().getSelectedItem();
        	Tip tip = tipComboBox.getSelectionModel().getSelectedItem();
    		//int klijentId = Integer.valueOf(klijent.split(" ")[0]);
    		
        	Popravak noviPopravak = new Popravak(nazivField.getText(), opisField.getText(), voziloField.getText(), Double.parseDouble(cijenaField.getText()), klijent.getId(), tip.getId());
        	try {
				BazaPodataka.dodajNoviPopravak(noviPopravak);
				
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
			}
        	
        	
        }		
    }
	
	@FXML
    private void handleCancel() {
        dijalogStage.close();
    }
	
	private boolean isInputValid() {
        String errorMessage = "";
        
        
        if (nazivField.getText() == null || nazivField.getText().length() == 0) {
            errorMessage += "Naziv nije ispravan!\n"; 
        }
        if (opisField.getText() == null || opisField.getText().length() == 0) {
            errorMessage += "Opis nije ispravan!\n"; 
        }
        if (voziloField.getText() == null || voziloField.getText().length() == 0) {
            errorMessage += "Model vozila nije ispravan!\n"; 
        }
        if (cijenaField.getText() == null || cijenaField.getText().length() == 0) {
            errorMessage += "Cijena nije unesena!\n"; 
        } else {
        	try{
        		Double.parseDouble(cijenaField.getText());        		
        	}catch(Exception e){
        		errorMessage += "Format cijene nije ispravan!\n";
        	}
        	
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Greška");
    		alert.setHeaderText("Uneseni podaci nisu ispravni");
    		alert.setContentText(errorMessage);

    		alert.showAndWait();
    		
            return false;
        }
    }

}
