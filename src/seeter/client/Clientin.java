/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seeter.client;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import sep.seeter.client.State;

/**
 *
 * @author owner
 */
public class Clientin 
{
    private String user;
    private String host;
    private int port;
    String draftTopic = null;
    List<String> draftLines = new LinkedList<>();
    State state = State.Main;
    
    
    public Clientin(String user, String host, int port)
    {
        this.user = user;
        this.host = host;
        this.port = port;
    }
    
    public void composeTopic(String userI)
    {
        List<String> split = Arrays.stream(userI.trim().split("\\ "))
                .map(x -> x.trim()).collect(Collectors.toList());
        String[] rawArgs = split.toArray(new String[split.size()]);
        state = State.Draft;
        draftTopic = rawArgs[0];
        System.out.println(user + " has composed a topic.");
    }
    
    public void fetchTopic()
    {
        System.out.println(user + " has fetched the topic.");
    }
    public void addBody()
    {
        System.out.println(user + " has added seet under the topic");
    }
    public void sendDraft()
    {
        System.out.println(user + " has send the seets.");
    }
    
}
