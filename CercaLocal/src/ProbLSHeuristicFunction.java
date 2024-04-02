/* Imports */

import aima.search.framework.HeuristicFunction;
public class ProbLSHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object state) {
        ProbLSBoard board = (ProbLSBoard)state;
        double sum = 0, nu;
        nu = 10; // cambiar per nombre usuraris de state
        for (int i = 0; i < nu; ++i) {
            usuari =  board.getUsuari(i);
            for (int j = 0; j < usuari.size(); ++j) {
                sum += usuari[j].getTemps();
            }
        }
        return (sum);
    }
}
