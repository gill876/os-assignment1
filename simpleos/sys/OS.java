package simpleos.sys;

import simpleos.memory.*;
import simpleos.processor.*;


public class OS {

    public static void main(String[] args){

        try {
            // 1 = a
            // 2 = b
            /**Instructions for Fibonacci sequence*/
            int instructions[] = {
                // Load AC from stdin
                3000,
                // Store AC to memory
                2001, // addr 1
                // Load AC from stdin
                3000,
                // Store AC to memory
                2002, // addr 2
                /* REPEAT */
                // Load AC from memory
                1001, // addr 1
                // Store AC to stdout
                7000,
                // Add to AC from memory
                5002, // addr 2
                // Store AC to memory
                2001, // addr 1
                // Load AC from memory
                1001, // addr 1
                // Subtract from AC from memory
                4002, // addr 2
                // Store AC to memory
                2002, // addr 2
                // GOTO
                6004,
                // GOTO
                6010, // 10 times
                // Halt
                9000
            };
            int stdin[] = {0, 1};
            MyMemory m = new MyMemory(instructions.length);
            m.loadValues(instructions);
            MyProcessor p = new MyProcessor(m, stdin);
            m.printSize();
            int cycle = 1;
            while (true) {
                p.fetch();
                //Thread.sleep(2000);
                cycle = p.execute();
                //Thread.sleep(2000);
                switch (cycle) {
                    case -1:
                        return;

                    case 2:
                        int stdout = p.getStdout();
                        System.out.println("\n\t!!!!!!!!!STDOUT: " + stdout + " !!!!!!!!!\n");
                        break;
                
                    default:
                        break;
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }// End man method

}// END class OS
