package dns.client;

import dns.client.gui.AppGUI;
import dns.client.tcp.TCPRequest;
import dns.client.udp.UDPRequest;

import java.io.IOException;

import static dns.client.Arguments.checkArguments;

/**
 * Created by mrchebik on 07.01.17.
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, NoSuchFieldException {
        if (args.length != 0) {
            if (args.length == 1 || args.length == 3 || args.length > 4) {
                System.err.println("Check your arguments!");
            } else {
                checkArguments(args);
            }
        }
        ReaderProperty.readProperty("config_client.properties", "args");
        if (Arguments.getQuery() == null) {
            new Arguments(ReaderProperty.getPropertyServer(), ReaderProperty.getPropertyQuery());
        }

        if (ReaderProperty.isPropertyGui()) {
            Thread thread = new Thread(new AppGUI());
            thread.join();
            thread.start();

            Thread.sleep(2000);
        }

        if (Arguments.getQuery().getBytes().length > 32) {
            TCPRequest.sendTCP(Arguments.getServer(), ReaderProperty.getPropertyPort(), Arguments.getQuery());
        } else {
            UDPRequest.sendUDP(Arguments.getServer(), ReaderProperty.getPropertyPort(), Arguments.getQuery());
        }
    }
}
