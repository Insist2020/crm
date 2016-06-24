package hr.tvz.crm.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

public class StatistikaController {
	
	@FXML
	PieChart tipPopravkaPieChart;
	@FXML
	LineChart<String, Number> stopaLineChart;
	@FXML
	BarChart<String, Float> prometBarChart;
	
	private Stage dijalogStage;
	
	public void setDijalogStage(Stage dijalogStage) {
        this.dijalogStage = dijalogStage;
    }
	
}
