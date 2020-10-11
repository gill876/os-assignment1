package simpleos.sys;

import simpleos.memory.*;
import simpleos.processor.*;


public class OS {

    public static void main(String[] args){

        try {
            int instructions[] = {1001, 800, 9000};
            int stdin[] = {0, 1};
            MyMemory m = new MyMemory(5);
            m.loadValues(instructions);
            MyProcessor p = new MyProcessor(m, stdin);
            m.printSize();
            int cycle = 1;
            while (cycle == 1) {
                p.fetch();
                Thread.sleep(2000);
                cycle = p.execute();
                Thread.sleep(2000);
            }
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }// End man method

}// END class OS
