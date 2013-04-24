//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\gui\\ChatFrame.java

package bot.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
import bot.client.event.ActionForWindow_control;
import bot.client.event.ActionForWindow_title;
import bot.client.event.ActionMouseKeyForCharFrame;
import bot.client.people.People;
import bot.comm.Context;

public class ChatFrame extends JApplet {
	private static final long serialVersionUID = 1L;
	private Communication communication;
	private JScrollPane scrollpane;
	private JScrollPane scrollinput;
	private JTextPane textarea;
	private JTextPane textfield;
	private JButton button;
	
	private JSplitPane panel_bot_people;
	private JSplitPane char_split;
	private JPanel char_people;
	
	private JPanel panel_bot = new JPanel();
	private JPanel panel_people = new JPanel();
	private People people = new People();

	private JButton button_train;
	private ActionMouseKeyForCharFrame amk = new ActionMouseKeyForCharFrame(
			this);

	private double init_chat_input_splitpane_location = 0.7;
	private int init_frame_width = 750;
	private int init_frame_height = 500;
	
	private Dimension default_prompt_size = new Dimension(216,100);

	public ChatFrame() {
		super();
		// panel_people.setLayout(new GridLayout(0,1));

		textarea = new JTextPane();
		textarea.setEditable(false);
		textarea.setOpaque(false);
		scrollpane = new JScrollPane();
		scrollpane.getViewport().add(textarea);
		scrollpane.getViewport().setOpaque(false);
		scrollpane.setBorder(null);
		scrollpane.setOpaque(false);
		
		textfield = new JTextPane();
		textfield.addKeyListener(amk);
		textfield.setOpaque(false);
		scrollinput = new JScrollPane();
		scrollinput.getViewport().add(textfield);
		scrollinput.getViewport().setOpaque(false);
		scrollinput.setBorder(null);
		scrollinput.setOpaque(false);
		
		JPanel up_input = new JPanel();
		up_input.setOpaque(false);
		
		JPanel input_parent = new JPanel();
		input_parent.setBorder(null);
		input_parent.setOpaque(false);
		input_parent.setLayout(new BorderLayout());
		input_parent.add(up_input,BorderLayout.NORTH);
		input_parent.add(scrollinput,BorderLayout.CENTER);

		char_split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		char_split.setBorder(null);
		char_split.setOpaque(false);
		char_split.setDividerSize(3);
		char_split.add(scrollpane);
		char_split.add(input_parent);
		char_split.setDividerLocation(0.6);

		button = new JButton("发送");
		button.addActionListener(amk);
		
		JPanel control = new JPanel();
		control.setOpaque(false);
		control.setLayout(new BorderLayout());
		control.add(button, BorderLayout.EAST);

		JPanel panel_char = new JPanel(){
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g) {
			     super.paintComponent(g);
			     Image image = Toolkit.getDefaultToolkit().createImage(Context.image_path+"back.jpg");
			     int x = this.getWidth();
			     int y = this.getHeight();
			     ImageIcon img = new ImageIcon(image.getScaledInstance(x, y, Image.SCALE_DEFAULT));
			     g.drawImage(img.getImage(),0,0,null);
			 }
		};
		panel_char.setLayout(new BorderLayout());
		panel_char.add(char_split, BorderLayout.CENTER);
		panel_char.add(control, BorderLayout.SOUTH);

		panel_bot_people = createShowBotPanel();

