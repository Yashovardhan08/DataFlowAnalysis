public class GreaterBoolExpr implements Expr{
	public Expr e1,e2;
	GreaterBoolExpr(Expr e1,Expr e2){
		this.e1 = e1;
		this.e2 = e2;
	}
	public int evaluate(){
		return e1.evaluate() < e2.evaluate() ? 1 : 0;
	}

	public String toString() {
		String ret = e1.toString() + " > " + e2.toString() +" ";
		return ret;
	}
}