package model.tables;

import java.util.LinkedList;

public class Histogram {

    //Prints an * for every pirate on a certain age
    public void printHistogram(TableAge tableAge) {
        LinkedList<Pirate>[] crew = tableAge.getCrew();
        int totalPirate = getTotalPirates(tableAge);
        int counter = 0;

        System.out.println("Number of pirates per age: ");
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < crew.length; i++) {

            if (crew[i].size() > 0) {
                System.out.print(i + " (" + crew[i].size() + ") " + "| ");
                for (int j = 0; j < crew[i].size(); j++) {
                    counter++;
                    if ((totalPirate / 1000) < counter) {
                        System.out.print("*");
                        counter = 0;
                    }
                }
                System.out.println();
            }
        }
        System.out.println("--------------------------------------------------\n");
    }

    int getTotalPirates(TableAge tableAge) {
        LinkedList<Pirate>[] crew = tableAge.getCrew();

        int count = 0;
        for (LinkedList<Pirate> pirates : crew) {
            count += pirates.size();
        }

        return count;
    }
}
