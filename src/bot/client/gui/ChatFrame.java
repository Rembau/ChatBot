//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\gui\\ChatFrame.java

package bot.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.Calendar;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import bot.client.communication.Communication;
import bot.client.event.ActionMouseKeyForCharFrame;
import bot.client.people.People;

public class ChatFrame extends JApplet {
	private static final long serialVersionUID = 1L;
	private Communication communication;
	private JScrollPane scrollpane;
	private JScrollPane scrollinput;
	private JTextPane textarea;
	private JTextPane textfield;
	private JButton button;
	private JSplitPane panel_bot_people;
	private JPanel panel_bot = new JPanel();
	private JPanel panel_people = new JPanel();
	private People people = new People();

	private JButton button_train;
	private ActionMouseKeyForCharFrame amk = new ActionMouseKeyForCharFrame(
			this);

	private int init_chat_input_splitpane_location = 350;
	private int init_frame_width = 750;
	private int init_frame_height = 500;
	private int init_char_people_splitpane_location = 550;

	public ChatFrame() {
		super();
		// panel_people.setLayout(new GridLayout(0,1));

		textarea = new JTextPane();
		textarea.setEditable(false);

		scrollpane = new JScrollPane();
		scrollpane.getViewport().add(textarea);
		textfield = new JTextPane();
		textfield.addKeyListener(amk);

		scrollinput = new JScrollPane();
		scrollinput.getViewport().add(textfield);

		JSplitPane char_split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		char_split.setDividerSize(3);
		char_split.add(scrollpane);
		char_split.add(scrollinput);
		char_split.setDividerLocation(init_chat_input_splitpane_location);
		// textarea.setPreferredSize(new Dimension(800,400));
		// textfield.setPreferredSize(new Dimension(700, 100));

		button = new JButton("发送");
		button.addActionListener(amk);
		JPanel control = new JPanel();
		control.setLayout(new BorderLayout());
		control.add(button, BorderLayout.EAST);

		// Box panel_char= Box.createVerticalBox();
		JPanel panel_char = new JPanel();
		panel_char.setLayout(new BorderLayout());
		panel_char.add(char_split, BorderLayout.CENTER);
		panel_char.add(control, BorderLayout.SOUTH);

		panel_bot_people = createShowBotPanel();

		JSplitPane char_people = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				true);
		char_people.add(panel_char);
		char_people.add(panel_bot_people);
		char_people.setDividerSize(3);
		char_people.setDividerLocation(init_char_people_splitpane_location);
		Container content = this.getContentPane();
		content.add(char_people);

		// this.setTitle("机器人聊天框");
		setBounds(300, 100, 800, 600);
		repaintPanel_people();
		this.setVisible(true);
		initBot();
	}

	public void initBot() {
		
		JTextPane label = null;
		try {
			label = new JTextPane();
			//label.setEditable(false);
			
			JScrollPane js = new JScrollPane();
			js.getViewport().add(label);
			//label.setText("正在连接机器人。。。");
			panel_bot.setLayout(new BorderLayout());
			panel_bot.add(js,BorderLayout.NORTH);

			initCommunication();
			String point = 
					  "1：在输入字符串前面加上’%’，可以进行搜索。\n"
					+ "2：输入teacher进入训练模式（须先登录）。\n" 
					+ "3：输入end退出训练模式。";
			label.setText(point);
		} catch (Exception e) {
			label.setText("连接失败");
			JOptionPane.showMessageDialog(null, "连接服务器失败！");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 提示
	 * 
	 * @param message
	 */
	public void prompt(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	/**
	 * 初始化通信模块
	 * 
	 * @throws java.lang.Exception
	 */
	public void initCommunication() throws Exception {
		communication = new Communication(this);
		communication.start();
	}

	/**
	 * @return javax.swing.JPanel
	 */
	public JSplitPane createShowBotPanel() {
		JSplitPane panel_showBot = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
		panel_showBot.setDividerSize(3);
		panel_showBot.add(panel_people);
		panel_showBot.add(panel_bot);
		return panel_showBot;
	}

	public void repaintPanel_people() {
		panel_people.removeAll();
		if (people.getId() == 1) {
			JLabel label_1 = new JLabel("用户");
			JLabel label_2 = new JLabel(people.getName());
			JLabel label_3 = new JLabel(people.getStatus());
			button_train = new JButton("开始训练");
			button_train.addActionListener(amk);
			panel_people.add(label_1);
			panel_people.add(label_2);
			panel_people.add(label_3);
			panel_people.add(button_train);
			this.validate();
		} else {
			JLabel label_1 = new JLabel("游客");
			JButton button_login = new JButton("登录");
			button_login.addActionListener(amk);
			panel_people.setLayout(new BorderLayout());
			panel_people.add(label_1, BorderLayout.CENTER);
			panel_people.add(button_login, BorderLayout.EAST);
			this.validate();
		}
	}

	/**
	 * 添加消息到聊天框中
	 * 
	 * @param message
	 *            :要添加的内容
	 * @param message
	 */
	public void addChatContent(String message) {
		addChatContent("me", message);
	}

	/**
	 * @param name
	 * @param message
	 */
	public void addChatContent(String name, String message) {
		Calendar ca = Calendar.getInstance();
		int h = ca.get(Calendar.HOUR_OF_DAY);
		int m = ca.get(Calendar.MINUTE);
		int s = ca.get(Calendar.SECOND);
		addPerson(name + "   " + h + ":" + m + ":" + s + "\n");
		addContent("" + message + "\n");
		textarea.setCaretPosition(textarea.getDocument().getLength());
	}

	/**
	 * @param content
	 */
	public void addContent(String content) {
		addContent(content, Color.black);
	}

	/**
	 * @param content
	 * @param color
	 */
	public void addContent(String content, Color color) {
		SimpleAttributeSet attrset = new SimpleAttributeSet();
		StyleConstants.setFontSize(attrset, 14);
		StyleConstants.setAlignment(attrset, StyleConstants.ALIGN_LEFT);
		StyleConstants.setLeftIndent(attrset, 20);
		insert(content, attrset);
	}

	/**
	 * @param name
	 */
	public void addPerson(String name) {
		SimpleAttributeSet attrset = new SimpleAttributeSet();
		StyleConstants.setForeground(attrset, Color.blue);
		StyleConstants.setFontSize(attrset, 12);
		insert(name, attrset);
	}

	/**
	 * @param str
	 * @param attrset
	 */
	public void insert(String str, AttributeSet attrset) {
		Document docs = textarea.getDocument();
		try {
			docs.insertString(docs.getLength(), str, attrset);
		} catch (BadLocationException ble) {
			System.out.println("BadLocationException:" + ble);
		}
	}

	/**
	 * 获取输入框中的字符
	 * 
	 * @return:字符
	 */
	public String getSendingMessage() {
		return textfield.getText();
	}

	/**
	 * 清空输入框
	 * 
	 */
	public void setTextFieldEmpty() {
		textfield.setText("");
	}

	public JButton getButton_train() {
		return button_train;
	}
	/**
	 * @return client.communication.Communication
	 */
	public Communication getCommunication() {
		return this.communication;
	}

	/**
	 * @return client.people.People
	 */
	public People getPeople() {
		return this.people;
	}

	/**
	 * @return javax.swing.JButton
	 */
	public JButton getSendButton() {
		return this.button;
	}
	public void init() {

	}
	public void destroy() {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Chat bot");
		ChatFrame cf = new ChatFrame();
		frame.add(cf);
		frame.setVisible(true);
		frame.setBounds(200, 100, cf.init_frame_width, cf.init_frame_height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
