package Code_2019228_2019412;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import Code_2019228_2019412.Opcodes;
import Code_2019228_2019412.Pass1;
import Code_2019228_2019412.Symbols;

public class Pass2 {
	static ArrayList<String> output_keeper = new ArrayList<String>();
	static ArrayList<String> sym = new ArrayList<String>();
	
	public static int counter(String symname) {	//i)
		int count = 0;
		for(String s1 : sym) {
			if(s1.equalsIgnoreCase(symname)) {
					count += 1;
			}
		}
		return count;
	}
	
	
	public static void symbol_table_checker() {	//ii)
		for(Symbols s1 : Pass1.symbol_keeper) {
			for(Symbols s2 : Pass1.symbol_keeper) {
				if(s1.name.equalsIgnoreCase(s2.name) && s1.lineno!=s2.lineno) {
					if(s1.address != s2.address) {
						if(s1.type.equalsIgnoreCase("variable")) {
							if(s1.address == null && s2.address != null) {
								s1.address = s2.address;
							}
							else {
								s2.address = s1.address;
							}
						}
						else {
							if(s2.val != -9) {
								s1.address = s2.address;
							}
							else {
								s2.address = s1.address;
							}
						}
					}
				}
			}
		}
	}
	
	
	public static void symbol_addressor() {		//iii)
		int i = 0;
		for(Symbols s1 : Pass1.symbol_keeper) {
			sym.add(s1.name);
			if(s1.type.equalsIgnoreCase("label")) {
				s1.address = formatter(0 + s1.lineno - 1);
			}
			else if(counter(s1.name) == 1) {
				s1.address = formatter(128 + i);
				i += 1;
			}
		}
	}
	
	
	public static String formatter(int a) {		//iv)
		return String.valueOf(String.format("%08d", Integer.parseInt(Integer.toBinaryString(a))));
	}

	
	public static void output_maker() {	//v)
		int j = 0;
		for(Opcodes o : Pass1.op_keeper) {
			if(o.assembly_op.equalsIgnoreCase("STP")) {
				output_keeper.add(formatter(0+j) + "   " + o.machine_op);
				break;
			}
			else if(o.assembly_op.equalsIgnoreCase("CLA")) {
				output_keeper.add(formatter(0+j) + "   " + o.machine_op);
				continue;
			}
			for(Symbols s : Pass1.symbol_keeper) {
				if(o.assembly_op.equalsIgnoreCase("BRP") || o.assembly_op.equalsIgnoreCase("BRZ") || o.assembly_op.equalsIgnoreCase("BRN")) {
					if(o.lineno == s.lineno && s.type.equalsIgnoreCase("label")) {
						output_keeper.add(formatter(0+j) + "   " + o.machine_op + "   " + s.address);
						break;
					}
				}
				if(o.lineno == s.lineno && s.lineno != -1 && s.type.equalsIgnoreCase("variable")) {
					output_keeper.add(formatter(0+j) + "   " + o.machine_op + "   " + s.address);
					break;
				}
			}
		j += 1;
		}
	}
	

	public static void writer() throws IOException{		//vi)
		File fl = new File("2019228_2019412_Output.txt");
		FileOutputStream stream = new FileOutputStream(fl);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));
		writer.write(" Address     Machine Code");
		writer.newLine();
		for(String o : output_keeper) {
			writer.write(o);
			writer.newLine();
		}
		writer.close();
	}
}
