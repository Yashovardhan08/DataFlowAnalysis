public class Block {
	public InstrList ilist ;
	public Type returnType; 
	Block(InstrList i){
		this.ilist = i;
		this.returnType = new VoidType(); 
	}
	public String toString(){
		return "{" + ilist.toString() + "}";
	}
	public int evaluate(){
		return 0;
	}
}