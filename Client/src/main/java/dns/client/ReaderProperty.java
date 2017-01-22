package dns.client;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by mrchebik on 07.01.17.
 */
public class ReaderProperty {
    private static String propertyServer;
    private static String propertyQuery;

    private static int propertyPort;
    private static boolean propertyGui;

    static void readProperty(String path, String type) {
        Properties properties = new Properties();

        try {
            properties.load(ReaderProperty.class.getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (type.equals("args")) {
            propertyServer = properties.getProperty("arg.server");
            propertyQuery = properties.getProperty("arg.query");
        }
        propertyPort = Integer.parseInt(properties.getProperty("server.port"));
        propertyGui = Boolean.parseBoolean(properties.getProperty("app.gui"));
    }

    static String getPropertyServer() {
        return propertyServer;
    }

    static String getPropertyQuery() {
        return propertyQuery;
    }

    public static int getPropertyPort() {
        return propertyPort;
    }

    public static boolean isPropertyGui() {
        return propertyGui;
    }
}
