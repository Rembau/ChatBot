//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\gui\\ChatFrame.java

package bot.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Calendar;
import javax.swing.Box;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import bot.client.communication.Communication;
import bot.client.event.ActionMouseKeyForCharFrame;
import bot.client.people.People;

public class ChatFrame extends JApplet 
{
   private static final long serialVersionUID = 1L;
   private Communication communication;
   private JScrollPane scrollpane;
   private JScrollPane scrollinput;
   private JTextPane textarea;
   private JTextPane textfield;
   private JButton button;
   private JPanel panel_showBot;
   private JPanel panel_bot = new JPanel ();
   private JPanel panel_people = new JPanel ();
   private People people = new People ();
   private ActionMouseKeyForCharFrame amk = new ActionMouseKeyForCharFrame (this);
   
   /**
    * @roseuid 50187B4A01A0
    */
   public ChatFrame() 
   {
		super();
		panel_people.setLayout(new GridLayout(0,1));
		
		textarea=new JTextPane();
		textarea.setEditable(false); 
		
		scrollpane=new JScrollPane();
		scrollpane.getViewport().add(textarea);
		textfield=new JTextPane();
		textfield.addKeyListener(amk);
		
		scrollinput=new JScrollPane();
		scrollinput.getViewport().add(textfield);
		
		button=new JButton("����");
		button.addActionListener(amk);
		Container content=this.getContentPane();

		content.setLayout(new BorderLayout());
		
		Box panel_char= Box.createVerticalBox();
		//panel_char.setLayout(new BorderLayout());
		panel_char.add(scrollpane);
		textarea.setPreferredSize(new Dimension(800,600));	
		textfield.setPreferredSize(new Dimension(700, 200));
		
		JPanel panel_input= new JPanel();
		panel_input.setLayout(new BorderLayout());
		panel_input.add(scrollinput,BorderLayout.CENTER);
		panel_input.add(button,BorderLayout.EAST);
		panel_char.add(panel_input);
		
		panel_showBot= createShowBotPanel();
		
		content.add(panel_char,BorderLayout.CENTER);
		content.add(panel_showBot,BorderLayout.EAST);
		
		//this.setTitle("�����������");
		setBounds(300,100,800,600);
		repaintPanel_people();
		this.setVisible(true);
		initBot();    
   }
   
   /**
    * @roseuid 50187B4A01A1
    */
   public void initBot() 
   {
		JLabel label=null;
		try {
			label=new JLabel("");
			label.setText("�������ӻ����ˡ�����");
			panel_bot.add(label);
			
			initCommunication();
			label.setText("<html>1:�������ַ���ǰ����ϡ�%�������Խ���������<br>" +
					"2������teacher����ѵ��ģʽ�����ȵ�¼����<br>" +
					"3������end�˳�ѵ��ģʽ��</html>");
		} catch (Exception e) {
			label.setText("����ʧ��");
			JOptionPane.showMessageDialog(null, "���ӷ�����ʧ�ܣ�");
			System.out.println(e.getMessage());
		}    
   }
   
   /**
    * ��ʾ
    * @param message
    * @roseuid 50187B4A01A2
    */
   public void prompt(String message) 
   {
		JOptionPane.showMessageDialog(this, message);    
   }
   
   /**
    * ��ʼ��ͨ��ģ��
    * @throws java.lang.Exception
    * @roseuid 50187B4A01A4
    */
   public void initCommunication() throws Exception 
   {
			communication = new Communication(this);
			communication.start();    
   }
   
   /**
    * @return javax.swing.JPanel
    * @roseuid 50187B4A01C5
    */
   public JPanel createShowBotPanel() 
   {
		JPanel panel_showBot= new JPanel();
		panel_showBot.setLayout(new BorderLayout());
		panel_showBot.add(panel_people,BorderLayout.NORTH);
		panel_showBot.add(panel_bot,BorderLayout.CENTER);
		return panel_showBot;    
   }
   
