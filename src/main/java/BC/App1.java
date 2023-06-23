package BC;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class App1 {
    public static void main(String[] args) {


        try {
            URL url = new URL("https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT");
            URLConnection connectionBC = url.openConnection();
            System.out.println("Connected to Binance BTCUSDT");
            // Read de end point contend
            BufferedReader readerBC = new BufferedReader(
                    new InputStreamReader(connectionBC.getInputStream())
            );
            Tracker data = new Gson().fromJson(readerBC, Tracker.class);
            String symbol = data.getSymbol();
            Double price = Double.valueOf(data.getPrice());
            System.out.println(symbol + "= " +price);
            if (price < 30700){
                System.out.println("Is time to buy");
            }
            readerBC.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
