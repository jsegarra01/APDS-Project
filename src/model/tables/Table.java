package model.tables;

import java.util.LinkedList;

public class Table{
    private static int numPirates;
    private static LinkedList<Pirate>[] crew;

    public Table(int numPirates) {
        Table.numPirates = numPirates;

        //Set the LinkedList array as our table
        crew = new LinkedList[numPirates];

        for(int i = 0; i < numPirates; i++){
            //Initialize each of the arrays into a linked list for collision avoidance
            crew[i] = new LinkedList();
        }
    }

    public void addPirate(Pirate pirate) {
        int index = getIndex(pirate.getName());

        // Add the pirate to the linked list in the position of the array specified by the key
        crew[index].add(pirate);
    }

    public void removePirate(Pirate pirate){
        int index = getIndex(pirate.getName());

        //Remove the pirate to the linked list in the position of the array specified by the key
        crew[index].remove(getPirate(pirate.getName()));
    }

    public Pirate getPirate(String name){
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
        return null;
    }

  // Return the value limited to our specific range
    private int getIndex(String name) {
        return getValueString(name) % numPirates;
    }

    //Gets the addition of all the characters of the string (Hashing)
    private int getValueString(String name) {
        int prod = 1;

        for (int i = 0; i < name.length(); i++) {
            prod += name.charAt(i);
        }
        return prod;
    }

    // Return our table
    public LinkedList<Pirate>[] getCrew(){
        return crew;
    }

}
