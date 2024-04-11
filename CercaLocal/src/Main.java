import java.io.IOException;
import java.util.Random;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;


public class Main {
    private static final int maxUsers    = 100;
    private static final int maxServers  = 50;
    private static final int maxReqFiles = 10;

    public static void main(String[] args) throws IOException {
        System.out.print("Problema amb atributs randoms (R) o manuals (M): ");
        char mode = (char) System.in.read();
        if (mode == 'R') {
            System.out.println("Generant problema aleatori...");

            long seed = System.currentTimeMillis();
            Random rand = new Random(seed);

            int numUsers    = rand.nextInt(maxUsers) + 1;
            int numServers  = rand.nextInt(maxServers) + 1;
            int problemSeed = rand.nextInt();
            int maxReq      = rand.nextInt(maxReqFiles) + 1;
            int minReps     = rand.nextInt((numServers / 2)) + 1;
            System.out.println(numUsers + " " + maxReq + " " + numServers + " " + minReps);
            ProbLSBoard board = new ProbLSBoard(numUsers, maxReq, numServers, minReps, problemSeed);
            board.printState();
            board.printTransTime();
            LSHillClimbingSearch(board);
        }
        else if (mode == 'M'){
            System.out.print("Introdueix el nombre de d'usuaris: ");
            System.out.print("Introdueix el nombre de servidors: ");
            System.out.print("Introdueix el nombre màxim de peticions per usuari: ");
            System.out.print("Introdueix el nombre minim de replicació de fitxers ");
        }
        else System.out.println("El mode: " + mode + " no és vàlid");

    }

    private static void LSHillClimbingSearch(ProbLSBoard board) {
        System.out.println("\nLS HillClimbing  -->");
        try {
            Problem problem =  new Problem(board,new ProbLSSuccessorFunction(), new ProbLSGoalTest(),new ProbLSHeuristicFunction());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);

            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
}