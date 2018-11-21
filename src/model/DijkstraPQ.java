package model;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Stream;
import java.util.stream.*;
import org.javatuples.Triplet; 

public class DijkstraPQ {
	/**
	 * This should be noted that I understand that I 
	 * have numerous examples of bad programming 
	 * practices here; I am attempting to get as 
	 * much done and tested as fast as possible.
	 * 
	 * This unfortunately means I'm breaking some
	 * protocols.
	 * 
	 * Implementation based on code provided from:
	 * Tutorial Horizon, Algorithms
	 * algorithms.tutorialhorizon.com
	 * https://algorithms.tutorialhorizon.com/dijkstras-shortest-path-algorithm-spt-adjacency-list-and-priority-queue-java-implementation/
	 * 
	 * */
	
    static class Edge {
    	/**
    	 * Important class for the route finding algorithm
    	 * 
    	 * 
    	 * */
        int source;
        int destination;
        int weight;
        
        

        public Edge(int source, int destination, int weight) {
            this.setSource(source);
            this.setDestination(destination);
            this.setWeight(weight);
        }



		public int getSource() {
			return source;
		}

		public void setSource(int source) {
			this.source = source;
		}



		public int getDestination() {
			return destination;
		}



		public void setDestination(int destination) {
			this.destination = destination;
		}



		public int getWeight() {
			return weight;
		}



		public void setWeight(int weight) {
			this.weight = weight;
		}



		@Override
		public String toString() {
			return "Edge [source=" + String.valueOf(this.getSource()) + ", destination=" + String.valueOf(this.getDestination()) + ", weight=" + String.valueOf(this.getWeight()) + "]";
		}
        
    }
    static class OrderLocation{
    	/**
    	 * portion related to the Edge class from
    	 * The Route Finding method
    	 * Part of the returned ordered list
    	 * for delivery orders
    	 * 
    	 * */
    	Edge location;
    	
    	
    	public OrderLocation(Edge loc) {
    		this.setOrderLocation(loc);
    	}
    	public void setOrderLocation(Edge oloc) {
    		this.location = oloc;
    	}
    	public Edge getOrderLocation() {
    		return this.location;
    		
    	}
    	
    }
    static class OrderNumber{
    	/**
    	 * Order Number portion
    	 * Part of the returned ordered list
    	 * for delivery orders
    	 * 
    	 * */
    	String orderNumber;
    	//Set<String, orderLocation>[] orderList;
    	
    	public OrderNumber(String oNumber) {
    		this.setOrderNumber(oNumber);
    		//this.orderLocation= oLoc;
    	}
    	public void setOrderNumber(String oNum) {
    		this.orderNumber = oNum;
    	}
    	public String getOrderNumber() {
    		return this.orderNumber;
    		
    	}
    	@Override
    	public String toString() {
    		return this.getOrderNumber();
    	}
    	
    }
    //<OrderNumber,OrderLocation>
    static class Order {
    	OrderNumber oNum;
    	OrderLocation oLoc;
    	int distance; //position in delivery
    	
    	//debugging, do not use
    	/**
    	public String toString() {
    		String output="";
    		output= "Order: "+String.valueOf(this.getoNum().toString())+" Location: "+String.valueOf(this.getoLoc().getOrderLocation().toString())+" ";
    		
    		return output;
    	}*/
    	
    	public Order(OrderNumber num,OrderLocation loc) {
    		this.setoNum(num);
    		this.setoLoc(loc);
    		
    	}

		public OrderNumber getoNum() {
			return oNum;
		}

		public void setoNum(OrderNumber oNum) {
			this.oNum = oNum;
		}

		public OrderLocation getoLoc() {
			return oLoc;
		}

		public void setoLoc(OrderLocation oLoc) {
			this.oLoc = oLoc;
		}

		public int getDistance() {
			return distance;
		}

		public void setDistance(int distance) {
			this.distance = distance;
		}
		@Override
		public String toString() {
			return "Order Number: "+ this.getoNum()+" Location: "+this.getoLoc().getOrderLocation().toString()+" ";
		}
    	
    	
    }
    
    static class Graph {
    	/**
    	 * Important class for the route finding algorithm
    	 * Adjustments have been made to keep track
    	 * of Orders for returning to customer/service team
    	 * 
    	 * */
        int vertices,listSize;
        final int LOC_COUNT=1;
        LinkedList<Edge>[] orderSequence;
        //PriorityQueue <Pair <OrderNumber,OrderLocation>> orderthing;
        
        //final int MEMORY_SIZE = 2; // extending length of Adjacency List
        //int listSize; //= vertices*MEMORY_SIZE;
        //vertices =*MEMORY_SIZE;
        //LinkedList<Edge>[] adjacencylist;
        LinkedList<Edge>[] adjacencylist;
        Vector<Order>orders; 
        //LinkedList<Order>[] orders;
        Graph(int vertices) {
            this.vertices = vertices;
            this.listSize = vertices;//*MEMORY_SIZE;
            adjacencylist = new LinkedList[vertices];
            orderSequence = new LinkedList[vertices];
            orders = new Vector(); 
            //vertices, new Comparator<Pair<Integer, Integer>
            
            //this.order = new PriorityQueue<>();
            //initialize adjacency lists for all the vertices
            for (int i = 0; i <vertices ; i++) {
                adjacencylist[i] = new LinkedList<>();
            }
        }
        public void addOrder(String orderNumber,int source, int destination, int weight) {
        //addEdge(source,destination,weight);
        Edge edge = new Edge(source,destination,weight);
        OrderNumber oNum = new OrderNumber(orderNumber);
        OrderLocation oLoc = new OrderLocation(edge);
        Order order = new Order (oNum,oLoc);
        //System.out.println(oNum.toString());
        //System.out.println(oLoc.getOrderLocation().toString());
        orders.add(order);
        //System.out.println("Order Added: "+ orders.lastElement().toString());
        //addEdge(source,destination,weight);
        addEdge(edge);
        }

