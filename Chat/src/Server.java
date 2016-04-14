import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.omg.Messaging.SyncScopeHelper;

@SuppressWarnings("unused")
/**
 * Server.
 * @author idan
 *
 */
public class Server {
	public static final int PORT = 1234;
	private ServerSocket serverSock;
	private  ArrayList<ServerThread> clientList;
	private boolean keepAlive;
	/**
	 * Open new server.
	 */
	public Server() {
		this(PORT);
	}
	/**
	 * Open new server.
	 * @param port.
	 */
	public Server(int nPort) {
		keepAlive=true;
		clientList = new ArrayList<ServerThread>();
		try {
			serverSock = new ServerSocket(PORT);
			System.out.println("Server is up and ready for connections...");
			while(keepAlive){
				Socket tmpSock = serverSock.accept();
				System.out.println("new try to connect: " + tmpSock.getPort());
				ServerThread t = new ServerThread(tmpSock);
				clientList.add(t);
				t.start();
			}
			serverSock.close();
		} catch(Exception e) {
			System.out.println("Unable to start/close the server: " + e);
		}
	}
	/**
	 * Send data to all clients.
	 * @param data.
	 */
	private synchronized void sendAll(Data data) {
		/*
		for(int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
			// try to write to the Client if it fails remove it from the list
			if(!ct.writeMsg(messageLf)) {
				al.remove(i);
				display("Disconnected Client " + ct.username + " removed from list.");
			}
		}
		 */
	}
	/**
	 * Remove client from the server.
	 * @param client.
	 */
	private synchronized void removeClient(String user) {
		//send to all
		for(int i = 0; i < clientList.size(); ++i) {
			ServerThread tmpClient = clientList.get(i);
			if(tmpClient.user.equals(user)) {
				clientList.remove(i);
				return;
			}
			//else .... send
		}
	}

	@Override
	public String toString() {
		return "server port: "+serverSock.getLocalPort();
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof Server && ((Server) o).serverSock.getLocalPort()==serverSock.getLocalPort())
			return true;
		return false;
	}
	/**
	 * server thread.
	 * @author idan
	 *
	 */
	public class ServerThread extends Thread {
		private String user;
		private ObjectInputStream input;
		private ObjectOutputStream output;
		private Socket socket;
		public ServerThread(Socket socket) {
			this.socket = socket;
			try
			{
				output = new ObjectOutputStream(socket.getOutputStream());
				input  = new ObjectInputStream(socket.getInputStream());
				user = ((Data)input.readObject()).getMsg(); //login will replace it.
				System.out.println("New user logged in: "+user);
			} catch (IOException e) {
				System.out.println("Exception creating new Input/output Streams: " + e);
				return;
			} catch (Exception e) {}
		}
		public void run(){
			boolean tStillAlive = true;
			Data data;
			while(tStillAlive) {
				try {
					data = (Data)input.readObject();
					switch (data.getType()) {
					case Data.WHOISIN:
						for (int i=0; i<clientList.size();i++)
							sendData(new Data(Data.WHOISIN,clientList.get(i).user));
						break;
					case Data.MESSAGE:
						String msg = user +": "+ data.getMsg();
						System.out.println(msg);
						for (int i=0;i<clientList.size();i++)
							clientList.get(i).sendData(new Data(Data.MESSAGE, msg));
						break;
					case Data.LOGOUT:
						for (int i=0;i<clientList.size();i++)
							clientList.get(i).sendData(new Data(Data.LOGOUT, data.getMsg()));
						tStillAlive = false;
						break;
					case Data.NEWUSER:
						for (int i=0;i<clientList.size();i++)
							clientList.get(i).sendData(new Data(Data.NEWUSER, data.getMsg()));
						break;
					}
				} catch (IOException | ClassNotFoundException e) {
					tStillAlive = false; 
					/* the logger will do it.
					try {
						for (int i=0; i<clientList.size();i++) 
							if (clientList.get(i).username.equals(username))
								clientList.remove(i);
						System.out.println("User logged off: "+username+ " ("+e.getMessage()+")");
						FileWriter logFile = new FileWriter("Log.txt", true);
						PrintWriter pw = new PrintWriter(logFile);
						pw.append(new Time()+ "  "+ e.getMessage() + "\n");
						pw.close();
						logFile.close();

					} catch (IOException e1) {}
					finally {
						tStillAlive = false;
					}
					 */
				}
			}
			//removeClient(user);
			close();
		}
		private void sendData(Data data) {
			try {
				output.writeObject(data);
			} catch (IOException e) {
				System.out.println("Error sending message to " + user);
				System.out.println(e.toString());
			}
		}
		private void close() {
			// try to close the connection
			try {
				if(output != null) output.close();
			} catch(Exception e) {}
			try {
				if(input != null) input.close();
			} catch(Exception e) {};
			try {
				if(socket != null) socket.close();
			} catch (Exception e) {}
		}
	}
}