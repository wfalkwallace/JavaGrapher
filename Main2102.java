import java.io.*;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * 
 */

/**
 * @author wgf2104
 *
 */
public class Main2102 {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		Random r = new Random();
		MyGraphMap g = new MyGraphMap();
		
		File mapData = null;
		JFrame frame = new JFrame("WFW's COMS3137 Graph Viewer - Choose a File"); //set title
		JFileChooser fc = new JFileChooser(); 
		fc.setDialogTitle("Pick a valid map data file");
		int returnVal = fc.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			mapData = fc.getSelectedFile();
		}
		if(mapData == null){
			System.out.println("System closed because an invalid file was offered");
			System.exit(1);
		}
		
//		FileGrapher fg = new FileGrapher(new File("/Users/wgf2104/Desktop/worldcities.txt"));
//		FileGrapher fg = new FileGrapher(new File("/Users/wgf2104/Desktop/fict100.txt"));
		FileGrapher fg = new FileGrapher(mapData);

		
		g = fg.graph();

		//populate graph with edges (2-8 per vertex/weight 100-2000)
		MyGraphNode[] vertices = g.getVertices().keySet().toArray(new MyGraphNode[g.size()]);
		ArrayList<MyGraphNode> edges = new ArrayList<MyGraphNode>();
		MyGraphNode e;

		for(MyGraphNode v: vertices){
			//random betw. 2-8 incl.
			for(int i=0; i<r.nextInt(6) + 2; i++){
				//produces edge from a different node in the vertex-space with random weight 100-2000
				e = vertices[r.nextInt(vertices.length)];
				while(e==v || edges.indexOf(e)>=0){
					e=vertices[r.nextInt(vertices.length)];
				}
				g.addE(v, e, r.nextInt(1900)+100);
			}
			edges.clear();
		}

/*
 * Debugging/testing - print out graph as adj list.
 */
//		int i=0;
//		for(MyGraphNode vert: vertices){
//			i++;
//			System.out.print(i + ":  ");
//			System.out.print(vert.getCity() + ", ");
//			System.out.print(vert.getCountry());
//			System.out.print("--->");
//			for(MyGraphEdge edg: g.getEdges(vert)){
//				System.out.print(edg.getEnd().getCity() + ", ");
//				System.out.print(edg.getEnd().getCountry() + ", ");
//				System.out.print(edg.getWeight());
//				System.out.print("; ");
//			}
//			System.out.println("");
//		}
		
		new MyGraphGUI(g, mapData.getName());
		
		
	}
}
