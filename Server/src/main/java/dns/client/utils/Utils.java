package dns.client.utils;

import dns.client.model.ServerConfig;

import java.util.List;

/**
 * Created by mrchebik on 07.01.17.
 */
public class Utils {
    public static String processCommand(String command, List<ServerConfig> data) {
        if (data.get(0).getName().equals("root")) {
            return "request:" + data.get(0).getIp() + ":" + data.get(0).getPort() + ":" + command;
        }
        for(ServerConfig serverConfig : data) {
            String[] serverDomain = serverConfig.getName().split("\\.");
            String[] neededDomain = command.split("\\.");

            int point = serverDomain.length;
            for (int i = serverDomain.length-1, j = neededDomain.length-1; i >= 0; i--, j-- ) {
                if (serverDomain[i].equals(neededDomain[j])) {
                    --point;

                    if (point == 0) {
                        if (command.equals(serverConfig.getName())) {
                            return serverConfig.getIp();
                        } else {
                            return "request:" + serverConfig.getIp() + ":" + serverConfig.getPort() + ":" + command;
                        }
                    }
                }
            }
        }

        return "Don't have any solutions.";
    }
}
