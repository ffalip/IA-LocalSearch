/* Imports */

import IA.DistFS.*;
import aima.search.framework.HeuristicFunction;
import java.util.ArrayList;

public class ProbLSHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object state) {
        ProbLSBoard board = (ProbLSBoard)state;
        int[][] transmisionTimes = board.getTransTime();
        
        int nservers = board.getNumServers();
        int[] count_servidors = new int[nservers];

        ArrayList<ArrayList<Pair<Integer, Integer>>> usuaris = board.getActualBoard();

        int nu = usuaris.size(); // cambiar per nombre usuraris de state
        for (int i = 0; i < nu; ++i) {
            for (int j = 0; j < usuaris.get(i).size(); ++j) {
                int idServer = usuaris.get(i).get(j).getSecond();
                count_servidors[idServer] += transmisionTimes[i][idServer];
            }
        }

        int sum = 0;
        for (int i = 0; i < nservers; ++i) {
            sum += count_servidors[i]*count_servidors[i];
        }
        return (sum);
    }
}
