package BC;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

public class App3 {

    public static void main(String[] args) {
        System.out.println(priceBC().getSymbol() + "= " + priceBC().getPrice());
        repeatInstructions();

    }

    public static Tracker priceBC() {
        Tracker data;
        try {
            URL url = new URL("https://fapi.binance.com/fapi/v1/ticker/price?symbol=BTCUSDT");
            URLConnection connectionBC = url.openConnection();
            BufferedReader readerBC = new BufferedReader(
                    new InputStreamReader(connectionBC.getInputStream())
            );
            data = new Gson().fromJson(readerBC, Tracker.class);
            String symbol = data.getSymbol();
            double price = Double.valueOf(data.getPrice());
            readerBC.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public static void repeatInstructions(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

            }
        };
        timer.scheduleAtFixedRate(task,0,1500);
    }
}
