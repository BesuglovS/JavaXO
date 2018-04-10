package edu.nayanova.it.challenge.c201804.xo;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        byte gameType = 0;

        do
        {
            System.out.println("Welcome!");
            System.out.println("1 - 2 человека");
            System.out.println("2 - Человек против системы");
            System.out.println("3 - Система против человека");
            System.out.println("4 - 2 системы");
            System.out.println("5 - Тест двух движков");
            System.out.println("0 - Выход");
            System.out.println("Введите тип игры: ");

            Boolean gameTypeOK;
            do
            {
                gameTypeOK = true;
                try
                {
                    gameType = Byte.parseByte(scanner.nextLine());
                }
                catch(NumberFormatException exc)
                {
                    System.out.println("Неправильный формат.");
                    gameTypeOK = false;
                }
            } while (!gameTypeOK);

            var myGame = new XoGame(gameType);
            myGame.RunGame();
        } while (gameType != 0);
    }
}
