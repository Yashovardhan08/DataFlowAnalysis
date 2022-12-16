public class FunctionDeclaration implements Instr{
	public Type type;
	public IdExpr id;
	public Block bl;
	public DeclarationList dl;
	FunctionDeclaration(Type type, IdExpr id, DeclarationList dl, Block bl){
		this.type = type ;
		this.id = id;
		this.bl = bl;
		this.dl = dl;
	}
	public String toString(){
		return type.toString() +" " +id.toString() +" ("+ dl.toString() +") "+ bl.toString();
	}
}
