package simpleos.processor;

import simpleos.memory.*;


public  class MyProcessor extends Processor {

    private MyMemory PC;    
    private MyMemory IR;    
    private MyMemory ACC;    


    public int fetch(){
        System.out.println("Processor is now fetching..");
        return 1;
    } 
    public int execute(){
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
            case "0000": // <0> (HALT)
            
                break;
        
            default:
                break;
        }
        return 1;
    } 

} //end abstract class Processor
