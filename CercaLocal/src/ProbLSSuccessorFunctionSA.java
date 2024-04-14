
//~--- non-JDK imports --------------------------------------------------------


import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ProbLSSuccessorFunctionSA implements SuccessorFunction {

    @SuppressWarnings("unchecked")
    public List getSuccessors(Object aState) { // retorna ArrayList on cada element es un sucessor

        // com a minim 1 dels 2 ha de ser true sino caca
        boolean OPERADOR_MOVE = true;
        boolean OPERADOR_SWAP = false;

        ArrayList retVal = new ArrayList();
        ProbLSBoard pare = (ProbLSBoard) aState;
        ProbLSHeuristicFunction LSHF  = new ProbLSHeuristicFunction();
        Random myRandom=new Random();

        ArrayList<Integer> users = pare.getUsersId();

        int num_rand = myRandom.nextInt(2);

        // IF PER FER OPERADOR DE MOVE
        if (true|| (OPERADOR_MOVE && !OPERADOR_SWAP)){

            int num_user, pos_fitxer, num_server;

            num_user = myRandom.nextInt(users.size());

            int id_user = users.get(num_user);

            pos_fitxer = myRandom.nextInt(pare.getActualBoard().get(id_user).size());

            int num_fitxer = pare.getActualBoard().get(id_user).get(pos_fitxer).getFirst();

            do{
                num_server = myRandom.nextInt(pare.getNumServers());
            } while( !(pare.validFileServer(num_fitxer, num_server) && num_server != pare.getActualBoard().get(id_user).get(pos_fitxer).getSecond()) ); // mentres el server no sigui possible buscar un nou


            ProbLSBoard fill = new ProbLSBoard(pare.getNumServers(), pare.getNumUsers(), pare.getFileLoc(), pare.getTransTime(), pare.getActualBoard());

            fill.changeTransmittingServer(id_user, num_fitxer, num_server);

            double valor = LSHF.getHeuristicValue(fill);
            String string_info = "Fitxer " + num_fitxer + " del usuari " + id_user + " passa del servidor " + pare.getActualBoard().get(id_user).get(pos_fitxer).getSecond() + " al servidor " + num_server + " cost(" + valor +")";
            // afegeix un fill al valor de return

            retVal.add(new Successor(string_info, fill));
            //System.out.println(string_info);

            return retVal;
        }

        // IF PER FER OPERADOR DE SWAP
        else if (num_rand == 1 || (!OPERADOR_MOVE && OPERADOR_SWAP)){

            int num_user1, num_user2, pos_fitxer1, pos_fitxer2, num_server1, num_server2;

            num_user1 = myRandom.nextInt(users.size());
            num_user2 = myRandom.nextInt(users.size());

            int id_user1 = users.get(num_user1);
            int id_user2 = users.get(num_user2);

            pos_fitxer1 = myRandom.nextInt(pare.getActualBoard().get(id_user1).size());
            pos_fitxer2 = myRandom.nextInt(pare.getActualBoard().get(id_user1).size());

            int num_fitxer1 = pare.getActualBoard().get(id_user1).get(pos_fitxer1).getFirst();
            int num_fitxer2 = pare.getActualBoard().get(id_user2).get(pos_fitxer2).getFirst();

            num_server1 = pare.getActualBoard().get(id_user1).get(pos_fitxer1).getSecond();
            num_server2 = pare.getActualBoard().get(id_user2).get(pos_fitxer2).getSecond();

            do{
                num_user1 = myRandom.nextInt(users.size());
                num_user2 = myRandom.nextInt(users.size());

                id_user1 = users.get(num_user1);
                id_user2 = users.get(num_user2);

                pos_fitxer1 = myRandom.nextInt(pare.getActualBoard().get(id_user1).size());
                pos_fitxer2 = myRandom.nextInt(pare.getActualBoard().get(id_user1).size());

                num_fitxer1 = pare.getActualBoard().get(id_user1).get(pos_fitxer1).getFirst();
                num_fitxer2 = pare.getActualBoard().get(id_user2).get(pos_fitxer2).getFirst();

                num_server1 = pare.getActualBoard().get(id_user1).get(pos_fitxer1).getSecond();
                num_server2 = pare.getActualBoard().get(id_user2).get(pos_fitxer2).getSecond();
            } while( !( pare.validFileServer(num_fitxer1, num_server2) && pare.validFileServer(num_fitxer1, num_server1) ) ); // mentres el swap no sigui possible


            ProbLSBoard fill = new ProbLSBoard(pare.getNumServers(), pare.getNumUsers(), pare.getFileLoc(), pare.getTransTime(), pare.getActualBoard());

            fill.changeTransmittingServer(id_user1, num_fitxer1, num_server2);
            fill.changeTransmittingServer(id_user2, num_fitxer2, num_server1);

            double valor = LSHF.getHeuristicValue(fill);
            String string_info = "Fitxer " + num_fitxer1 + " del usuari " + id_user1 + " passa al servidor " + num_server2 + " i Fitxer " + num_fitxer2 + " del usuari " + id_user2 + " passa al servidor " + num_server1 +" cost (" + valor +")";

            // afegeix un fill al valor de return
            retVal.add(new Successor(string_info, fill));
            return retVal;
        }

        return retVal;

    }

}
