package za.ac.vhuthu.sagrada;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import za.ac.vhuthu.messages.Message;

public class GameGroups {
    private static final ReentrantLock lock=new ReentrantLock();

    public static final ArrayList<ArrayList<GameClient>> groups=new ArrayList<ArrayList<GameClient>>();

    public static void join(GameClient client){

        lock.lock();
        boolean joined=false;
        for (int x=0;x<groups.size();x++){
            ArrayList cur=groups.get(x);
            if(cur.size()<4){
                cur.add(client);
                joined=true;
            }
        }
        if(!joined)
            groups.add(new ArrayList<GameClient>());
        lock.unlock();
    }

    public static void leave(GameClient Client){
        lock.lock();
        for (int x=0;x<groups.size();x++){
            ArrayList<GameClient> arr=new ArrayList<>();
            for(int y=0;y<arr.size();y++){
                if(arr.get(y).equals(Client)){
                    arr.remove(y);
                }
            }
        }
        lock.unlock();
    }
    public static void send(String groupName, Message message) {
        lock.lock();
        // Is there a group with the given name? If not, exit.
        //if(!groups.containsKey(groupName)) return;

        // Get clients in group.
        //Set<ChatClient> clients = groups.get(groupName);
        ArrayList<GameClient> x=groups.get(0);
        // Send message to each client.
        for(GameClient client : x)
            client.send(message);
        lock.unlock();
    }

}
