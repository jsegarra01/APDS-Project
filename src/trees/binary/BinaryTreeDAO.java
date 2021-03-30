package trees.binary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BinaryTreeDAO {

    private static final String PATH = "res/BinaryTrees/treeXXS.paed";

    public static List<BinaryNode> parseNodes() throws IOException {
        List<BinaryNode> treasureList = new ArrayList<>();

        File file = new File(PATH);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        int counter = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < counter; i++) {
            String[] info = bufferedReader.readLine().split(",");
            treasureList.add(new BinaryNode(info[0], Long.parseLong(info[1])));
        }

        bufferedReader.close();
        return treasureList;
    }
}
