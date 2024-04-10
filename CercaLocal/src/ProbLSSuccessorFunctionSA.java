
//~--- non-JDK imports --------------------------------------------------------


import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ProbLSSuccessorFunction implements SuccessorFunction {

    @SuppressWarnings("unchecked")
    public List getSuccessors(Object aState) { // retorna ArrayList on cada element es un sucessor
        ArrayList retVal = new ArrayList();
        ProbLSBoard pare = (ProbLSBoard) aState;
        ProbLSHeuristicFunction LSHF  = new ProbLSHeuristicFunction();
        Random myRandom=new Random();

        int num_user, pos_fitxer, num_server;

        num_user = myRandom.nextInt(pare.getNumUsers());

        pos_fitxer = myRandom.nextInt(pare.getActualBoard().get(num_user).size());

        int num_fitxer = pare.getActualBoard().get(num_user).get(pos_fitxer).getFirst();

        do{
            num_server = myRandom.nextInt(pare.getNumServers());
        }while( !(pare.validFileServer(num_fitxer, num_server) && num_server != pare.getUsuaris().get(num_user).get(pos_fitxer).getSecond()) ); // mentres el server no sigui possible buscar un nou


        ProbLSBoard fill = new ProbLSBoard(pare.getNumServers(), pare.getNumUsers(), pare.getFileLoc(), pare.getTransTime(), pare.getActualBoard());

        fill.changeTransmittingServer(num_user, num_fitxer, num_server);

        double valor = LSHF.getHeuristicValue(fill);
        String string_info = "Fitxer " + num_fitxer + " del usuari " + num_user + " passa del servidor " + pare.getActualBoard().get(num_user).get(pos_fitxer).getSecond() + " al servidor " + num_server;

        // afegeix un fill al valor de return
        retVal.add(new Successor(string_info, fill));

        return retVal;

    }

}
