
//~--- non-JDK imports --------------------------------------------------------


import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;


public class ProbLSSuccessorFunction implements SuccessorFunction {

    @SuppressWarnings("unchecked")
    public List getSuccessors(Object aState) { // retorna ArrayList on cada element es un sucessor
        ArrayList retVal = new ArrayList();
        ProbLSBoard pare = (ProbLSBoard) aState;
        ProbLSHeuristicFunction LSHF  = new ProbLSHeuristicFunction();


        for (int num_user = 0; num_user < pare.getNumUsers(); ++num_user){ // per cada usuari
            for (int pos_fitxer = 0; pos_fitxer < pare.getUsuaris().get(num_user).length(); ++pos_fitxer){ // per cada fitxer de l'usuari
                for (int num_server = 0; num_server < pare.num_servers(); ++num_server){ // per cada servidor

                    int num_fitxer = pare.getUsuaris().get(num_user).get(pos_fitxer).getFirst();

                    if (pare.validFileServer(num_fitxer, num_server) && num_server != pare.getUsuaris().get(num_user).get(pos_fitxer).getSecond()){ // server es compatible amb el fitxer i no es el mateix server que ja tenia

                        ProbLSBoard fill = new ProbLSBoard(pare.getNumServers(), pare.getNumUsers(), pare.getFileLoc(), pare.getTransTime(), pare.getActualBoard());

                        fill.changeTransmittingServer(num_user, num_fitxer, num_server);

                        double valor = LSHF.getHeuristicValue(fill);
                        String string_info = "Fitxer " + num_fitxer + " del usuari " + num_user + " passa del servidor " + pare.getUsuaris().get(num_user).get(pos_fitxer).getSecond() + " al servidor " + num_server;

                        // afegeix un fill al valor de return
                        retVal.add(new Successor(string_info, fill));
                    }

                }
            }
        }


        return retVal;

    }

}
