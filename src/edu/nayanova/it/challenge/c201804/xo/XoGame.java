package edu.nayanova.it.challenge.c201804.xo;

import edu.nayanova.it.challenge.c201804.xo.data.EngineInterface;
import edu.nayanova.it.challenge.c201804.xo.data.Toolbox;
import edu.nayanova.it.challenge.c201804.xo.data.XoField;
import edu.nayanova.it.challenge.c201804.xo.data.XoMove;
import edu.nayanova.it.challenge.c201804.xo.engines.RandomEngine;

import java.io.IOException;
import java.util.Scanner;

public class XoGame {
    private final byte type;
    private XoField field;

    public XoGame(byte gameType) {
        type = gameType;
        field = new XoField();
    }

    public void RunGame() throws IOException {
        switch (type)
        {
            case 1:
                HumansGame();
                break;
            case 2:
                HumanVsSystem(true);
                break;
            case 3:
                HumanVsSystem(false);
                break;
            case 4:
                SystemsGame();
                break;
            case 5:
                EnginesTest();
                break;
        }
    }

    private void HumansGame() throws IOException {
        do
        {
            MakeMove(HumanMove());
        } while (Finished() == 3);

        PrintField(true);

        if (Finished() == 0)
        {
            System.out.println("Ничья.");
        }
        else
        {
            System.out.println("Победа! Выиграли - " + Toolbox.BytetoChar(Finished()));
        }

        System.in.read();
    }

    private XoMove HumanMove() throws IOException {
        Scanner scanner = new Scanner(System.in);

        var result = new XoMove((byte) 0 ,(byte) 0);

        do
        {
            PrintField(true);

            System.out.println("Введите строку хода: ");
            result.line = (byte)(Byte.parseByte(scanner.nextLine()) - 1);

            System.out.println("Введите столбец хода: ");
            result.column = (byte)(Byte.parseByte(scanner.nextLine()) - 1);

            if (field.F[result.line][result.column] != 0)
            {
                System.out.println("Неправильный ход.");
                System.in.read();
            }
        } while (field.F[result.line][result.column] != 0);

        return result;
    }

    private byte Finished() {
        for (int i = 0; i <= 2; i++)
        {
            if (((field.F[i][0] == field.F[i][1]) &&
            (field.F[i][1] == field.F[i][2])) &&
            (field.F[i][0] != 0))
            {
                return field.F[i][0];
            }
        }

        for (int i = 0; i <= 2; i++)
        {
            if (((field.F[0][i] == field.F[1][i]) &&
            (field.F[1][i] == field.F[2][i])) &&
            (field.F[0][i] != 0))
            {
                return field.F[0][i];
            }
        }

        if (((field.F[0][0] == field.F[1][1]) &&
        (field.F[1][1] == field.F[2][2])) &&
        (field.F[0][0] != 0))
        {
            return field.F[0][0];
        }

        if (((field.F[0][2] == field.F[1][1]) &&
        (field.F[1][1] == field.F[2][0])) &&
        (field.F[0][2] != 0))
        {
            return field.F[0][2];
        }

        for (int i = 0; i <= 2; i++)
        {
            for (int j = 0; j <= 2; j++)
            {
                if (field.F[i][j] == 0)
                return 3;
            }
        }

        return 0;
    }

    private void PrintField(boolean clearConsole) {
        if (clearConsole)
        {
            //Console.Clear();
        }

        for (int i = 0; i <= 2; i++)
        {
            for (int j = 0; j <= 2; j++)
            {
                System.out.print(Toolbox.BytetoChar(field.F[i][j]) + " ");
            }
            System.out.println();
        }
    }

    private void MakeMove(XoMove move)
    {
        field.F[move.line][move.column] = DetectMove(field);
    }

    private static byte DetectMove(XoField field) // Следующий ход делают: 1 - X; 2 - O
    {
        byte xcol = 0,
             ocol = 0;
        for (int i = 0; i <= 2; i++)
        {
            for (int j = 0; j <= 2; j++)
            {
                switch (field.F[i][j])
                {
                    case 1:
                        xcol++;
                        break;
                    case 2:
                        ocol++;
                        break;
                }
            }
        }

        return (byte)((xcol > ocol) ? 2 : 1);
    }

    private void HumanVsSystem(boolean humanPlaysForX) throws IOException {
        //var engines = GetXoGameEngines();

        //var myEngineInfo = ChooseEngine(engines, "Выберите игровой движок: ", true);

        //var myEngine = LoadEngine(myEngineInfo);

        EngineInterface myEngine = new RandomEngine();

        Boolean correctEngineMove;

        do
        {
            correctEngineMove = true;

            if (((DetectMove(field) == 1) && (humanPlaysForX)) || // Ходят Х и человек за Х
                    ((DetectMove(field) == 2) && (!humanPlaysForX)))   // Ходят О и человек не за Х
            {
                MakeMove(HumanMove());
            }
            else
            {
                var engineMove = myEngine.GetMove(field);
                if (CheckMove(engineMove, field))
                {
                    MakeMove(engineMove);
                }
                else
                {
                    PrintField(true);

                    System.out.println("Ошибка движка. Попытка сделать ход {" +
                            (engineMove.line + 1) + "," + (engineMove.column + 1) + "}");
                    correctEngineMove = false;
                }
            }
        } while ((Finished() == 3) && (correctEngineMove));

        if (correctEngineMove)
        {
            PrintField(true);

            if (Finished() == 0)
            {
                System.out.println("Ничья.");
            }
            else
            {
                System.out.println("Победа! Выиграли - " + Toolbox.BytetoChar(Finished()));
            }
        }

        System.in.read();
    }

