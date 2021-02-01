package tictactoe;
// импорт сканнера
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //создание объекта сканнера
        Scanner scanner = new Scanner(System.in);
        
        //инициализация массива символов, в котором будут храниться значения игрового поля
        char[] arrA = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        //вывод пустого поля
        printXO(arrA);
        //инициализация массива игроков
        char[] players = {'X', 'O'};
        
        //инициализация вспомогательной переменной k для вычисления очереди хода игроков
        int k = 0;

        while (continueFunc(arrA)) {
            //вызов метода с попыткой сделать ход
            tryEnter(scanner, arrA, players[k]);
            
            //при удачном выполнении хода, учитываем, что игрок сделал ход, и передаем очередь другому (Х ходит первым по условию задачи всегда)
            k++;
            k = k % 2;
        }
        
        //проверка, выиграл ли кто-то или ничья
        if (aWins(arrA, 'X')) {
            System.out.println("X wins");
        } else if (aWins(arrA, 'O')) {
            System.out.println("O wins");
        } else {
            System.out.println("Draw");
        }
    }
    
    //метод с рекурсией, чтобы игрок сделал свой ход, исключениие возникает в том случае, если было введено недоступное значение
    static void tryEnter(Scanner scanner, char[] array, char g) {
        try {
            enterCell(scanner, array, g);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            tryEnter(scanner, array, g);
        }
    }
    
    //метод ввода клетки игрового поля, где игрок поставит отметку
    static void enterCell(Scanner scanner, char[] array, char a) {
        
        System.out.print("Enter the coordinates: ");
        scanner.useDelimiter("\n");
        String str = scanner.next();
        
        // раздение введенной строки по пробелу в массив строк
        String[] arrString = str.split(" ");
        
        // если введена строка с всего лиь одной подстрокой, то это неверный аргумент, вызов исключения
        if (arrString.length == 1) {
            throw new IllegalArgumentException("You should enter numbers!");
        } else {
            // проверка, является ли ввод числами, иначе -- вызов исключения
            if (isNumeric(arrString[0]) && isNumeric(arrString[1])) {
                int fCoord = Integer.parseInt(arrString[0]);
                int sCoord = Integer.parseInt(arrString[1]);
                
                // проверка правильности по диапазону допустимых значений
                if (fCoord > 3 || sCoord > 3 || fCoord < 1 || sCoord < 1) {
                    throw new IllegalArgumentException("Coordinates should be from 1 to 3!");
                } else {
                    //вычисление абсолютной координаты в нашем массиве символов
                    int coord = 3 * (fCoord - 1) + sCoord - 1;
                       
                    // провека не занята ли эта клетка
                    if (array[coord] != ' ') {
                        throw new IllegalArgumentException("This cell is occupied! Choose another one!");
                    } else {
                        //заполнение клетки и вывод изменений
                        array[coord] = a;
                        printXO(array);
                    }
                }
            } else {
                throw new IllegalArgumentException("You should enter numbers!");
            }
        }
    }

    //метод печати игрового поля
    static void printXO(char[] array) {
        System.out.println("\n---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| " + array[i * 3] + " " + array[i * 3 + 1] + " " + array[i * 3 + 2] + " |\n");
        }
        System.out.println("---------");
    }

    //проверка являетс ли подстрока числом
    public static boolean isNumeric(String str) throws NumberFormatException {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    //функция-условие продолжение цикла, возвращает истину если игра не закончена
    static boolean continueFunc(char[] array) {
        return !(aWins(array, 'X') || aWins(array, 'O') || isFilled(array));
    }

    // демоверсия стадии 3
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

    //метод-проверка, заполнено ли поле
    static boolean isFilled(char[] arr) {
        int k = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == ' ') {
                k++;
            }
        }
        return k == 0;
    }
    
    //метод-проверка является ли ситуация возможной, в финальной реализации проекта не используется в меру ненадобности
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

    
    //метод "выиграл ли", проверяет победил ли определенный игрок
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
