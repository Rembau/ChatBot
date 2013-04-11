package client.communication;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import client.communication.message.ChatContent;
import client.communication.message.CommandMessage;
import comm.MessageCombine;
import client.communication.message.ReceiveMessage;
import client.communication.message.UserInformation;
import client.gui.ChatFrame;

public class ClientHandler extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(ClientHandler.class);
	private ChatFrame cf;
	private boolean isTrainNow = false;
	private IoSession session;
	public ClientHandler(ChatFrame cf){
		this.cf = cf;
	}
	public void sessionClosed(IoSession session){
		logger.info("���ӹرգ��ͻ����˳���");
		System.exit(1);
	}
	public void messageReceived(IoSession session, Object message) {
		try {
			logger.info(message.toString());
			handleMessage(message.toString());
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
	public void handleMessage(String message) throws Exception {
		cf.getSendButton().setEnabled(true);

		ReceiveMessage rm = new ReceiveMessage(message);
		String content[] = rm.getContents();
		if (rm.getType() == 1) {
			cf.addChatContent(content[0], content[1]);
		} else if (rm.getType() == 2) {
			if (content.length == 1) {
				cf.prompt("�û������������");
				return;
			}
			cf.getPeople().setName(content[0]);
			cf.getPeople().setStatus(content[1]);
			cf.repaintPanel_people();
		} else if (rm.getType() == 3) {
			cf.addChatContent(content[0], content[1]);
		}
	}
	public void sendCommandMessage(String message) {
		String str[] = new String[] { "A:", "a:", "A��", "a��" };
		String message_[] = null;
		for (int i = 0; i < str.length; i++) {
			message_ = message.split(str[i]);
			if (message_.length == 2) {
				message_[1] = str[i] + message_[1];
				break;
			}
		}
		if ((message_[0].startsWith("Q:") || message_[0].startsWith("q:") || (message_[0]
				.startsWith("Q��") || message_[0].startsWith("q��")))
				&& (message_[1].startsWith("A:")
						|| message_[1].startsWith("a:") || (message_[1]
						.startsWith("A��") || message_[1].startsWith("a��")))) {
			sendMessage(new CommandMessage(new String[] { "train", message_[0],
					message_[1] }));
		} else {
			cf.addChatContent("��������ʾ��", "���ѵ��������ѵ���淶����");
		}
	}
	public void sendCharMessage() {
		String message = cf.getSendingMessage();
		if (!message.equals("")) {
			sendCharMessage(message);
			cf.setTextFieldEmpty();
		}
	}
	public void sendCharMessage(String message) {
		cf.addChatContent(message);
		message = message.trim();
		// logger.info(message);
		if (isTrainNow && !message.equals(Command.endTrain)) {
			sendCommandMessage(message);
		} else if (message.equals(Command.enterTrain)
				&& cf.getPeople().getId() == 1) {
			isTrainNow = true;
			sendMessage(new CommandMessage(new String[] { message }));
		} else if (isTrainNow && message.equals(Command.endTrain)) {
			isTrainNow = false;
			sendMessage(new CommandMessage(new String[] { message }));
		} else {
			String i = cf.getPeople().getPeopleAttributeNow().getIrascible()
					+ "";
			String p = cf.getPeople().getPeopleAttributeNow().getProficient()
					+ "";
			String n = cf.getPeople().getPeopleAttributeNow().getNormal() + "";
			sendMessage(new ChatContent(new String[] { message, i, p, n }));
		}
	}
	/**
	 * �����û���¼��Ϣ
	 * 
	 * @param user_num
	 *            �û���
	 * @param password
	 *            ����
	 * @roseuid 50187B51032C
	 */
	public void sendUserInfomation(String user_num, String password) {
		sendMessage(new UserInformation(new String[] { user_num, password }));
		cf.getPeople().setUserId(user_num);
	}
	/**
	 * @param str
	 *            Ҫ���͵�����
	 * @param type
	 *            1������ 2���û���Ϣ
	 * @param message
	 * @return boolean
	 * @throws Exception
	 * @roseuid 50187B51032F
	 */
	public boolean sendMessage(MessageCombine message) {
		sendMessage(message.getMessageContent());
		return true;
	}
	/**
	 * @param message
	 * @return boolean
	 * @roseuid 50187B510339
	 */
	public boolean sendMessage(String message) {
		session.write(message);
		return true;
	}
	public void sessionCreated(IoSession session){
		this.session=session;
		sendMessage(new ChatContent(new String[] { "���", "1", "1", "1" }));
	}
}
