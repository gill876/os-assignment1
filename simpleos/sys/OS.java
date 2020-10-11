package simpleos.sys;

import simpleos.memory.*;
import simpleos.processor.*;


public class OS {

    public static void main(String[] args){

        try {
            int instructions[] = {1001, 800, 2004, 5001, 0, 100, 4005, 3000, 3000, 7000, 6009, 6005,9000};
            int stdin[] = {4, 5};
            MyMemory m = new MyMemory(5);
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
