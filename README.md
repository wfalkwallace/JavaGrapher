William Falk-Wallace
wfalkwallace@gmail.com


This program is bundled as a runnable JAR, so the utilized JUNG libraries are included, and can be found at:

https://www.dropbox.com/s/s007csx7gwodq1s/wgf2104_3137_Programming3.jar




This program was for a Data Structures class at Columbia University. It loads up a selected map txt file chosen through a GUI, assigns (between 2 and 8) random outgoing edges and costs (between 100 and 2000), and displays that graph, along with options to load, quit, select cities and states for information, and find the closest adjacent city by GPS and by edge cost. Included are a large map text file and a smaller one of fictional  
locations. The format for a valid text file is:

<number of cities in file>
<city name>, <state name>
<x coordinate>
<y coordinate>
<city name>, <state name>
<x coordinate>
<y coordinate>
…


My program implements 6 classes, many of which depend on JUNG 2.0.1 JARs. The runtime for populating the graph as an adjacency list is O(n),
along with randomizing and assigning edges and weights. The adjacency list uses a hashtable for 
vertex lookup, and so its O(1) there, and so adding edges and vertices is constant time plus the number of edges (always <8, here) as well. Getting the adjacencies for graph printing and 
finding the shortest is O(E) so the drawing and shortest are O(V+E). Same for loading a new graph.
