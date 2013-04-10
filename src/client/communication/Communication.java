//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\communication\\Communication.java

package client.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;

import client.communication.message.ChatContent;
import client.communication.message.CommandMessage;
import client.communication.message.MessageCombine;
import client.communication.message.ReceiveMessage;
import client.communication.message.UserInformation;
import client.gui.ChatFrame;

public class Communication extends Thread {
	private static final Logger logger = Logger.getLogger(Communication.class);
	private String host = "127.0.0.1";
	private int port = 406;
	private boolean isTrainNow = false;

	/**
	 * 标记是否是训练模式
	 */
	@SuppressWarnings("unused")
	private boolean isWaitRec = false;
	private Socket socket;
	private DataInputStream inD = null;
	private DataOutputStream outD = null;
	private ChatFrame cf;

	/**
	 * @param cf
	 * @throws java.lang.Exception
	 * @roseuid 50187B510290
	 */
	public Communication(ChatFrame cf) throws Exception {
		this.cf = cf;
		init();
	}

	/**
	 * @throws java.lang.Exception
	 * @roseuid 50187B5102C0
	 */
	void init() throws Exception {
		socket = new Socket(host, port);
		inD = new DataInputStream(socket.getInputStream());
		outD = new DataOutputStream(socket.getOutputStream());
	}

	/**
	 * @roseuid 50187B5102EE
	 */
	public void run() {
		String message = null;
		sendMessage(new ChatContent(new String[] { "你好", "1", "1", "1" }));
		while (true) {
			try {
				if (socket.isConnected()) {
					message = receive();
					logger.info("Communication.run()" + message);
					handleMessage(message);
				} else {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}

	/**
	 * @param message
	 * @throws java.lang.Exception
	 * @roseuid 50187B5102EF
	 */
	public void handleMessage(String message) throws Exception {
		isWaitRec = false;
		cf.getSendButton().setEnabled(true);

		ReceiveMessage rm = new ReceiveMessage(message);
		String content[] = rm.getContents();
		if (rm.getType() == 1) {
			cf.addChatContent(content[0], content[1]);
		} else if (rm.getType() == 2) {
			if (content.length == 1) {
				cf.prompt("用户名或密码错误");
				return;
			}
			cf.getPeople().setName(content[0]);
			cf.getPeople().setStatus(content[1]);
			cf.repaintPanel_people();
		} else if (rm.getType() == 3) {
			cf.addChatContent(content[0], content[1]);
		}
	}

	/**
	 * @param message
	 * @roseuid 50187B51031D
	 */
	public void sendCommandMessage(String message) {
		String str[] = new String[] { "A:", "a:", "A：", "a：" };
		String message_[] = null;
		for (int i = 0; i < str.length; i++) {
			message_ = message.split(str[i]);
			if (message_.length == 2) {
				message_[1] = str[i] + message_[1];
				break;
			}
		}
		if ((message_[0].startsWith("Q:") || message_[0].startsWith("q:") || (message_[0]
				.startsWith("Q：") || message_[0].startsWith("q：")))
				&& (message_[1].startsWith("A:")
						|| message_[1].startsWith("a:") || (message_[1]
						.startsWith("A：") || message_[1].startsWith("a：")))) {
			sendMessage(new CommandMessage(new String[] { "train", message_[0],
					message_[1] }));
		} else {
			cf.addChatContent("机器人提示：", "你的训练不符合训练规范啊。");
		}
	}

	/**
	 * @roseuid 50187B51031F
	 */
	public void sendCharMessage() {
		String message = cf.getSendingMessage();
		if (!message.equals("")) {
			sendCharMessage(message);
			cf.setTextFieldEmpty();
		}
	}

	/**
	 * 发送谈话内容
	 * 
	 * @param str
	 * @param message
	 * @roseuid 50187B510320
	 */
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
	 * 发送用户登录信息
	 * 
	 * @param user_num
	 *            用户名
	 * @param password
	 *            密码
	 * @roseuid 50187B51032C
	 */
	public void sendUserInfomation(String user_num, String password) {
		sendMessage(new UserInformation(new String[] { user_num, password }));
		cf.getPeople().setUserId(user_num);
	}

	/**
	 * @param str
	 *            要发送的文字
	 * @param type
	 *            1，聊天 2，用户信息
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
		try {
			outD.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @return java.lang.String
	 * @throws java.lang.Exception
	 * @roseuid 50187B51033D
	 */
	public String receive() throws Exception {
		return inD.readUTF();
	}
}
// boolean Communication.sendMessage(server.handle.message.MessageCombine){
// if(isWaitRec && message.getType()==1){
// return false;
// } else {
// /**
// * 设置是否禁止用户发聊天内容。
// */
// isWaitRec = true;
// cf.getSendButton().setEnabled(false);
// }
// logger.info(message.getMessageContent());
// sendMessage(message.getMessageContent());
// return true;
// }
