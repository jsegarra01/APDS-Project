import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new DataReader().parseData("graphXXS.paed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
