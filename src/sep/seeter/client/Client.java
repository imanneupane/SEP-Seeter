package sep.seeter.client;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    
    private String getInput(BufferedReader reader) throws IOException
    {
        String input = reader.readLine();
        if (input == null)
        {
            throw new IOException("Input Stream closed while reading");
        }
        return input.toLowerCase();
    }
    
    private void userOptions(String user, String host, int port)throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ClientControl client = new ClientControl(user, host, port);
        CLFormatter helper = new CLFormatter(host, port);
        UserInput inHandler = new UserInput(client);
        System.out.print(CLFormatter.formatSplash(user));
        try{
        while(true)
        {
            if (state == State.Main)
            {
                System.out.print(CLFormatter.formatMainMenuPrompt());
            }
            else
            {
                System.out.print(CLFormatter.formatDraftingMenuPrompt(client.getDraftTopic(), client.getList()));
            }
            String cmdIn = getInput(reader);
            client.setCommand(cmdIn);
            String command = client.passCmd();
            if(command.contentEquals("exit"))break;
            if(command.contentEquals("compose"))
            {
                state = State.Draft;
            }
            else if(command.contentEquals("send"))
            {
                state = State.Main;
            }
            inHandler.enterInput(command);
        }
        }
        catch (RuntimeException ex) 
        {
            throw ex;
        }
        catch (Exception e)
        {
            System.out.print("Error found" + e); 
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
    
    public static void main(String[] args) throws IOException
    {
        Client clientRun = new Client();
        clientRun.userOptions(args[0], args[1], Integer.parseInt(args[2]));
    }
    
/*
  private String user;
  private String host;
  private int port;

  public Client(String user, String host, int port) 
  {
      this.user = user;
      this.host = host;
      this.port = port;
  }

  public static void main(String[] args) throws IOException 
  {
    Client client = new Client(args[0], args[1], Integer.parseInt(args[2]));
    client.run();
  }
  
  // Run the client
  @SuppressFBWarnings(
      value = "DM_DEFAULT_ENCODING",
      justification = "When reading console, ignore default encoding warning")
          
  private void run() throws IOException 
  {

    BufferedReader reader = null;
    CLFormatter helper = null;
    try 
    {
      reader = new BufferedReader(new InputStreamReader(System.in));

      if (this.user.isEmpty() || this.host.isEmpty()) 
      {
        System.err.println("User/host has not been set.");
      }
      helper = new CLFormatter(this.host, this.port);
      System.out.print(helper.formatSplash(this.user));
      processUI(helper, reader);
    } 
        
    catch (RuntimeException ex) 
    {
      throw ex;
    } 
    catch (Exception e)
    {
      System.out.print("Error found" + e); 
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
  
  // Read a line of user input
  private String readUI(BufferedReader reader) throws IOException
  {
     String raw = reader.readLine();
      if (raw == null) 
      {
        throw new IOException("Input stream closed while reading.");
      } 
      return raw;
  }
  
  private void processUI(CLFormatter helper, BufferedReader reader) throws IOException, ClassNotFoundException
  {
      
      String draftTopic = null;
      List<String> draftLines = new LinkedList<>();
      boolean done = false;
      State state = State.Main;
      
      while (done != true)
      {
        if (state == State.Main)
        {
           System.out.print(CLFormatter.formatMainMenuPrompt());
        }
        else
        {
           System.out.print(CLFormatter.
            formatDraftingMenuPrompt(draftTopic, draftLines));
        }
        
        String raw = readUI(reader);
              //CLFormatter helper = null;
        List<String> split = Arrays.stream(raw.trim().split("\\ "))
          .map(x -> x.trim()).collect(Collectors.toList());
        String cmd = split.remove(0);  // First word is the command keyword
        String[] rawArgs = split.toArray(new String[split.size()]);

        switch(cmd)
        {
            case "exit":
                done = true;
                break;
            case "compose":
                state = State.Draft;
                draftTopic = rawArgs[0];
                break;
            case "fetch":
                // Fetch seets from server
                helper.chan.send(new SeetsReq(rawArgs[0]));
                SeetsReply rep = (SeetsReply) helper.chan.receive();
                System.out.print(
                helper.formatFetched(rawArgs[0], rep.users, rep.lines));
                break; 
            case "body":
                // Add a seet body line
                String line = Arrays.stream(rawArgs).
                collect(Collectors.joining());
                draftLines.add(line);
                break;
            case "send":
                // Send drafted seets to the server, and go back to "Main" state
                helper.chan.send(new Publish(user, draftTopic, draftLines));
                state = State.Main;
                draftTopic = null;
                break;
            default:
                System.out.print(helper.formatSplash(this.user));
                
        }
      }
  } 
 */   
}
