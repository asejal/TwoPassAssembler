package Code_2019228_2019412;

public class Opcodes {
	String assembly_op;
	String machine_op;
	int lineno;
	int operand; //no of operands (0 or 1)
	
	Opcodes(){
		assembly_op = null;
		machine_op = null;
		lineno = -1;
		operand = -1;
	}
	
	Opcodes(String ao, String mo, int lno, int o){
		assembly_op = ao;
		machine_op = mo;
		lineno = lno;
		operand = o;		
	}
	
	public String toString() {
		return ("Machine opcode: " + machine_op + "  Assembly opcode: " + assembly_op + "  Line number: " + lineno);
	}
}
