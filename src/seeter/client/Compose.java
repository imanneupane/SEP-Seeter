/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seeter.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author owner
 */
public class Compose implements RunUI
{
    private Clientin client;
    private String userI;
    /**
     * Constructor
     * @param client which is set to this client
     */
    public Compose(Clientin client)
    {
        this.client = client;
    }
    
    /**
     * Runs the command that overrides the parent run method
     * Executes composeTopic
     */
    @Override
    public void run()
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try 
        {
            userI = reader.readLine();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Compose.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        client.composeTopic(userI);       
    }
    
}