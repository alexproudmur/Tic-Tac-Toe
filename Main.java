package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char[] arrA = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        printXO(arrA);
        char[] players = {'X', 'O'};
        int k = 0;

        while (continueFunc(arrA)) {
            tryEnter(scanner, arrA, players[k]);
            k++;
            k = k % 2;
        }

        if (aWins(arrA, 'X')) {
            System.out.println("X wins");
        } else if (aWins(arrA, 'O')) {
            System.out.println("O wins");
        } else {
            System.out.println("Draw");
        }
    }

    static void tryEnter(Scanner scanner, char[] array, char g) {
        try {
            enterCell(scanner, array, g);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            tryEnter(scanner, array, g);
        }
    }

    static void enterCell(Scanner scanner, char[] array, char a) {
        System.out.print("Enter the coordinates: ");
        scanner.useDelimiter("\n");
        String str = scanner.next();

        String[] arrString = str.split(" ");

        if (arrString.length == 1) {
            throw new IllegalArgumentException("You should enter numbers!");
        } else {
            if (isNumeric(arrString[0]) && isNumeric(arrString[1])) {
                int fCoord = Integer.parseInt(arrString[0]);
                int sCoord = Integer.parseInt(arrString[1]);

                if (fCoord > 3 || sCoord > 3 || fCoord < 1 || sCoord < 1) {
                    throw new IllegalArgumentException("Coordinates should be from 1 to 3!");
                } else {
                    int coord = 3 * (fCoord - 1) + sCoord - 1;

                    if (array[coord] != ' ') {
                        throw new IllegalArgumentException("This cell is occupied! Choose another one!");
                    } else {
                        array[coord] = a;
                        printXO(array);
                    }
                }
            } else {
                throw new IllegalArgumentException("You should enter numbers!");
            }
        }
    }


    static void printXO(char[] array) {
        System.out.println("\n---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| " + array[i * 3] + " " + array[i * 3 + 1] + " " + array[i * 3 + 2] + " |\n");
        }
        System.out.println("---------");
    }


    public static boolean isNumeric(String str) throws NumberFormatException {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean continueFunc(char[] array) {
        return !(aWins(array, 'X') || aWins(array, 'O') || isFilled(array));
    }

//    static void general(char[] array) {
//        if (isImpossible(array)) {
//            System.out.print("Impossible");
//        } else {
//            if (aWins(array, 'X')) {
//                System.out.println("X wins");
//            } else if (aWins(array, 'O')) {
//                System.out.println("O wins");
//            } else if (isFilled(array)){
//                System.out.println("Draw");
//            } else {
//                System.out.println("Game not finished");
//            }
//        }
//    }

    static boolean isFilled(char[] arr) {
        int k = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == ' ') {
                k++;
            }
        }
        return k == 0;
    }

    static boolean isImpossible(char[] arr) {
        int x = 0;
        int o = 0;

        for (char i : arr) {
            if (i == 'X') {
                x++;
            } else if (i == 'O') {
                o++;
            }
        }

        return Math.abs(x - o) > 1 || (aWins(arr, 'X') && aWins(arr, 'O'));
    }


    static boolean aWins(char[] arr, char a) {
        boolean win = false;

        for (int i = 0; i < 3; i++) {
            if (arr[3 * i] == a && arr[1 + 3 * i] == a && arr[2 + 3 * i] == a) {
                win = true;
                break;
            } else if (arr[i] == a && arr[i + 3] == a && arr[i + 6] == a) {
                win = true;
                break;
            }
        }

        if ((arr[0] == arr[4] && arr[4] == arr[8] && arr[8] == a) || (arr[2] == arr[4] && arr[4] == arr[6] && arr[6] == a)) {
            win = true;
        }
        return win;
    }
}
