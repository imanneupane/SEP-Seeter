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
 *This class is the receiver class
 * @author il17aag
 */
public class ClientControl 
{
    private final String user;
    private final String host;
    private final int port;
    String draftTopic = null;
    List<String> draftLines = new LinkedList<>();   
    private String command;
    private String[] rawArgs;
    
    /**
     *Constructors require name of user, host and port no
     * @param user username 
     * @param host localhost
     * @param port port number
     */
    public ClientControl(String user, String host, int port)
    {
        this.user = user;
        this.host = host;
        this.port = port;
    }
    
    /**
     *  composes a topic as user input
     *  rawargs holds the topic user inputs
     */
    public void composeTopic()
    {
        draftTopic = rawArgs[0];
    }
    
    /**
     * fetch the topic from the server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void fetchTopic() throws IOException, ClassNotFoundException
    {
        CLFormatter helper = new CLFormatter(this.host, this.port);
        helper.chan.send(new SeetsReq(rawArgs[0]));
        SeetsReply rep = (SeetsReply) helper.chan.receive();
        System.out.print(helper.formatFetched(rawArgs[0], rep.users, rep.lines));
    }
    
    /**
     * add line to inside the composed topic 
     */
    public void addBody()
    {
        String line = Arrays.stream(rawArgs).collect(Collectors.joining());
        if (line.isEmpty()){}
        else
        {
            draftLines.add(line);
        } 
    }
    
    /**
     * sends the composed topic draft to the server
     * clears the current draft topic and line
     * 
     * @throws IOException
     */
    public void sendDraft() throws IOException
    {
        CLFormatter helper = new CLFormatter(this.host, this.port);
        helper.chan.send(new Publish(user, draftTopic, draftLines));
        draftTopic = null;
        draftLines.clear();
    }
    
    /**
     * Gets user input and sets them in an array
     * @param inputC user input 
     */
    public void setCommand(String inputC)
    {
        List<String> split = Arrays.stream(inputC.trim().split("\\ "))
                    .map(x -> x.trim()).collect(Collectors.toList());
        command = split.remove(0);
        rawArgs = split.toArray(new String[split.size()]);
    }

    /**
     * provides command input to the user
     * @return
     */
    public String passCmd()
    {
        return command;
    }

    /**
     *provides draft topic
     * @return
     */
    public String getDraftTopic()
    {
       return draftTopic;
    }

    /**
     * provides the list inside the draft topic
     * @return
     */
    public List getList()
    {
       return draftLines;
    }
}
