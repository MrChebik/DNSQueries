package dns.client.tcp;

import dns.client.model.ServerConfig;
import dns.client.udp.UDPRequest;
import dns.client.udp.UDPResponse;
import dns.client.utils.Utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by mrchebik on 07.01.17.
 */
public class TCPResponse extends Thread {
    private Socket socket = null;

    private List<ServerConfig> serverConfigs;
    private boolean recursive;

    public TCPResponse(Socket socket, List<ServerConfig> serverConfigs, boolean recursive) {
        super("TCPResponse");
        this.socket = socket;
        this.serverConfigs = serverConfigs;
        this.recursive = recursive;
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            byte[] buf = new byte[64*1024];

            String outline = Utils.processCommand(new String(buf, 0, is.read(buf)).trim(), serverConfigs);
            UDPResponse.buffer.setResponse(outline);

            assert outline != null;
            if (outline.contains("request") && recursive) {
                while (UDPResponse.buffer.getResponse().contains("request")) {
                    String[] lines = UDPResponse.buffer.getResponse().split(":");
                    UDPRequest.sendUDP(lines[1], Integer.parseInt(lines[2]), lines[3]);
                    if (UDPResponse.buffer.getResponse().equals("Don't have any solutions")) {
                        break;
                    }
                }
            }

            os.write(UDPResponse.buffer.getResponse().getBytes());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
