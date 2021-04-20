package model.trees.binary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BinarySearchTreeDAO {

    private static final String PATH = "res/BinaryTrees/treeXXS.paed";

    public static List<BinaryNode<Long>> parseNodes() throws IOException {
        List<BinaryNode<Long>> treasureList = new ArrayList<>();

        File file = new File(PATH);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        int counter = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < counter; i++) {
            String[] info = bufferedReader.readLine().split(",");
            treasureList.add(new AVLNode<>(Long.parseLong(info[1]), info[0]));
        }

        bufferedReader.close();
        return treasureList;
    }
}

