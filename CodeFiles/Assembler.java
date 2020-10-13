package Code_2019228_2019412;

import Code_2019228_2019412.Opcodes;
import Code_2019228_2019412.Pass1;
import Code_2019228_2019412.Pass2;
import Code_2019228_2019412.Symbols;

public class Assembler {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("____________________________________________________________________________________");
		System.out.println("Starting Assembler");
		System.out.println("____________________________________________________________________________________");
		System.out.println();
		Pass1.dictmaker();
		int e = Pass1.file_reader();
		if(e == 1) {
			System.out.println("____________________________________________________________________________________");
			System.out.println("First Pass Unsuccessful");
			System.out.println("____________________________________________________________________________________");
			System.exit(0);
		}
		Pass1.operand_checker();
		Pass1.final_symbol_table_check();
		int f = Pass1.symbol_checker();
		System.out.println(); 
		if(f == 1) {
			System.out.println("____________________________________________________________________________________");
			System.out.println("First Pass Unsuccessful");
			System.out.println("____________________________________________________________________________________");
			System.exit(0);
		}
		System.out.println();
		System.out.println("____________________________________________________________________________________");
		System.out.println("First Pass Successful");
		System.out.println("____________________________________________________________________________________");
		System.out.println();
		System.out.println("_________________________________Symbol Table_______________________________________");
		for(Symbols s : Pass1.symbol_keeper) {
			System.out.println(s.toString());
		}
		System.out.println();
		System.out.println("_________________________________Opcode Table_______________________________________");
		for(Opcodes o : Pass1.op_keeper) {
			System.out.println(o.toString());
		}
		Pass2.symbol_addressor();
		Pass2.symbol_table_checker();
		Pass2.output_maker();
		Pass2.writer();
		System.out.println();
		System.out.println("____________________________________________________________________________________");
		System.out.println("Second Pass Successful");
		System.out.println("____________________________________________________________________________________");
		System.out.println();
		System.out.println();
		System.out.println("_________________________________Symbol Table_______________________________________");
		for(Symbols s : Pass1.symbol_keeper) {
			System.out.println(s.w_add_toString());
		}
		System.out.println();
//		for(String o : Pass2.output_keeper) {
//			System.out.println(o);
//		}
		System.out.println("____________________________________________________________________________________");
		System.out.println("Successful Assembly");
		System.out.println("____________________________________________________________________________________");
	}

}
