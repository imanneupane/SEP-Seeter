package sep.seeter.client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author owner
 */
public enum State 
{

    /**
     *Set state to main
     */
    Main("Main State"),

    /**Set state to draft
     *
     */
    Draft("Drafting State");
    private String state;
    
    private State(String st)
    {
        state = st;
    }
    @Override
    public String toString()
    {
        return state;
    }
    
}
