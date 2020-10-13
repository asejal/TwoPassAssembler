package Code_2019228_2019412;

public class Symbols {
	String name;
	String type;
	String address;
	int lineno;
	int val = -1;
	
	Symbols() {
		name = null;
		type = null;	//default constructor
		address = null;
		lineno = -1;
		val = -1;
	}
	
//	Symbols(String t, int lno, int v){	//for literals
//		name = null;
//		type = t;
//		lineno = lno;
//		val = v;
//	}
	
	Symbols(String nom, String t, int lno) {
		name = nom;
		type = t;		//for labels and variables
		lineno = lno;	
	}
	
	Symbols(String nom, String t, int lno,int v) {
		name = nom;
		type = t;		//for Pass2 final check
		lineno = lno;
		val = v;
	}
	
	public String toString() {
		String n = String.format("%1$" + 10 + "s", name);
		String t = String.format("%1$" + 10 + "s", type);
		String l = String.format("%1$" + 5 + "s", lineno);
		return ("Name: " + n + "  Type: " + t + "  Line: " + l + "  Value: " + val);
	}
	
	public String w_add_toString() {
		String n = String.format("%1$" + 10 + "s", name);
		String t = String.format("%1$" + 10 + "s", type);
		String l = String.format("%1$" + 5 + "s", lineno);
		String v = String.format("%1$" + 5 + "s", val);
		return ("Name: " + n + "  Type: " + t + "  Line: " + l + "  Value: " + v + " Address: " + address);
	}
}
