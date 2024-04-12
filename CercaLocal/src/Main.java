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
        long seed = 0;
        Random rand = new Random(seed);
        int problemSeed;

        int numUsers;
        int numServers;
        int maxReq;
        int minReps;
        int solIni = 2;
        /*
        if (mode == 'R') {
            System.out.println("Generant problema aleatori...");
             seed = System.currentTimeMillis();
             numUsers    = rand.nextInt(maxUsers) + 1;
             numServers  = rand.nextInt(maxServers-1) + 2;
             maxReq      = rand.nextInt(maxReqFiles) + 1;
             minReps     = rand.nextInt((numServers / 2)) + 1;
            System.out.println(numUsers + " " + maxReq + " " + numServers + " " + minReps);

        }*/
        //else {
            numUsers    = 5;
            numServers  = 4;
            maxReq      = 2;
            minReps     = 2;
        //}
        //for (int i = 0; i < 10; ++i) {
            problemSeed = rand.nextInt(Integer.MAX_VALUE);
            System.out.println(problemSeed);

            ProbLSBoard board = new ProbLSBoard(numUsers, maxReq, numServers, minReps, solIni, problemSeed);
            board.printState();
            //LSHillClimbingSearch(board);
            LSSimulatedAnnealingSearch(board);
        //}
        //board.printServersTime();

    }

    private static void LSHillClimbingSearch(ProbLSBoard board) {
        System.out.println("\nLS HillClimbing  -->");
        try {
            Problem problem =  new Problem(board,new ProbLSSuccessorFunction(), new ProbLSGoalTest(),new ProbLSHeuristicFunction1());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);
            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void LSSimulatedAnnealingSearch(ProbLSBoard board) {
        System.out.println("\nTSP Simulated Annealing  -->");
        try {
            Problem problem =  new Problem(board,new ProbLSSuccessorFunctionSA(), new ProbLSGoalTest(),new ProbLSHeuristicFunction1());
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(2000,100,5,0.001);
            search.traceOn();
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
            //String action = (String) actions.get(i);
            System.out.println((actions.get(i)));
        }
    }
}