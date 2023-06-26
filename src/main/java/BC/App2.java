package BC;

import com.google.gson.Gson;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

public class App2 {
    static boolean stop = true;
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                try {
                    URL url = new URL("https://fapi.binance.com/fapi/v1/ticker/price?symbol=BTCUSDT");
                    URLConnection connectionBC = url.openConnection();
                    //System.out.println("Connected to Binance BTCUSDT");
                    // Read de end point contend
                    BufferedReader readerBC = new BufferedReader(
                            new InputStreamReader(connectionBC.getInputStream())
                    );
                    Tracker data = new Gson().fromJson(readerBC, Tracker.class);
                    String symbol = data.getSymbol();
                    Double price = Double.valueOf(data.getPrice());
                    System.out.println(symbol + "= " +price);
                    if (price < 30645){
                        stop = false;
                        Toolkit.getDefaultToolkit().beep();
                        System.out.println("Is time to buy");
                    }
                    readerBC.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        if (stop){

            timer.scheduleAtFixedRate(task,0,1500);
        }

    }
}
