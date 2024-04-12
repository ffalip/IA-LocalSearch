
//~--- non-JDK imports --------------------------------------------------------


import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;


public class ProbLSSuccessorFunction implements SuccessorFunction {

    @SuppressWarnings("unchecked")
    public List getSuccessors(Object aState) { // retorna ArrayList on cada element es un sucessor

        ArrayList retVal = new ArrayList();
        ProbLSBoard pare = (ProbLSBoard) aState;
        ProbLSHeuristicFunction LSHF  = new ProbLSHeuristicFunction();

        ArrayList<Integer> users = pare.getUsersId();

        // FOR PER OPERADOR MOVE
        for (int num_user = 0; num_user < users.size(); ++num_user){ // per cada usuari

            int id_user = users.get(num_user);
            for (int pos_fitxer = 0; pos_fitxer < pare.getActualBoard().get(id_user).size(); ++pos_fitxer){ // per cada fitxer de l'usuari
                for (int num_server = 0; num_server < pare.getNumServers(); ++num_server){ // per cada servidor

                    int num_fitxer = pare.getActualBoard().get(id_user).get(pos_fitxer).getFirst();

                    if (pare.validFileServer(num_fitxer, num_server) && num_server != pare.getActualBoard().get(id_user).get(pos_fitxer).getSecond()){ // server es compatible amb el fitxer i no es el mateix server que ja tenia

                        ProbLSBoard fill = new ProbLSBoard(pare.getNumServers(), pare.getNumUsers(), pare.getFileLoc(), pare.getTransTime(), pare.getActualBoard());

                        fill.changeTransmittingServer(id_user, num_fitxer, num_server);

                        double valor = LSHF.getHeuristicValue(fill);
                        String string_info = "Fitxer " + num_fitxer + " del usuari " + id_user + " passa del servidor " + pare.getActualBoard().get(id_user).get(pos_fitxer).getSecond() + " al servidor " + num_server +" cost(" + valor +")";
                        // afegeix un fill al valor de return
                        retVal.add(new Successor(string_info, fill));
                    }

                }
            }
        }

        // FOR PER FER OPERADOR SWAP
        for (int num_user = 0; num_user < users.size(); ++num_user){ // per cada usuari
            int id_user = users.get(num_user);
            for (int pos_fitxer = 0; pos_fitxer < pare.getActualBoard().get(id_user).size(); ++pos_fitxer){ // per cada fitxer de l'usuari

                for ((int num_user2 = 0; num_user2 < users.size(); ++num_user2){ // per cada usuari2
                    int id_user2 = users.get(num_user2);
                    for (int pos_fitxer2 = 0; pos_fitxer2 < pare.getActualBoard().get(id_user2).size(); ++pos_fitxer2){ // per cada fitxer de l'usuari2

                        int num_fitxer = pare.getActualBoard().get(id_user).get(pos_fitxer).getFirst();
                        int num_fitxer2 = pare.getActualBoard().get(id_user2).get(pos_fitxer2).getFirst();

                        int server_fitxer = pare.getActualBoard().get(id_user).get(pos_fitxer).getSecond();
                        int server_fitxer2 = pare.getActualBoard().get(id_user2).get(pos_fitxer2).getSecond();

                        // fitxers son de servers diferents i els 2 fitxers estan disponibles als 2 servidors
                        if (pare.validFileServer(num_fitxer, server_fitxer) && pare.validFileServer(num_fitxer, server_fitxer2) && pare.validFileServer(num_fitxer2, server_fitxer) && pare.validFileServer(num_fitxer2, server_fitxer2)){

                            fill.changeTransmittingServer(id_user, num_fitxer, num_server2);
                            fill.changeTransmittingServer(id_user2, num_fitxer2, num_server);

                            double valor = LSHF.getHeuristicValue(fill);
                            String string_info = "Fitxer " + num_fitxer + " del usuari " + id_user + " passa al servidor " + num_server2 + " i Fitxer " + num_fitxer2 + " del usuari " + id_user2 + " passa al servidor " + num_server " +" cost(" + valor +")";
                            // afegeix un fill al valor de return
                            retVal.add(new Successor(string_info, fill));

                        }

                    }

                }

            }
        }


        return retVal;

        }

    }

}
