package edu.nayanova.it.challenge.c201804.xo.engines;

import edu.nayanova.it.challenge.c201804.xo.data.EngineInterface;
import edu.nayanova.it.challenge.c201804.xo.data.Position;
import edu.nayanova.it.challenge.c201804.xo.data.XoField;
import edu.nayanova.it.challenge.c201804.xo.data.XoMove;

import java.util.ArrayList;
import java.util.Random;

public class RandomEngine implements EngineInterface {

    public Position AnalysePosition(XoField field)
    {
        return null;
    }

    public XoMove GetMove(XoField field)
    {
        var freeCellMoves = new ArrayList<XoMove>();

        for (byte i = 0; i < 3; i++)
        {
            for (byte j = 0; j < 3; j++)
            {
                if (field.F[i][j] == 0)
                {
                    freeCellMoves.add(new XoMove(i,j));
                }
            }
        }

        var rnd = new Random();
        var randMoveIndex = rnd.nextInt(freeCellMoves.size());

        return freeCellMoves.get(randMoveIndex);
    }
}
