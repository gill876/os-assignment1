package simpleos.sys;

import simpleos.memory.*;
import simpleos.processor.*;


public class OS {

    public static void main(String[] args){

        try {
            // 1 = a
            // 2 = b
            int instructions[] = {
                // Load AC from stdin
                3000,
                // Store AC to memory
                2001,
                // Load AC from stdin
                3000,
                // Store AC to memory
                2002,
                /* REPEAT */
                // Load AC from memory
                1001,
                // Store AC to stdout
                7000,
                // Add to AC from memory
                5002,
                // Store AC to memory
                2001,
                // Load AC from memory
                1001,
                // Subtract from AC from memory
                4002,
                // Store AC to memory
                2002,
                // GOTO
                6004,
                // GOTO
                6010,
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
                        System.out.println("\nSTDOUT: " + stdout + "\n");
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
