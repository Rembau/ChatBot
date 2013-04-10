package server.aboutBot.analyzer;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import server.conn.DBoperate;

public class Index {
	private static String indexPath="conf"+File.separator+"fenciFile";
	private IndexWriter indexWriter;
	private static Version version = Version.LUCENE_35; 
	static private IKAnalyzer ik = new IKAnalyzer(true);
	public Index(){
		
	}

	public void createIndex(){
		 try {  
		    	File indexFile = new File(indexPath);  
		    	indexFile.delete();
		        FSDirectory directory = FSDirectory.open(indexFile);
		        IndexWriterConfig conf = new IndexWriterConfig(version, ik);  
		        indexWriter = new IndexWriter(directory, conf);
		    } catch (Exception e) {  
		        e.printStackTrace();  
		    } 
		String sql="select * from b_teacherQuestion";
		ResultSet rs = DBoperate.select(sql);
		try {
			while(rs.next()){
				Document doc=new Document(); 
				doc.add(new Field("question",rs.getString("t_question"),Field.Store.YES,Field.Index.ANALYZED));
				System.out.println(rs.getString("t_question"));
				doc.add(new Field("answer",rs.getString("t_answer"),Field.Store.YES,Field.Index.NO));
				System.out.println(rs.getString("t_answer"));
				doc.add(new Field("userId",rs.getString("t_userId"),Field.Store.YES,Field.Index.NO));
				System.out.println(rs.getString("t_userId"));
				indexWriter.addDocument(doc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				indexWriter.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			try {
				rs.getStatement().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			sql="update b_teacherQuestion set t_mark=1";
			DBoperate.update(sql);
		}
	}
	public void appendIndex(){
		 try {  
		    	File indexFile = new File(indexPath);  
		        FSDirectory directory = FSDirectory.open(indexFile);
		        IndexWriterConfig conf = new IndexWriterConfig(version, ik);
		        conf.setOpenMode(OpenMode.APPEND);
		        indexWriter = new IndexWriter(directory, conf);
		    } catch (Exception e) {  
		        e.printStackTrace();  
		    } 
		String sql="select * from b_teacherQuestion where t_mark=0";
		ResultSet rs = DBoperate.select(sql);
		try {
			while(rs.next()){
				Document doc=new Document(); 
				doc.add(new Field("question",rs.getString("t_question"),Field.Store.YES,Field.Index.ANALYZED));
				System.out.println(rs.getString("t_question"));
				doc.add(new Field("answer",rs.getString("t_answer"),Field.Store.YES,Field.Index.NO));
				System.out.println(rs.getString("t_answer"));
				doc.add(new Field("userId",rs.getString("t_userId"),Field.Store.YES,Field.Index.NO));
				System.out.println(rs.getString("t_userId"));
				doc.add(new Field("questionId",rs.getString("t_questionId"),Field.Store.YES,Field.Index.NO));
				
				indexWriter.addDocument(doc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  finally{
			try {
				indexWriter.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			try {
				rs.getStatement().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			sql="updata  b_teacherQuestion set t_mark=1";
			DBoperate.update(sql);
		}
	}
	public static void main(String[] args) {
		Index index = new Index();
		index.createIndex();
		//index.appendIndex();
	}
}
