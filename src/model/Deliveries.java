package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.DijkstraPQ.Edge;
import model.DijkstraPQ.Graph;

public class Deliveries extends Graph{
	//Vertices here are known as Locations
	Deliveries(int locations) {
		//Locations determines how many stops there are to make
		super(locations);
		//Int represents order placement, ergo priority
		
		
		/*
		 *     static class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }*/
		//private Map<Integer,Edge> delivery;


		
		
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String [] args) {
        int vertices,counter = 0;
        int source,destination,weight;
        final int source_vertex =0;
        vertices=7;
        String parse,oNum="";
        Deliveries graph = new Deliveries(vertices);
        //final int incrementer =5;
        /**
       
        final String quit="quit!";
        Scanner input = new Scanner(System.in);
        System.out.println("How many deliveries?");
        System.out.println("Reminder: Minimum deliveries are three.");
        parse = input.nextLine();
        vertices = Integer.valueOf(parse);
        
       
        
        //Testing Add Edge and route finding algorithm
        do{
        	System.out.println("Location: "+(counter+1));
        	
        	System.out.println("Enter the Order Number: \n" );
        	parse = input.nextLine();
        	oNum = parse;
        	
        	System.out.println("Enter the source: \n" );
        	parse = input.nextLine();
        	

                source = Integer.valueOf(parse);
        		
        		System.out.println("Enter the destination: \n");
                parse = input.nextLine();
                destination = Integer.valueOf(parse);
                
        		System.out.println("Enter the weight: \n");
                parse = input.nextLine();
                weight = Integer.valueOf(parse);
                
        		//graphInput.addEdge(source, destination, weight);
                graph.addOrder(oNum,source, destination, weight);
        		//graphInput.find_Route(source_vertex);
                counter++;
        }while(counter<vertices);
        //graphInput.find_Route(source_vertex);
         * 
         */
        //graph.addOrder(oNum,source, destination, weight);
        graph.addOrder("1",0, 1, 4);
        graph.addOrder("2",0, 2, 3);
        graph.addOrder("3",0, 2, 1);
        graph.addOrder("4",0, 3, 2);
        graph.addOrder("5",0, 3, 4);
        graph.addOrder("6",0, 4, 2);
        graph.addOrder("7",0, 5, 6);

        graph.find_Route(source_vertex);
        /*
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 3, 4);
        graph.addEdge(3, 4, 2);
        graph.addEdge(4, 5, 6);
        graph.find_Route(0);
		*/
	}

}
