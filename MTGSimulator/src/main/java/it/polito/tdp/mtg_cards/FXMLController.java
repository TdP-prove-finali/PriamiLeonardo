/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.mtg_cards;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.mtg_cards.model.Carta;
import it.polito.tdp.mtg_cards.model.Espansione;
import it.polito.tdp.mtg_cards.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxEspansione"
    private ComboBox<Espansione> boxEspansione; // Value injected by FXMLLoader

    @FXML // fx:id="boxPrimaCarta"
    private ComboBox<Carta> boxPrimaCarta; // Value injected by FXMLLoader

    @FXML // fx:id="btnApriBuste"
    private Button btnApriBuste; // Value injected by FXMLLoader

    @FXML // fx:id="boxSecondaCarta"
    private ComboBox<Carta> boxSecondaCarta; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaMazzo"
    private Button btnCreaMazzo; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doApriBuste(ActionEvent event) {
    	txtResult.clear();
    	Espansione e = boxEspansione.getValue();
    	if(e==null) {
    		txtResult.appendText("scegli prima l'espansione");
    		return;
    	}
    	List<Carta> carte = this.model.elencoCarte(e.getCode());
    	for(Carta s : carte) {
    		txtResult.appendText(s+"\n");
    	}
    	boxPrimaCarta.getItems().clear();
    	boxSecondaCarta.getItems().clear();
    	boxPrimaCarta.getItems().addAll(carte);
    	boxSecondaCarta.getItems().addAll(carte);
    }

    @FXML
    void doCreaMazzo(ActionEvent event) {
    	txtResult.clear();
    	Carta c1 = boxPrimaCarta.getValue();
    	Carta c2 = boxSecondaCarta.getValue();
    	if(c1==c2 && c1!=null) {
    		txtResult.appendText("seleziona due carte diverse tra loro");
    		boxPrimaCarta.getItems().clear();
        	boxSecondaCarta.getItems().clear();
    		return;
    	}
    	if(c1==null && c2!=null) {
    		txtResult.appendText("seleziona la prima carta e poi eventualmente la seconda");
    		boxPrimaCarta.getItems().clear();
        	boxSecondaCarta.getItems().clear();
    		return;
    	}
    	this.model.ottimo(c1, c2);
    	for(Carta c : this.model.getMagie()) {
    		txtResult.appendText(c.toString()+"\n");
    	}
    	txtResult.appendText("Pianure: "+this.model.getNumeroPianureOttimo()+"\n");
    	txtResult.appendText("Isole: "+this.model.getNumeroIsoleOttimo()+"\n");
    	txtResult.appendText("Paludi: "+this.model.getNumeroPaludiOttimo()+"\n");
    	txtResult.appendText("Montagne: "+this.model.getNumeroMontagneOttimo()+"\n");
    	txtResult.appendText("Foreste: "+this.model.getNumeroForesteOttimo()+"\n");
    	txtResult.appendText("Terre totali: "+this.model.getNumeroTerreOttimo()+"\n");
    	txtResult.appendText("Forza mazzo: "+this.model.getForzaMassima()+"\n");
    	boxPrimaCarta.getItems().clear();
    	boxSecondaCarta.getItems().clear();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxEspansione != null : "fx:id=\"boxEspansione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxPrimaCarta != null : "fx:id=\"boxPrimaCarta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnApriBuste != null : "fx:id=\"btnApriBuste\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxSecondaCarta != null : "fx:id=\"boxSecondaCarta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaMazzo != null : "fx:id=\"btnCreaMazzo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxEspansione.getItems().addAll(this.model.espansioni());
    }
}
