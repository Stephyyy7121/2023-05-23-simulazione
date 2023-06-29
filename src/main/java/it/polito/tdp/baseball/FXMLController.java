package it.polito.tdp.baseball;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

//import it.polito.tdp.baseball.model.Grado;
import it.polito.tdp.baseball.model.Model;
import it.polito.tdp.baseball.model.People;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnConnesse;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnDreamTeam;

    @FXML
    private Button btnGradoMassimo;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtYear;

    private boolean gradoCreato = false;
    
    @FXML
    void doCalcolaConnesse(ActionEvent event) {
    	
    }

    
    
    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	String inputAnno = this.txtYear.getText();
    	String inputSalario = this.txtSalary.getText();
    	
    	//controllo input 
    	if (inputAnno =="" || inputSalario == "") {
    		txtResult.setText("Errore : Non e' stato inserito dato");
    	}
    	
    	//conversione
    	Integer anno = 0;
    	Double salario = 0.0;
    	try {
    		anno = Integer.parseInt(inputAnno);
        	 salario = Double.parseDouble(inputSalario);
    	} catch (NumberFormatException e ) {
    		txtResult.setText("Errore : non sono stati inseriti dei valori validi");
    		return;
    	}
    	
    	model.creaGrafo(anno, salario);
    	txtResult.appendText("Grafo creato!\n#Vertici: " + this.model.getNumVertici() + "\n#Archi: " + this.model.getNumVertici());
    	
    	
    	
    	
    }

    
    @FXML
    void doDreamTeam(ActionEvent event) {

    }

    
    @FXML
    void doGradoMassimo(ActionEvent event) {

    }

    
    @FXML
    void initialize() {
        assert btnConnesse != null : "fx:id=\"btnConnesse\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGradoMassimo != null : "fx:id=\"btnGradoMassimo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSalary != null : "fx:id=\"txtSalary\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYear != null : "fx:id=\"txtYear\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }

}
