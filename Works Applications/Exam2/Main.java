/**
 * @author Wei Min
 * @time 2016-03-17
 * @name Travel Information Center
 */
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.ArrayDeque;
import java.util.Vector;

public class Main {

	/**
	 * @function get one adjacent city hasn't been visited
	 * @param City
	 * @return City or null
	 */
	public static City getAdjCity(City c) {
		ArrayList<City> alc = c.getAdj();
		if (alc != null) {
			for (int k = 0; k < alc.size(); k++) {
				if (alc.get(k).isVisited == false) {
					alc.get(k).isVisited = true;
					return alc.get(k);
				}
			}
		}
		
		return null;
	}

	/**
	 * @function search the closest festive city, and get the distance
	 * @param Graph graph, City start:the start city for BFS
	 * @return distance between start city and closet festive city
	 * 		   if -1, doesn't find out festive city
	 */
	public int bfs(Graph graph, City start) {
		int dist[];//record distance for every city
		ArrayDeque<City> queue = new ArrayDeque<City>();
		City city;

		int num = graph.city_list.length;
		dist = new int[num];
		dist[start.label] = 0;
		queue.add(start);
		start.isVisited = true;
		
		//BFS
		while (!queue.isEmpty()) {
			City head = queue.remove();
			if (head.isFestive == true) {//find out the closest city
				return dist[head.label];
			} else {
				while ((city = getAdjCity(head)) != null) {
					queue.add(city);
					dist[city.label] = dist[head.label] + 1;
				}
			}
		}

		return -1;
	}

	public static void main(String[] args) {
		Vector<Integer> dis_vec = new Vector<Integer>();//queries result
		Scanner scan = new Scanner(new BufferedInputStream(System.in));
		int n = scan.nextInt();// number of cities
		int m = scan.nextInt();// number of queries

		// n cities
		Graph graph = new Graph(n);
		for (int i = 0; i < n; i++) {
			graph.city_list[i] = new City(i);
		}
		graph.city_list[0].isFestive = true;

		// highways,n-1 lines
		for (int i = 1; i < n; i++) {
			int c1 = scan.nextInt() - 1;
			int c2 = scan.nextInt() - 1;
			graph.addEdge(c1, c2);
		}
		// queries,m lines
		for (int j = 0; j < m; j++) {
			int q = scan.nextInt();
			int qc = scan.nextInt();
			if (q == 2) {
				// BFS,get the shortest distance to festive city
				for (int k = 0; k < n; k++) {
					graph.city_list[k].isVisited = false;
				}
				Main main = new Main();
				dis_vec.add(main.bfs(graph, graph.city_list[qc - 1]));
			} else if (q == 1) {
				//new festive city
				graph.city_list[qc - 1].isFestive = true;
			}
		}

		for (int i = 0; i < dis_vec.size(); i++) {
			System.out.println(dis_vec.get(i));
		}
		scan.close();
	}

}

/* city class,the node */
class City {
	int label;// city number
	public boolean isFestive;// if city has festive events
	public boolean isVisited;// if city has been visited
	public ArrayList<City> adjacent = null;// adjacent cities

	public City(int _lable) {
		label = _lable;
		isFestive = false;
		isVisited = false;
	}

	// add adjacent city
	public void addAdj(City city) {
		if (adjacent == null)
			adjacent = new ArrayList<City>();
		adjacent.add(city);
	}
	
	//get adjacent cities
	public ArrayList<City> getAdj() {
		return adjacent;
	}
}

/* city graph,connections between cities */
class Graph {
	City[] city_list;//city list

	Graph(int num) {
		city_list = new City[num];
	}

	public void addEdge(int from, int to) {
		city_list[from].addAdj(city_list[to]);
		city_list[to].addAdj(city_list[from]);
	}
}