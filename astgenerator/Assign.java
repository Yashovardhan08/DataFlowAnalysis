public class Assign implements Instr{
	public IdExpr id;
	public Expr expr;
	public Assign(String id1, Expr e1){
		this.id= new IdExpr(id1);
		this.expr=e1;
	}
	public String toString(){
		return this.id.toString() + " = " + this.expr.toString();
	}
	public int evaluate() {
		return 0;
	}
}