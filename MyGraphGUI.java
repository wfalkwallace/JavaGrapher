/**
 * 
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;


import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.*;
//import edu.uci.ics.jung.graph.util.*;
//import edu.uci.ics.jung.samples.SimpleGraphDraw;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;


public class MyGraphGUI{


	Dimension dim;
	String filePath;
	Graph<MyGraphNode, MyGraphEdge> jungGraph;
	JFrame frame;
	Random r = new Random();

	public MyGraphGUI(final MyGraphMap gr, String file){
		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		dim = toolkit.getScreenSize();	
		filePath = file;
		jungGraph = new DirectedSparseMultigraph<MyGraphNode, MyGraphEdge>();

		MyGraphNode[] vertices = gr.getVertices().keySet().toArray(new MyGraphNode[gr.size()]);

		for(MyGraphNode v: vertices){
			jungGraph.addVertex(v);
		}

		for(MyGraphNode v: vertices){
			jungGraph.addVertex(v);
			for(MyGraphEdge e: gr.getEdges(v)){
				jungGraph.addEdge(e, v, e.getEnd());
			}
		}

		//visualization code...

		Layout<MyGraphNode, MyGraphEdge> layout = new CircleLayout<MyGraphNode, MyGraphEdge>(jungGraph);
		layout.setSize(new Dimension((int)dim.getWidth()*3/5,(int)dim.getWidth()*3/5)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<MyGraphNode, MyGraphEdge> vv = new BasicVisualizationServer<MyGraphNode, MyGraphEdge>(layout);
		//		vv.setPreferredSize(new Dimension((int)screenDim.getWidth()*2/3, (int)screenDim.getHeight())); //Sets the viewing area size

		frame = new JFrame("WFW's COMS3137 Graph Viewer"); //set title
		frame.setPreferredSize(new Dimension((int)dim.getWidth(), (int)dim.getHeight())); //fill screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close on x-out
		BorderLayout bl = new BorderLayout(); //use border layout (NSEWC)
		frame.setLayout(bl); //apply layout to frame

		JScrollPane graphScroller = new JScrollPane(vv); //put graph in scrollpane in case too big
		frame.add(graphScroller, BorderLayout.CENTER); //scroll pane in large left pane (no WEST added, so center is left)

		//load and save buttons
		JButton load_button = new JButton("load");
		JButton quit_button = new JButton("quit");
		JPanel action_panel = new JPanel(); //rightside action Panel
		action_panel.setLayout(new BorderLayout());
		JPanel file_panel = new JPanel(); //top save-load display panel
		JPanel control_panel = new JPanel(); //save-load button Panel
		file_panel.setLayout(new BoxLayout(file_panel, BoxLayout.Y_AXIS));
		final JTextArea pathTextArea = new JTextArea(1,30);
		pathTextArea.setEditable(false);
		pathTextArea.append(filePath);
		control_panel.add(load_button);
		control_panel.add(quit_button);
		file_panel.add(pathTextArea);
		file_panel.add(control_panel);
		action_panel.add(file_panel, BorderLayout.NORTH);

		JPanel ptpicker = new JPanel();
		JPanel info_panel = new JPanel();
		info_panel.setLayout(new BoxLayout(info_panel, BoxLayout.Y_AXIS));
		//state info
		JComboBox statelist = new JComboBox(gr.getStateList());
		statelist.insertItemAt("States", 0);
		statelist.setSelectedIndex(0);
		ptpicker.add(statelist);
		//city info/start pt
		final JComboBox citylist = new JComboBox(gr.getCityList());
		citylist.insertItemAt("Cities", 0);
		citylist.setSelectedIndex(0);
		ptpicker.add(citylist);
		info_panel.add(ptpicker);
		final JTextArea infoText = new JTextArea();
		JPanel closepanel = new JPanel();
		JButton gpsClose = new JButton("Closest by GPS");
		JButton costClose = new JButton("Closest by Cost");
		closepanel.add(gpsClose);
		closepanel.add(costClose);

		gpsClose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				MyGraphNode v = gr.getVertex((String)citylist.getSelectedItem());
				String closename = "";
				double closedist = 1000000;
				double dist;
				for(MyGraphEdge e: gr.getEdges(v)){
					dist = Math.sqrt(Math.pow((v.getX()-e.getEnd().getX()), 2)) + Math.pow((v.getY()-e.getEnd().getY()), 2);
					if(dist < closedist){
						closename = e.getEnd().getCity();
						closedist = dist;
					}
				}
				infoText.setText("");
				infoText.append("The closest city to " + v.getCity() + ", by GPS, is " + closename + ", as a distance of " + (int)closedist + ".\n");
			}
		});

		costClose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				MyGraphNode v = gr.getVertex((String)citylist.getSelectedItem());
				String closename = "";
				double closedist = 1000000;
				double dist;
				for(MyGraphEdge e: gr.getEdges(v)){
					dist = e.getWeight();
					if(dist < closedist){
						closename = e.getEnd().getCity();
						closedist = dist;
					}
				}
				infoText.setText("");
				infoText.append("The closest city to " + v.getCity() + ", by Edge Cost, is " + closename + ", as a distance of " + (int)closedist + ".\n");
			}
		});


		statelist.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				JComboBox cb = (JComboBox)evt.getSource();
				String state = (String)cb.getSelectedItem();
				infoText.setText("");
				if(!state.equals("States")){
					for(String c: gr.getCitiesByState(state)){
						infoText.append("-" + c + " is in " + state + " with " + gr.getInCount(gr.getVertex(c)) + " incoming and " + gr.getOutCount(gr.getVertex(c)) + " outgoing\n");
					}
				}
			}
		});

		citylist.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				JComboBox cb = (JComboBox)evt.getSource();
				String city = (String)cb.getSelectedItem();
				infoText.setText("");
				if(!city.equals("City")){
					infoText.append(city + " is in " + gr.getVertex(city).getCountry() + " at (" + gr.getVertex(city).getX() + ", " + gr.getVertex(city).getY() + ")\n");
					for(String a: gr.getCityAdjacencies(city)){
						infoText.append("-" + a + " is adjacent to " + city + " with a cost of " + gr.getEdgeWeight(city, a) + "\n");
					}
				}
			}
		});

		info_panel.add(closepanel);
		info_panel.add(infoText);
		action_panel.add(info_panel, BorderLayout.CENTER);

		//add menus etc here


		frame.add(action_panel, BorderLayout.EAST);

		//add button action listeners
		load_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				MyGraphMap gra = null;
				JFileChooser fc = new JFileChooser(); 
				int returnVal = fc.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					final File file = fc.getSelectedFile();
					filePath = file.getAbsolutePath();
					pathTextArea.append(filePath + "\n");
					FileGrapher fg = new FileGrapher(file);
					try {
						gra = fg.graph();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				MyGraphNode[] vertices = gra.getVertices().keySet().toArray(new MyGraphNode[gra.size()]);
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
						gra.addE(v, e, r.nextInt(1900)+100);
					}
					edges.clear();

					jungGraph = new DirectedSparseMultigraph<MyGraphNode, MyGraphEdge>();

					MyGraphNode[] verts = gra.getVertices().keySet().toArray(new MyGraphNode[gra.size()]);

					for(MyGraphNode ve: verts){
						jungGraph.addVertex(ve);
					}

					for(MyGraphNode ve: verts){
						jungGraph.addVertex(ve);
						for(MyGraphEdge ed: gra.getEdges(ve)){
							jungGraph.addEdge(ed, ve, ed.getEnd());
						}
					}

				}
				frame.repaint();
			}
		});
		quit_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				frame.dispose();
				System.exit(0);
			}
		});


		frame.pack();
		frame.setVisible(true);
	}







}
