package BC;

import com.google.gson.Gson;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class App6 {
    static Scanner scan = new Scanner(System.in);
    static double actualPrice;
    static double startPrice;
    static String symbol;
    static double factorRisk;

    public static void main(String[] args) {
        screen();
        startPrice = Double.valueOf(priceBC().getPrice());
        System.out.println("Starting Price: " + priceBC().getSymbol() + "= " + startPrice);
        windowInfo();
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
        File alert = new File("src/main/java/resource/btc_alarm.wav");
        TimerTask task = new TimerTask() {
            String earnFormat = "";

            @Override
            public void run() {
                DecimalFormat formatDouble = new DecimalFormat("#.00");
                double earn = 0.0;
                double gap = Double.valueOf(priceBC().getPrice()) - startPrice;
                String gapSTR = formatDouble.format(gap);
                System.out.println("Actual Price BTC = " + priceBC().getPrice());
                System.out.println("Mark: " + startPrice + "  | Gap: " + gapSTR);
                System.out.println();

                if (Double.valueOf(priceBC().getPrice()) <= (startPrice - factorRisk)) {
                    System.out.println("buy buy buy...");

                    earn = Math.abs(Double.valueOf(priceBC().getPrice()) - startPrice);
                    earnFormat = formatDouble.format(earn);

                    Thread thread = new Thread(() -> {
//                        JOptionPane.showMessageDialog(null, "Down buy buy buy...  $" + earnFormat, "BTC = " + priceBC().getPrice() + "  " + "START PRICE = " + startPrice, JOptionPane.WARNING_MESSAGE);
                        screenAlert();
                    });
                    thread.start();
                    btcAlert(alert);
                } else if (Double.valueOf(priceBC().getPrice()) >= (startPrice + factorRisk)) {
                    System.out.println("Up sell sell sell...");

                    earn = Math.abs(Double.valueOf(priceBC().getPrice()) - startPrice);
                    earnFormat = formatDouble.format(earn);

                    Thread thread = new Thread(() -> {
//                        JOptionPane.showMessageDialog(null, "UP sell sell...  $" + earnFormat, "BTC = " + priceBC().getPrice() + "  " + "START PRICE = " + startPrice, JOptionPane.WARNING_MESSAGE);
                        screenAlert();
                    });
                    thread.start();
                    btcAlert(alert);
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1);
    }

    public static void windowInfo() {
        JFrame frame = new JFrame("BTCUSDT    ||  MARK         ||  GAP ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(355, 60);

        JLabel label = new JLabel("BTCUSDT REAL TIME");
        Font font = label.getFont();
        float fontSize = font.getSize() + 3;
        label.setFont(font.deriveFont(fontSize));
        frame.getContentPane().add(label);
        frame.setAlwaysOnTop(true);

        // Crear un hilo para actualizar la información en tiempo real
        Thread thread = new Thread(() -> {
            while (true) {
                // Actualizar la información
                String information = btcRealTimeInformation();

                // Actualizar el texto del JLabel
                label.setText(information);


                try {
                    Thread.sleep(0); // Esperar 0 segundo antes de la siguiente actualización
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        // Iniciar el hilo
        thread.start();
        // Mostrar la ventana
        frame.setVisible(true);

    }

    private static String btcRealTimeInformation() {
        // Simulación de obtención de información en tiempo real
        // Puedes implementar aquí la lógica para obtener la información actualizada
        DecimalFormat formatDouble = new DecimalFormat("#.00");
        double gap = Double.valueOf(priceBC().getPrice()) - startPrice;
        String gapSTR = formatDouble.format(gap);
        return "      " + priceBC().getPrice() + "    " + startPrice + "     " + gapSTR + "       Risk:     " + factorRisk;
    }

    public static double screen() {
        System.out.println("---------------------------------");
        System.out.println("Welcome To Binance BitCoin Tracer");
        System.out.println("Determine your Risk Factor");
        String greenLemon = "(1000)";
        String green = " (550) ";
        String orange = " (350) ";
        String red = " (190) ";
        System.out.println(greenLemon + green + orange + red);
        System.out.println("---------------------------------");
        factorRisk = scan.nextDouble();
        return factorRisk;
    }
    public static void btcAlert(File sound){
        for (int i = 0; i < 10; i++) {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(sound));
                clip.start();
                Thread.sleep(clip.getMicrosecondLength()/1000);

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static void screenAlert(){
        String earnFormat = "";
        DecimalFormat formatDouble = new DecimalFormat("#0.00");

        double gap = Double.valueOf(priceBC().getPrice()) - startPrice;
        String gapSTR = formatDouble.format(gap);

        JOptionPane optionPane = new JOptionPane();
        optionPane.setMessage("BTC CHANGE GAP:  $ " + gapSTR);
        optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
        optionPane.setOptionType(JOptionPane.DEFAULT_OPTION);

        JDialog dialog = optionPane.createDialog(null, "BTC = " + priceBC().getPrice() + "  " + "START PRICE = " + startPrice);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }
}
