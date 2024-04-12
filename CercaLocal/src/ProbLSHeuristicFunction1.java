/* Imports */

import aima.search.framework.HeuristicFunction;

import java.util.ArrayList;
import java.util.HashMap;

public class ProbLSHeuristicFunction1 implements HeuristicFunction {

    public boolean equals(Object obj) {
        boolean retValue;
        retValue = super.equals(obj);
        return retValue;
    }
    public double getHeuristicValue(Object state) {
        ProbLSBoard board = (ProbLSBoard)state;
        HashMap<Integer, int[]> transmisionTimes = board.getTransTime();
        
        int nservers = board.getNumServers();

        HashMap<Integer, ArrayList<Pair<Integer,Integer>>> usuaris = board.getActualBoard();
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

        double max = 0;
        for (int i = 0; i < nservers; ++i) {
            if ((double) count_servidors[i] >= max) {
                max = count_servidors[i];
            }
        }
        return (max);
    }
}
