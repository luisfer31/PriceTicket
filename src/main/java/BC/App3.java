package BC;

import com.google.gson.Gson;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class App3 {
    static Scanner scan = new Scanner(System.in);
    static double actualPrice;
    static double startPrice;
    static String symbol;
    static double factorRisk;

    public static void main(String[] args) {
        screen();
        startPrice = Double.valueOf(priceBC().getPrice());
        System.out.println("Starting Price: " + priceBC().getSymbol() + "= " + startPrice);
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
            symbol = data.getSymbol();
            actualPrice = Double.valueOf(data.getPrice());
            readerBC.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public static void repeatInstructions() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(priceBC().getPrice());
                System.out.println("Star " + startPrice);
                if (Double.valueOf(priceBC().getPrice()) <= (startPrice - factorRisk)) {
                    System.out.println("buy buy buy...");
                    Toolkit.getDefaultToolkit().beep();
                } else if (Double.valueOf(priceBC().getPrice()) >= (startPrice + factorRisk)) {
                    System.out.println("sell sell sell...");
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public static double screen() {
        System.out.println("Welcome To Binance BitCoin Tracer");
        System.out.println("Determine your Risk Factor");
        System.out.println("(190)  (350)  (550)  (1000)");
        factorRisk  = scan.nextDouble();
        return factorRisk;
    }
}
