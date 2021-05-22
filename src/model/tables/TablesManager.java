package model.tables;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class TablesManager {

    private final Table table;
    private final TableAge tableAge;
    private final Histogram histogram;

    //Set the length of the array that forms our table
    private static final int N = 5;
    private static final int NAge = 60;


    public TablesManager () throws IOException {
        table = new Table(N);
        tableAge = new TableAge(NAge);
        initTables();

        histogram = new Histogram();
        // printTable(table);
    }

    private void initTables() throws IOException {
        List<Pirate> crewList = TableDAO.parseNodes();

        for (Pirate pirate : crewList) {
            table.addPirate(pirate);
            tableAge.addPirate(pirate);
        }
    }

    public void addPirate(String name, int age, String role) {
        table.addPirate(new Pirate(name, age, role));
        tableAge.addPirate(new Pirate(name, age, role));

        System.out.println();
        System.out.println("The pirate was correctly added to the crew.\n");
    }

    public void removePirate(String name) {
        Pirate pirate = table.getPirate(name);

        System.out.println();
        if (null != pirate) {
            table.removePirate(pirate);
            tableAge.removePirate(pirate);

            System.out.println("The pirate was correctly removed from the crew. F\n");
        }
        else {
            System.out.println("This pirate is not part of our crew!\n");
        }
    }

    public void showPirate(String name) {
        Pirate pirate = table.getPirate(name);

        System.out.println();
        if (pirate != null) {
            System.out.println("||---------------------------");
            System.out.println("|| Name: " + pirate.getName());
            System.out.println("|| Age: " + pirate.getAge());
            System.out.println("|| Role: " + pirate.getRole());
            System.out.println("||---------------------------\n");
        }
        else {
            System.out.println("This pirate is not part of our crew!\n");
        }
    }

    public void printHistogram() {
        histogram.printHistogram(tableAge);
    }

    public void printTable(Table table) {
        LinkedList<Pirate>[] crew = table.getCrew();
        for(int i = 0; i < crew.length; i++){
            System.out.println("------------------------------------------------");
            System.out.println("Index: " + i);
            System.out.println("------------------------------------------------");
            for(int j = 0; j < crew[i].size(); j++){
                showPirate(crew[i].get(j).getName());
            }
        }
    }
}
