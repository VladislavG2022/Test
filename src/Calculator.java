import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class Calculator
{

    //Переделываем римские цифры в арабские
    static int romeTranslateToArabic(String inStr)
    {
        int number = 0;
        int[] romeList = new int[]{0, 1, 4, 5, 9, 10, 40, 50, 90, 100};
        String[] romeChar = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

        for (int i = 0; i <10; i++)
        {
            if (inStr.equals(romeChar[i]))
            {
                number = i + 1;
            }
        }
       return number;
    }

    //Арабские в римские
    static String arabicToRome(int number)
    {
        String[] romeChar = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        String[] romeCharDec = new String[]{"X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        int num = 0;
        String result="";

        if(number == 100) return "C";
        else if(number >10)
        {
            num = number / 10;
            if((number-num*10) > 0)
                result = romeCharDec[num-1] + romeChar[number-num*10-1];
            else result = romeCharDec[num-1];
         }
        else result = romeChar[number-1];
        return result;
    }

    public static String calc(String input) throws RuntimeException// Метод вычмсления
    {
        String s1 = new String(); //Первая переменная
        String s2 = new String(); //Вторая переменная
        byte var = 1; //Сначала первая переменная для вычисления
        Function fun = Function.NO; //Пока нет функции вычисления
        boolean arabicNumber = false; //Являются ли числа арабскими
        boolean romeNumber = false; //Являются ли числа римскими
        int i1 = 0, i2 = 0, i3 = 0;

        for(int i =0;i<input.length();i++)
        {
            switch (input.charAt(i))
            {
                case ' ':
                    continue;
                case '+':
                    fun = Function.PLUS;
                    var++;
                    continue;
                case '-':
                    fun = Function.MINUS;
                    var++;
                    continue;
                case '*':
                    fun = Function.MULTI;
                    var++;
                    continue;
                case '/':
                    fun = Function.DIV;
                    var++;
                    continue;
            }


            //Запоминаем первую переменную и вторую для вычисления
            if (var == 1)  s1 = s1 + input.charAt(i);
                else s2 = s2 + input.charAt(i);
        }
        //Проверка на количество операндов
        if (var > 2 || var == 1 || s1.length() == 0)
        {
            throw new RuntimeException("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        try
        {
            i1 = Integer.parseInt(s1);
            arabicNumber = true;
        }
        catch (NumberFormatException nfe)
        {
            i1 = romeTranslateToArabic(s1);
            if(i1 == 0)
                throw new RuntimeException("т.к. строка не является математической операцией");
            else romeNumber = true;
        }
        try
        {
            i2 = Integer.parseInt(s2);
            arabicNumber = true;
        }
        catch (NumberFormatException nfe)
        {
            i2 = romeTranslateToArabic(s2);
            if(i1 == 0 || i2 == 0)
                throw new RuntimeException("т.к. строка не является математической операцией");
            else romeNumber = true;
        }
        if(arabicNumber && romeNumber)
            throw new RuntimeException("т.к. используются одновременно разные системы счисления");

        //Проверяем на диапозон чисел
        if(i1>10 || i1<1 || i2>10 || i2<0)
        {
            throw new RuntimeException("т.к. используются числа не в диапозоне от 1 до 10");
        }

            switch (fun)
            {
                case NO:
                    System.out.println("NO");
                    break;
                case PLUS:
                    i3 = i1 + i2;
                    break;
                case MINUS:
                    i3 = i1 - i2;
                    break;
                case MULTI:
                    i3 = i1 * i2;
                    break;
                case DIV:
                    i3 = i1 / i2;
                    break;
           }

        if (arabicNumber)
            input = String.valueOf(i3);
        else
        {
            if (i3>0) input = arabicToRome(i3);
            else
                throw new RuntimeException("т.к. в римской системе нет отрицательных чисел");
        }

        return input;
    }

    public static void main(String[] args)
    {
        String textInput = new String();
        String result = new String();

        //Приветствие
        TimeZone TZ2 = TimeZone.getTimeZone("Europe/Moscow");
        System.out.print("Привет!\nТекущее время: ");
        DateFormat format =  new SimpleDateFormat("HH:mm ");// Формат времени
        Date date1 = new Date();
        System.out.print(format.format(date1)); // Выводим время
        System.out.println(" (По гринвичу +" + TZ2.getRawOffset()/3600000 + " часа)");// Выводим разницу по времени
        format =  new SimpleDateFormat("dd/MMMM/yyyy (EEEE)");// Формат даты
        System.out.println("Текущая дата: " + format.format(date1));// Выводим дату
        System.out.println("======================================================================================");

        //Ввод строки для вычисления
        System.out.print("Введите операцию для вычисления: ");
        Scanner text = new Scanner(System.in);
        textInput = text.nextLine();

         //Запускаем метод по вычислению результата
        result = calc(textInput);
        System.out.println("Результат вычисления: " + result);

    }
}
