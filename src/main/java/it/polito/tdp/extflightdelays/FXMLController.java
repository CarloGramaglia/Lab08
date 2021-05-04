/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.extflightdelays.model.Model;
import it.polito.tdp.extflightdelays.model.Rotta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="distanzaMinima"
    private TextField distanzaMinima; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	this.txtResult.clear();
    	
    	double media = 0;
    	try {
    		media = Double.parseDouble(this.distanzaMinima.getText());
    	}catch(NumberFormatException nfe) {
    		this.txtResult.setText("Inserire un numero!!");
    		return;
    	}
    	
    	model.creaGrafo(media);
    	
    	this.txtResult.appendText("Ci sono "+model.contaVertici()+" vertici!"+"\n");
    	this.txtResult.appendText("Ci sono "+model.contaArchi()+" archi!"+"\n");
    	
    	StringBuilder sb = new StringBuilder();
    	for(Rotta r: model.stampaListaArchi()) {
    		sb.append(String.format("%-70s", r.getAirportOrigin().getAirportName()));
    		sb.append(String.format("%-70s", r.getAirportDestination().getAirportName()));
    		sb.append(String.format("%-1f\n", r.getDistance()));
    	}
    	
    	this.txtResult.appendText("Lista archi: \n"+sb.toString());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'Scene.fxml'.";
    }

    public void setModel(Model model) {
    	this.model = model;
    	this.txtResult.setStyle("-fx-font-family: monospace");
    }
}
