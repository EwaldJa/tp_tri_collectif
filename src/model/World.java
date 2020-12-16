package model;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    private int size_n, size_m, nbObjA, nbObjB, nbAgent;

    private List<List<Cell>> world;

    public World(int size_n, int size_m, int nbObjA, int nbObjB, int nbAgent) {
        this.size_n = size_n; this.size_m = size_m; this.nbObjA = nbObjA; this.nbObjB = nbObjB; this.nbAgent = nbAgent;
        world = new ArrayList<>();
        Random rnd = new Random();
    }

    /*
    public void initialize(int size_n, int size_m, int nbObjA, int nbObjB, int nbAgent) {
        world = new ArrayList<>();
        world.add(firstStack);
        for (int i = 1; i < nbLocation; i++) {
            List<MetaBloc> loc = new ArrayList<>();
            loc.add(table);
            world.add(loc); }
    }



    public List<MetaBloc> getAvailableLocations(Bloc toCheck) {
        List<MetaBloc> availableLocations = new ArrayList<>();
        for (int i = 0; i < nbLocation - 1; i++) {
            List<MetaBloc> loc_i = world.get(i);
            MetaBloc summit = loc_i.get(loc_i.size() - 1);
            if (!summit.equals(toCheck)) {
                availableLocations.add(summit); } }
        return availableLocations;
    }

    public boolean canMove(Bloc toCheck) throws BlocNotFoundException {
        int currentLocation = getLocationOfBloc(toCheck);
        return world.get(currentLocation).indexOf(toCheck) == (world.get(currentLocation).size() - 1);
    }

    public MetaBloc moveToLocation(Bloc toMove, MetaBloc whereTo) throws MovementUnavailableException, BlocNotFoundException {
        int newLocation = getLocationOfBloc(whereTo);
        int currentLocation = getLocationOfBloc(toMove);
        List<MetaBloc> currentLoc = world.get(currentLocation);
        List<MetaBloc> newLoc = world.get(newLocation);
        if (currentLoc.indexOf(toMove) != (currentLoc.size() - 1)) { throw new MovementUnavailableException("Bloc #" + toMove.getBlocName() + " is not at the summit of the location #" + currentLocation); }
        else {
            currentLoc.remove(toMove);
            newLoc.add(toMove);
            return newLoc.get(newLoc.size() - 2); }
    }

    private int getLocationOfBloc(MetaBloc bloc) throws BlocNotFoundException {
        boolean hasToBeFirst = false;
        if (bloc instanceof Table) { hasToBeFirst = true; }
        for (int i = 0; i < nbLocation - 1; i++) {
            List<MetaBloc> loc = world.get(i);
            if (loc.indexOf(bloc) != -1) {
                if (hasToBeFirst) {
                    if (loc.indexOf(bloc) == (loc.size()-1)) {
                        return i; } }
                else {
                    return i; } } }
        throw new BlocNotFoundException("Bloc #" + bloc.getBlocName() + "does not exists in the world");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = nbBlocs; i >= 1; i--) {
            for (int j = 0; j < nbLocation - 1; j ++) {
                sb.append(blocTitle(world.get(j), i)).append(" ");
            }
            sb.append("\n"); }
        return sb.toString();
    }

    public String blocTitle(List<MetaBloc> pile, int index) {
        return ((pile.size() > index) ? ("|" + pile.get(index).getBlocName() + "|") : "| |");
    }
    */


}