		char_people = new JPanel();
		char_people.setLayout(new BorderLayout());
		char_people.add(panel_char,BorderLayout.CENTER);
		char_people.add(panel_bot_people,BorderLayout.EAST);
		Container content = this.getContentPane();
		content.add(char_people);
		// this.setTitle("机器人聊天框");
		repaintPanel_people();
		this.setVisible(true);
		initBot();
	}

	public void initBot() {
		
		JTextPane label = null;
		label = new JTextPane();
		label.setEditable(false);
		label.setPreferredSize(default_prompt_size);
		
		JScrollPane js = new JScrollPane();
		js.getViewport().add(label);
		label.setText("正在连接机器人。。。");
		panel_bot.setLayout(new BorderLayout());
		panel_bot.add(js,BorderLayout.NORTH);
		initCommunication(label);
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
	public void initCommunication(JTextPane label) {
		communication = new Communication(this,label);
		communication.start();
	}

	/**
	 * @return javax.swing.JPanel
	 */
	public JSplitPane createShowBotPanel() {
		JSplitPane panel_showBot = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
		panel_showBot.setDividerSize(3);
		panel_showBot.add(panel_people,JSplitPane.TOP);
		panel_showBot.add(panel_bot,JSplitPane.BOTTOM);
		//panel_showBot.setSize(200, 1000);
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
	public static void main(String[] args) {
		//JFrame frame = new JFrame("Chat bot");
		//ChatFrame cf = new ChatFrame();
		//frame.add(cf);
		//frame.setVisible(true);
		//frame.setBounds(200, 100, cf.init_frame_width, cf.init_frame_height);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ActionForWindow_control awc = new ActionForWindow_control();
		JButton min = new JButton(Context.window_control_min);
		min.setOpaque(false);
		min.addActionListener(awc);
		JButton max = new JButton(Context.window_control_max);
		max.setOpaque(false);
		max.addActionListener(awc);
		JButton close = new JButton(Context.window_control_close);
		close.setOpaque(false);
		close.addActionListener(awc);
		
		JPanel window_title = new JPanel();
		ActionForWindow_title awt = new ActionForWindow_title();
		window_title.addMouseListener(awt);
		window_title.addMouseMotionListener(awt);
		window_title.setPreferredSize(new Dimension(200,50));
		window_title.setOpaque(false);
		
		JPanel window_control = new JPanel();
		window_control.setOpaque(false);
		window_control.setLayout(new GridLayout(0,3));
		window_control.add(min);
		window_control.add(max);
		window_control.add(close);
		
		JPanel window_control_parent = new JPanel();
		window_control_parent.setLayout(new BorderLayout());
		window_control_parent.add(window_control,BorderLayout.NORTH);
		window_control_parent.setOpaque(false);
		
		JPanel window_top = new JPanel(){
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g) {
			     super.paintComponent(g);
			     Image image = Toolkit.getDefaultToolkit().createImage(Context.image_path+"title.jpg");
			     int x = this.getWidth();
			     int y = this.getHeight();
			     ImageIcon img = new ImageIcon(image.getScaledInstance(x, y, Image.SCALE_DEFAULT));
			     g.drawImage(img.getImage(),0,0,null);
			 }
		};
		window_top.setLayout(new BorderLayout());
		window_top.add(window_title,BorderLayout.CENTER);
		window_top.add(window_control_parent,BorderLayout.EAST);
		
		ChatFrame cf = new ChatFrame();
		
		JFrame window = new JFrame("Chat bot");
		window.setUndecorated(true);
		
		JPanel all = new JPanel();
		all.setLayout(new BorderLayout());
		all.add(window_top,BorderLayout.NORTH);
		all.add(cf,BorderLayout.CENTER);
		all.setBorder(BorderFactory.createLineBorder(Color.black));
		
		Container content = window.getContentPane();
		content.add(all);
		window.setBounds(200, 100, cf.init_frame_width, cf.init_frame_height);
		window.setVisible(true);
		cf.getChar_split().setDividerLocation(cf.init_chat_input_splitpane_location);
	}
	public JSplitPane getPanel_bot_people() {
		return panel_bot_people;
	}

	public void setPanel_bot_people(JSplitPane panel_bot_people) {
		this.panel_bot_people = panel_bot_people;
	}

	public JSplitPane getChar_split() {
		return char_split;
	}

	public void setChar_split(JSplitPane char_split) {
		this.char_split = char_split;
	}
}