        //public void addEdge(int source, int destination, int weight) {
        public void addEdge(Edge edge) {
            //Edge edge = new Edge(source, destination, weight);
            adjacencylist[edge.getSource()].addFirst(edge);
            
            //System.out.println("Size of Adjacency List: "+adjacencylist.length);
            edge = new Edge(edge.getDestination(), edge.getSource(), edge.getWeight());
            adjacencylist[edge.getDestination()].addFirst(edge); //for undirected graph
        }
        

        public void find_Route(int sourceVertex){

            boolean[] SPT = new boolean[vertices];
            //distance used to store the distance of vertex from a source
            int [] distance = new int[vertices];

            //Initialize all the distance to infinity
            for (int i = 0; i <vertices ; i++) {
            	//distance[i] = this.listSize;
                distance[i] = Integer.MAX_VALUE;
                
            }
            /**
             * Attempting to use triplet for order indexing during Route Find
             * */
            //Initialize priority queue
            //override the comparator to do the sorting based keys
            PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(vertices, new Comparator<Pair<Integer, Integer>>() {
            //PriorityQueue<Triplet<Integer, Integer>> pq = new PriorityQueue<>(vertices, new Comparator<Pair<Integer, Integer>>() {   
            @Override
                public int compare(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
                    //sort using distance values
                    int key1 = p1.getKey();
                    int key2 = p2.getKey();
                    return key1-key2;
                }
            });
            //create the pair for for the first index, 0 distance 0 index
            distance[0] = 0;
            Pair<Integer, Integer> p0 = new Pair<>(distance[0],0);
            //add it to pq
            pq.offer(p0);

            //while priority queue is not empty
            // Graph's Order priority queue is filled with members to be sorted
            
            while(!pq.isEmpty()){
                //extract the min
                Pair<Integer, Integer> extractedPair = pq.poll();
                
                 
                //this.order.add(extractedPair);
                //extracted vertex
                int extractedVertex = extractedPair.getValue();
                if(SPT[extractedVertex]==false) {
                    SPT[extractedVertex] = true;

                    //iterate through all the adjacent vertices and update the keys
                    LinkedList<Edge> list = adjacencylist[extractedVertex];
                    for (int i = 0; i < list.size(); i++) {
                        Edge edge = list.get(i);
                        int destination = edge.destination;
                        //only if edge destination is not present in mst
                        if (SPT[destination] == false) {
                            ///check if distance needs an update or not
                            //means check total weight from source to vertex_V is less than
                            //the current distance value, if yes then update the distance
                            int newKey =  distance[extractedVertex] + edge.weight ;
                            int currentKey = distance[destination];
                            if(currentKey>newKey){
                                Pair<Integer, Integer> p = new Pair<>(newKey, destination);
                                pq.offer(p);
                                distance[destination] = newKey;
                            }
                        }
                    }
                }
            }
            //print Shortest Path Tree
            
            printDijkstra(distance, sourceVertex);
            for(Order o: orders) {
            System.out.println(" Order: "+o );
            }
           /** for (Order o: orders) {
            System.out.println("Orders: "+ o.getoNum().toString());
            }*/
            /**Attempting to work with Streams, so far bad idea
            				List<Order> outGoing=
            					orders
            					.build()
            					.collect(Collectors.toList());
            				outGoing.a
            				//outGoing.forEach(order->System.out.println(order.toString()));
            				for (Order o : orders.build().collect(Collectors.)) {
            					System.out.println(" Order Number: "
            				+o.getoNum()
            				+" "
            				+o.getoLoc());
            				}
            */
            					
            
        }

        public void printDijkstra(int[] distance, int sourceVertex){
            System.out.println("Dijkstra Algorithm: (Adjacency List + Priority Queue)");
            //create "if" to set if source = 0 then set print to home.
            for (int i = 0; i <vertices ; i++) {
            	//For original Dijkstra output:
                //System.out.println("Source Vertex: " + sourceVertex + " to vertex " +   + (i+this.LOC_COUNT) +
                //        " distance: " + distance[i]);
                System.out.println("Distance from center to location: " +   + (i+this.LOC_COUNT) +
                        " distance: " + distance[i]);
            }
        }

        public static void main(String[] args) {
            int vertices = 6;
            Graph graph = new Graph(vertices);
        /**    graph.addEdge(0, 1, 4);
            graph.addEdge(0, 2, 3);
            graph.addEdge(1, 2, 1);
            graph.addEdge(1, 3, 2);
            graph.addEdge(2, 3, 4);
            graph.addEdge(3, 4, 2);
            graph.addEdge(4, 5, 6);*/
        }
    }
}





