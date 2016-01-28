import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static final int PORT = 1234;
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		//ClientGUI gui = new ClientGUI();
		Socket socket = new Socket("localhost", PORT);
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		output.writeObject(new Data(Data.NEWUSER,"ghsi"));
		Data data;
		Scanner sc = new Scanner(System.in);
		while (true) {
			output.writeObject(new Data(Data.MESSAGE,sc.nextLine()));
			data = (Data)input.readObject();
			switch(data.getType())
			{
			case Data.MESSAGE:
				System.out.println(data.getMsg());
				break;
			}
		}
	}
}