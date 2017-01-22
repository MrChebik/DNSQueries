package dns.client;

import dns.client.model.ServerConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by mrchebik on 07.01.17.
 */
public class ReaderProperty {
    private static ServerConfig server0;
    private static ServerConfig serverROOT;
    private static ServerConfig serverCOM;
    private static ServerConfig serverTEST_COM;

    private static List<ServerConfig> serverROOTChildren = new ArrayList<>();
    private static List<ServerConfig> serverCOMChildren = new ArrayList<>();
    private static List<ServerConfig> serverTEST_COMChildren = new ArrayList<>();

    static void readProperty(String path) throws NoSuchFieldException, IllegalAccessException {
        Properties properties = new Properties();

        try {
            properties.load(ReaderProperty.class.getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        server0 = new ServerConfig(properties.getProperty("server.ip0"), properties.getProperty("server.domain0"), Integer.parseInt(properties.getProperty("server.port0")));
        serverROOT = new ServerConfig(properties.getProperty("server.ip1"), properties.getProperty("server.domain1"), Integer.parseInt(properties.getProperty("server.port1")));
        serverCOM = new ServerConfig(properties.getProperty("server.ip2"), properties.getProperty("server.domain2"), Integer.parseInt(properties.getProperty("server.port2")));
        serverTEST_COM = new ServerConfig(properties.getProperty("server.ip3"), properties.getProperty("server.domain3"), Integer.parseInt(properties.getProperty("server.port3")));

        try {
            properties.load(ReaderProperty.class.getClassLoader().getResourceAsStream("root.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addDomains(serverROOTChildren, properties);

        try {
            properties.load(ReaderProperty.class.getClassLoader().getResourceAsStream("com.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addDomains(serverCOMChildren, properties);

        try {
            properties.load(ReaderProperty.class.getClassLoader().getResourceAsStream("test.com.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addDomains(serverTEST_COMChildren, properties);
    }

    private static void addDomains(List<ServerConfig> serverConfigs, Properties properties) {
        serverConfigs.add(new ServerConfig(properties.getProperty("server.ip0"), properties.getProperty("server.domain0"), Integer.parseInt(properties.getProperty("server.port0"))));
        serverConfigs.add(new ServerConfig(properties.getProperty("server.ip1"), properties.getProperty("server.domain1"), Integer.parseInt(properties.getProperty("server.port1"))));
        serverConfigs.add(new ServerConfig(properties.getProperty("server.ip2"), properties.getProperty("server.domain2"), Integer.parseInt(properties.getProperty("server.port2"))));
    }

    public static ServerConfig getServer0() {
        return server0;
    }

    public static ServerConfig getServerROOT() {
        return serverROOT;
    }

    public static ServerConfig getServerCOM() {
        return serverCOM;
    }

    public static List<ServerConfig> getServerROOTChildren() {
        return serverROOTChildren;
    }

    public static List<ServerConfig> getServerCOMChildren() {
        return serverCOMChildren;
    }

    public static ServerConfig getServerTEST_COM() {
        return serverTEST_COM;
    }

    public static List<ServerConfig> getServerTEST_COMChildren() {
        return serverTEST_COMChildren;
    }
}
