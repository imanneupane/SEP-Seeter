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
public class Compose implements RunUI
{
    private Clientin client;
    
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
        client.composeTopic();
    }
    
}
