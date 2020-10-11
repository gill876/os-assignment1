package simpleos.processor;

import simpleos.memory.*;
import java.util.List;
import java.util.ArrayList;


public class MyProcessor extends Processor {

    private MyMemory PC;    
    private MyMemory IR;    
    private MyMemory ACC;

    private int counter;
    private int exec = 1;
    private int cache[];
    private int gotoC;

    private MyMemory Memory;

    private int stdin[];
    private int stdinC;
    private int stdout;

    public MyProcessor(MyMemory Memory, int stdin[]) {
        this.PC = new MyMemory(1);
        this.IR = new MyMemory(1);
        this.ACC = new MyMemory(1);
        this.Memory = Memory;
        this.counter = 0;
        this.PC.setValue(0, counter);

        setStdin(stdin);
        this.stdinC = 0;
        this.cache = new int[4];
        this.cache[0] = 0;
        this.gotoC = 0;
    }

    public void setStdin(int stdin[]) {
        this.stdin = stdin;
    }

    public int getStdin() {
        int stdiin = this.stdin[this.stdinC];
        this.stdinC++;
        return stdiin;
    }

    public void setStdout(int stdout) {
        this.stdout = stdout;
    }

    public int getStdout() {
        return this.stdout;
    }

    public int fetch() {
        System.out.println("Processor is now fetching..");
        int pc = this.PC.getValue(0);
        int memval = this.Memory.getValue(pc);
        this.IR.setValue(0, memval); 
        return this.exec;
    } 
    public int execute() {
        this.exec = 1;
        System.out.println("Processor is now execting..");
        String instruction = intToBinaryHex(this.IR.getValue(0));
        String opcode = instruction.substring(0, 4);
        String address = instruction.substring(4, 16);
        switch (opcode) {
            case "0001": // <1> Load AC from memory
                int value = getValueFromMemLoc(address);
                this.ACC.setValue(0, value);
                System.out.println("\n\t***Loaded AC from memory***\n");
                break;
            case "0010": // <2> Store AC to memory
                int acVal = this.ACC.getValue(0);
                int index = hexBinarytoInt(address);
                this.Memory.setValue(index, acVal);
                System.out.println("\n\t***Stored AC to memory***\n");
                break;
            case "0101": // <5> Add to AC from memory
                int acSum = getValueFromMemLoc(address);
                int acPrev = this.ACC.getValue(0);
                acSum+= acPrev;
                this.ACC.setValue(0, acSum);
                System.out.println("\n\t***Added to AC from memory***\n");
                break;
            case "0100": // <4> Subtract from AC from memory
                int acDiff = getValueFromMemLoc(address);
                int acPrev2 = this.ACC.getValue(0);
                acPrev2-= acDiff;
                this.ACC.setValue(0, acPrev2);
                System.out.println("\n\t***Subtracted to AC from memory***\n");
                break;
            case "0011": // <3> Load AC from stdin
                int stdIn = getStdin();
                this.ACC.setValue(0, stdIn);
                System.out.println("\n\t***Loaded AC from stdin***\n");
                break;
            case "0111": // <7> Store AC to stdout
                int stdOut = this.ACC.getValue(0);
                setStdout(stdOut);
                System.out.println("\n\t***Stored AC to stdout***\n");
                this.exec = 2;
                break;
            case "0110": // <6> (GOTO)
                // System.out.println(this.gotoC);
                if (this.cache[0] == 0) {
                    this.cache[0] = 1; // set that it should expect the amount of times it should goto
                    // the location in memory specified

                    this.cache[1] = hexBinarytoInt(address); // the memory address it should go to

                    // Increment PC
                    int pc = this.PC.getValue(0);
                    pc++;

                    this.PC.setValue(0, pc);
                    return this.exec;
                } else if(this.cache[0] == 1){
                    this.cache[2] = hexBinarytoInt(address); // amount of times it should go to the location
                    this.cache[3] = this.PC.getValue(0); // Save current PC value
                    this.PC.setValue(0, this.cache[1]); // Bring PC back to declared state
                    this.cache[0] = 2; // Change so that it goes to the else statement
                    System.out.println("\n\t###LOOP START###\n");
                    return this.exec;
                } else {
                    if (this.gotoC == this.cache[2]) { // Check if the amount of times has reached
                        // End of GOTO
                        this.PC.setValue(0, this.cache[3]); // Restore PC value
                        // Reset cache
                        this.cache = new int[4];
                        this.cache[0] = 0;
                        System.out.println("\n\t###LOOP END###\n");
                    }

                    if (this.PC.getValue(0) == this.cache[3]) { // If the PC reaches the stored state but the amount
                        // of times has not reached the declared amount, reset the PC
                        this.PC.setValue(0, this.cache[1]); // Bring PC back
                        return this.exec;
                    }

                    this.gotoC++; // Increment the amount of times
                }
                break;
            case "1000": // <8> (COMP)
            
                break;
            case "1001": // <9> (HALT)
                this.exec = -1;
                System.out.println("\n\t***The processor will halt***\n");
                break;
        
            default:
                break;
        }

        // Increment PC
        int pc = this.PC.getValue(0);
        pc++;

        this.PC.setValue(0, pc);

        return this.exec;
    }

    public int getValueFromMemLoc(String address) {
        // Get value from Memory
        int memloc = hexBinarytoInt(address);
        int memvalue = this.Memory.getValue(memloc);
        String memv = "0000" + intToBinaryHex(memvalue).substring(4, 16);
        int value = hexBinarytoInt(memv);
        return value;
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
        if (output.length() < 16) {
            int count = 16 - output.length();
            output = new String(new char[count]).replace("\0", "0") + output;
        }

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