   /**
    * @roseuid 50187B4A01C6
    */
   public void repaintPanel_people() 
   {
		panel_people.removeAll();
		if(people.getId()==1){
			JLabel label_1 = new JLabel("�û�");
			JLabel label_2 = new JLabel(people.getName());
			JLabel label_3 = new JLabel(people.getStatus());
			JButton button_train = new JButton("��ʼѵ��");
			button_train.addActionListener(amk);
 			panel_people.add(label_1);
 			panel_people.add(label_2);
 			panel_people.add(label_3);
 			panel_people.add(button_train);
 			this.validate();
		} else {
			JLabel label_1 = new JLabel("�ο�");
			JButton button_login = new JButton("��¼");
			button_login.addActionListener(amk);
			panel_people.add(label_1);
			panel_people.add(button_login);
			this.validate();
		}    
   }
   
   /**
    * �����Ϣ���������
    * @param message:Ҫ��ӵ�����
    * @param message
    * @roseuid 50187B4A01D4
    */
   public void addChatContent(String message) 
   {
		addChatContent("me",message);    
   }
   
   /**
    * @param name
    * @param message
    * @roseuid 50187B4A01D6
    */
   public void addChatContent(String name, String message) 
   {
		Calendar ca=Calendar.getInstance();
		int h=ca.get(Calendar.HOUR_OF_DAY);
		int m=ca.get(Calendar.MINUTE);
		int s=ca.get(Calendar.SECOND);
	    addPerson(name+"   "+h+":"+m+":"+s+"\n");
	    addContent(""+message+"\n");
		textarea.setCaretPosition(textarea.getDocument().getLength());    
   }
   
   /**
    * @param content
    * @roseuid 50187B4A01D9
    */
   public void addContent(String content) 
   {
		addContent(content,Color.black);    
   }
   
   /**
    * @param content
    * @param color
    * @roseuid 50187B4A01E4
    */
   public void addContent(String content, Color color) 
   {
		SimpleAttributeSet attrset=new SimpleAttributeSet();
		StyleConstants.setFontSize(attrset,14);
		StyleConstants.setAlignment(attrset, StyleConstants.ALIGN_LEFT);
		StyleConstants.setLeftIndent(attrset, 20);
		insert(content,attrset);    
   }
   
   /**
    * @param name
    * @roseuid 50187B4A01EF
    */
   public void addPerson(String name) 
   {
		SimpleAttributeSet attrset=new SimpleAttributeSet();
		StyleConstants.setForeground(attrset,Color.blue);
		StyleConstants.setFontSize(attrset,12);
		insert(name,attrset);    
   }
   
   /**
    * @param str
    * @param attrset
    * @roseuid 50187B4A01F4
    */
   public void insert(String str, AttributeSet attrset) 
   {
		Document docs=textarea.getDocument();
		try{
			docs.insertString(docs.getLength(),str,attrset); 	    	
		}catch(BadLocationException ble){
			System.out.println("BadLocationException:"+ble);	
		}    
   }
   
   /**
    * ��ȡ������е��ַ�
    * @return:�ַ�
    * @roseuid 50187B4A01FF
    */
   public String getSendingMessage() 
   {
		return textfield.getText();    
   }
   
   /**
    * ��������
    * @roseuid 50187B4A0200
    */
   public void setTextFieldEmpty() 
   {
		textfield.setText("");    
   }
   
   /**
    * @return client.communication.Communication
    * @roseuid 50187B4A0203
    */
   public Communication getCommunication() 
   {
		return this.communication;    
   }
   
   /**
    * @return client.people.People
    * @roseuid 50187B4A0204
    */
   public People getPeople() 
   {
		return this.people;    
   }
   
   /**
    * @return javax.swing.JButton
    * @roseuid 50187B4A0205
    */
   public JButton getSendButton() 
   {
		return this.button;    
   }
   
   /**
    * @roseuid 50187B4A0213
    */
   public void init() 
   {
    
   }
   
   /**
    * @roseuid 50187B4A0214
    */
   public void destroy() 
   {
    
   }
   
   /**
    * @param args
    * @roseuid 50187B4A0215
    */
   public static void main(String[] args) 
   {
		JFrame frame = new JFrame();
		ChatFrame cf = new ChatFrame();
		frame.add(cf);
		frame.setVisible(true);
		frame.setBounds(200, 100,cf.getX(), cf.getY());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
   }
}
