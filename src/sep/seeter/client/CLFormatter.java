package sep.seeter.client;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import sep.seeter.net.channel.ClientChannel;
import sep.seeter.net.message.Message;

/**
 * A helper class for the current prototype {@link Client client}.  <i>E.g.</i>,
 * for formatting Command Line messages.
 */
public class CLFormatter 
{
  static ClientChannel chan;  // Client-side channel for talking to a Seeter server
  //private static final String RESOURCE_PATH = "resources/MessageBundle";
  //private ResourceBundle strings;
  
  public CLFormatter(String host, int port) 
  {
    this.chan = new ClientChannel(host, port);
  }
  /*
  public CLFormatter()
  {
    this(new Locale("en", "GB"));
  }
  public CLFormatter(Locale locale)
  {
    strings = ResourceBundle.getBundle(RESOURCE_PATH, locale);
  }
  */
  /* Interact with Seeter server */

  private void send(Message msg) throws IOException {
    this.chan.send(msg);
  }

  private Message receive() throws IOException, ClassNotFoundException {
    return this.chan.receive();
  }

  /* Following are the auxiliary methods for formatting the UI text */
/*
    public String formatSplash(String user) 
    {
        return "\n "+ strings.getString("greeting") + " " + user + "!\n"
            + strings.getString("msg_note")
            + strings.getString("msg_note1");
    }

    public String formatMainMenuPrompt() 
    {
        return "\n"+ strings.getString("msg_note2")
            + "\n> ";
    }

    public String formatDraftingMenuPrompt(String topic,
      List<String> lines) 
    {
        return "\n " + strings.getString("msg_draft") + formatDrafting(topic, lines)
            + "\n" + strings.getString("msg_note3")
            + "\n> ";
    }
*/
  static String formatDrafting(String topic, List<String> lines) {
    StringBuilder b = new StringBuilder("#");
    b.append(topic);
    int i = 1;
    for (String x : lines) {
      b.append("\n");
      b.append(String.format("%12d", i++));
      b.append("  ");
      b.append(x);
    };
    return b.toString();
  }

  static String formatFetched(String topic, List<String> users,
      List<String> fetched) {
    StringBuilder b = new StringBuilder("Fetched: #");
    b.append(topic);
    Iterator<String> it = fetched.iterator();
    for (String user : users) {
      b.append("\n");
      b.append(String.format("%12s", user));
      b.append("  ");
      b.append(it.next());
    };
    b.append("\n");
    return b.toString();
  }
}
