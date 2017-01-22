package dns.client;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mrchebik on 07.01.17.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        ReaderProperty.readProperty("server.properties");

        Thread server = null;
        if (args[0].equals("none")) {
            server = new Server(ReaderProperty.getServer0(), new ArrayList<>(Arrays.asList(ReaderProperty.getServerROOT())), true);
        } else if (args[0].equals("root")) {
            server = new Server(ReaderProperty.getServerROOT(), ReaderProperty.getServerROOTChildren(), false);
        } else if (args[0].equals("com")) {
            server = new Server(ReaderProperty.getServerCOM(), ReaderProperty.getServerCOMChildren(), false);
        } else if (args[0].equals("test.com")) {
            server = new Server(ReaderProperty.getServerTEST_COM(), ReaderProperty.getServerTEST_COMChildren(), false);
        } else {
            System.out.println("error, missing type of server (none, root, com)");
            System.exit(0);
        }
        server.join();
        server.start();
    }
}
