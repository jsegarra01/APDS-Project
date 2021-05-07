package model.trees.r;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RTreeDAO {

    private static final String PATH = "res/RTrees/r-treeXXS.paed";

    public static List<RPoint> parseNodes() throws IOException {
        List<RPoint> treasurePosition = new ArrayList<>();

        File file = new File(PATH);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        int counter = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < counter; i++) {
            String[] info = bufferedReader.readLine().split(",");
            treasurePosition.add(new RPoint(info[0], new Point(Float.parseFloat(info[1]) * 100, Float.parseFloat(info[2]) * 100)));
        }

        bufferedReader.close();
        return treasurePosition;
    }

}
