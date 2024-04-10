/* Imports */

import aima.search.framework.HeuristicFunction;

import java.util.ArrayList;

public class ProbLSHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object state) {
        ProbLSBoard board = (ProbLSBoard)state;
        Integer sum = 0, nu;
        ArrayList<ArrayList<Pair<Integer, Integer>>> usuaris = board.getUsuaris();
        nu = usuaris.size(); // cambiar per nombre usuraris de state
        for (int i = 0; i < nu; ++i) {
            for (int j = 0; j < usuaris.get(i).size(); ++j) {
                sum += usuaris.get(i).get(j).getSecond();
            }
        }
        return (sum);
    }
}
