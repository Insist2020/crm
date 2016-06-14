package hr.tvz.crm.controller;

import java.util.ArrayList;
import java.util.List;

import hr.tvz.crm.baza.BazaPodataka;
import hr.tvz.crm.main.Klijent;
import hr.tvz.crm.main.Popravak;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
		//int klijentId = Integer.valueOf(klijent.split(" ")[0]);
		
    	try {
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
		}	
    }
	
	@FXML
    private void handleCancel() {
        dijalogStage.close();
    }

}
