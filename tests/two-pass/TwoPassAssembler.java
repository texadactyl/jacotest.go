// hacked from https://www.pracspedia.com/SPCC/2passjava.html
/*
	Program to implement 2 Pass Assembler in Java
	Author: Manav Sanghavi		Author Link: https://www.facebook.com/manav.sanghavi
	www.pracspedia.com
*/
import java.util.*;
import java.io.*;

class Tuple {
	String mnemonic, bin_opcode, type;
	int length;
	
	Tuple() {}
	
	Tuple(String s1, String s2, String s3, String s4) {
		mnemonic = s1;
		bin_opcode = s2;
		length = Integer.parseInt(s3);
		type = s4;
	}
}

class SymTuple {
	String symbol, ra;
	int value, length;
	
	SymTuple(String s1, int i1, int i2, String s2) {
		symbol = s1;
		value = i1;
		length = i2;
		ra = s2;
	}
}

class LitTuple {
	String literal, ra;
	int value, length;
	
	LitTuple() {}
	
	LitTuple(String s1, int i1, int i2, String s2) {
		literal = s1;
		value = i1;
		length = i2;
		ra = s2;
	}
}

class TwoPassAssembler {
	static int lc;
	static LinkedList<Tuple> mot;
	static LinkedList<String> pot;
	static LinkedList<SymTuple> symtable;
	static LinkedList<LitTuple> littable;
	static ArrayList<Integer> lclist;
	static AltHashMap basetable;
	static FileOutputStream output_pass2;
	static FileOutputStream output_pass1;
	static int line_no;
	static byte[] NL = System.lineSeparator().getBytes();
	
