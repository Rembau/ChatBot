package server.handle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import server.aboutBot.Bot;
import server.handle.message.ChatContent;
import server.handle.message.ReceiveMessage;
import server.handle.message.TrainMessage;
import server.handle.message.UserLoginResult;
import server.tools.Ck;

public class Dispatcher {
	private static final Logger logger = Logger.getLogger(Dispatcher.class);
	private static ServerSocket serverSocket;
	public Dispatcher(){
		try {
			serverSocket = new ServerSocket(406);
			Ck.ck(this.getClass(),"����˿����ɹ���");
			while(true){
				Socket socket = serverSocket.accept();
				new Thread_bot(socket).start();
				Ck.ck(this.getClass(),socket.getInetAddress()+"���ӳɹ���");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	class Thread_bot extends Thread{
		private Socket socket;
		private Bot bot = new Bot();
		private DataInputStream inD=null;
		private DataOutputStream outD=null;
		public Thread_bot(Socket socket){
			this.socket=socket;
			bot.getMemory().getPeople().setIp(socket.getRemoteSocketAddress().toString());
			try {
				inD = new DataInputStream(socket.getInputStream());
				outD = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void run(){
			String message=null;
			while(true){
				try {
					message=receive();
					logger.info(socket.getRemoteSocketAddress()+"receive:"+message);
					long i = System.currentTimeMillis();
					handleMessage(message);
					logger.info("ʹ��ʱ��Ϊ��"+(System.currentTimeMillis() - i));
				} catch (SocketException e) {
					logger.info(socket+" "+e.getMessage());
					try {
						socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					//e.printStackTrace();
					break;
				} catch(Exception e){
					e.printStackTrace();
				}
			}							
		}
		public void handleMessage(String message) throws Exception{
			ReceiveMessage rm = new ReceiveMessage(message);
			String contents[] = rm.getContents();
			if(rm.getType()==1){
				String reply = bot.execute(contents);
				sendMessage(new ChatContent(new String[]{reply}).getMessageContent());
			} else if(rm.getType()==2){
				String r[] =new LoginCheck(bot).check(contents);
				sendMessage(new UserLoginResult(r).getMessageContent());
				if(r.length==2){
					sendMessage(new ChatContent(new String[]{r[1]+"��ӭ�㰡��"}).getMessageContent());
				}
			} else if(rm.getType()==3){
				String reply=bot.trainModel(contents);
				sendMessage(new TrainMessage(new String[]{Bot.getName(),reply}).getMessageContent());
			}
		}
		/**
		 * @param str Ҫ���͵�����
		 * @param type 1������ 2���û���Ϣ 3��ѵ����Ϣ
		 * @throws Exception
		 */
		public void sendMessage(String str) throws Exception{
			logger.info("send:"+str);
			bot.getMemory().initReplyTime();
			outD.writeUTF(str);
		}
		public String receive() throws Exception{
			return inD.readUTF();
		}
	}
	public static void main(String[] args) {
		new Dispatcher();
	}
}
