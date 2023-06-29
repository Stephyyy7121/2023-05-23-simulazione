package it.polito.tdp.baseball.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.baseball.db.BaseballDAO;

public class Model {
	
	private BaseballDAO dao ;
	private Map<String, People> idMapPlayer;
	private Graph<People, DefaultEdge> grafo;
	private List<People> vertici;
	private List<Arco> archi;
	
	//ricorsione 
	private int numGiocatori;
	private double salarioTot;
	private List<People> dreamTeam;
	
	
	public Model() {
		
		this.dao = new BaseballDAO();
		this.idMapPlayer = new HashMap<>();
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		this.archi = new ArrayList<>();
	}
	
	
	public void riempireIdMap() {
		
		List<People> allPlayer = this.dao.readAllPlayers();
		
		for (People p: allPlayer) {
			
			if (this.idMapPlayer.get(p.getPlayerID())== null) {
				this.idMapPlayer.put(p.getPlayerID(), p);
			}
		}
	}
	
	public void loadNodes(int year, double salary) {
		
		riempireIdMap();
		
		if (this.vertici.isEmpty()) {
			this.vertici = this.dao.getVertici(year, salary, idMapPlayer);
		}
	}
	
	public void clearGraph() {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		this.vertici = new ArrayList<>();
	}
	
	public void creaGrafo(int year, double salary) {
		
		clearGraph();
		loadNodes(year, salary);
		
		Graphs.addAllVertices(this.grafo, this.vertici);
		
		if (this.archi.isEmpty()) {
			this.archi = this.dao.getArchi(year, salary, idMapPlayer);
		}
		
		for (Arco a : this.archi) {
			People source = a.getSource();
			People target = a.getTarget();
			if (this.vertici.contains(source) && this.vertici.contains(target)) {
				this.grafo.addEdge(source, target);
			}
			
		}
	}
	
	public List<People> getVertici() {
		return this.vertici;
	}
	
	public int numArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	public People gradoMassimo() {
		
		People massimo = null;
		int grado = 0;
		for (People p : this.vertici) {
			
			int cuurentGrado = this.grafo.outDegreeOf(p);
			if (cuurentGrado > grado) {
				grado = cuurentGrado;
				massimo  = p ;
			}
		}
		
		return massimo;
	}
	
	
	public int numComponentiConnesse() {
		
		ConnectivityInspector<People, DefaultEdge> ci = new ConnectivityInspector<>(grafo);
		List<Set<People>> listaConnesse = ci.connectedSets();
		
		return listaConnesse.size();
	}
	
	
	//ricorsione 
	
	public List<People> getDreamTeam() {
		
		this.dreamTeam = new ArrayList<>();
		this.numGiocatori = 0;
		this.salarioTot =0.0;
		
		//dominio  --> all'inizio tutti i vertici ma poi piano piano bisogna togliere tutti i nodi successori in quanto 
		//per come e' costruito il grafo, due nodi sono connessi se sono stati nello stesso team quel'anno;
		List<People> dominioIniziale = new ArrayList<>(this.grafo.vertexSet());
		
		//togliere tutti i nodi isolati
		List<People> isolati = new ArrayList<>();
		for (People p : this.vertici) {
			if (this.grafo.outDegreeOf(p)==0) {
				isolati.add(p);
			}
		}
		dominioIniziale.removeAll(isolati);
		
		List<People> parziale = new ArrayList<>();
		
		ricorsione(parziale, dominioIniziale);
		
		return this.dreamTeam;
		
	}
	
	private void ricorsione(List<People> parziale, List<People> dominio) {
		
		if (dominio.isEmpty()) {
			
			double money = getSalarioTotale(parziale);
			if (money > this.salarioTot) {
				if (parziale.size() >= this.numGiocatori) {
					this.numGiocatori = parziale.size();
					this.salarioTot = money;
					this.dreamTeam = new ArrayList<>(parziale);
				}
			}
			return;
		}
		
		if (parziale.size() > this.numGiocatori) {
			this.numGiocatori = parziale.size();
			this.dreamTeam = new ArrayList<>(parziale);
		}
		
		for (People p : dominio) {
			if (!parziale.contains(p)) {
				parziale.add(p);
				
				List<People> newDominio = new ArrayList<>(dominio);
				newDominio.remove(p);
				newDominio.removeAll(Graphs.neighborListOf(this.grafo, p));
				
				ricorsione(parziale, newDominio);
				parziale.remove(parziale.size()-1);
				
			}
		}
	}


	private double getSalarioTotale(List<People> parziale) {
		// TODO Auto-generated method stub
		
		double salarioTot = 0.0;
	
		return 0;
	}
	
	
	
}
	