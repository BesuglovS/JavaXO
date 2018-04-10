package edu.nayanova.it.challenge.c201804.xo.data;

import java.util.ArrayList;
import java.util.List;

public class Position {
    public XoField Field;
    public byte Evaluation;
    public List<Position> Next;

    public Position() {
        Field = new XoField();
        Evaluation = (byte) 255;
        Next = new ArrayList<>();
    }

    public Position(Position source) {
        Field = new XoField();

        for (int i = 0; i <= 2; i++)
        {
            System.arraycopy(source.Field.F[i],
                    0, Field.F[i], 0, 3);
        }

        Evaluation = (byte) 255;

        Next = new ArrayList<>();
    }
}
