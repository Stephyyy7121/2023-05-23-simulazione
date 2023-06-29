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
    	
    	txtResult.clear();
    	if (!this.gradoCreato) {
    		txtResult.appendText("Il grafo non e' stato creato\n");
    	}
    	
    	int num = this.model.numComponentiConnesse();
    	txtResult.appendText("Ci sono " + num + " componenti connesse\n");
    }

    
    
    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	String inputSalary = txtSalary.getText();
    	String inputAnno = txtYear.getText();
    	
    	if (inputSalary.compareTo("") ==0 || inputAnno.compareTo("")==0) {
    		txtResult.appendText("Inserire valore\n");
    	}
    	
    	int anno = 0;
    	double salary = 0.0;
    	
    	try {
    		anno = Integer.parseInt(inputAnno);
    		salary = Double.parseDouble(inputSalary)*1000000;
    		
    	}catch (NumberFormatException e ) {
    		txtResult.appendText("Non sono statri inseriti dei valori accettabili\n");
    		return ;
    	}
    	
    	if (salary <= 0) {
    		txtResult.appendText("Inserire uno stipendio positivo\n");
    	}
    	if (anno < 1871 || anno > 2019) {
    		txtResult.appendText("Inserire anno compreso tra 1871 e 2019\n");
    	}
    	
    	this.model.creaGrafo(anno, salary);
    	this.gradoCreato = true;
    	txtResult.appendText("Grafo creato !\n#Vertici: " + this.model.getVertici().size() + "\n#Archi: " + this.model.numArchi()+"\n");
    	this.btnConnesse.setDisable(false);
    	this.btnGradoMassimo.setDisable(false);
    }

    
    @FXML
    void doDreamTeam(ActionEvent event) {

    }

    
    @FXML
    void doGradoMassimo(ActionEvent event) {


    	txtResult.clear();
    	if (!this.gradoCreato) {
    		txtResult.appendText("Il grafo non e' stato creato\n");
    	}
    	
    	People p = this.model.gradoMassimo();
    	txtResult.appendText("Il vertice con nodo massimo e' : " + p +"\n");
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