    private boolean CheckMove(XoMove engineMove, XoField field) {
        return field.F[engineMove.line][engineMove.column] == 0;
    }

    private void SystemsGame() throws IOException {
//        var engines = GetXoGameEngines();
//
//        var enginePlaysXInfo =
//                ChooseEngine(engines, "Выберите игровой движок для крестиков: ", true);
//
//        var enginePlaysOInfo =
//                ChooseEngine(engines, "Выберите игровой движок для ноликов: ", false);

//        var enginePlaysX = LoadEngine(enginePlaysXInfo);
//        var enginePlaysO = LoadEngine(enginePlaysOInfo);

        EngineInterface enginePlaysX = new RandomEngine();
        EngineInterface enginePlaysO = new RandomEngine();

        var engineMove = new XoMove((byte) 0,(byte) 0);
        Boolean correctMove;
        do
        {
            switch (DetectMove(field))
            {
                case 1: // X
                    engineMove = enginePlaysX.GetMove(field);
                    break;
                case 2: // O
                    engineMove = enginePlaysO.GetMove(field);
                    break;
            }

            correctMove = CheckMove(engineMove, field);
            if (correctMove)
                MakeMove(engineMove);

            PrintField(true);

            System.out.println("Для продолжения нажмите любую клавишу...");
            System.in.read();

        } while ((Finished() == 3) && (correctMove));

        PrintField(true);

        if (!correctMove)
        {
            System.out.println("Попытка сделать ход в непустую ячейку:" +
                    engineMove.line + ", " + engineMove.column);
            return;
        }

        if (Finished() == 0)
        {
            System.out.println("Ничья.");
        }
        else
        {
            System.out.println("Победа! Выиграли - " + Toolbox.BytetoChar(Finished()));
        }

        System.in.read();
    }

    private void EnginesTest() throws IOException {
        Scanner scanner = new Scanner(System.in);
//        var engines = GetXoGameEngines();
//
//        var engineInfo1 =
//                ChooseEngine(engines, "Выберите первый игровой движок: ", true);
//
//        var engineInfo2 =
//                ChooseEngine(engines, "Выберите второй игровой движок: ", false);

        System.out.println("Введите количество туров: ");
        var roundQuantity = Integer.parseInt(scanner.nextLine());

//        var engine1 = LoadEngine(engineInfo1);
//        var engine2 = LoadEngine(engineInfo2);
        EngineInterface engine1 = new RandomEngine();
        EngineInterface engine2 = new RandomEngine();

        int engine1Wins = 0,   // Побед первого движка
            engine2Wins = 0,   // Побед второго движка
            engine1Faults = 0, // Ошибок первого движка
            engine2Faults = 0, // Ошибок второго движка
            draws = 0;         // Ничьих


        var engineMove = new XoMove((byte) 0, (byte) 0);
        byte correctMoves;

        for (int i = 1; i < (roundQuantity * 2) + 1; i++)
        {
            field = new XoField(new byte[3][3]);

            do
            {
                correctMoves = 0;

                switch (DetectMove(field))
                {
                    case 1: // X
                        engineMove = i % 2 == 1 ?
                                engine1.GetMove(field) :
                                engine2.GetMove(field);

                        break;
                    case 2: // O
                        engineMove = i % 2 == 1 ?
                                engine2.GetMove(field) :
                                engine1.GetMove(field);
                        break;
                }

                if (CheckMove(engineMove, field))
                    MakeMove(engineMove);
                else
                    correctMoves = (byte)(((DetectMove(field)) == 1) ?
                            ((i % 2 == 1) ? 1 : 2) :
                            ((i % 2 == 1) ? 2 : 1));

            } while ((Finished() == 3) && (correctMoves == 0));

            if (correctMoves != 0)
            {
                if (correctMoves == 1) // Ошибся 1-й движок
                    engine1Faults++;
                else // Второй
                    engine2Faults++;
            }
            else
            {
                switch (Finished())
                {
                    case 0:
                        draws++;
                        break;
                    case 1:
                        if (i%2 == 1)
                        {
                            engine1Wins++;
                        }
                        else
                        {
                            engine2Wins++;
                        }
                        break;
                    case 2:
                        if (i%2 == 1)
                        {
                            engine2Wins++;
                        }
                        else
                        {
                            engine1Wins++;
                        }
                        break;
                }
            }

            System.out.println("Всего игр = " + i);
            System.out.println("Ничьих = " + draws);
            System.out.println("Побед 1 = " + engine1Wins);
            System.out.println("Побед 2 = " + engine2Wins);
            System.out.println("Ошибок 1 = " + engine1Faults);
            System.out.println("Ошибок 2 = " + engine2Faults);
        }

        System.in.read();
    }
}
