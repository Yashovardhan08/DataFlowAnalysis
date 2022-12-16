public class Type {
	public final String name;
  
	  public Type(String name) {
	  this.name = name;
	  }
  
	public boolean equals(Object type) {
	  if(!(type instanceof Type)) {
			return false;
		  }
		  return this.name.equals(((Type)type).name);
	  }
  
	  public String toString() {
		return this.name;
	  }
  }
  