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
public class AddSeets implements Command
{
    private final ClientControl client;
    
    /**
     *AddSeets constructors initialised with parameter client 
     * @param client client called
     */
    public AddSeets(ClientControl client)
    {
        this.client = client;
    }
    
    /**
     * Runs the command that overrides the parent run method
     * Executes addBody
     */
    @Override
    public void execute()
    {
        client.addBody();
    }
    
}
