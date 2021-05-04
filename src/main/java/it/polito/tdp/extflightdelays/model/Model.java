package it.polito.tdp.extflightdelays.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private Graph<Airport,DefaultWeightedEdge> grafo;
	private ExtFlightDelaysDAO dao;
	private Map<Integer, Airport> idMap;
	
	public Model() {
		dao = new ExtFlightDelaysDAO();
		idMap = new HashMap<Integer,Airport>();
	}
	
	public void creaGrafo(double media) {
		grafo = new SimpleWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		dao.loadAllAirports(idMap);
		Graphs.addAllVertices(grafo, idMap.values());
		
		System.out.println("# VERTICI: "+grafo.vertexSet().size());
		
		for(Rotta r : dao.getRotte(media,idMap)) {
			if(grafo.getEdge(r.getAirportOrigin(), r.getAirportDestination()) == null)
				Graphs.addEdge(this.grafo, r.getAirportOrigin(), r.getAirportDestination(), r.getDistance());
			else {
				double avgVecchia = grafo.getEdgeWeight(grafo.getEdge(r.getAirportOrigin(),r.getAirportDestination()));
				Graphs.addEdge(this.grafo, r.getAirportOrigin(), r.getAirportDestination(), (r.getDistance()+avgVecchia)/2);
			}
		}
	}
	
	public int contaVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int contaArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Rotta> stampaListaArchi() {
		List<Rotta> result = new ArrayList<Rotta>();
		
		for(DefaultWeightedEdge dwe : grafo.edgeSet()) {
			result.add(new Rotta(grafo.getEdgeSource(dwe),grafo.getEdgeTarget(dwe),grafo.getEdgeWeight(dwe)));
		}
		
		return result;
	}

}
