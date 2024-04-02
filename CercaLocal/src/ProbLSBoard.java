/**
 * Representació de l'estat del problema
 */

/* Imports */
import IA.DistFS.*;

public class ProbLSBoard {
    /* Atributs */

    //private

    /** Constructora
     * Genera una instància del problema
     */
    public ProbLSBoard(int users, int maxReq, int numServers, int minReps, int seed)
    {
        Requests req = new Requests(users, maxReq, seed);
        try {
            Servers serves = new Servers(numServers, minReps, seed);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //inicialitzar matrius


    }

    public int num_usuaris() {
        return 0;
    }

    //public llista_usuaris()
    //public llista_servers()


    /* Operadors */

}
