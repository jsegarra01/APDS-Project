package model.tables;

import java.util.LinkedList;

public class TableAge {
    private static int numPirates;
    private static LinkedList<Pirate>[] crew;

    public TableAge(int numPirates){
        //Set the total slots from the parameters
        TableAge.numPirates = numPirates;
        //Set the LinkedList array as our table
        crew = new LinkedList[numPirates];
        for(int i = 0; i < numPirates; i++){
            crew[i] = new LinkedList();
            //Initialize each of the arrays into a linked list for collision avoidance
        }
    }

    public void addPirate(Pirate pirate){
        //Get the index from the key of the table
        int index = getIndex(pirate.getAge());
        //Add the pirate to the linked list in the position of the array specified by the key
        crew[index].add(pirate);
    }

    public void removePirate(Pirate pirate){
        //Get the index from the key of the table
        int index = getIndex(pirate.getAge());
        //Remove the pirate to the linked list in the position of the array specified by the key
        crew[index].remove(getPirate(pirate));
    }

    private Pirate getPirate(Pirate pirate){
        int index = getIndex(pirate.getAge());
        if (crew[index] != null) {
            for (int i = 0; i < crew[index].size(); i++) {
                if (crew[index].get(i).getName().equals(pirate.getName())) {
                    return crew[index].get(i);
                }
            }
        }
        System.out.println("The pirate you are looking for is not on this ship!");
        return null;
    }


    private int getIndex(int age){
        return age % numPirates;
    }

    public LinkedList<Pirate>[] getCrew(){
        return crew;
    }
}
