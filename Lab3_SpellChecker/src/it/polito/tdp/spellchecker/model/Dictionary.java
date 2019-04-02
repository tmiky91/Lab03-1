package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dictionary {
	
	private List<String> dizionario;
	//private List<String> dizionario = new ArrayList<String>();
	//private List<RichWord> richWords = new ArrayList<RichWord>();
	private String language;
	
	public Dictionary() {
		
	}

	public boolean loadDictionary(String language) {
		if(dizionario != null && this.language.equals(language)) { //Controllo se il dizionario è già stato utilizzato con la lingua selezionata
			return true;
		}
		dizionario = new ArrayList<String>(); //Perchè non posso inizializzarla fuori dal metodo?
		this.language=language; //Che vuol dire?
		try {
			FileReader fr = new FileReader("rsc/"+language+".txt"); //Si può mettere anche FileReader(language)?
			BufferedReader br = new BufferedReader(fr);
			String word;
			while((word=br.readLine())!=null) {
				
				dizionario.add(word.toLowerCase());
			}
			Collections.sort(dizionario); //E' necessario?
			br.close();
			System.out.print("Dizionario "+language+" caricato. Trovate " + dizionario.size()+ " parole.");
			return true;
		}
		catch(IOException e) {
			System.out.println("Errore lettura file!");
			return false;
		}
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList){
		List<RichWord> richWords = new ArrayList<RichWord>();
		for(String str: inputTextList) {
			RichWord richWord = new RichWord(str);
			if(dizionario.contains(str)) {
				richWord.setCorrect(true);
			}
			else {
				richWord.setCorrect(false);
			}
			richWords.add(richWord);
		}
		return richWords;
	}
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList){
		List<RichWord> richWords = new ArrayList<RichWord>();
		for(String str: inputTextList) {
			RichWord richWord = new RichWord(str);
			boolean trovato = false;
			for(String word: dizionario) {
				if(word.equalsIgnoreCase(str)) {
					trovato = true;
					break;
				}
			}
			if(trovato) {
				richWord.setCorrect(true);
			}
			else {
				richWord.setCorrect(false);
			}
			richWords.add(richWord);
		}
		return richWords;	
	}
}
