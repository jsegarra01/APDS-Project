package model.tables;

import model.trees.binary.AVLNode;
import model.trees.binary.BinaryNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {
    private static final String PATH = "res/Tables/tablesXS.paed";

    public static List<Pirate> parseNodes() throws IOException {
        List<Pirate> crewList = new ArrayList<>();

        File file = new File(PATH);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        int counter = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < counter; i++) {
            String[] info = bufferedReader.readLine().split(",");
            crewList.add(new Pirate(info[0], Integer.parseInt(info[1]), info[2]));
        }

        bufferedReader.close();
        return crewList;
    }
}
