package dns.client.udp;

import dns.client.buffer.Buffer;
import dns.client.model.ServerConfig;
import dns.client.utils.Utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.charset.Charset;
import java.util.List;

import static dns.client.Flag.TC;

/**
 * Created by mrchebik on 07.01.17.
 */
public class UDPResponse extends Thread {
    private DatagramChannel channel = null;
    private SelectableChannel selectableChannel = null;

    private List<ServerConfig> serverConfigs;
    private boolean recursive;

    public static Buffer buffer = new Buffer();

    public UDPResponse(DatagramChannel channel, SelectableChannel selectableChannel, List<ServerConfig> serverConfigs, boolean recursive) {
        super("UDPResonse");
        this.channel = channel;
        this.selectableChannel = selectableChannel;
        this.serverConfigs = serverConfigs;
        this.recursive = recursive;
    }

    @Override
    public void run() {
        try {
            ByteBuffer buf = ByteBuffer.allocate(32);
            buf.clear();
            InetSocketAddress address = (InetSocketAddress) ((DatagramChannel) selectableChannel).receive(buf);

            String inputLine = new String(buf.array(), Charset.forName("UTF-8"));

            if (inputLine.trim().length() != 0) {
                String outputLine = Utils.processCommand(inputLine.trim(), serverConfigs);
                System.out.println(outputLine);
                buffer.setResponse(outputLine);

                assert outputLine != null;
                if (outputLine.contains("request") && recursive) {
                    while (buffer.getResponse().contains("request")) {
                        String[] lines = buffer.getResponse().split(":");
                        UDPRequest.sendUDP(lines[1], Integer.parseInt(lines[2]), lines[3]);
                        if (buffer.getResponse().equals("Don't have any solutions")) {
                            break;
                        }
                    }
                }

                buf.clear();
                try {
                    buf.put(buffer.getResponse().getBytes());
                } catch (BufferOverflowException overflow) {
                    TC.set(true);
                    buf.put(("TC: " + TC.isSet()).getBytes());
                }
                buf.flip();

                channel.send(buf, address);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
