import java.io.*;
import java.util.*;


/**
 * 
 */

/**
 * @author wgf2104
 *
 */
public class FileGrapher {

	File data;
	MyGraphMap graph;
	int num;

	public FileGrapher(File in){
		data = in;
		graph = new MyGraphMap();
	}

	public MyGraphMap graph() throws FileNotFoundException{
		Scanner reader = new Scanner(data);
		//Assumes assignment defined file format beginning with number of cities contained
		num = Integer.parseInt(reader.nextLine());
		graph.setSize(num);
		String name, city, country;
		double xCoord, yCoord;
		for(int i=0; i<num; i++){
			name = reader.nextLine();
			xCoord = Double.parseDouble(reader.nextLine());
			yCoord = Double.parseDouble(reader.nextLine());
			
			if(name.contains(",")){
				city = name.split(", ")[0];
				country = name.split(", ")[1];
//				System.out.println(city + "==" + country + "==" + xCoord + "==" + yCoord);
				graph.addV(new MyGraphNode(city, country, xCoord, yCoord));
			}
			else
//				System.out.println(name + "==" + name + "==" + xCoord + "==" + yCoord);
				graph.addV(new MyGraphNode(name, name, xCoord, yCoord));

		}
		reader.close();
		return graph;
	}


}
