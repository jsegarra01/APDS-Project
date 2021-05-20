package model.tables;

import java.util.LinkedList;

public class Table{
    private static int numPirates;
    private static LinkedList<Pirate>[] crew;

    public Table(int numPirates){
        //Set the total slots from the parameters
        Table.numPirates = numPirates;
        //Set the LinkedList array as our table
        crew = new LinkedList[numPirates];
        for(int i = 0; i < numPirates; i++){
            //Initialize each of the arrays into a linked list for collision avoidance
            crew[i] = new LinkedList();
        }
    }

    public void addPirate(Pirate pirate){
        //Get the index from the key of the table
        int index = getIndex(pirate.getName());
        //Add the pirate to the linked list in the position of the array specified by the key
        crew[index].add(pirate);
        System.out.println();
        System.out.println("The pirate was correctly added to the crew.");
    }

    public void removePirate(Pirate pirate){
        //Get the index from the key of the table
        int index = getIndex(pirate.getName());
        //Remove the pirate to the linked list in the position of the array specified by the key
        crew[index].remove(getPirate(pirate.getName()));
        System.out.println();
        System.out.println("The pirate was correctly removed from the crew.");
    }

    public void printPirateInfo(String name){
        //Get the index from the key of the table
        Pirate pirate = getPirate(name);
        //Check that the pirate exists
        if(pirate != null){
            System.out.println("||---------------------------");
            System.out.println("|| Name: " + pirate.getName());
            System.out.println("|| Age: " + pirate.getAge());
            System.out.println("|| Role: " + pirate.getRole());
            System.out.println("||---------------------------");
        }
        else{
            System.out.println("This pirate is not part of our crew!");
        }


    }

    public Pirate getPirate(String name){
        //Return the pirate from the table based on the name
        int index = getIndex(name);
        //Check the list in that position is not null
        if (crew[index] != null) {
            //Verify if any of the positions of the linked lists contains this name
            for (int i = 0; i < crew[index].size(); i++) {
                if (crew[index].get(i).getName().equals(name)) {
                    return crew[index].get(i);
                }
            }
        }
        System.out.println("The pirate you are looking for is not on this ship!");
        return null;
    }

    private int getValueString(String name){
        //Gets the addition of all the characters of the string (Hashing)
        int prod = 1;
        for (int i = 0; i < name.length(); i++)
        {
            prod += name.charAt(i);
        }
        return prod;
    }

    private int getIndex(String name){
        return getValueString(name) % numPirates;
    } //Return the value limited to our specific range

    public LinkedList<Pirate>[] getCrew(){
        return crew;
    } //Return our table

}
