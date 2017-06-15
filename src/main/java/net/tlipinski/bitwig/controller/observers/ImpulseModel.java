package net.tlipinski.bitwig.controller.observers;

public enum ImpulseModel {
    IMPULSE_25(25), IMPULSE_49(49), IMPULSE_61(61);

    ImpulseModel(int keys) {
        this.keys = keys;
    }

    public static ImpulseModel get(int keys) {
        return ImpulseModel.valueOf("IMPULSE_" + keys);
    }

    public final int keys;
}
