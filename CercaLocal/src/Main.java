import java.io.IOException;
import java.util.Random;


public class Main {
    private static final int maxUsers    = 100;
    private static final int maxServers  = 50;
    private static final int maxReqFiles = 10;

    public static void main(String[] args) throws IOException {
        System.out.print("Problema amb atributs randoms (R) o manuals (M): ");
        char mode = (char) System.in.read();
        System.out.println();
        if (mode == 'R') {
            System.out.println("Generant problema aleatori...");

            long seed = System.currentTimeMillis();
            Random rand = new Random(seed);

            int numUsers    = rand.nextInt(maxUsers) + 1;
            int numServers  = rand.nextInt(maxServers) + 1;
            int problemSeed = rand.nextInt();
            int maxReq      = rand.nextInt(maxReqFiles) + 1;
            int minReps     = rand.nextInt((numServers / 2)) + 1;
            ProbLSBoard board = new ProbLSBoard(numUsers, maxReq, numServers, minReps, problemSeed);

        }
        else if (mode == 'M'){
            System.out.print("Introdueix el nombre de d'usuaris: ");
            System.out.print("Introdueix el nombre de servidors: ");
            System.out.print("Introdueix el nombre màxim de peticions per usuari: ");
            System.out.print("Introdueix el nombre minim de replicació de fitxers ");

        }
        else System.out.println("El mode: " + mode + " no és vàlid");

    }
}