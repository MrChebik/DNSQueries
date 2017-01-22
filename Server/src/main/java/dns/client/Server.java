package dns.client;

import dns.client.model.ServerConfig;
import dns.client.tcp.TCPResponse;
import dns.client.udp.UDPResponse;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by mrchebik on 07.01.17.
 */
public class Server extends Thread {
    private final ServerConfig serverConfig;
    private final List<ServerConfig> serverConfigs;
    private final boolean recursive;

    public Server(ServerConfig serverConfig, List<ServerConfig> serverConfigs, boolean recursive) {
        this.serverConfig = serverConfig;
        this.serverConfigs = serverConfigs;
        this.recursive = recursive;
    }

    public void run() {
        SocketAddress port = new InetSocketAddress(serverConfig.getPort());
        try {
            Selector selector = Selector.open();

            ServerSocketChannel tcpServer = ServerSocketChannel.open();
            tcpServer.socket().bind(port);
            tcpServer.configureBlocking(false);
            tcpServer.register(selector, SelectionKey.OP_ACCEPT);

            DatagramChannel udpServer = DatagramChannel.open();
            udpServer.socket().bind(port);
            udpServer.configureBlocking(false);
            udpServer.register(selector, SelectionKey.OP_READ);

            while(true) {
                try {
                    selector.select();

                    Set<SelectionKey> keys = selector.selectedKeys();

                    for (Iterator<SelectionKey> i = keys.iterator(); i.hasNext();) {
                        SelectionKey key = i.next();
                        i.remove();

                        Channel c = key.channel();

                        if (key.isAcceptable() && c == tcpServer) {
                            new TCPResponse(tcpServer.accept().socket(), serverConfigs, recursive).start();
                        } else if (key.isReadable() && c == udpServer) {
                            new UDPResponse(udpServer, key.channel(), serverConfigs, recursive).start();
                        }
                    }
                    selector.selectedKeys().clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
