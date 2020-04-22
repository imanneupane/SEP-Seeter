package sep.seeter.client;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import sep.seeter.net.message.Bye;

/**
 * This class is an initial work-in-progress prototype for a command line
 * Seeter client. It has been rather hastily hacked together, as often the case
 * with early exploratory coding, and it is incomplete and buggy. However, it
 * does compile and run, and basic functionality, such as sending and fetching
 * seets to and from an instance of {@link sep.seeter.server.Server}, is
 * working. Try it!
 * <p>
 * The arguments required to run the client correspond to the
 * {@link #set(String, String, int)} method: a user name, and the host name and
 * port at which the server as listening.
 * <p>
 * You can compile and run this client using NetBeans (<i>e.g.</i>, Run
 * {@literal ->} Set Project Configuration {@literal ->} Customize...
 * {@literal ->} New...).
 * <p>
 * Assuming you have compiled using NetBeans (or another method), you can also
 * run Client from the command line.
 * <ul>
 * <li>
 * {@code C:\...\seeter>  java -cp build\classes Client userid localhost 8888}
 * </ul>
 * <p>
 * You will be significantly reworking and extending this client. Unlike the
 * base framework, you have mostly free reign to modify the client to meet the
 * specification as you see fit. (The base framework comprises the packages
 * {@link sep.seeter.server}, {@link sep.seeter.server}, {@link sep.seeter.server}
 * and {@link sep.seeter.server}, which you should not modify.) The only
 * constraints on your client are as follows.
 * <ul>
 * <li>
 * This class (<i>i.e.</i>, {@code Client}) must remain the main class for
 * running your client (<i>i.e.</i>, via the static
 * {@linkplain #main(String[]) main} method).
 * <li>
 * The {@linkplain Client#main(String[]) main} method must accept the same
 * arguments as currently, <i>i.e.</i>, user name, host name and port number.
 * <li>
 * Your client must continue to accept the specified commands on the standard
 * input stream ({@code System.in}), and output on the standard output stream
 * ({@code System.out}).
 * </ul>
 * <p>
 * You will likely find it helpful to generate the API documentation (if you're
 * not already reading this there!) for the provided classes other than this
 * class, which are stable. After importing the project into NetBeans, simply
 * right click the project in the Projects window and select Generate Javadoc.
 * By default, the output is written to the {@code dist/javadoc} directory.
 *
 * @see CLFormatter
 */
public class Client 
{
    private State state = State.Main;
    private static final String RESOURCE_PATH = "resources/MessageBundle";
    private final ResourceBundle strings;

    /**
     *Constructor initialises locale method 
     */
    public Client()
    {
        this(new Locale("en", "GB"));
    }

    /**
     * Constructor require locale 
     * @param locale object for internationalisation
     */
    public Client(Locale locale)
    {
        strings = ResourceBundle.getBundle(RESOURCE_PATH, locale);
    }
    
    private String getInput(BufferedReader reader) throws IOException
    {
        String input = reader.readLine();
        if (input == null)
        {
            throw new IOException(strings.getString("msg_error1"));
        }
        return input;
    }
    
    private void userOptions(String user, String host, int port)throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ClientControl client = new ClientControl(user, host, port);
        CLFormatter helper = new CLFormatter(host, port);
        UserInput inHandler = new UserInput(client);
        System.out.print(formatSplash(user));
        try{
        while(true)
        {
            if (state == State.Main)
            {
                System.out.print(formatMainMenuPrompt());
            }
            else
            {
                System.out.print(formatDraftingMenuPrompt(client.getDraftTopic(), client.getList()));
            }
            String cmdIn = getInput(reader);
            client.setCommand(cmdIn.toLowerCase(Locale.ENGLISH));
            String command = client.passCmd();
            if(command.contentEquals("exit"))break;
            if(command.contentEquals("compose"))
            {
                state = State.Draft;
                System.out.println(user + strings.getString("msg_message"));
            }
            else if(command.contentEquals("send"))
            {
                state = State.Main;
            }
            
            try
            {
                inHandler.enterInput(command);
            }
            catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e)
            {
                if (command.contentEquals("compose"))
                {
                    System.out.println(strings.getString("msg_composeError"));
                    state = State.Main;
                }
                else if(command.contentEquals("send"))
                {
                    System.out.println(strings.getString("msg_sendError"));
                    state = State.Draft;
                }
            }
        }
        }
        catch (RuntimeException ex) 
        {
            throw ex;
        }
        catch (Exception e)
        {
            System.out.print(strings.getString("msg_error") + e); 
        }
    
        finally 
        {
            if (helper.chan.isOpen()) 
            {
                // If the channel is open, send Bye and close
                helper.chan.send(new Bye());
                helper.chan.close();
            }       
        }
    
    }
    
    /**
     *Main method
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        Client clientRun = new Client();
        clientRun.userOptions(args[0], args[1], Integer.parseInt(args[2]));
    }
    
    private String formatSplash(String user) 
    {
        return "\n "+ strings.getString("greeting") + " " + user + "!\n"
            + strings.getString("msg_note")
            + strings.getString("msg_note1");
    }

    private String formatMainMenuPrompt() 
    {
        return "\n"+ strings.getString("msg_note2")
            + "\n> ";
    }

    private String formatDraftingMenuPrompt(String topic,
      List<String> lines) 
    {
        return "\n " + strings.getString("msg_draft") + CLFormatter.formatDrafting(topic, lines)
            + "\n" + strings.getString("msg_note3")
            + "\n> ";
    }
    
}
