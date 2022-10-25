package principal;

public class Semaforo {
    // Indica que no hay lectores leyendo, ni ningún escritor escribiendo.
    // En este estado pueden entrar lectores a leer, o un escritor a escribir.
    public final static int LIBRE = 0;
    // Indica que hay lectores leyendo. Puede entrar un lector más a leer, pero no un escritor.
    public final static int CON_LECTORES = 1;
    // Indica que hay un escritor escribiendo. No puede entrar ningún lector ni escritor.
    public final static int CON_ESCRITOR = 2;
    // Indica el estado actual del semáforo. (inicialmente: LIBRE)
    private int estado = LIBRE;
    // indica el número de lectores que están leyendo. (inicialmente: 0)
    private int numLectores = 0;

    /** Método que permite a un lector entrar a leer. */
    public synchronized void accesoLeer() {
        String nombre = Thread.currentThread().getName();
        if (estado == LIBRE) {
            System.out.println("BD: " + estado + " - " + numLectores + "L " + nombre + " ENTRA a leer");
            estado = CON_LECTORES;
        } else if (estado != CON_LECTORES) {
            while (estado == CON_ESCRITOR) {
                try {
                    System.out.println("BD: " + estado + " - " + numLectores + "L " + nombre + " ESPERA a leer");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("BD: " + estado + " - " + numLectores + "L " + nombre + " ENTRA a leer");

            estado = CON_LECTORES;
        } else { // En este punto el estado es CON_LECTORES
            System.out.println("BD: " + estado + " - " + numLectores + "L " + nombre + " ENTRA a leer");
        }
        numLectores++;
        System.out.println("BD: " + estado + " - " + numLectores + "L " + nombre + " LEYENDO......");
    }

    /** Método que permite a un escritor entrar a escribir. */
    public synchronized void accesoEscribir() {
        String nombre = Thread.currentThread().getName();
        if (estado == LIBRE) {
            System.out.println("BD: " + estado + " - " + numLectores + "L " + nombre + " ENTRA a escribir");
            estado = CON_ESCRITOR;
        } else {
            while (estado != LIBRE) {
                try {
                    System.out.println("BD: " + estado + " - " + numLectores + "L " + nombre + " ESPERA a escribir");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("BD: " + estado + " - " + numLectores + "L " + nombre + " ENTRA a escribir");
            estado = CON_ESCRITOR;
        }
        System.out.println("BD: " + estado + " - " + numLectores + "L " + nombre + " ESCRIBIENDO......");
    }

    public synchronized void finEscritura() {
        estado = LIBRE; // cambiamos el estado a LIBRE
        // mensaje para comprobar funcionamiento.
        System.out.println("BD: " + estado + " - " + numLectores + "L " + Thread.currentThread().getName() + " TERMINA de escribir");
        notifyAll(); // notificamos a todos los hilos que estén esperando
    }

    public synchronized void finLectura() {
        System.out.println("BD: " + estado + " - " + numLectores + "L " + Thread.currentThread().getName() + " TERMINA de leer");
        numLectores--; // decrementamos el número de lectores
        if (numLectores == 0) { // si no quedan lectores
            estado = LIBRE; // cambiamos el estado a LIBRE
            // mensaje para comprobar funcionamiento.
            System.out.println("BD: " + estado + " - " + numLectores + "L " + Thread.currentThread().getName() + " TERMINA de leer");
            notifyAll(); // notificamos a todos los hilos que estén esperando
        }
    }
}
