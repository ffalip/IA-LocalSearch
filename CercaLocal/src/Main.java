import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.time.LocalTime;

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
        int solIni = 1;
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
            numUsers    = 200;
            numServers  = 50;
            maxReq      = 5;
            minReps     = 5;
        //}


        for (int i = 0; i < 5; ++i) {
            problemSeed = rand.nextInt(Integer.MAX_VALUE);
            LocalTime currentTime = LocalTime.now();
            System.out.println(currentTime);
            System.out.println(problemSeed);
            ProbLSBoard board = new ProbLSBoard(numUsers, maxReq, numServers, minReps, solIni, problemSeed);
            //board.printState();
            LSHillClimbingSearch(board, i);
            //LSSimulatedAnnealingSearch(board, i);
            LocalTime newcurrentTime = LocalTime.now();
            System.out.println(board.getTotalTime());
            System.out.println(currentTime.until(newcurrentTime, ChronoUnit.MILLIS));
            System.out.println("----------");
            minReps += 5;
        }
        //board.printServersTime();

    }

    private static void LSHillClimbingSearch(ProbLSBoard board, int i) {
        System.out.println("\nLS HillClimbing  -->");
        try {
            Problem problem =  new Problem(board,new ProbLSSuccessorFunction(), new ProbLSGoalTest(),new ProbLSHeuristicFunction1());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);
            System.out.println();
            //printActions(agent.getActions(), i);
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void LSSimulatedAnnealingSearch(ProbLSBoard board, int i) {
        System.out.println("\nTSP Simulated Annealing  -->");
        try {
            Problem problem =  new Problem(board,new ProbLSSuccessorFunctionSA(), new ProbLSGoalTest(),new ProbLSHeuristicFunction());
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(1000000,10000,5,0.001);
            //search.traceOn();
            SearchAgent agent = new SearchAgent(problem,search);
            //printActions(agent.getActions(), i);
            //printInstrumentation(agent.getInstrumentation());
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

    private static void printActions(List actions, int x) {
        String PATH = x +".txt";
        File file = new File(PATH);
        if (file.exists()) file.delete();

        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
            float valor = extractFloatFromParentheses(action);
            try {

                appendFloatToFile(PATH, valor);
                //System.out.println("Float value appended to the file successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while appending the float value to the file: " + e.getMessage());
            }
        }
    }
    private static float extractFloatFromParentheses(String action) {
        // Find the opening and closing parentheses
        int startIndex = action.indexOf('(');
        int endIndex = action.indexOf(')');

        // Extract the substring within the parentheses
        String floatStr = action.substring(startIndex + 1, endIndex);

        // Parse the substring to float
        float floatValue = Float.parseFloat(floatStr);

        return floatValue;
    }

    private static void appendFloatToFile(String filePath, float value) throws IOException {
        // Open the file in append mode
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

        // Append the float value to the file
        writer.append(Float.toString(value));
        writer.newLine(); // Add a new line for better readability or separation

        // Close the writer
        writer.close();
    }
}