import java.util.Comparator;

public class InDegreeNodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node x, Node y) {
		if(x.inDegree<y.inDegree) {
			return 1;
		}
		if(x.inDegree>y.inDegree) {
			return -1;
		}
		return 0;
	}

}
