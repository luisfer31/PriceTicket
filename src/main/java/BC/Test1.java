package BC;

import javax.swing.*;

public class Test1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana Emergente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);

        JLabel label = new JLabel("Información en tiempo real");
        frame.getContentPane().add(label);
        frame.setAlwaysOnTop(true);

        // Crear un hilo para actualizar la información en tiempo real
        Thread thread = new Thread(() -> {
            while (true) {
                // Actualizar la información
                String informacion = obtenerInformacionEnTiempoReal();

                // Actualizar el texto del JLabel
                label.setText(informacion);

                try {
                    Thread.sleep(1000); // Esperar 1 segundo antes de la siguiente actualización
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

    private static String obtenerInformacionEnTiempoReal() {
        // Simulación de obtención de información en tiempo real
        // Puedes implementar aquí la lógica para obtener la información actualizada
        return "Información actualizada: " + System.currentTimeMillis();
    }
}
