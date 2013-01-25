package server.aboutBot.analyzer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.TreeSet;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class Fenci {
	/**
	 * �ִ�
	 * @param str ��Ҫ�ִʵ��ı�
	 * @param type �ִʷ�ʽ��trueΪ���ִܷʣ�falseΪ��ϸ���ȷִ�
	 * @return ���зִʵ�����
	 */
	public static TreeSet<String> IKAnalysis(String str,boolean type){
		TreeSet<String> sb=new TreeSet<String>();
		try {
		   byte[] bt =str.getBytes();
		   InputStream ip = new ByteArrayInputStream(bt);
		   Reader read =new InputStreamReader(ip);
		   IKSegmenter iks = new IKSegmenter(read,type);
		   Lexeme t;
		   while((t=iks.next())!= null){
			   sb.add(t.getLexemeText());
			   //Ck.ck(t.getLexemeText());
		   }
		} catch(IOException e) {
			e.printStackTrace();
		}
		return sb;
	}
	public static void main(String args[]){
		String str="����˭��";
		TreeSet<String> wordList=Fenci.IKAnalysis(str,true);
		for(String str1:wordList){
			System.out.println(str1);
		}
	}
}
