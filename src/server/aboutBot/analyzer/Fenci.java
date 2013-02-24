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
	 * 分词
	 * @param str 需要分词的文本
	 * @param type 分词方式，true为智能分词，false为最细粒度分词
	 * @return 所有分词的链表
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
		String str="你是谁？";
		TreeSet<String> wordList=Fenci.IKAnalysis(str,true);
		for(String str1:wordList){
			System.out.println(str1);
		}
	}
}
