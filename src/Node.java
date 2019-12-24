import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Node {
	public LinkedList<Word> words;
	public LinkedList<Node> neighbors;
	public Integer inDegree;
	
	public Node(Word word) {
		words = new LinkedList<Word>();
		neighbors = new LinkedList<Node>();
		inDegree = 0;
		this.words.add(word);
	}
	
	public void addWord(Word word) {
		words.add(word);
	}
	
	public String getMostCommonType() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(int i=0; i<words.size(); i++) {
			String type = words.get(i).type;
			if(map.get(type) != null) {
				map.put(type, map.get(type)+1);
			}
			else {
				map.put(type, 1);
			}
		}
		Iterator<Map.Entry<String,Integer>> itr = map.entrySet().iterator();
		String maxType = "";
		int max = 0;
		while(itr.hasNext()) {
			Map.Entry<String, Integer> entry = itr.next();
			if(entry.getValue()>max) {
				maxType = entry.getKey();
				max = entry.getValue();
			}
		}
		return maxType;
	}
	
}
