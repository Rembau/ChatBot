package server.core.analyzer;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IndexSearch{
	private static String indexPath="conf"+File.separator+"file";
	private static Version version = Version.LUCENE_35; 
	static private IKAnalyzer ik = new IKAnalyzer(true);
	static IndexReader reader= null;
	static {
		try {
			reader = IndexReader.open(FSDirectory.open(new File(indexPath)));
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static LinkedList<ResultAnswer> search(String queryString){
		LinkedList<ResultAnswer> r = new LinkedList<ResultAnswer>();
		String[] queryFields={"question"};   
        IndexSearcher searcher=new IndexSearcher(reader);  
        QueryParser parser = new MultiFieldQueryParser(version, queryFields, ik);  
		try {
	        Query query = parser.parse(queryString);
	        TopDocs results = searcher.search(query,null,10); 
	        ScoreDoc[] hits=results.scoreDocs;  
	        //System.out.println(hits.length);
	        for(ScoreDoc sd:hits){  
	        	ResultAnswer r_= new ResultAnswer();
	        	int docID=sd.doc;  
	        	Document doc=searcher.doc(docID);   
	        	r_.setAnswer(doc.get("answer"));
	        	r_.setQuestion(doc.get("question"));
	        	r_.setUserId(doc.get("userId"));
	        	r.add(r_);
	         }  
	        searcher.close();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return r;
	}
	public static void main(String args[]){
		for(ResultAnswer r:IndexSearch.search("≤ª”√–ª")){
			System.out.println(r.getQuestion()+" "+r.getAnswer());
		}
	}
}
