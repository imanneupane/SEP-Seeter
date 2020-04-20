/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seeter.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author owner
 */
public class ClientRun 
{    
    private State state = State.Main;
    
    private String getInput(BufferedReader reader) throws IOException
    {
        String input = reader.readLine();
        if (input == null)
        {
            throw new IOException("Input Stream closed while reading");
        }
        return input.toLowerCase();
    }
    
    private void userOptions(String[] args)throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        Clientin client = new Clientin(args[0], args[1], Integer.parseInt(args[2]));
        UserInput inHandler = new UserInput(client);
        
        while(true)
        {
            if (state == State.Main)
            {
                System.out.print(CLFormatter.formatMainMenuPrompt());
            }
            else
            {
                System.out.print(CLFormatter.formatDraftingMenuPrompt(client.getDraftTopic(), client.getList()));
            }
            String cmdIn = getInput(reader);
            client.setCommand(cmdIn);
            String command = client.passCmd();
            System.out.println(command);
            if(command.contentEquals("exit"))break;
            if(command.contentEquals("compose"))
            {
                state = State.Draft;
            }
            else if(command.contentEquals("send"))
            {
                state = State.Main;
            }
            inHandler.enterInput(command);
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        ClientRun clientRun = new ClientRun();
        clientRun.userOptions(args);
    }  
}
