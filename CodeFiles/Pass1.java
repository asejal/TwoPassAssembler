package Code_2019228_2019412;

import Code_2019228_2019412.Symbols;
import Code_2019228_2019412.Opcodes;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.System;

public class Pass1 {
	
	static Dictionary<String,String> opcodes = new Hashtable<String,String>();
	
	static ArrayList<Symbols> symbol_keeper = new ArrayList<Symbols>();
	static ArrayList<Opcodes> op_keeper = new ArrayList<Opcodes>();

	public static void dictmaker() {	//i)
	opcodes.put("CLA","0000");
	opcodes.put("LAC","0001");
	opcodes.put("SAC","0010");
	opcodes.put("ADD","0011");
	opcodes.put("SUB","0100");
	opcodes.put("BRZ","0101");
	opcodes.put("BRN","0110");
	opcodes.put("BRP","0111");
	opcodes.put("INP","1000");
	opcodes.put("DSP","1001");
	opcodes.put("MUL","1010");
	opcodes.put("DIV","1011");
	opcodes.put("STP","1100");
	}
	

	public static int file_reader() throws Exception{		//ii)
		File ifile = new File("C:\\Users\\ASEJAL\\Desktop\\2019228_2019412_Project1\\2019228_2019412_Code\\2019228_2019412_Input.txt");
		BufferedReader reader = new BufferedReader(new FileReader(ifile));
		String text;					//read lines one by one from file
		int ino = 1;
		int num_of_instructns = 1;
		while((text = reader.readLine())!=null) {
			if(text.isBlank()) {
				ino = ino + 1;
				continue;
			}
			instructionmaker(text,ino);
			ino = ino + 1;
			num_of_instructns += 1;
		}
		int e = memory_check(num_of_instructns);
		reader.close();
		return e;
	}
	
	
	public static int check_for_valid_opcode(String instruct[]) {		//iii)
		for(int i = 0; i < instruct.length; i++) {
			if(((Hashtable<String, String>) opcodes).containsKey(instruct[i])) {
				return i;
			}
		}
		return -1;
	}
	
	
	public static void add_variable(String instruct[], int opcode_at,int inum) {		//xii)
		if(opcode_at + 1 == instruct.length) {
			return;
		}
		else if(!((Hashtable<String, String>) opcodes).containsKey(instruct[opcode_at + 1]) && !instruct[opcode_at + 1].startsWith("^^") && !branch_statement(instruct)) {
			symbol_keeper.add(new Symbols(instruct[opcode_at + 1],"variable",inum));
		}
	}
	
	
	public static boolean symbol_value_finder(String instruct[], int inum) {		//iv)
		if(instruct.length == 3 && (instruct[1].equalsIgnoreCase("DC") || instruct[1].equalsIgnoreCase("DB") || instruct[1].equalsIgnoreCase("DW"))) {
			return true;
		}
		else
			return false;
	}
	
	
	public static boolean branch_statement(String instruct[]) {		//v)
		if(instruct[0].equalsIgnoreCase("BRP") || instruct[0].equalsIgnoreCase("BRZ") || instruct[0].equalsIgnoreCase("BRN")) {
			return true;
		}
		else
			return false;
	}
	
	
	public static void final_symbol_table_check() {		//vi)
		for(Symbols s1 : symbol_keeper) {
			for(Symbols s2 : symbol_keeper) {
				if(s1.name.equalsIgnoreCase(s2.name) && s1.lineno!=s2.lineno) {
					if(s1.val != s2.val) {
						if(s1.val == 0 && s2.val != 0 && s2.val!=-1) {
							s1.val = s2.val;
						}
						else if(s1.val == 0 && s2.val!=0 && s2.val == -1) {
							s2.val = s1.val;
						}
						else {
							s2.val = s1.val;
						}
					}
					if(s1.lineno == -1 || s2.lineno == -1) {
						s1.lineno = -1;
						s2.lineno = -1;
					}
				}
			}
		}
	}
	
	
	public static void operand_checker() {		//vii)
		boolean flag = false;
		for(Opcodes opcode : op_keeper) {
				if(opcode.assembly_op.equalsIgnoreCase("STP") || opcode.assembly_op.equalsIgnoreCase("CLA")) {
					for(Symbols s : symbol_keeper) {
						if(opcode.lineno == s.lineno) {
							if(s.type.equalsIgnoreCase("variable")) {
								System.out.println("WARNING! MultipleOperandsException");
								System.out.println("--- No operands needed for opcode " + opcode.assembly_op + " at line " + opcode.lineno + " ---");
								clean_operands(opcode.lineno,0);
								continue;
							}
						}
					}
					if(opcode.assembly_op.equalsIgnoreCase("STP")) {
						flag = true;
					}
				}
				else {
					if(opcode.assembly_op.equalsIgnoreCase("BRP") || opcode.assembly_op.equalsIgnoreCase("BRZ") || opcode.assembly_op.equalsIgnoreCase("BRN")) {
						continue;
					}
					int count = 0;
					int inum = opcode.lineno;
					for(Symbols s : symbol_keeper) {
						if(opcode.lineno == s.lineno && s.type.equalsIgnoreCase("variable")) {
							count += 1;
							inum = opcode.lineno;
						}
					}
					if(count == 0) {
						System.out.println("WARNING! ZeroOperandsException");
						System.out.println("--- No operand is supplied for opcode " + opcode.assembly_op + " at line " + inum + " ---");
						line_cleaner(inum);
						continue;
					}
				}
		}
		if(!flag) {
			System.out.println("WARNING! StopStatementMissingException");
			System.out.println("--- Stop statement missing in program ---");
		}
	}
	
