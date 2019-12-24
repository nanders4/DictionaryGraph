import java.sql.*;
import java.util.*;

public class DictionaryGraph {
	
	private Map<String, Node> stringToNode;
	private Map<String, Integer> stringToCount;
	private Map<String, Word> stringToWord;
	
	public DictionaryGraph() {
		stringToNode = new HashMap<String, Node>();
		stringToCount = new HashMap<String, Integer>();
		stringToWord = new HashMap<String, Word>();
	}
	
	public void makeGraph() throws ClassNotFoundException, SQLException {
		ArrayList<String> words = new ArrayList<String>();
		ArrayList<String> wordTypes = new ArrayList<String>();
		ArrayList<String> defs = new ArrayList<String>();
		
		//Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://127.0.0.1:3306/entries";
		Connection con = DriverManager.getConnection(url,"root","Na373737");
		Statement st = con.createStatement();
		ResultSet results = st.executeQuery("SELECT word, wordtype, definition FROM entries");
		
		while(results.next()) {
			String string = results.getString("word");
			words.add(string);
			string = results.getString("wordtype");
			wordTypes.add(string);
			string = results.getString("definition");
			defs.add(string);
		}
		con.close();
		
		//create all word nodes
		for(int i=0; i<words.size(); i++) {
			String wordString = words.get(i);
			String wordType = wordTypes.get(i);
			String defString = defs.get(i);
			wordString = wordString.toLowerCase();
			defString = defString.toLowerCase().replaceAll("[,.&;()]", "");
			Node node;
			Word word = new Word(wordString, wordType, defString);
			if(!stringToNode.containsKey(word.word)) {
				node = new Node(word);
				stringToNode.put(word.word, node);
				stringToCount.put(word.word,  0);
				stringToWord.put(word.word, word);
			}
			else {
				node = stringToNode.get(word.word);
				node.words.add(word);
			}
			
		}
		
		//make connections
		Iterator<Node> iterator = stringToNode.values().iterator();
		while(iterator.hasNext()) {
			Node node = iterator.next();
			//for each Word in the Node
			for(int j=0; j<node.words.size(); j++) {
				Word word = node.words.get(j);
				String[] defWords = word.def.split(" ");
				Node wordNode = stringToNode.get(word.word);
				
				//for each word in the def
				for(int i=0; i<defWords.length; i++) {
					if(stringToNode.containsKey(defWords[i])) {
						Node defNode = stringToNode.get(defWords[i]);
						wordNode.neighbors.add(defNode);
						defNode.inDegree++;
						stringToCount.replace(defWords[i], defNode.inDegree);
					}
				}
			}
		}
	}
	
	public Word[] getKMax(int k, String type) {
		Comparator<Node> comp = new InDegreeNodeComparator();
		PriorityQueue<Node> pq = new PriorityQueue<Node>(comp);
		Iterator<Node> iterator = stringToNode.values().iterator();
		while(iterator.hasNext()) {
			Node node = iterator.next();
			pq.add(node);
		}
		
		Word[] ret = new Word[k];
		int i=0;
		if(type=="") {
			while(i<k && !pq.isEmpty()) {
				Node topNode = pq.remove();
				ret[i] = new Word(topNode.words.get(0).word, topNode.getMostCommonType(), "");
				i++;
			}
		}
		else {
			while(i<k && !pq.isEmpty()) {
				Node topNode = pq.remove();
				String commonType = topNode.getMostCommonType();
				if(commonType.equals(type)) {
					ret[i] = new Word(topNode.words.get(0).word, commonType, "");
					i++;
				}
			}
		}
		return ret;
		
	}
}
