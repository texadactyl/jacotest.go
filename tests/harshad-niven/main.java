/*
    Harshad numbers (also called Niven numbers)

    A Harshad number is an integer that is divisible by the sum of its digits.
    
    https://en.wikipedia.org/wiki/Harshad_number
*/

public class main {

    static final int MAX_COUNT = 100;
    
    static final int expected[] = {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 18, 20, 21, 24, 27, 30, 36, 40, 42, 45, 48, 50, 
        54, 60, 63, 70, 72, 80, 81, 84, 90, 100, 102, 108, 110, 111, 112, 114, 117, 120, 
        126, 132, 133, 135, 140, 144, 150, 152, 153, 156, 162, 171, 180, 190, 192, 195, 198, 
        200, 201, 204, 207, 209, 210, 216, 220, 222, 224, 225, 228, 230, 234, 240, 243, 247, 252, 
        261, 264, 266, 270, 280, 285, 288, 300, 306, 308, 312, 315, 320, 322, 324, 330, 333, 336, 
        342, 351, 360, 364, 370, 372
    };

    public static void main(String[] args) {
        int count = 0;
        int num = 1;
        int errorCount = 0;

        System.out.printf("First %d Harshad numbers:\n", MAX_COUNT);

        while (count < MAX_COUNT) {
            if (isHarshad(num)) {
                System.out.printf("%d ", count+1);
                errorCount += Checkers.checker("", expected[count], num);
                count++;
            }
            num++;
        }

        System.out.println();
        Checkers.theEnd(errorCount);
    }

    // Test if a number is Harshad.
    public static boolean isHarshad(int arg) {
        int sum = sumOfDigits(arg);
        return arg % sum == 0;
    }

    // Compute the sum of digits that make up a number.
    public static int sumOfDigits(int arg) {
        int sum = 0;
        while (arg > 0) {
            sum += arg % 10;
            arg /= 10;
        }
        return sum;
    }
}

