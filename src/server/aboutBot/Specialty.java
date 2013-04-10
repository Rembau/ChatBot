package server.aboutBot;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeSet;


import server.botException.InitException;
import server.conn.DBoperate;
import server.tools.Ck;
import server.tools.ReadFile;


public class Specialty {
	/**
	 * 遇到不理解的话时，回答的回复
	 */
	private ArrayList<String> unknowReplyList = new ArrayList<String>(); 
	/**
	 * 特定属性对应的回答
	 */
	private Hashtable<Integer,Hashtable<String,String>> specifics=
		new Hashtable<Integer,Hashtable<String,String>>();
	/**
	 * key ,属性	 对应表
	 */
	private Hashtable<String,String> keyForSpecific = new Hashtable<String,String>();
	private TreeSet<String> keyForStop = new TreeSet<String>();
	private LinkedList<String> keyForStopForW = new LinkedList<String>();
	/**
	 * 构造方法
	 */
	
	public Specialty(){
		try{
			init();
			System.out.println("机器人属性初始化完成！");
		} catch (Exception e){
			System.out.println(e.getMessage());
			Throwable e1=e.getCause();
			e1.printStackTrace();
		}
	}
	/**
	 * 查找和关键字key有关的，且rank为rank的回答
	 * @param key 关键字
	 * @param rank 等级
	 * @return 要打印的回复
	 */
	public String findSpReply(String key,int rank){
		System.out.println("findSpReply"+specifics.get(rank).get(key)+" "+rank);
		if(specifics.size()==0){
			return "none";
		}
		return specifics.get(rank).get(key);
	}
	/**
	 * 查找内容中是否包含已存关键字。如：名字等
	 * @param content
	 * @return 与找到的关键字对应的属性关键字，如：name
	 */
	public String findSpKey(TreeSet<String> wordList){
		if(keyForSpecific.size()==0){
			return "none";
		}
		Enumeration<String> keys=keyForSpecific.keys();
		while(keys.hasMoreElements()){
			String key=keys.nextElement();
			if(wordList.contains(key)){
				System.out.println("findSpKey"+keyForSpecific.get(key));
				return keyForSpecific.get(key);
			}
		}
		return "none";
	}
	public String getUnknowAnswer(){
		Random randow=new Random();
		int x=randow.nextInt(unknowReplyList.size());
		return unknowReplyList.get(x);
	}
	/**
	 * 去除某类副词
	 * @param content
	 * @return
	 */
	public String removeStopWordFuci(String content){
		for(String str:keyForStopForW){
			content=content.replace(str, "");
		}
		return content;
	}
	/**
	 * 入库去除停用词
	 * @param content
	 * @return
	 */
	public String removeStopWord(String content){
		for(String str:keyForStop){
			content=content.replace(str, "");
		}
		return content;
	}
	/**
	 * 查询去除停用词
	 * @param content
	 * @return
	 */
	public TreeSet<String> removeStopWords(TreeSet<String> wordList){
			wordList.removeAll(keyForStop);
			return wordList;
	}
	/*
	public String removeStopWordForR(String content){
		for(String str:keyForStopForR){
			content=content.replace(str, "");
		}
		return content;
	}*/
	/**
	 * 初始化
	 * @throws Exception
	 */
	public void init() throws Exception{
		try{
			Hashtable<String,String> specific=null;
			Object attribute[];
			File file= new File(getURI("keyForAttribute.txt"));
			LinkedList<String> keyList=new ReadFile().readLineList(file);
			attribute=keyList.toArray();
			file = new File(getURI("botInformation.txt"));
			LinkedList<String> contentList= new ReadFile().readLineList(file);
			for(String str:contentList){
				specific = new Hashtable<String,String>(); 
				int level;
				if(str.startsWith(":")){
					continue;
				} else {
					String str_[]=str.split(";");
					level= Integer.valueOf(str_[0]);
					for(int i=1;i<str_.length;i++){
						specific.put(attribute[i-1].toString(), str_[i].substring(1));
						Ck.ck(level+" "+attribute[i-1].toString()+" "+ str_[i]);
					}
				}
				specifics.put(level, specific);
			}
		} catch(Exception e){
			Exception e1=new InitException(e.getMessage()+":bot属性常用回答");
			e1.initCause(e);
			throw e1;
		}
		//
		try{
			File file= new File(getURI("keyforinformation.txt"));
			LinkedList<String> keyList=new ReadFile().readLineList(file);
			for(String str:keyList){
				String str_[]=str.split(" ");
				keyForSpecific.put(str_[0].trim(), str_[1].trim());
				System.out.println("specialty.init()"+str_[0]+" "+str_[1]);
			}
		} catch(Exception e){
			Exception e1=new InitException(e.getMessage()+":bot属性常用问题关键字");
			e1.initCause(e);
			throw e1;
		}
		//
		try{
			File file = new File(getURI("unknow.txt"));
			LinkedList<String> list=new ReadFile().readLineList(file);
			for(String str:list){
				String str_[]=str.split(" ");
				unknowReplyList.add(str_[0].trim());
				//System.out.println("specialty.init()"+str_[0]+" "+str_[1]);
			}
		} catch(Exception e){
			Exception e1=new InitException(e.getMessage()+":对于无法理解的话的回答");
			e1.initCause(e);
			throw e1;
		}
		//
		try{
			File file = new File(getURI("stopWord.txt"));
			LinkedList<String> list=new ReadFile().readLineList(file);
			//TreeSet<String> t = new TreeSet<String>();
			for(String str:list){
				keyForStop.add(str.trim());
				//t.add(str.trim());
				//System.out.println("specialty.init()"+str_[0]+" "+str_[1]);
			}
			/*
			  汉字根据拼音排序
			String[] s = new String[t.size()];
			int i=0;
			for(String str:t){
				s[i++]=str;
				System.out.println(str);
			}
			Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);  
			Arrays.sort(s, cmp);
			for(int j=0;j<s.length;j++)
			System.out.println(s[j]);*/
		} catch(Exception e){
			Exception e1=new InitException(e.getMessage()+":停用词1");
			e1.initCause(e);
			throw e1;
		}
		//
		try{
			//TreeSet<String> t = new TreeSet<String>();
			File file = new File(getURI("stopWordFor.txt"));
			LinkedList<String> list=new ReadFile().readLineList(file);
			for(String str:list){
				keyForStopForW.add(str.trim());
				//t.add(str.trim());
			}
			/*String[] s = new String[t.size()];
			int i=0;
			for(String str:t){
				s[i++]=str;
				System.out.println(str);
			}
			Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);  
			Arrays.sort(s, cmp);
			for(int j=0;j<s.length;j++)
			System.out.println(s[j]);*/
		} catch(Exception e){
			Exception e1=new InitException(e.getMessage()+":停用词2");
			e1.initCause(e);
			throw e1;
		}
	};
	public String getURI(String fileName){
		String uri=null;
		uri="conf"+File.separator+""+fileName;
		return uri;
	}
	public static void main(String args[]){
		new Specialty();
		DBoperate.close();
	}
}
