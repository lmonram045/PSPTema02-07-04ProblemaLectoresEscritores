package principal;

public class HiloLector extends Thread {

    private Semaforo semaforo;

    /** Constructor HiloLector */
    public HiloLector(String nombre, Semaforo s) {
        this.setName(nombre);
        this.semaforo = s;
    }

    /** Método run */
    @Override
    public void run() {
        // Mensaje para comprobar el funcionamiento
        System.out.println(getName() + ": Intentando leer");
        // Llamamos al método accesoLeer del semáforo
        semaforo.accesoLeer(); // El hilo ha leido (se bloquea hasta que este libre)
        try {
            // Dormimos el hilo un tiempo aleatorio antes de comunicar el fin de la lectura, para dar ocasión a que otros
            // hilos hagan intentos fallidos de lectura/escritura y comprobar que el semáforo funciona correctamente.
            sleep((int) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // comunicamos al semáforo que hemos terminado de leer
        semaforo.finLectura();
    }
}

