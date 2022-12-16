public class FCall implements Expr{
	public String fname;
	public ExprList args;
	public FCall(String id,ExprList el){
		this.fname = id;
		this.args = el;
	}
	public String toString() {
		String ret = fname + "(" +args.toString() +")\n";
		return ret;
	}
	public int evaluate(){
		return 0;
	}
}
