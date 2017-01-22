package dns.client;

/**
 * Created by mrchebik on 07.01.17.
 */
public class Arguments {
    private static String server;
    private static String query;

    Arguments(String server, String query) {
        Arguments.server = server;
        Arguments.query = query;
    }

    static void checkArguments(String[] args) {
        for (int i = 0; i <= args.length - 2; i += 2) {
            for (int j = 0; j < args.length; j++) {
                if (args[i].contains(Main.class.getDeclaredFields()[j].getName())) {
                    if (!args[i].substring(0, 2).equals("--")) {
                        System.err.println("The argument is not correct");
                        System.exit(0);
                    } else {
                        try {
                            Main.class.getDeclaredField(Main.class.getDeclaredFields()[j].getName()).set(Main.class, args[i + 1]);
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
            }
        }
    }

    static String getQuery() {
        return query;
    }

    static String getServer() {
        return server;
    }
}
