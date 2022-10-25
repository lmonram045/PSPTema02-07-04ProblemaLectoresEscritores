package principal;

public class HiloEscritor extends Thread {

    private Semaforo semaforo;

    /** Constructor HiloEscritor */
    public HiloEscritor(String nombre, Semaforo s) {
        this.setName(nombre);
        this.semaforo = s;
    }

    /** Método run */
    @Override
    public void run() {
        // Mensaje para comprobar el funcionamiento
        System.out.println(getName() + ": Intentando escribir");
        // Llamamos al método accesoEscribir del semáforo
        semaforo.accesoEscribir(); // El hilo ha escrito (se bloquea hasta que este libre)
        try {
            // Dormimos el hilo un tiempo aleatorio antes de comunicar el fin de la lectura, para dar ocasión a que otros
            // hilos hagan intentos fallidos de lectura/escritura y comprobar que el semáforo funciona correctamente.
            sleep((int) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // comunicamos al semáforo que hemos terminado de escribir
        semaforo.finEscritura();
    }
}
