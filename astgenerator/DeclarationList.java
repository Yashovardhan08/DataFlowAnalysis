import java.util.ArrayList;

public class DeclarationList {
	public ArrayList<Declaration> dList;
	public DeclarationList(Declaration d){
		this.dList = new ArrayList<>();
		dList.add(d);
	}
	public void addDecl(Declaration d){
		this.dList.add(d);
	}
	public String toString() {
		String ret = "";
		for (Declaration decl : dList) {
			ret = ret + decl.toString();
			ret = ret + ", ";
		}
		return ret;
	}
}
