/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seeter.client;

/**
 *
 * @author owner
 */
public class SendSeet implements RunUI
{
    private Clientin client;
    
    public SendSeet(Clientin client)
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
        client.sendDraft();
    }
    
}
