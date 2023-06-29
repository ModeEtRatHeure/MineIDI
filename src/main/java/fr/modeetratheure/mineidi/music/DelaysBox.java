package fr.modeetratheure.mineidi.music;

import fr.modeetratheure.mineidi.utils.storage.Pair;

public class DelaysBox {

    private final double delay;
    private final int inputConnections;

    public DelaysBox(double delay, int inputConnections){
        this.delay = delay;
        this.inputConnections = inputConnections;
    }

    public double getDelay() {
        return delay;
    }

    public int getInputConnections() {
        return inputConnections;
    }

    public Pair</*Number of repeater with a delay of 4 ticks*/Integer, /*Delay of the remaining repeater*/Integer> getRedstoneCircuitData(){
        int repeaterOf4TicksNumber = ((int) delay) / 4;
        return new Pair<>(repeaterOf4TicksNumber, ((int) delay) - 4 * repeaterOf4TicksNumber);
    }
}
