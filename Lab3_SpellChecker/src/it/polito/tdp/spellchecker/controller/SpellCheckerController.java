package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SpellCheckerController {
	
	private Dictionary dictionary;
	private List<String> inputTextList;
	private boolean ricercaLineare=false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> btnComboBox;

    @FXML
    private TextField txtInput;

    @FXML
    private Button btnCheck;

    @FXML
    private TextArea txtResult;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblErrori;

    @FXML
    private Button btnClear;

    @FXML
    void doClearText(ActionEvent event) {
    	txtInput.clear();
    	txtResult.clear();
    	lblErrori.setText("");
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	if(btnComboBox.getValue()==null) {
    		txtInput.setText("Errore: Selezionare una lingua!");
    		return;
    	}
    	if(!dictionary.loadDictionary(btnComboBox.getValue())) {
    		txtInput.setText("Errore: Caricamento dizionario fallito!");
    		return;
    	}
    	String text = txtInput.getText();
    	if(text.isEmpty()) {
    		txtInput.setText("Errore: Inserire un testo!");
    		return;
    	}
    	long start = System.nanoTime();
    	text = text.replaceAll("\n", " ");
    	StringTokenizer st = new StringTokenizer(text, " ");
    	while(st.hasMoreTokens()) {
    		inputTextList.add(st.nextToken());
    	}
    	List<RichWord> outputTextList; //come distingue fra le due ricerche?
    	if(ricercaLineare) {
    		outputTextList=dictionary.spellCheckTextLinear(inputTextList);
    	}
    	else {
    		outputTextList=dictionary.spellCheckText(inputTextList);
    	}
    	
    	int numErrori = 0;
    	StringBuilder richText = new StringBuilder();
    	for(RichWord w: outputTextList) {
    		if(!w.isCorrect()) {
    			numErrori++;
    			richText.append(w.getParola()+"\n");
    		}
    	}
    	Long end = System.nanoTime();
    	txtResult.setText(richText.toString());
    	lblStatus.setText("Spell Check completato in "+(end-start)+" secondi");
    	lblErrori.setText("Il testo contiene " +numErrori+ " errori");

    }

    @FXML
    void initialize() {
        assert btnComboBox != null : "fx:id=\"btnComboBox\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnCheck != null : "fx:id=\"btnCheck\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblStatus != null : "fx:id=\"lblStatus\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblErrori != null : "fx:id=\"lblErrori\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'SpellChecker.fxml'.";
    }

	void setModel(Dictionary model) {
		dictionary = new Dictionary();
        inputTextList = new LinkedList<String>();
        txtResult.setDisable(true);
        btnComboBox.getItems().addAll("English", "Italian");
		
	}
}
