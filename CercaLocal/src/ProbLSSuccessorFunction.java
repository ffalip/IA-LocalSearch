
//~--- non-JDK imports --------------------------------------------------------


import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;


public class ProbLSSuccessorFunction implements SuccessorFunction {

    @SuppressWarnings("unchecked")
    public List getSuccessors(Object aState) { // retorna ArrayList on cada element es un sucessor
        ArrayList retVal = new ArrayList();
        ProbLSBoard pare = (ProbLSBoard) aState;

        for (int i = 0; i < 1; ++i){

            // crear fill
            ProbLSBoard fill = new ProbLSBoard("INFO PER CREAR EL FILL IGUAL AL PARE");

            for (int num_user = 0; num_user < pare.num_usuaris(); ++num_user){
                for (int num_fitxer = 0; num_fitxer < pare.llista_usuaris()[nom_user].length(); ++num_fitxer){
                    for (int num_server = 0; num_server < pare.llista_servers().length(); ++num_server){

                        if (pare.fitxer_i_server_compatibles(num_fitxer, num_server)){
                            String string_info = "";
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
