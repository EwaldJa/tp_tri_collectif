import model.Agent;
import model.Environment;
import utils.ConstantsUtils;

import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Environment environment = new Environment(ConstantsUtils.NB_COLUMNS, ConstantsUtils.NB_LINES,
                                ConstantsUtils.NB_OBJ_A, ConstantsUtils.NB_OBJ_B,
                                ConstantsUtils.NB_AGENT, ConstantsUtils.AGENT_RANGE,
                                ConstantsUtils.K_PLUS, ConstantsUtils.K_MINUS,
                                ConstantsUtils.USE_ERROR, ConstantsUtils.ERROR_RATIO);


        List<Agent> agents = environment.init();

        String initial = environment.toString();

        final int NB_ITER = 1000000000;
        int currIter = 0;
        while(currIter < NB_ITER) {
            Collections.shuffle(agents);
            for (Agent a:agents) {
                try {
                    a.perception();
                    a.action(); }
                catch (Exception e) {
                    e.printStackTrace(); }
            }
            if (currIter % (NB_ITER/1000)  == 0) {
                System.out.println("\n"+currIter);
                environment.display(); }
            currIter++; }

        System.out.println("\n\n\n\n\n\n\n\nMonde initial : \n");
        System.out.println(initial);
        System.out.println("\n\n\nMonde trié : \n");
        environment.display();

    }
}
