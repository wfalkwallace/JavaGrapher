import java.util.*;

/**
 * 
 */

/**
 * @author wgf2104
 *
 */
public class MyGraphMap {

	private Hashtable<MyGraphNode, ArrayList<MyGraphEdge>> graph;
	private int numVert;

	public MyGraphMap(){
		graph = new Hashtable<MyGraphNode, ArrayList<MyGraphEdge>>();
	}

	public void addV(MyGraphNode v){
		graph.put(v, new ArrayList<MyGraphEdge>());
	}
	
	public void addE(MyGraphNode v, MyGraphNode e, int w){
		if(graph.containsKey(v)){
			ArrayList<MyGraphEdge> tmpedgelist = graph.get(v);
			tmpedgelist.add(new MyGraphEdge(v, e, w));
			graph.put(v, tmpedgelist);
		}
		else{
			addV(v);
			addE(v, e, w);
		}
	}
	
	public void addE(MyGraphNode v, MyGraphEdge e){
		if(graph.containsKey(v)){
			graph.get(v).add(e);
		}
		else{
			addV(v);
			addE(v, e);
		}
	}

	/**
	 * @return the vertices
	 */
	public Hashtable<MyGraphNode, ArrayList<MyGraphEdge>> getVertices() {
		return graph;
	}
	
	public ArrayList<MyGraphEdge> getEdges(MyGraphNode v){
		return graph.get(v);
	}

	/**
	 * @return the numVert
	 */
	public int size() {
		return numVert;
	}

	/**
	 * @param numVert the numVert to set
	 */
	public void setSize(int numVert) {
		this.numVert = numVert;
	}
	
	public int getOutCount(MyGraphNode v){
		return graph.get(v).size();
	}
	
	public int getInCount(MyGraphNode v){
		int inCount=0;
		for(MyGraphNode vert: graph.keySet()){
			for(MyGraphEdge edg: graph.get(vert)){
				if(edg.getEnd() == v){
					inCount++;
					continue;
				}
			}
		}
		return inCount;
	}
	
	//get vertex by city name
	public MyGraphNode getVertex(String city){
		for(MyGraphNode vert: graph.keySet()){
			if(vert.getCity()==city)
				return vert;
		}
		return null;
	}
	
	//get edge by city names
	public int getEdgeWeight(String vcity, String ecity){
		for(MyGraphNode vert: graph.keySet()){
			if(vert.getCity().equals(vcity)){
				for(MyGraphEdge e: graph.get(vert)){
					if(e.getEnd().getCity().equals(ecity)){
						return e.getWeight();
					}
				}
			}
		}
		return -1;
	}
	
	public String[] getStateList(){
		String[] states = new String[numVert];
		int i=0;
		for(MyGraphNode v: graph.keySet()){
			states[i] = v.getCountry();
			i++;
		}
		return states;
	}
	
	public String[] getCityList(){
		String[] cities = new String[numVert];
		int i=0;
		for(MyGraphNode v: graph.keySet()){
			cities[i] = v.getCity();
			i++;
		}
		return cities;
	}
	
	public ArrayList<String> getCitiesByState(String state){
		ArrayList<String> cities = new ArrayList<String>();
		for(MyGraphNode v: graph.keySet()){
			if(v.getCountry().equals(state))
				cities.add(v.getCity()); 
		}
		return cities;
	}
	
	public ArrayList<String> getCityAdjacencies(String city){
		ArrayList<String> adjs = new ArrayList<String>();
		for(MyGraphNode v: graph.keySet()){
			if(v.getCity().equals(city)){
				for(MyGraphEdge e: graph.get(v)){
					adjs.add(e.getEnd().getCity()); 
				}
			}
		}
		return adjs;
	}
	

}
