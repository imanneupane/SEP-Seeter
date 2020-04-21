/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sep.seeter.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import sep.seeter.net.message.Publish;
import sep.seeter.net.message.SeetsReply;
import sep.seeter.net.message.SeetsReq;

/**
 *
 * @author owner
 */
public class ClientControl 
{
    private final String user;
    private String host;
    private int port;
    String draftTopic = null;
    List<String> draftLines = new LinkedList<>();   
    CLFormatter helper = new CLFormatter(this.host, this.port);
    private String command;
    private String[] rawArgs;
    
    public ClientControl(String user, String host, int port)
    {
        this.user = user;
        this.host = host;
        this.port = port;
    }
    
    public void composeTopic()throws ArrayIndexOutOfBoundsException
    {
        if (rawArgs.length == 0)
        {
            System.out.println("Should not be empty");
        }
        draftTopic = rawArgs[0];
        System.out.println(user + " has composed a topic.");
    }
    
    public void fetchTopic() throws IOException, ClassNotFoundException
    {
        helper.chan.send(new SeetsReq(rawArgs[0]));
        SeetsReply rep = (SeetsReply) helper.chan.receive();
        System.out.print(helper.formatFetched(rawArgs[0], rep.users, rep.lines));
    }
    
    public void addBody()
    {
        String line = Arrays.stream(rawArgs).collect(Collectors.joining());
        draftLines.add(line);
        System.out.println(user + " has added seet under the topic"); 
    }
    
    public void sendDraft() throws IOException
    {
        helper.chan.send(new Publish(user, draftTopic, draftLines));
        draftTopic = null;
        System.out.println(user + " has send the seets.");
    }
    
    public void setCommand(String inputC)
    {
        List<String> split = Arrays.stream(inputC.trim().split("\\ "))
                    .map(x -> x.trim()).collect(Collectors.toList());
        command = split.remove(0);
        rawArgs = split.toArray(new String[split.size()]);
    }
    public String passCmd()
    {
        return command;
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
