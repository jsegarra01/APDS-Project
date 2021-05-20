package model.tables;

import model.trees.binary.AVLBinarySearchTree;
import model.trees.binary.BinaryNode;
import model.trees.binary.BinarySearchTreeDAO;
import model.trees.binary.BinarySearchTreeTraversal;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class TablesManager {
    //Both instance of the tables
    private final Table table;
    private final TableAge tableAge;
    //Get the instance of a Histogram class for the last option
    private static Histogram histogram;
    //Set the length of the array that forms our table
    private static final int N = 5;
    private static final int NAge = 60;

    //Initializes our tables
    public TablesManager () throws IOException {
        table = new Table(N);
        tableAge = new TableAge(NAge);
        initTables();

        printTable(table);
    }

    private void initTables() throws IOException {
        List<Pirate> crewList = TableDAO.parseNodes();
        //Adds the information from the json
        for (Pirate pirate : crewList) {
            table.addPirate(pirate);
            tableAge.addPirate(pirate);
        }
    }

    //Adds a pirate to both tables
    public void addPirate(String name, int age, String role){
        table.addPirate(new Pirate(name, age, role));
        tableAge.addPirate(new Pirate(name, age, role));
    }

    //Removes a pirate from both tables
    public void removePirate(String name){
        table.removePirate(table.getPirate(name));
        tableAge.removePirate(table.getPirate(name));
    }

    //Gets the information from the first table
    public void printPirateInfo(String name){
        table.printPirateInfo(name);
    }

    //Used the histogram class to create a numeric representation fo a table
    public void printHistogram(){
        histogram = new Histogram(tableAge);
        histogram.printHistogram();
    }

    //Prints the tables to verify its composition
    public void printTable(Table table){
        LinkedList<Pirate>[] crew = table.getCrew();
        for(int i = 0; i < crew.length; i++){
            System.out.println("Index: " + i);
            System.out.println("------------------------------------------------");
            System.out.println("------------------------------------------------");
            for(int j = 0; j < crew[i].size(); j++){
                System.out.println("--------------------------");
                System.out.println("index " + j);
                System.out.println("Name: " + crew[i].get(j).getName());
                System.out.println("Name: " + crew[i].get(j).getAge());
                System.out.println("Name: " + crew[i].get(j).getRole());
                System.out.println("--------------------------");
            }
            System.out.println();
            System.out.println();
        }
    }

}
