/**
 * Representació de l'estat del problema
 */

/* Imports */
import IA.DistFS.*;
import java.util.ArrayList;
import java.util.Random;

public class ProbLSBoard {
    /* Atributs */
    private static int numServers;
    private static int numUsers;
    private static ArrayList<int[]> fileLoc;
    private static int[][] transTime;
    private ArrayList<ArrayList<Pair<Integer, Integer>>> actualBoard;


    /** Constructora
     * Genera una instància del problema
     */
    public ProbLSBoard(int users, int maxReq, int numS, int minReps, int seed)
    {
        try {
            Random rand = new Random(seed);
            Requests requests = new Requests(users, maxReq, seed);
            Servers servers = new Servers(numS, minReps, seed);
            numServers = numS;
            numUsers   = users;
            fileLoc = new ArrayList<>();
            actualBoard = new ArrayList<>();
            transTime = new int[numUsers][numServers];

            for (int i = 0; i < users; ++i) actualBoard.add(new ArrayList<Pair<Integer, Integer>>());

            for (int i = 0; i < numUsers; i++)
                for (int j = 0; j < numServers; j++) {
                    transTime[i][j] = servers.tranmissionTime(i, j);
                }

            for (int i = 0; i < requests.size(); i++) {
                int usrId = requests.getRequest(i)[0];
                int fileId = requests.getRequest(i)[1];

                int[] fileIdLoc = servers.fileLocations(fileId).stream().mapToInt(a->a).toArray();
                fileLoc.add(fileId, fileIdLoc);

                int randS = fileIdLoc[rand.nextInt(fileIdLoc.length)];
                actualBoard.get(usrId).add(new Pair<>(fileId, randS));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public ProbLSBoard(int numS, int numU, ArrayList<int[]> fLoc, int[][] tTime,
                       ArrayList<ArrayList<Pair<Integer, Integer>>> board)
    {
        numServers  = numS;
        numUsers    = numU;
        fileLoc     = fLoc;
        transTime   = tTime;
        actualBoard = board;
    }
    public int getNumServers() { return numServers; }
    public int getNumUsers() { return numUsers; }
    public int[][] getTransTime() {return transTime; }
    public ArrayList<int[]> getFileLoc() { return fileLoc; }
    public ArrayList<ArrayList<Pair<Integer, Integer>>> getActualBoard() {return actualBoard;}

    public boolean validFileServer(int fileId, int serverId)
    {
        for (int i = 0; i < fileLoc.get(fileId).length; i++)
            if (fileLoc.get(fileId)[i] == serverId) return true;
        return false;
    }

    public void changeTransmittingServer(int userId, int fileId, int newServerId)
    {
        for ( Pair<Integer, Integer> a : actualBoard.get(userId)) {
            if (a.first == fileId) {
                int idx = actualBoard.get(userId).indexOf(a);
                actualBoard.get(userId).get(idx).second = newServerId;
            }
        }
    }

    /* Operadors */

}
