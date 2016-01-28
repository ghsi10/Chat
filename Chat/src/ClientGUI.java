
import java.awt.EventQueue;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
public class ClientGUI {

	//*********  Fields  *********//
	private JFrame frame;
	private JFrame frame_1;
	private JTextField chatInputArea;
	private JTextArea GC_textArea;
	private JPanel loginPane;
	private static JList<DefaultListModel<String>> usersJList;
	private static DefaultListModel<String> usersListModel = new DefaultListModel<String>();
	private String loginName, loginMessage;
	private JScrollPane usersPanel;
	private JButton btnSendText;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
					window.frame_1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public ClientGUI() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() throws Exception{
		//********* Initialize LoginFrame *********//
		frame = new JFrame();
		frame.setBounds(400, 400, 330, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//********* Get Client Name (LOGIN) *********//

		loginPane = new JPanel();
		loginPane.setBounds(0, 0, 0, 0);
		frame.getContentPane().add(loginPane);
		loginMessage = "Welcome! Please Provide a User name.";
		while(true){
			loginName = JOptionPane.showInternalInputDialog(loginPane, loginMessage, "Login", 0);
			if(!loginName.equals("")) // *******  add Conditions to login name
				break;
			loginMessage = "Error please provide a valid User Name";
		}
		usersListModel.addElement(loginName);
		frame.dispose();

		//********* Initialize MainFrame *********//
		frame_1 = new JFrame();
		frame_1.setBounds(100, 100, 652, 715);
		frame_1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_1.getContentPane().setLayout(null);
		frame_1.setVisible(true);

		//********* Initialize Chat Input *********//
		chatInputArea = new JTextField();
		chatInputArea.setBounds(151, 490, 380, 20);
		frame_1.getContentPane().add(chatInputArea);
		chatInputArea.setColumns(10);
		
		//********* Initialize Users List and Panel *********//
		usersPanel = new JScrollPane();
		usersPanel.setBounds(10, 29, 137, 480);
		frame_1.getContentPane().add(usersPanel);
		usersJList = new JList(usersListModel);
		usersPanel.setViewportView(usersJList);
		
		//************** buttons ****************//
		btnSendText = new JButton("Send Text");
		btnSendText.setBounds(535, 489, 95, 23);
		frame_1.getContentPane().add(btnSendText);
		
		//****** Chat Tabs **********//
		JTabbedPane ChatTabs = new JTabbedPane(JTabbedPane.TOP);
		ChatTabs.setBounds(151, 13, 475, 466);
		frame_1.getContentPane().add(ChatTabs);
				GC_textArea = new JTextArea();
				ChatTabs.addTab("Global Chat", null, GC_textArea, null);
				GC_textArea.setEditable(false);
		
		//*********** Event Actions ***********//
		btnSendText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GC_textArea.append("[" + (new Time()).hoursFormat() + "] " +loginName + ": " + chatInputArea.getText()+"\n");
				chatInputArea.setText("");
			}
		});
		chatInputArea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GC_textArea.append("[" + (new Time()).hoursFormat() + "] " +loginName + ": " + chatInputArea.getText()+"\n");
				chatInputArea.setText("");
			}
		});
	}
	public String getLoginName()
	{
		return loginName;
	}
	public void addToUsersModel(String name)
	{
		usersListModel.addElement(name);
	}
}
