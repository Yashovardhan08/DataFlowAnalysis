public class Program{
	public InstrList instrList;
	public Program(InstrList il) {
		this.instrList = il;
	}
	public String toString() {
		return instrList.toString();
		// return " Program :\n" + this.iList.toString() + "\n";
	}
}
