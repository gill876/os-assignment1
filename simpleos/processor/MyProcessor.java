package simpleos.processor;

import simpleos.memory.*;
import java.util.List;
import java.util.ArrayList;


public class MyProcessor extends Processor {

    private MyMemory PC;    
    private MyMemory IR;    
    private MyMemory ACC;

    private MyMemory Memory;

    private int stdin;
    private int stdout;

    public MyProcessor(MyMemory PC, MyMemory IR, MyMemory ACC, MyMemory Memory) {
        this.PC = PC;
        this.IR = IR;
        this.ACC = ACC;
        this.Memory = Memory;
    }

    public void setStdin(int stdin) {
        this.stdin = stdin;
    }

    public int getStdin() {
        return this.stdin;
    }

    public void setStdout(int stdout) {
        this.stdout = stdout;
    }

    public int getStdout() {
        return this.stdout;
    }

    public int fetch() {
        System.out.println("Processor is now fetching..");
        
        return 1;
    } 
    public int execute() {
        System.out.println("Processor is now execting..");
        String instruction = "";
        switch (instruction) {
            case "0001": // <1> Load AC from memory
                
                break;
            case "0010": // <2> Store AC to memory
            
                break;
            case "0101": // <5> Add to AC from memory
                
                break;
            case "0100": // <4> Subtract from AC from memory
            
                break;
            case "0011": // <3> Load AC from stdin
                
                break;
            case "0111": // <7> Store AC to stdout
            
                break;
            case "0110": // <6> (GOTO)
                
                break;
            case "1000": // <8> (COMP)
            
                break;
            case "1001": // <9> (HALT)
            
                break;
        
            default:
                break;
        }
        return 1;
    }

    public static String intToBinaryHex(int number) {
        String hex0[] = new String[4]; // store 12 bit binary hex
        String output = "";
        int pos = 0;
        for (String i: (String.valueOf(number)).split("")) { // Traverse through each digit and turn into String
            hex0[pos] = Integer.toBinaryString(Integer.parseInt(i)); // convert String into binary 
            if (hex0[pos].length() < 4) { // make into 4 bit binary by adding 0's
                int count = 4 - hex0[pos].length();
                hex0[pos] = new String(new char[count]).replace("\0", "0") + hex0[pos]; // pad binary with 0's to make into 4 bit
            }
            //System.out.println(i);
            output+= hex0[pos];
            pos++;
        }
        //System.out.println(output);
        return output;
    }
    
    public static int hexBinarytoInt(String hexbin) {
        int integer = 0;
        String fullInt = "";

        for (String i: splitStringFixedLen(hexbin, 4)) {
            fullInt+= Integer.parseInt(i, 2); // Convert from binary to decimal
        }

        integer = Integer.parseInt(fullInt); // Turn string to integer
        return integer;
    }
    
    // http://www.java2s.com/example/java-utility-method/string-split-by-length/splitstringfixedlen-string-data-int-interval-d9c70.html
    public static String[] splitStringFixedLen(String data, int interval) {
        List<String> dataPiece = new ArrayList<String>();

        int addedOffset;
        for (int offset = 0; offset < data.length(); offset += addedOffset) {
            String subData = data.substring(offset,
                    Math.min(data.length(), (offset + interval)));
            addedOffset = subData.lastIndexOf('\n');
            if (addedOffset >= 0) {
                subData = subData.substring(0, addedOffset);
                ++addedOffset;
            } else {
                addedOffset = interval;
            }
            dataPiece.add(subData);
        }
    
        String[] result = new String[dataPiece.size()];
        dataPiece.toArray(result);
        return result;
    }

} //end class Processor
