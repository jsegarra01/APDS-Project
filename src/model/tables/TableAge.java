package model.tables;

import java.util.LinkedList;

public class TableAge {

    private static int numPirates;
    private static LinkedList<Pirate>[] crew;

    public TableAge(int numPirates) {
        TableAge.numPirates = numPirates;
        crew = new LinkedList[numPirates];

        //Initialize each of the arrays into a linked list for collision avoidance
        for (int i = 0; i < numPirates; i++) {
            crew[i] = new LinkedList<>();
        }
    }

    public void addPirate(Pirate pirate) {
        int index = getIndex(pirate.getAge());

        //Add the pirate to the linked list in the position of the array specified by the key
        crew[index].add(pirate);
    }

    public void removePirate(Pirate pirate) {
        int index = getIndex(pirate.getAge());

        //Remove the pirate to the linked list in the position of the array specified by the key
        crew[index].remove(getPirate(pirate));
    }

    private Pirate getPirate(Pirate pirate) {

        int index = getIndex(pirate.getAge());
        if (crew[index] != null) {
            for (int i = 0; i < crew[index].size(); i++) {
                if (crew[index].get(i).getName().equals(pirate.getName())) {
                    return crew[index].get(i);
                }
            }
        }

        return null;
    }

    private int getIndex(int age) {
        return age % numPirates;
    }

    public LinkedList<Pirate>[] getCrew() {
        return crew;
    }
}
