package dns.client.model;

/**
 * Created by mrchebik on 07.01.17.
 */
public class ServerConfig {
    private String ip;
    private String name;
    private int port;

    public ServerConfig(String ip, String name, int port) {
        this.ip = ip;
        this.name = name;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
