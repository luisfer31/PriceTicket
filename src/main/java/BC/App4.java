package BC;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class App4 {
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
                DecimalFormat formatDouble = new DecimalFormat("#.00");
                double earn = 0.0;
                String earnFormat = "";
                System.out.println(priceBC().getPrice());
                System.out.println("Star " + startPrice);
                if (Double.valueOf(priceBC().getPrice()) <= (startPrice - factorRisk) ) {
                    System.out.println("buy buy buy...");
                    Toolkit.getDefaultToolkit().beep();
//                    beepAlert();
                    earn = Math.abs(Double.valueOf(priceBC().getPrice()) - startPrice);
                    earnFormat = formatDouble.format(earn);
                    //String mensaje = "<html><font color='red'>Texto en color rojo</font></html>";
                    JOptionPane.showMessageDialog(null, "Down buy buy...  $" + earnFormat, "BTC = " + priceBC().getPrice() + "  " + "START PRICE = " + startPrice, JOptionPane.WARNING_MESSAGE);
                } else if (Double.valueOf(priceBC().getPrice()) >= (startPrice + factorRisk)) {
                    System.out.println("sell sell sell...");
                    Toolkit.getDefaultToolkit().beep();
//                    beepAlert();
                    earn = Math.abs(Double.valueOf(priceBC().getPrice()) - startPrice);
                    earnFormat = formatDouble.format(earn);
                    JOptionPane.showMessageDialog(null, "UP sell sell...  $" + earnFormat, "BTC = " + priceBC().getPrice() + "  " + "START PRICE = " + startPrice, JOptionPane.WARNING_MESSAGE);
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public static double screen() {
        System.out.println("---------------------------------");
        String boldText = "\u001B[1mWelcome To Binance BitCoin Tracer \u001B[0m";
        System.out.println(boldText);
        System.out.println("Determine your Risk Factor");
        String greenLemon = "\u001B[38;5;118m(1000) \u001B[0m";
        String green = "\u001B[32m (550) \u001B[0m";
        String orange = "\u001B[38;5;208m (350) \u001B[0m";
        String red =   "\u001B[31m (190) \u001B[0m";
        System.out.println( greenLemon + green + orange + red);
        System.out.println("---------------------------------");
        factorRisk = scan.nextDouble();
        return factorRisk;
    }
    public static void beepAlert() {
        for (int i = 0; i < 3; i++) {
            Toolkit.getDefaultToolkit().beep();
            // Wait for 1 second between each beep
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