	static void pass1() throws Exception {
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("input_source_code.txt")));
		output_pass1 = new FileOutputStream("output_pass1.txt");
		FileOutputStream output_symtable = new FileOutputStream("output_symtable.txt");
		FileOutputStream output_littable = new FileOutputStream("output_littable.txt");
		String s;
		while((s = input.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(s, " ", false);
			String s_arr[] = new String[st.countTokens()];
			for(int i=0 ; i < s_arr.length ; i++) {
				s_arr[i] = st.nextToken();
			}
			if(!searchPot1(s_arr)) {
				searchMot1(s_arr);
				output_pass1.write(s.getBytes());
				output_pass1.write(NL);
			}
			lclist.add(lc);
		}
		int j;
		String output = new String();
		System.out.println("Symbol Table:");
		System.out.println("Symbol    Value  Length   R/A");
		for(SymTuple i : symtable) {
			output = i.symbol;
			for(j=i.symbol.length() ; j < 10 ; j++) {
				output = output.concat(" ");
			}
			output = output.concat(String.format("%d", i.value));
			for(j = String.valueOf(i.value).length() ; j < 7 ; j++) {
				output = output.concat(" ");
			}
			String wstr = String.format("%d        %s", i.length, i.ra);
			output = output.concat(wstr);

			System.out.println(output);
			output_symtable.write(output.getBytes());
			output_symtable.write(NL);
		}
		System.out.println("\nLiteral Table:");
		System.out.println("Literal   Value  Length   R/A");
		for(LitTuple i : littable) {
			output = i.literal;
			for(j=i.literal.length() ; j < 10 ; j++) {
				output = output.concat(" ");
			}
			output = output.concat(String.format("%d", i.value));
			for(j = String.valueOf(i.value).length() ; j < 7 ; j++) {
				output = output.concat(" ");
			}
			String wstr = String.format("%d        %s", i.length, i.ra);
			output = output.concat(wstr);
			System.out.println(output);
			output_littable.write(output.getBytes());
			output_littable.write(NL);
		}
	}
	
	static void pass2() throws Exception {
		line_no = 0;
		output_pass2 = new FileOutputStream("output_pass2.txt");
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("output_pass1.txt")));
		String s;
		System.out.println("Pass 2 input:");
		while((s = input.readLine()) != null) {
			System.out.println(s);
			StringTokenizer st = new StringTokenizer(s, " ", false);
			String s_arr[] = new String[st.countTokens()];
			for(int i=0 ; i < s_arr.length ; i++) {
				s_arr[i] = st.nextToken();
			}
			if(!searchPot2(s_arr)) {
				searchMot2(s_arr);
			}
			line_no++;
		}
		System.out.println("\nPass 2 output:");
		input = new BufferedReader(new InputStreamReader(new FileInputStream("output_pass2.txt")));
		while((s = input.readLine()) != null) {
			System.out.println(s);
		}
	}
	
	static boolean searchPot1(String[] s) {
		int i = 0;
		int l = 0;
		int potval = 0;
		
		if(s.length == 3) {
			i = 1;
		}
		s = tokenizeOperands(s);
		
		if(s[i].equalsIgnoreCase("DS") || s[i].equalsIgnoreCase("DC")) {
			potval = 1;
		}
		if(s[i].equalsIgnoreCase("EQU")) {
			potval = 2;
		}
		if(s[i].equalsIgnoreCase("START")) {
			potval = 3;
		}
		if(s[i].equalsIgnoreCase("LTORG")) {
			potval = 4;
		}
		if(s[i].equalsIgnoreCase("END")) {
			potval = 5;
		}
		
		switch(potval) {
			case 1:
				// DS or DC statement
				String x = s[i+1];
				int index = x.indexOf("F");
				if(i == 1) {
					symtable.add(new SymTuple(s[0], lc, 4, "R"));
				}
				if(index != 0) {
					// Ends with F
					l = Integer.parseInt(x.substring(0, x.length()-1));
					l *= 4;
				} else {
					// Starts with F
					for(int j=i+1 ; j<s.length ; j++) {
						l += 4;
					}
				}
				lc += l;
				return true;
			
			case 2:
				// EQU statement
				if(!s[2].equals("*")) {
					symtable.add(new SymTuple(s[0], Integer.parseInt(s[2]), 1, "A"));
				} else {
					symtable.add(new SymTuple(s[0], lc, 1, "R"));
				}
				return true;
			
			case 3:
				// START statement
				symtable.add(new SymTuple(s[0], Integer.parseInt(s[2]), 1, "R"));
				return true;
			
			case 4:
				// LTORG statement
				ltorg(false);
				return true;
			
			case 5:
				// END statement
				ltorg(true);
				return true;
		}
		return false;
	}
	
	static void searchMot1(String[] s) {
		Tuple t = new Tuple();
		int i = 0;
		if(s.length == 3) {
			i = 1;
		}
		s = tokenizeOperands(s);
		for(int j=i+1 ; j < s.length ; j++) {
			if(s[j].startsWith("=")) {
				littable.add(new LitTuple(s[j].substring(1, s[j].length()), -1, 4, "R"));
			}
		}
		if((i == 1) && (!s[0].equalsIgnoreCase("END"))) {
			symtable.add(new SymTuple(s[0], lc, 4, "R"));
		}
		for(Tuple x : mot) {
			if(s[i].equals(x.mnemonic)) {
				t = x;
				break;
			}
		}
		lc += t.length;
	}
	
	static void ltorg(boolean isEnd) {
		Iterator<LitTuple> itr = littable.iterator();
		LitTuple lt = new LitTuple();
		boolean isBroken = false;
		while(itr.hasNext()) {
			lt = itr.next();
			if(lt.value == -1) {
				isBroken = true;
				break;
			}
		}
		if(!isBroken) {
			return;
		}
		if(!isEnd) {
			while(lc%8 != 0) {
				lc++;
			}
		}
		lt.value = lc;
		lc += 4;
		while(itr.hasNext()) {
			lt = itr.next();
			lt.value = lc;
			lc += 4;
		}
	}
	
    // Binary search a linked list.
    static int binarySearch(LinkedList<String> list, String key) {
        int low = 0;
        int high = list.size() - 1;
        
        while (low <= high) {
            int mid = (low + high) >>> 1;
            String midVal = list.get(mid);
            int cmp = midVal.compareTo(key);
            
            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1); // key not found
    }

	static boolean searchPot2(String[] s) {
		int i = 0;
		
		if(s.length == 3) {
			i = 1;
		}
		if(binarySearch(pot, s[i]) >= 0) {
			if(s[i].equalsIgnoreCase("USING")) {
				s = tokenizeOperands(s);
				
				if(s[i+1].equals("*")) {
					s[i+1] = String.format("%d", lclist.get(line_no));
				} else {
					for(int j=i+1 ; j<s.length ; j++) {
						int value = getSymbolValue(s[j]);
						if(value != -1) {
							s[j] = String.format("%d", value);
						}
					}
				}
				//System.out.printf("DEBUG searchPot2 i: %d, s[i]: %s, s[i+1]: %s, s[i+2]: %s\n", i, s[i], s[i+1], s[i+2]);
				basetable.put(Integer.parseInt(s[i+2].trim()), Integer.parseInt(s[i+1].trim()));
			}
			return true;
		}
		return false;
	}
	
	static void searchMot2(String[] s) throws IOException {
		Tuple t = new Tuple();
		int i = 0;
		int j;
		
		if(s.length == 3) {
			i = 1;
		}
		s = tokenizeOperands(s);
		
		for(Tuple x : mot) {
			if(s[i].equals(x.mnemonic)) {
				t = x;
				break;
			}
		}
		
		String output = new String();
		String mask = new String();
		if(s[i].equals("BNE")) {
			mask = "7";
		} else if(s[i].equals("BR")) {
			mask = "15";
		} else {
			mask = "0";
		}
		if(s[i].startsWith("B")) {
			if(s[i].endsWith("R")) {
				s[i] = "BCR";
			} else {
				s[i] = "BC";
			}
			ArrayList<String> temp = new ArrayList<>();
			for(String x : s) {
				temp.add(x);
			}
			temp.add(i+1, mask);
			//s = temp.toArray(new String[temp.size()]); // <------------- CHECKCAST issue
			s = new String[temp.size()];
            for (int ii = 0; ii < temp.size(); ii++) {
                s[ii] = temp.get(ii);
            }
		}
		if(t.type.equals("RR")) {
			output = s[i];
			for(j=s[i].length() ; j<6 ; j++) {
				output = output.concat(" ");
			}
			for(j=i+1 ; j<s.length ; j++) {
				int value = getSymbolValue(s[j]);
				if(value != -1) {
					s[j] = String.format("%d", value);
				}
			}
			output = output.concat(s[i+1]);
			for(j=i+2 ; j<s.length ; j++) {
				output = String.format("%s, %s", output, s[j]);
			}
		} else {
			output = s[i];
			for(j=s[i].length() ; j<6 ; j++) {
				output = output.concat(" ");
			}
			for(j=i+1 ; j<s.length-1 ; j++) {
				int value = getSymbolValue(s[j]);
				if(value != -1) {
					s[j] = String.format("%d", value);
				}
			}
			s[j] = createOffset(s[j]);
			output = output.concat(s[i+1]);
			for(j=i+2 ; j<s.length ; j++) {
				output = String.format("%s, %s", output, s[j]);
			}
		}
		output_pass2.write(output.getBytes());
		output_pass2.write(NL);
	}
	
	static String createOffset(String s) {
		String original = s;
		Integer[] key = basetable.getKeys();
		int offset, new_offset;
		int index = 0;
		int value = -1;
		int index_reg = 0;
		if(s.startsWith("=")) {
			value = getLiteralValue(s);
		} else {
			int parenthesis = s.indexOf("(");
			String index_string = new String();
			if(parenthesis != -1) {
				s = s.substring(0, s.indexOf("("));
				index_string = original.substring(original.indexOf("(")+1, original.indexOf(")"));
				index_reg = getSymbolValue(index_string);
			}
			value = getSymbolValue(s);
		}
		offset = Math.abs(value - basetable.get(key[index]));
		for(int i=1 ; i<key.length ; i++) {
			new_offset = Math.abs(value - basetable.get(key[i]));
			if(new_offset < offset) {
				offset = new_offset;
				index = i;
			}
		}
		String result = String.format("%d(%d, %d)", offset, index_reg, key[index]);
		return result;
	}
	
	static int getSymbolValue(String s) {
		for(SymTuple st : symtable) {
			if(s.equalsIgnoreCase(st.symbol)) {
				return st.value;
			}
		}
		return -1;
	}
	
	static int getLiteralValue(String s) {
		s = s.substring(1, s.length());
		for(LitTuple lt : littable) {
			if(s.equalsIgnoreCase(lt.literal)) {
				return lt.value;
			}
		}
		return -1;
	}
	
	static String[] tokenizeOperands(String[] s) {
		LinkedList<String> temp = new LinkedList<>();
		for(int j=0 ; j<s.length-1 ; j++) {
			temp.add(s[j]);
		}
		StringTokenizer st = new StringTokenizer(s[s.length-1], " ,", false);
		while(st.hasMoreTokens()) {
			temp.add(st.nextToken());
		}
		s = temp.toArray(new String[0]);
		return s;
	}
	
	static void initializeTables() throws Exception {
		symtable = new LinkedList<>();
		littable = new LinkedList<>();
		lclist = new ArrayList<>();
		basetable = new AltHashMap();
		mot = new LinkedList<>();
		pot = new LinkedList<>();
		String s;
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(new FileInputStream("machine_opcode_table.txt")));
		Tuple tuple;
		while((s = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(s, " ", false);
			tuple = new Tuple(st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken());
			mot.add(tuple);
		}
		br = new BufferedReader(new InputStreamReader(new FileInputStream("pseudo_opcode_table.txt")));
		while((s = br.readLine()) != null) {
			pot.add(s);
		}
		
	}
}

class AltHashMap {
    HashMap<Integer, Integer> map = new HashMap<>();
    Integer[] keys = new Integer[100];
    int keyCount = 0;
    
    void put(Integer k, Integer v) {
        if (!map.containsKey(k)) {
            // Expand array if needed
            if (keyCount >= keys.length) {
                Integer[] newKeys = new Integer[keys.length * 2];
                System.arraycopy(keys, 0, newKeys, 0, keys.length);
                keys = newKeys;
            }
            keys[keyCount] = k;
            keyCount++;
        }
        map.put(k, v);
    }
    
    Integer get(Integer k) {
        return map.get(k);
    }
    
    boolean containsKey(Integer k) {
        return map.containsKey(k);
    }
    
    int size() {
        return keyCount;
    }
    
    Integer[] getKeys() {
        Integer[] giveKeys = new Integer[keyCount];
        for (int ii = 0; ii < keyCount; ii++)
            giveKeys[ii] = keys[ii];
        return giveKeys;
    }
}
