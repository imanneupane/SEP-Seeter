/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seeter.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author owner
 */
public class FetchTopic implements RunUI
{
    private final Clientin client;

    
    public FetchTopic(Clientin client)
    {
        this.client = client;
    }
    
    /**
     * Runs the command that overrides the parent run method
     * Executes fetchTopic
     */
    @Override
    public void run()
    {
        try {
            client.fetchTopic();
        } catch (IOException ex) {
            Logger.getLogger(FetchTopic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FetchTopic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
