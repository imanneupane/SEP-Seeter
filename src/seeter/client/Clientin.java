/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seeter.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import sep.seeter.client.State;
import sep.seeter.net.message.Publish;
import sep.seeter.net.message.SeetsReply;
import sep.seeter.net.message.SeetsReq;

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
    CLFormatter helper = new CLFormatter(this.host, this.port);
    
    public Clientin(String user, String host, int port)
    {
        this.user = user;
        this.host = host;
        this.port = port;
    }
    
    public void composeTopic(String userI)
    {
        draftTopic = splitTrim(userI)[0];
        System.out.println(user + " has composed a topic.");
    }
    
    public void fetchTopic(String userI) throws IOException, ClassNotFoundException
    {
        helper.chan.send(new SeetsReq(splitTrim(userI)[0]));
        SeetsReply rep = (SeetsReply) helper.chan.receive();
        System.out.print(helper.formatFetched(splitTrim(userI)[0], rep.users, rep.lines));
    }
    public void addBody(String userI)
    {   
        String line = Arrays.stream(splitTrim(userI)).collect(Collectors.joining());
        draftLines.add(line);
        System.out.println(user + " has added seet under the topic");
    }
    public void sendDraft() throws IOException
    {
        helper.chan.send(new Publish(user, draftTopic, draftLines));
        draftTopic = null;
        System.out.println(user + " has send the seets.");
    }
    
    private String[] splitTrim(String inputC)
    {
        List<String> split = Arrays.stream(inputC.trim().split("\\ "))
                .map(x -> x.trim()).collect(Collectors.toList());
        String[] rawArgs = split.toArray(new String[split.size()]);
        return rawArgs;
    }
    public String getDraftTopic()
    {
       return draftTopic;
    }
    public List getList()
    {
       return draftLines;
    }
}
