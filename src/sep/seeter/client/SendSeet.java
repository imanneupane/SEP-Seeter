/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sep.seeter.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author owner
 */
public class SendSeet implements Command
{
    private final ClientControl client;
    
    public SendSeet(ClientControl client)
    {
        this.client = client;
    }
    
    /**
     * Runs the command that overrides the parent run method
     * Executes sendDraft
     */
    @Override
    public void run()
    {
        try {
            client.sendDraft();
        } catch (IOException ex) {
            Logger.getLogger(SendSeet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
