package edu.nayanova.it.challenge.c201804.xo.data;

public interface EngineInterface {
    Position AnalysePosition(XoField field);

    XoMove GetMove(XoField field);
}
