import model.Agent;
import model.World;
import utils.ConstantsUtils;

import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        World world = new World(ConstantsUtils.NB_COLUMNS, ConstantsUtils.NB_LINES,
                                ConstantsUtils.NB_OBJ_A, ConstantsUtils.NB_OBJ_B,
                                ConstantsUtils.NB_AGENT, ConstantsUtils.AGENT_RANGE,
                                ConstantsUtils.K_PLUS, ConstantsUtils.K_MINUS);


        List<Agent> agents = world.init();

        String initial = world.toString();

        final int NB_ITER = 1000000;
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
            if (currIter % 1000  == 0) {
                world.display(); }
            currIter++; }

        System.out.println("\n\n\n\n\n\n\n\nMonde initial : \n");
        System.out.println(initial);
        System.out.println("\n\n\nMonde trié : \n");
        world.display();

    }
}
