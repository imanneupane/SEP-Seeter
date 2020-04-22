/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sep.seeter.client;

/**
 *
 * @author il17aag
 */
public class Compose implements Command
{
    private final ClientControl client;
    /**
     * Constructor require receiver client 
     * @param client class called
     */
    public Compose(ClientControl client)
    {
        this.client = client;
    }
    
    /**
     * Runs the command that overrides the parent run method
     * Executes composeTopic
     */
    @Override
    public void execute()
    {
        client.composeTopic();       
    }
    
}
