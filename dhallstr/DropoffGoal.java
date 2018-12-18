package dhallstr;

import hlt.*;

public class DropoffGoal extends Goal {
    PlayerId id;
    boolean crashOkay;

    public DropoffGoal(PlayerId me, boolean crashOkay) {
        id = me;
        this.crashOkay = crashOkay;
    }

    public boolean overrideUnsafe(MapCell cell) { return crashOkay && meetsGoal(cell); }

    @Override
    public int rateTile(Game game, MapCell cell, Ship s, PlannedLocations plan) {
        return cell.hasStructure() && cell.structure.owner.equals(id) ? 300 - cell.cost - 5 * cell.actualDist : -10001;
    }

    @Override
    public int getNumberStays(Ship s, MapCell cell, PlannedLocations plan, GameMap map) {
        if (crashOkay) return 0;
        int halite = plan.getProjectedHalite(map, cell.position, cell.dist);
        int myHalite = s.halite - cell.cost;
        int turnsStayed;
        for (turnsStayed = 0; ; turnsStayed++) {
            int mined = Math.min(cell.collectAmount(halite), Constants.MAX_HALITE - myHalite);
            halite -= mined;
            myHalite += mined;
            if (mined < Magic.getCollectDownTo(map)) break;
        }
        return turnsStayed;
    }

    public boolean meetsGoal(MapCell cell) {
        return cell.structure != null && cell.structure.owner.equals(id);
    }

    public int getMaxTurns() {
        return Constants.MAX_TURNS;
    }

    public Intent getIntent() {
        return crashOkay ? Intent.CRASH_HOME : Intent.DROPOFF;
    }
}
