package BC;

import com.google.gson.Gson;
import netscape.javascript.JSObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class App {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT");
            URLConnection connectionBC = url.openConnection();
            System.out.println("Connected to Binance BTCUSDT");
            // Read de end point contend
            BufferedReader readerBC = new BufferedReader(
                    new InputStreamReader(connectionBC.getInputStream())
            );
            String priceBTCUSD;
            while ((priceBTCUSD = readerBC.readLine()) != null) {
                System.out.println(priceBTCUSD);
            }

            readerBC.close();
            System.out.println(priceBTCUSD);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String json = """
                {
                "symbol":"BTCUSDT",
                "price": "31054.380000"
                }
                """;

        Tracker data = new Gson().fromJson(json, Tracker.class);
        String symbol = data.getSymbol();
        Double price = Double.valueOf(data.getPrice());
        if (price < 50000){
            System.out.println("Is time to buy");
        }


    }


}
