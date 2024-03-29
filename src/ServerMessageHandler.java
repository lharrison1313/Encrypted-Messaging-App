import javax.xml.crypto.Data;

public class ServerMessageHandler implements  Runnable{
    private Server server;
    private String senderName;

    public ServerMessageHandler(Server server, String userName){
        this.server = server;
        this.senderName = userName;


    }

    @Override
    public void run() {
        if(server != null){

            Object item;
            System.out.println("user " + senderName + " message handler is running");

            while(server.isUserOnline(senderName) && server.isServerOnline()){
                try{

                     item = server.getObject(senderName);
                     //if the object is a message relay the message to the recipient
                     if(item instanceof Message){
                         if(server.getUserPrivilege(senderName) != 0){
                             server.relayMessage((Message) item);
                         }
                     }
                     //if the object is a command execute the command
                     else if(item instanceof Command){
                         server.commandProcessor((Command) item, senderName);
                     }

                }
               catch(Exception e){
                    System.out.println(e);
                }

            }
            System.out.println("user " + senderName + " has left " + server.getServerName() );
        }
    }
}
