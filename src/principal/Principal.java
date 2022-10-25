package principal;

public class Principal {
    public static void main(String[] args) {
        // Creamos un semáforo
        Semaforo semaforo = new Semaforo();
        // Creamos 5 hilos lectores
        for (int i = 0; i < 5; i++) {
            new HiloLector("Lector " + i, semaforo).start();
        }
        // Creamos 2 hilos escritores
        for (int i = 0; i < 2; i++) {
            new HiloEscritor("Escritor " + i, semaforo).start();
        }
    }
}

