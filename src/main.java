import java.sql.SQLException;

public class main {

	public static void main(String[] argv) {
		DictionaryGraph dq = new DictionaryGraph();
		try {
			dq.makeGraph();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Word[] bases = dq.getKMax(30, "n.");
		for(int i=0; i<bases.length; i++) {
			System.out.println(bases[i].word+ " "+bases[i].type);
		}
	}
}
