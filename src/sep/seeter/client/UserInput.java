/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sep.seeter.client;

import java.util.HashMap;

/**
 *
 * @author owner
 */
public class UserInput 
{
    /**
     * HashMap used to hold the user input.
     * [String -> Command]:
     * "compose" -> composeTopic
     * "fetch" -> fetchTopic
     * "body" -> addSeets
     * "send" -> sendTopic
     */
    private final HashMap<String, Command> commands;
    
    public UserInput(ClientControl client)
    {
        commands = new HashMap<>();
        //setting key to each of the command
        commands.put("compose", new Compose(client));
        commands.put("fetch", new FetchTopic(client));
        commands.put("body", new AddSeets(client));
        commands.put("send", new SendSeet(client));
    }
    
    /**
     *Checks if the user input is a command so it runs the command. 
     * @param cmd
     */
    public void enterInput(String cmd)
    {
        cmd = cmd.toLowerCase();
            if(commands.containsKey(cmd))
            {
                commands.get(cmd).run();
            }
            else
            {
                System.out.println("Sorry, Wrong Input command");
            }
        
    }

}
