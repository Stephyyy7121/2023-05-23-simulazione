package it.polito.tdp.baseball.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.baseball.db.BaseballDAO;

public class Model {
	
	BaseballDAO dao;
	
	Graph<People, DefaultEdge> grafo;
	List<People> vertici;
	//lista archi
	
	
	//necessario avere un IdentityMap per potere ottenere l'oggeto People a partire dall'id dei giocatori
	Map<String, People> idMapPlayer;
	
	
	public Model() {
		
		dao = new BaseballDAO();
		this.grafo = new SimpleGraph<People,DefaultEdge>(DefaultEdge.class);
		this.idMapPlayer = new HashMap<>();
		
	}
	
	public Map<String, People> getIdMapPlayer() {
		return this.idMapPlayer;
	}
	
	public void loadNodes(int anno, double salario) {
		
		if (this.vertici.isEmpty()) {
			this.vertici = dao.getVertices(anno, salario);
		}
	}
	
	public void clearGraph() {
		
		this.grafo =  new SimpleGraph<People,DefaultEdge>(DefaultEdge.class);
		this.vertici = new ArrayList<>();
	}
	
	public void creaGrafo(int anno, double salario) {
		
		clearGraph();
		loadNodes(anno, salario);
		
		Graphs.addAllVertices(this.grafo, this.vertici);
		
		for (People p : this.vertici) {
			idMapPlayer.put(p.getPlayerID(), p);
		}
		
		
		//archi --> due vertici sono connessi se hanno giocato nella stessa squadra nell'anno indicato in input 
		List<Arco> archi = dao.getEdge(anno, salario, idMapPlayer);
		
		for (Arco a : archi) {
			this.grafo.addEdge(a.getSource(), a.getTarget());
		}
	}
	
	public int getNumVertici() {
		return this.grafo.edgeSet().size();
	}
	
	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}
	}
	