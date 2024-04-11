/**
 * Representació de l'estat del problema
 */

/* Imports */
import IA.DistFS.*;

import java.util.*;

public class ProbLSBoard {
    /* Atributs */
    private static int numServers;
    private static int numUsers;
    private static HashMap<Integer, int[]> fileLoc;
    private static HashMap<Integer, int[]> transTime;
    private HashMap<Integer, ArrayList<Pair<Integer, Integer>>> actualBoard;


    /** Constructora
     * Genera una instància del problema
     */
    public ProbLSBoard(int users, int maxReq, int numS, int minReps, int seed)
    {
        try {
            Random rand = new Random(seed);
            Requests requests = new Requests(users, maxReq, seed);
            Servers servers = new Servers(numS, minReps, seed);
            numServers  = numS;
            numUsers    = users;
            fileLoc     = new HashMap<>();
            actualBoard = new HashMap<>();
            transTime   = new HashMap<>();

            for (int i = 0; i < requests.size(); i++) {
                int usrId = requests.getRequest(i)[0];
                int fileId = requests.getRequest(i)[1];

                int[] fileIdLoc = servers.fileLocations(fileId).stream().mapToInt(a->a).toArray();
                fileLoc.put(fileId, fileIdLoc);

                int randS = fileIdLoc[rand.nextInt(fileIdLoc.length)];
                actualBoard.computeIfAbsent(usrId, k -> new ArrayList<>());
                actualBoard.get(usrId).add(new Pair<Integer,Integer>(fileId, randS));
            }
            for (int userId : actualBoard.keySet()) {
                int[] transTimeUserId = new int[numServers];
                for (int i = 0; i < numServers; i++) transTimeUserId[i] = servers.tranmissionTime(i, userId);
                transTime.put(userId, transTimeUserId);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public ProbLSBoard(int numS, int numU, HashMap<Integer, int[]> fLoc, HashMap<Integer, int[]> tTime,
                       HashMap<Integer, ArrayList<Pair<Integer, Integer>>> board)
    {
        numServers  = numS;
        numUsers    = numU;
        fileLoc     = deepCopyIntArrayHashMap(fLoc);
        transTime   = deepCopyIntArrayHashMap(tTime);
        actualBoard = deepCopyPairArrayListHashMap(board);
    }
    // Method to deep copy HashMap<Integer, int[]>
    private HashMap<Integer, int[]> deepCopyIntArrayHashMap(HashMap<Integer, int[]> original) {
        HashMap<Integer, int[]> copy = new HashMap<>();
        for (Integer key : original.keySet()) {
            int[] originalArray = original.get(key);
            int[] newArray = Arrays.copyOf(originalArray, originalArray.length);
            copy.put(key, newArray);
        }
        return copy;
    }

    // Method to deep copy HashMap<Integer, ArrayList<Pair<Integer, Integer>>>
    private HashMap<Integer, ArrayList<Pair<Integer, Integer>>> deepCopyPairArrayListHashMap(HashMap<Integer, ArrayList<Pair<Integer, Integer>>> original) {
        HashMap<Integer, ArrayList<Pair<Integer, Integer>>> copy = new HashMap<>();
        for (Integer key : original.keySet()) {
            ArrayList<Pair<Integer, Integer>> originalList = original.get(key);
            ArrayList<Pair<Integer, Integer>> newList = new ArrayList<>();
            for (Pair<Integer, Integer> pair : originalList) {
                // Creating a new Pair object with the same values
                Pair<Integer, Integer> newPair = new Pair<>(pair.first, pair.second);
                newList.add(newPair);
            }
            copy.put(key, newList);
        }
        return copy;
    }
    public int getNumServers() { return numServers; }
    public int getNumUsers() { return numUsers; }
    public HashMap<Integer, int[]> getTransTime() {return transTime; }
    public HashMap<Integer, int[]> getFileLoc() { return fileLoc; }
    public HashMap<Integer, ArrayList<Pair<Integer, Integer>>> getActualBoard() {return actualBoard;}
    public ArrayList<Integer> getUsersId() { return new ArrayList<Integer>(actualBoard.keySet()); }

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

    public void printState()
    {
        for (HashMap.Entry<Integer, ArrayList<Pair<Integer,Integer>>> entry: actualBoard.entrySet()) {
            System.out.print("user" + entry.getKey() + ": ");
            for (Pair<Integer,Integer> p : entry.getValue())
                System.out.print("(" + p.first + "," + p.second + ") ");
            System.out.println();
        }
    }
}
