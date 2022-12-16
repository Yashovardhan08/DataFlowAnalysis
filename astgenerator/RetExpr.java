public class RetExpr implements Instr{
	public Expr expr;
	RetExpr(Expr e){
		this.expr = e;
	}
	@Override
	public String toString() {
		return "return "+ expr.toString()+" ";
	}
}