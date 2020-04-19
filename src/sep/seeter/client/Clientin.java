/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sep.seeter.client;

/**
 *
 * @author owner
 */
public class Clientin 
{
    private String user;
    private String host;
    private int port;
    
    
    public Clientin(String user, String host, int port)
    {
        this.user = user;
        this.host = host;
        this.port = port;
    }
    
    public void composeTopic()
    {
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
