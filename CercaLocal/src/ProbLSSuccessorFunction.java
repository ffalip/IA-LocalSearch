
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


        for (int num_user = 0; num_user < pare.num_usuaris(); ++num_user){ // per cada usuari
            for (int num_fitxer = 0; num_fitxer < pare.getUsuaris()[nom_user].length(); ++num_fitxer){ // per cada fitxer de l'usuari
                for (int num_server = 0; num_server < pare.num_servers(); ++num_server){ // per cada servidor

                    if (pare.fitxerMesServerCompatibles(num_fitxer, num_server) && num_server != pare.getUsuaris()[num_user][num_fitxer].get_second()){ // server es compatible amb el fitxer i no es el mateix server que ja tenia

                        ProbLSBoard fill = new ProbLSBoard("INFO PER CREAR EL FILL IGUAL AL PARE");

                        fill.canviarFitxerDeServer(num_user, num_fitxer, num_server);

                        double valor = LSHF.getHeuristicValue(fill);
                        String string_info = "";

                        // afegeix un fill al valor de return
                        retVal.add(new Successor(string_info, fill));
                    }

                }
            }
        }


        return retVal;

    }

}
