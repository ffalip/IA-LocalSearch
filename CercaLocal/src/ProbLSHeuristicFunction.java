/* Imports */

import IA.DistFS.*;
import aima.search.framework.HeuristicFunction;
import java.util.ArrayList;
import java.util.HashMap;

public class ProbLSHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object state) {
        ProbLSBoard board = (ProbLSBoard)state;
        HashMap<Integer, int[]> transmisionTimes = new HashMap<>();//board.getTransTime();
        
        int nservers = board.getNumServers();

        HashMap<Integer, ArrayList<Pair<Integer,Integer>>> usuaris = new HashMap<>();
        int[] count_servidors = new int[nservers];

        for (HashMap.Entry<Integer, ArrayList<Pair<Integer,Integer>>> entry : usuaris.entrySet()) {
            Integer key = entry.getKey();
            ArrayList<Pair<Integer,Integer>> value = entry.getValue();
            int nreq = value.size();
            for (int j = 0; j < nreq; ++j) {
                int idServer = value.get(j).second;
                count_servidors[idServer] += transmisionTimes.get(key)[idServer]; //transtimes correcte
            }
        }

        int sum = 0;
        for (int i = 0; i < nservers; ++i) {
            sum += count_servidors[i]*count_servidors[i];
        }
        return (sum);
    }
}
