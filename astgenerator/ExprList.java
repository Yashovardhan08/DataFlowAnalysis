import java.util.ArrayList;

public class ExprList{
	public ArrayList<Expr> exprList;
	public ExprList(Expr e){
		this.exprList = new ArrayList<>();
		this.exprList.add(e);
	}
	public void pushExpr(Expr e){
		this.exprList.add(e);
	}
	public String toString() {
		String ret = "";
		for (Expr expr : exprList) {
			ret = ret + expr.toString() +" , ";
		}
		return "("+ret+")";
	}
}