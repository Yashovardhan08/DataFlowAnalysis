public class WhileExpr implements Expr {
	public Expr e;
	public Block b;

	WhileExpr(Expr e,Block b){
		this.e = e;
		this.b = b;
	}

	public int evaluate() {
		return 0;
	}

	public String toString() {
		String ret = "while(" + this.e.toString() +") "+ this.b.toString();
		return ret;
	}
}
