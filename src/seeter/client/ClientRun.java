/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seeter.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author owner
 */
public class ClientRun 
{    
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
        
        String input = getInput(reader);
        Clientin client = new Clientin(args[0], args[1], Integer.parseInt(args[2]));
        UserInput inHandler = new UserInput(client);
        
        while(true)
        {
            String command = getInput(reader);
            if(command.contentEquals("quit"))break;
            inHandler.enterInput(command);
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        ClientRun clientRun = new ClientRun();
        clientRun.userOptions(args);
    }  
}
