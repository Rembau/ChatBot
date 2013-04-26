//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\gui\\ChatFrame.java

package bot.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.apache.log4j.Logger;

import com.sun.awt.AWTUtilities;

import bot.client.communication.Communication;
import bot.client.event.ActionForWindow_title;
import bot.client.event.ActionMouseKeyForCharFrame;
import bot.client.event.Button_effect;
import bot.client.people.People;
import bot.comm.Context;

/**
 * 
 * 
 *
 */
public class ChatFrame extends JApplet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ChatFrame.class);
	
	private Communication communication;
	private JScrollPane scrollpane;		//显示内容滚动面板
	private JScrollPane scrollinput;	//输入框滚动面板
	private JTextPane textarea;			//显示内容文字面板
	private JTextPane textfield;		//输入内容文字面板
	private JButton button;				//输入框下面 发送按钮
	
	private JSplitPane panel_bot_people;	//右边 显示用户和机器人分割面板
	private JSplitPane char_split;			//左边显示内容和输入框的分割面板
	private JPanel char_people;				//panel_bot_people和char_split的父面板
	
	private JPanel panel_bot = new JPanel();	//显示提示和机器人
	private JPanel panel_people = new JPanel();	//显示登陆用户信息和命令
	private People people = new People();		

	private JButton button_train;				//开始结束训练按钮
	private ActionMouseKeyForCharFrame amk = new ActionMouseKeyForCharFrame(
			this);

	private double init_chat_input_splitpane_location = 0.7;
	private int init_frame_width = 750;
	private int init_frame_height = 500;
	
	private Dimension default_prompt_size = new Dimension(216,100);
	
	private boolean load_complete = false;
	
	private FontType attrset = new FontType();
	private FontType attrset_bot = new FontType();
	private FontType attrset_chat_content = new FontType("people");
	
	private static ChatFrame cf = null;

	public FontType getAttrset() {
		return attrset;
	}

	public void setAttrset(FontType attrset) {
		this.attrset = attrset;
	}
	public static ChatFrame instance(){
		if(cf==null){
			cf = new ChatFrame();
			cf.initBot();
		}
		return cf;
	}
	public ChatFrame() {
		super();
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
		JPanel up_input = new FontSetPanel();	//输入框上面的一个横条面板
		
		JPanel input_parent = new JPanel();	//输入框的父面板
		input_parent.setBorder(null);
		input_parent.setOpaque(false);
		input_parent.setLayout(new BorderLayout());
		input_parent.add(up_input,BorderLayout.NORTH);
		input_parent.add(scrollinput,BorderLayout.CENTER);

		char_split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		BasicSplitPaneUI bspu = new BasicSplitPaneUI(){
			 public void paint(Graphics g, JComponent c) {
				 super.paint(g, c);		   
			 }
			 public BasicSplitPaneDivider createDefaultDivider() {
				 return new BasicSplitPaneDivider(this){
					 private static final long serialVersionUID = 1L;
					 public void paint(Graphics g){
							
					 }
				 };
			  }
			
		};
		char_split.setUI(bspu);
		
		char_split.setBorder(null);
		char_split.setOpaque(false);
		
		char_split.add(scrollpane);
		char_split.add(input_parent);

		button = new JButton(Context.cmd_send_message);
		button.addActionListener(amk);
		button.addMouseListener(new Button_effect());
		button.setBorder(new BevelBorder(BevelBorder.RAISED));
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		
		JPanel control = new JPanel();		//显示发送按钮的面板，在输入框下面
		control.setOpaque(false);
		FlowLayout flt = new FlowLayout(FlowLayout.RIGHT);
		control.setLayout(flt);
		control.add(button, BorderLayout.EAST);

		JPanel panel_char = new JPanel(){		//显示 内容框，输入框，和按钮面板
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

		panel_bot_people = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
		panel_bot_people.setBorder(new LineBorder(Color.white,1,true));
		panel_bot_people.setDividerSize(3);
		panel_bot_people.setDividerLocation(60);
		panel_bot_people.add(panel_people,JSplitPane.TOP);
		panel_bot_people.add(panel_bot,JSplitPane.BOTTOM);

		char_people = new JPanel();
		char_people.setLayout(new BorderLayout());
		char_people.add(panel_char,BorderLayout.CENTER);
		char_people.add(panel_bot_people,BorderLayout.EAST);
		Container content = this.getContentPane();
		content.add(char_people);
		repaintPanel_people();
		this.setVisible(true);
	}
	public void initBot() {
		cf.getTextfield().setCharacterAttributes(cf.getAttrset(), true);
		JTextPane label = null;
		label = new JTextPane();
		label.setEditable(false);
		label.setBorder(null);
		label.setPreferredSize(default_prompt_size);
		
		JScrollPane js = new JScrollPane();
		js.getViewport().add(label);
		js.setOpaque(false);
		js.setBorder(null);
		label.setText("正在连接机器人。。。");
		
		JEditorPane ep = new JEditorPane();  
	
		ep.setEditable(false);
		ep.setContentType("text/html");
		ep.setPreferredSize(new Dimension(225,300));
		try {
			ep.setPage(getClass().getClassLoader().getResource("bot.html"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		panel_bot.setBorder(new TitledBorder("BOT"));
		panel_bot.setOpaque(false);
		panel_bot.setLayout(new BorderLayout());
		panel_bot.add(js,BorderLayout.NORTH);
		panel_bot.add(ep,BorderLayout.CENTER);
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
		communication = new Communication(label);
		communication.start();
	}

	public void repaint_input_panel(){
		String str = textfield.getText();
		textfield.setText("");
		Document docs = textfield.getDocument();
		try {
			docs.insertString(0, str, attrset);
		} catch (BadLocationException ble) {
			logger.info("BadLocationException:" + ble);
		}
	}
	public void repaintPanel_people() {
		panel_people.removeAll();
		panel_people.setPreferredSize(new Dimension(225,150));
		if (people.getId() == 1) {
			JPanel panel = new JPanel();
			
			JLabel label_name = new JLabel(people.getName());
			JLabel label_weight = new JLabel(people.getStatus());
			
			JPanel user_info = new JPanel();
			user_info.setLayout(new FlowLayout(FlowLayout.LEFT));
			user_info.add(label_name);
			user_info.add(label_weight);
			
			button_train = new Bot_button(Context.cmd_train_start);
			button_train.addActionListener(amk);
			panel.setLayout(new BorderLayout());
			panel.add(user_info,BorderLayout.CENTER);
			panel.add(button_train,BorderLayout.EAST);
			
			panel_people.setLayout(new BorderLayout());
			panel_people.add(panel,BorderLayout.CENTER);
			this.validate();
		} else {
			JLabel label_1 = new JLabel("游客");
			JButton button_login = new Bot_button(Context.cmd_login);
			button_login.addActionListener(amk);
			panel_people.setLayout(new BorderLayout());
			panel_people.add(label_1, BorderLayout.CENTER);
			panel_people.add(button_login, BorderLayout.EAST);
			panel_people.setBorder(new TitledBorder("USER"));
			//this.validate();
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
		while(!this.isLoad_complete()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Calendar ca = Calendar.getInstance();
		int h = ca.get(Calendar.HOUR_OF_DAY);
		int m = ca.get(Calendar.MINUTE);
		int s = ca.get(Calendar.SECOND);
		addPerson(name + "   " + h + ":" + m + ":" + s + "\n");
		if(name.equals(Context.mark_me)){
			insert(message+"\n", attrset);
		} else{
			insert(message+"\n", attrset_bot);
		}
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
		insert(content, attrset);
	}

	/**
	 * @param name
	 */
	public void addPerson(String name) {
		insert(name, attrset_chat_content);
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
			logger.info("BadLocationException:" + ble);
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
	
	public JTextPane getTextfield() {
		return textfield;
	}

	public void setTextfield(JTextPane textfield) {
		this.textfield = textfield;
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
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				Image title_image = Toolkit.getDefaultToolkit().createImage(Context.image_path+"title.png");
				JLabel title_label = new JLabel(new ImageIcon(title_image.getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
				title_label.setBorder(new LineBorder(Color.white,1,true));
				
				JPanel window_title = new JPanel();
				ActionForWindow_title awt = new ActionForWindow_title();
				window_title.addMouseListener(awt);
				window_title.addMouseMotionListener(awt);
				window_title.setOpaque(false);
				FlowLayout flt = new FlowLayout(FlowLayout.LEFT);
				flt.setVgap(10);
				window_title.setLayout(flt);
				window_title.add(title_label);
				
				JPanel window_control = new Window_control();
				
				JPanel window_control_parent = new JPanel();
				window_control_parent.setLayout(new BorderLayout());
				window_control_parent.add(window_control,BorderLayout.NORTH);
				window_control_parent.setOpaque(false);
				
				JPanel window_top = new JPanel(){
					private static final long serialVersionUID = 1L;
					public void paintComponent(Graphics g) {
					     super.paintComponent(g);
					     Image image = Toolkit.getDefaultToolkit().createImage(Context.image_path+"window_top.jpg");
					     int x = this.getWidth();
					     int y = this.getHeight();
					     ImageIcon img = new ImageIcon(image.getScaledInstance(x, y, Image.SCALE_DEFAULT));
					     g.drawImage(img.getImage(),0,0,null);
					 }
				};
				window_top.setLayout(new BorderLayout());
				window_top.add(window_title,BorderLayout.CENTER);
				window_top.add(window_control_parent,BorderLayout.EAST);
				ChatFrame cf = ChatFrame.instance();
				JPanel all = new JPanel();
				all.setLayout(new BorderLayout());
				all.add(window_top,BorderLayout.NORTH);
				all.add(cf,BorderLayout.CENTER);
				all.setBorder(new LineBorder(Color.black,1,true));
				
				JFrame window = new JFrame("Chat bot");
				window.setUndecorated(true);
				window.setIconImage(title_image);
				Container content = window.getContentPane();
				content.add(all);
				window.setBounds(200, 100, cf.init_frame_width, cf.init_frame_height);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setVisible(true);
				AWTUtilities.setWindowOpaque(window, false);
				
				RoundRectangle2D.Double rr = new RoundRectangle2D.Double(0,0,
						window.getWidth(),window.getHeight(),
						10,10);
				
				AWTUtilities.setWindowShape(window,rr);
				//AWTUtilities.setWindowOpacity(window, 0.93f); //设置透明
				cf.getChar_split().setDividerLocation(cf.init_chat_input_splitpane_location);
				cf.load_complete=true;
			}
			
		});
	}
	public boolean isLoad_complete() {
		return load_complete;
	}

	public void setLoad_complete(boolean load_complete) {
		this.load_complete = load_complete;
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