	public static void clean_operands(int inum, int n_of_op) {		//viii)
		int count = 0;
		for(Symbols s : symbol_keeper) {
			if(s.lineno == inum) {				
				count += 1;
				if(count > n_of_op) {
					s.lineno = -1;
				}
			}
		}
	}
	
	
	public static void line_cleaner(int inum) {	//to clean line ix)
		for(Opcodes o : op_keeper) {
			if(o.lineno == inum) {
				o.lineno = -1;
			}
		}
		for(Symbols s : symbol_keeper) {
			if(s.lineno == inum) {
				s.lineno = -1;
			}
		}
	}
	
	public static int symbol_checker() {		//x)
		int flag = 0;
		for(Symbols s : symbol_keeper) {
			if(s.val == -1 && s.type.equalsIgnoreCase("variable")) {
				System.out.println("ERROR! UndefinedSymbolException");
				System.out.println("--- Symbol " + s.name + " is used at line " + s.lineno + " but never defined ---");
				flag = 1;
			}
		}
		return flag;
	}
	

	public static int memory_check(int ino) {	//xiii)
		int flag = 0;
		if(ino>127) {
			flag = 1;
			System.out.println("ERROR! NotEnoughMemoryException");
			System.out.println("--- Program cannot be compiled since enough memory is not available ---");
		}
		return flag;
	}
	

	public static void instructionmaker(String instruction, int ino) {		//xi)
		if(instruction.startsWith("^^")) {
			System.out.println("We note your comment");
			return;
		}
		String cur_instruct[] = instruction.trim().split("\\s+");
		if(cur_instruct.length > 2 && !((Hashtable<String, String>) opcodes).containsKey(cur_instruct[0])) {
			if(!cur_instruct[1].equalsIgnoreCase("DC") && !cur_instruct[1].equalsIgnoreCase("DB") && !cur_instruct[1].equalsIgnoreCase("DW")) { // check for label
				symbol_keeper.add(new Symbols(cur_instruct[0],"label",ino)); // add cur_instruct[0] to symbol table
			}
		}
		if(branch_statement(cur_instruct)) {
			symbol_keeper.add(new Symbols(cur_instruct[1],"label",ino,-9));
		}
		int valid_opcode_at = check_for_valid_opcode(cur_instruct);
		if(valid_opcode_at>=0) {
			if(cur_instruct[valid_opcode_at].equalsIgnoreCase("STP") || cur_instruct[valid_opcode_at].equalsIgnoreCase("CLA")) {
				op_keeper.add(new Opcodes(cur_instruct[valid_opcode_at],opcodes.get(cur_instruct[valid_opcode_at]),ino,0)); //put into opcode table
			}
			else {
				if(cur_instruct.length > (valid_opcode_at + 2) && !cur_instruct[valid_opcode_at + 2].startsWith("^^")) {
					System.out.println("WARNING! MultipleOperandsException");
					System.out.println("--- Only one operand needed for opcode " + cur_instruct[valid_opcode_at] + " at line " + ino + " ---"); 
				}
				op_keeper.add(new Opcodes(cur_instruct[valid_opcode_at],opcodes.get(cur_instruct[valid_opcode_at]) ,ino,1)); //put cur_instruct[i] in operand table
			}
		}
		else if(cur_instruct.length == 2 && valid_opcode_at<0){
			System.out.println("WARNING! IllegalOpcodeException");
			System.out.println("--- Invalid Opcode at line " + ino + " ---");
			line_cleaner(ino);
			return;
		}		
		add_variable(cur_instruct,valid_opcode_at,ino); 
		if(symbol_value_finder(cur_instruct,ino)) {
			for(Symbols s : symbol_keeper) {
			if(s.name.equalsIgnoreCase(cur_instruct[0])) {
				if(s.val != -1) {
					System.out.println("WARNING! MultipleDefinitionsForSymbolException");
					System.out.println("--- Symbol "+s.name+" has been defined more than once at line "+ ino +" ---");
				}
				s.val = Integer.parseInt(cur_instruct[2].substring(1,cur_instruct[2].length()-1)); //write value of symbol in symbol table as cur_instruct[1]
				break;
			}
			}
		}
		return;
	}
}