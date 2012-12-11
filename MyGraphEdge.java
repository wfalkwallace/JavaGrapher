import org.apache.commons.collections15.Transformer;

/**
 * 
 */

/**
 * @author wgf2104
 *
 */
public class MyGraphEdge implements Transformer<MyGraphEdge, Number>{

	MyGraphNode start;
	MyGraphNode end;
	int weight;
	
	public MyGraphEdge(MyGraphNode a, MyGraphNode b){
		start = a;
		end = b;
		weight = 0;
	}
	
	public MyGraphEdge(MyGraphNode a, MyGraphNode b, int w){
		start = a;
		end = b;
		weight = w;
	}

	/**
	 * @return the start
	 */
	public MyGraphNode getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public MyGraphNode getEnd() {
		return end;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	@Override
	public Number transform(MyGraphEdge arg0) {
		// TODO Auto-generated method stub
		return arg0.getWeight();
	}
	
	
	
}
