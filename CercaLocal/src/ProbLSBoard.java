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
    public ProbLSBoard(int users, int maxReq, int numS, int minReps, int solIni, int seed)
    {
        try {
            Random rand = new Random(System.currentTimeMillis());
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

                int transmitingS = -1;
                if (solIni == 1) {                    //solució inicial random
                    transmitingS = fileIdLoc[rand.nextInt(fileIdLoc.length)];
                }
                else {                                //solució inicial greedy
                    int minTime = Integer.MAX_VALUE;
                    for (int s: fileLoc.get(fileId)) {
                        int time = servers.tranmissionTime(s, usrId);
                        if (time < minTime) {
                            minTime = time;
                            transmitingS = s;
                        }
                    }
                }
                actualBoard.computeIfAbsent(usrId, k -> new ArrayList<>());
                actualBoard.get(usrId).add(new Pair<Integer,Integer>(fileId, transmitingS));
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
        /* Print transTime
        System.out.println();
        for (HashMap.Entry<Integer, int[]> entry : transTime.entrySet()) {
            System.out.print("user" + entry.getKey() + ": ");
            for (int i : entry.getValue()) {
                System.out.print(i + " ");
            }
            System.out.println();
        }*/
        /* Print fileLoc
        System.out.println();
        for (HashMap.Entry<Integer, int[]> entry : fileLoc.entrySet()) {
            System.out.print("file" + entry.getKey() + ": ");
            for (int i : entry.getValue()) {
                System.out.print(i + " ");
            }
            System.out.println();
        }*/
    }

    public String toString()
    {
        String ret = "";
        for (HashMap.Entry<Integer, ArrayList<Pair<Integer,Integer>>> entry: actualBoard.entrySet()) {
            ret+="user" + entry.getKey() + ": \n";
            for (Pair<Integer,Integer> p : entry.getValue())
                ret+="(" + p.first + "," + p.second + ") \n";
            ret+= "\n";
        }
        return ret;
    }

    public void printServersTime() {
        int nservers = numServers;
        int[] count_servidors = new int[nservers];
        for (HashMap.Entry<Integer, ArrayList<Pair<Integer,Integer>>> entry : actualBoard.entrySet()) {
            Integer key = entry.getKey();
            ArrayList<Pair<Integer,Integer>> value = entry.getValue();
            int nreq = value.size();
            for (int j = 0; j < nreq; ++j) {
                int idServer = value.get(j).second;
                count_servidors[idServer] += transTime.get(key)[idServer]; //transtimes correcte
            }
        }
        for (int i = 0; i < nservers; ++i) {
            System.out.println(i + "temps:" + count_servidors[i]);
        }
    }
    public int getTotalTime() {
        int nservers = getNumServers();

        HashMap<Integer, ArrayList<Pair<Integer,Integer>>> usuaris = getActualBoard();
        int[] count_servidors = new int[nservers];

        for (HashMap.Entry<Integer, ArrayList<Pair<Integer,Integer>>> entry : usuaris.entrySet()) {
            Integer key = entry.getKey();
            ArrayList<Pair<Integer,Integer>> value = entry.getValue();
            int nreq = value.size();
            for (int j = 0; j < nreq; ++j) {
                int idServer = value.get(j).second;
                count_servidors[idServer] += transTime.get(key)[idServer]; //transtimes correcte
            }
        }

        int sum = 0;
        for (int i = 0; i < nservers; ++i) {
            sum += count_servidors[i];
        }
        return (sum);
    }
    public void getTimeServers() {
        int nservers = getNumServers();

        HashMap<Integer, ArrayList<Pair<Integer,Integer>>> usuaris = getActualBoard();
        int[] count_servidors = new int[nservers];

        for (HashMap.Entry<Integer, ArrayList<Pair<Integer,Integer>>> entry : usuaris.entrySet()) {
            Integer key = entry.getKey();
            ArrayList<Pair<Integer,Integer>> value = entry.getValue();
            int nreq = value.size();
            for (int j = 0; j < nreq; ++j) {
                int idServer = value.get(j).second;
                count_servidors[idServer] += transTime.get(key)[idServer]; //transtimes correcte
            }
        }

        int max = 0;
        for (int i = 0; i < nservers; ++i) {

            System.out.println(count_servidors[i]);

        }

    }
}
