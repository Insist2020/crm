package hr.tvz.crm.controller;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import hr.tvz.crm.baza.BazaPodataka;
import hr.tvz.crm.main.Klijent;
import hr.tvz.crm.main.Popravak;
import hr.tvz.crm.main.Tip;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainController implements Initializable {
	@FXML
	private ListView<Klijent> mainKlijentiListView;
	@FXML
	private TableView<Popravak> popravciTable;
	@FXML
	private TableColumn<Popravak, Long> datumPopravkaColumn;
	@FXML
	private TableColumn<Popravak, String> nazivPopravkaColumn; 
	@FXML
	private TableColumn<Popravak, String> opisPopravkaColumn;
	@FXML
	private TableColumn<Popravak, String> voziloNaPopravkuColumn;
	@FXML
	private TableColumn<Popravak, Double> cijenaPopravkaColumn;
	@FXML
	private TextField filterKlijentiField;
	@FXML
	private TextField filterPopravciField;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		populateMainListView();
		
		PropertyValueFactory<Popravak, Long> datumPopravkaColumnFactory = new PropertyValueFactory<Popravak, Long>("datum");
		datumPopravkaColumn.setCellValueFactory(datumPopravkaColumnFactory);
		datumPopravkaColumn.setCellFactory(col -> new TableCell<Popravak, Long>() {
		    @Override
		    public void updateItem(Long datum, boolean empty) {
		        super.updateItem(datum, empty);
		        if (empty) {
		            setText(null);
		        } else {		        	
		        	String formattedDtm = Instant.ofEpochSecond(datum).atZone(ZoneId.of("GMT+1")).format(formatter);
		            setText(formattedDtm);
		        }
		    }
		});
		
		//datumPopravkaColumn.setCellValueFactory(new PropertyValueFactory<Popravak, String>("datum"));
		nazivPopravkaColumn.setCellValueFactory(new PropertyValueFactory<Popravak, String>("naziv")); 
		opisPopravkaColumn.setCellValueFactory(new PropertyValueFactory<Popravak, String>("opis")); 
		voziloNaPopravkuColumn.setCellValueFactory(new PropertyValueFactory<Popravak, String>("vozilo"));
		cijenaPopravkaColumn.setCellValueFactory(new PropertyValueFactory<Popravak, Double>("cijena"));
		
		populatePopravciTable();
	}
	
	public void populateMainListView() {
		List<Klijent> listaKlijenata = new ArrayList<>();
		try {
			listaKlijenata = BazaPodataka.dohvatiSveKlijente();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		ObservableList<Klijent> observableListaKlijenata = FXCollections.observableList(listaKlijenata);
		FilteredList<Klijent> filteredData = new FilteredList<>(observableListaKlijenata, p -> true);
		
		filterKlijentiField.textProperty().addListener((observable, odlValue, newValue) -> {
			filteredData.setPredicate(klijent -> {
				if(newValue == null || newValue.isEmpty()){
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (klijent.getIme().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (klijent.getPrezime().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (Integer.toString(klijent.getId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				return false;
				
			});			
		});
		
		SortedList<Klijent> sortedData = new SortedList<>(filteredData);
		
		mainKlijentiListView.setItems(sortedData);		
		mainKlijentiListView.getSelectionModel().select(0);
		populatePopravciTable();
	}
	
	public void populatePopravciTable() {
		Klijent klijent = mainKlijentiListView.getSelectionModel().getSelectedItem();
		
		List<Popravak> listaPopravaka = new ArrayList<>();
		try {
			listaPopravaka = BazaPodataka.dohvatiPopravke(klijent.getId());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		ObservableList<Popravak> popravci = FXCollections.observableArrayList(listaPopravaka);
		FilteredList<Popravak> filteredData = new FilteredList<>(popravci, p -> true);
		
		filterPopravciField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(popravak -> {
				if(newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
	        	String formattedDtm = Instant.ofEpochSecond(popravak.getDatum()).atZone(ZoneId.of("GMT+1")).format(formatter);
				
				if (popravak.getNaziv().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (popravak.getOpis().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (popravak.getVozilo().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Double.toString(popravak.getCijena()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (formattedDtm.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
				
                return false;
			});
		});
		
		SortedList<Popravak> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(popravciTable.comparatorProperty());
		
		popravciTable.setItems(sortedData);
		popravciTable.getSelectionModel().select(0);
		popravciTable.setPlaceholder(new Label("Odabrani korisnik nema popravaka"));
		
	}
	
	@FXML
	public void doExit(){
		Platform.exit();		
	}
	
	
	
	@FXML
	public void handleDodajNovogKlijenta() {
		try {
			FXMLLoader loader = new FXMLLoader(); 
			URL location = DodajNovogKlijentaController.class.getResource("../view/DodajNovogKlijentaView.fxml"); 
			loader.setLocation(location); 
			loader.setBuilderFactory(new JavaFXBuilderFactory()); 
			Parent root = (Parent)loader.load(location.openStream()); 
			DodajNovogKlijentaController controller = (DodajNovogKlijentaController)loader.getController();
			Stage stage = new Stage(); 
			stage.setTitle("Dodaj novog klijenta"); 
			stage.setScene(new Scene(root, 600, 300));
			stage.show(); 
			controller.setDijalogStage(stage);
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
	}
	
	@FXML
	public void handleObrisiKlijenta() {
		Klijent klijent = mainKlijentiListView.getSelectionModel().getSelectedItem();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Obriši klijenta?");
		//alert.getButtonTypes().set(0, new ButtonType("OK"));
		alert.getButtonTypes().set(1, new ButtonType("Poništi", ButtonData.CANCEL_CLOSE));
		alert.setHeaderText(null);
		alert.setContentText("Obriši klijenta " + klijent.getIme() + " " + klijent.getPrezime() + "?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				if(BazaPodataka.obrisiKlijenta(klijent.getId())){
					Alert alert1 = new Alert(AlertType.INFORMATION);
		        	alert1.setTitle("Klijent obrisan!");
		        	alert1.setHeaderText(null);
		        	alert1.setContentText("Klijent " + klijent.getIme() + " " + klijent.getPrezime() + " je uspješno obrisan!");
		        	Optional<ButtonType> result1 = alert1.showAndWait();
		        	if(result1.get() == ButtonType.OK)
		        		populateMainListView();
				};
			} catch (Exception e) {
				Alert alert1 = new Alert(AlertType.WARNING);
        		alert1.setTitle("Brisanje klijenta");
        		alert1.setHeaderText(null);
        		alert1.setContentText("Brisanje klijenta nije uspjelo!\n\n" + e.getMessage());
        		alert1.showAndWait();
			}			
		}
	}
	
	@FXML
	public void handleDodajNoviPopravak() {
		try {
			FXMLLoader loader = new FXMLLoader();
			URL location = DodajNoviPopravakController.class.getResource("../view/DodajNoviPopravakView.fxml"); 
			loader.setLocation(location); 
			loader.setBuilderFactory(new JavaFXBuilderFactory()); 
			Parent root = (Parent)loader.load(location.openStream()); 
			DodajNoviPopravakController controller = (DodajNoviPopravakController)loader.getController();

			//ObservableList<String> options = FXCollections.observableArrayList(listaImenaIPrezimena);
			List<Klijent> listaKlijenata = new ArrayList<>();
			List<Tip> listaTipova = new ArrayList<>();
			try {
				listaKlijenata = BazaPodataka.dohvatiSveKlijente();
				listaTipova = BazaPodataka.dohvatiSveTipove();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ObservableList<Klijent> optionsKlijenti = FXCollections.observableArrayList(listaKlijenata);
			controller.klijentComboBox.getItems().addAll(optionsKlijenti);
			//controller.klijentComboBox.setValue(this.mainKlijentiListView.getSelectionModel().getSelectedItem());
			controller.klijentComboBox.setValue(this.mainKlijentiListView.getSelectionModel().getSelectedItem());
			
			ObservableList<Tip> optionsTipovi = FXCollections.observableArrayList(listaTipova);
			controller.tipComboBox.getItems().addAll(optionsTipovi);
			//controller.klijentComboBox.setValue(this.mainKlijentiListView.getSelectionModel().getSelectedItem());
			controller.tipComboBox.setValue(listaTipova.get(1));
			
			Stage stage = new Stage(); 
			stage.setTitle("Dodaj novi popravak"); 
			stage.setScene(new Scene(root, 600, 300)); 
			stage.show();
			controller.setDijalogStage(stage);
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
	}
	
	@FXML
	public void handleObrisiPopravak() {
		Popravak popravak = popravciTable.getSelectionModel().getSelectedItem();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Obriši popravak?");
		alert.getButtonTypes().set(1, new ButtonType("Poništi", ButtonData.CANCEL_CLOSE));
		alert.setHeaderText(null);
		alert.setContentText("Obriši popravak: " + popravak.getNaziv() + "?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				if(BazaPodataka.obrisiPopravak(popravak.getId())){
					Alert alert1 = new Alert(AlertType.INFORMATION);
		        	alert1.setTitle("Popravak obrisan!");
		        	alert1.setHeaderText(null);
		        	alert1.setContentText("Popravak je uspješno obrisan!");
		        	Optional<ButtonType> result1 = alert1.showAndWait();
		        	if(result1.get() == ButtonType.OK)
		        		populatePopravciTable();
				};
			} catch (Exception e) {
				Alert alert1 = new Alert(AlertType.WARNING);
        		alert1.setTitle("Brisanje popravka");
        		alert1.setHeaderText(null);
        		alert1.setContentText("Brisanje popravka nije uspjelo!\n\n" + e.getMessage());
        		alert1.showAndWait();
			}			
		}
	}
	
	@FXML
	public void handleStatistika() {
		try {			
			FXMLLoader loader = new FXMLLoader(); 
			URL location = StatistikaController.class.getResource("../view/StatistikaView.fxml"); 
			loader.setLocation(location); 
			loader.setBuilderFactory(new JavaFXBuilderFactory()); 
			Parent root = (Parent)loader.load(location.openStream()); 
			StatistikaController controller = (StatistikaController)loader.getController();
			
			// PIE CHART
			Map<String, Integer> statistikaPopravaka = BazaPodataka.dohvatiStatistikuPopravaka();
			ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
			
			// TODO check if map is empty
			for (Map.Entry<String, Integer> entry : statistikaPopravaka.entrySet()) { 
				//System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				PieChart.Data popravak = new PieChart.Data(entry.getKey(), entry.getValue());
				pieChartData.add(popravak);
			}
			
			controller.tipPopravkaPieChart.setData(pieChartData);			
			
			// LINE CHART
			// klijenti
			Map<String, Integer> statistikaBrojaKlijenata = BazaPodataka.dohvatiStatistikuBrojaKlijenata();
	        XYChart.Series klijenti = new XYChart.Series();
	        klijenti.setName("Broj klijenata");
	        for (Map.Entry<String, Integer> entry : statistikaBrojaKlijenata.entrySet()) {
	        	klijenti.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
			}
	        controller.stopaLineChart.getData().add(klijenti);
	        
	        // popravci
	        Map<String, Integer> statistikaBrojaPopravaka = BazaPodataka.dohvatiStatistikuBrojaPopravaka();
	        XYChart.Series popravci = new XYChart.Series();
	        popravci.setName("Broj popravaka");
	        for (Map.Entry<String, Integer> entry : statistikaBrojaPopravaka.entrySet()) {
	        	popravci.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
			}
	        controller.stopaLineChart.getData().add(popravci);
	        
	        // BAR CHART
	        Map<String, Float> statistikaPrometa = BazaPodataka.dohvatiStatistikuPrometa();
	        XYChart.Series promet = new XYChart.Series();
	        promet.setName("Promet");
	        for (Map.Entry<String, Float> entry : statistikaPrometa.entrySet()) {
	        	promet.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
			}
	        controller.prometBarChart.getData().add(promet);
			
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("Statistika");
			stage.setScene(scene);
			
			
			stage.show();
			
			/*Scene scene = new Scene(new Group());
	        stage.setTitle("Imported Fruits");
	        stage.setWidth(500);
	        stage.setHeight(500);	        

	        ((Group) scene.getRoot()).getChildren().add(chart);
	        stage.setScene(scene);
	        stage.show();*/
			
			controller.setDijalogStage(stage);
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
	}
	
	@FXML
	public void handleNoviRacun() {
		try {
			FXMLLoader loader = new FXMLLoader();
			URL location = DodajNoviPopravakController.class.getResource("../view/DodajNoviRacunView.fxml"); 
			loader.setLocation(location); 
			loader.setBuilderFactory(new JavaFXBuilderFactory()); 
			Parent root = (Parent)loader.load(location.openStream()); 
			DodajNoviRacunController controller = (DodajNoviRacunController)loader.getController();

			//ObservableList<String> options = FXCollections.observableArrayList(listaImenaIPrezimena);
			List<Klijent> listaKlijenata = new ArrayList<>();
			
			try {
				listaKlijenata = BazaPodataka.dohvatiSveKlijente();				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ObservableList<Klijent> optionsKlijenti = FXCollections.observableArrayList(listaKlijenata);
			controller.klijentComboBox.getItems().addAll(optionsKlijenti);
			controller.klijentComboBox.setValue(this.mainKlijentiListView.getSelectionModel().getSelectedItem());
			
			List<Popravak> listaPopravaka = new ArrayList<>();
			try {
				// this.mainKlijentiListView.getSelectionModel().getSelectedItem().getId()
				listaPopravaka = BazaPodataka.dohvatiPopravke(controller.klijentComboBox.getValue().getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			ObservableList<Popravak> optionsPopravci = FXCollections.observableArrayList(listaPopravaka);
			controller.popravakComboBox.getItems().addAll(optionsPopravci);
			controller.popravakComboBox.setValue(optionsPopravci.get(0));
			
			// On change listener
			controller.klijentComboBox.valueProperty().addListener(new ChangeListener<Klijent>() {
				@Override
				public void changed(ObservableValue<? extends Klijent> observable, Klijent oldValue, Klijent newValue) {
					List<Popravak> listaNovihPopravaka = new ArrayList<>();
					try {
						listaNovihPopravaka = BazaPodataka.dohvatiPopravke(newValue.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}
					ObservableList<Popravak> optionsNoviPopravci = FXCollections.observableArrayList(listaNovihPopravaka);
					controller.popravakComboBox.getItems().removeAll(controller.popravakComboBox.getItems());
					controller.popravakComboBox.getItems().addAll(optionsNoviPopravci);
					controller.popravakComboBox.setValue(optionsNoviPopravci.get(0));
				}
			});
			
			Stage stage = new Stage(); 
			stage.setTitle("Generiraj raèun"); 
			stage.setScene(new Scene(root, 600, 300)); 
			stage.show();
			controller.setDijalogStage(stage);
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
	}
}
