public class IfStatement implements Expr{
	public Block b1,b2;
	public Expr e1;
	IfStatement(Expr e1,Block b1,Block b2){
		this.b1 = b1;
		this.b2 = b2;
		this.e1 = e1;
	}

	public String toString() {
		// TODO Auto-generated method stub
		String ret = "";
		ret += "if(" + e1.toString() +") "+ b1.toString();
		if(b2!=null){
			ret = ret + "\nelse "+ b2.toString()+"\n";
		}
		return ret;
	}

	public int evaluate() {
		// TODO Auto-generated method stub
		return b1.evaluate();
	}
}
