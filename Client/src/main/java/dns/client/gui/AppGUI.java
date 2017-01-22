package dns.client.gui;

import dns.client.ReaderProperty;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by mrchebik on 09.01.17.
 */
public class AppGUI extends Application implements Runnable {
    private static TextArea area;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("DNSQueries");

        BorderPane borderPane = new BorderPane();
        area = new TextArea();
        area.setEditable(false);
        borderPane.setCenter(area);

        Scene scene = new Scene(borderPane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void outPut(String query, String data, String protocol, boolean flag) {
        String output = "Query: " + query + "\nIP address: " + data + "\nProtocol: " + protocol + "\nTC: " + flag + "\n\n";

        if (ReaderProperty.isPropertyGui()) {
            area.appendText(output);
        } else {
            System.out.println(output);
        }
    }

    @Override
    public void run() {
        AppGUI.launch();
    }
}
