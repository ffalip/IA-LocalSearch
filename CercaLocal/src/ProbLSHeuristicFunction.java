/* Imports */

import aima.search.framework.HeuristicFunction;

import java.util.List;

public class ProbLSHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object state) {
        ProbLSBoard board = (ProbLSBoard)state;
        double sum = 0, nu;
        List<List<Float>>usuaris = board.getUsuaris();
        nu = usuaris.size(); // cambiar per nombre usuraris de state
        for (int i = 0; i < nu; ++i) {
            for (int j = 0; j < usuaris[i].size(); ++j) {
                sum += usuaris[j].getTemps();
            }
        }
        return (sum);
    }
}
