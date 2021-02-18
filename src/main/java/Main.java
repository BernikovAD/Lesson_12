

public class Main {

/*
    Необходимо написать два метода, которые делают следующее:
    1.Создают одномерный длинный массив, например:
    static final int SIZE = 10 000 000;
    static final int HALF = size / 2;
    float[] arr = new float[size].
    Заполняют этот массив единицами.
    Засекают время выполнения: long a = System.currentTimeMillis().
    Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
    arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
    Проверяется время окончания метода System.currentTimeMillis().
    В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a).
*/


    private static final int SIZE = 10000000;
    private static final int HALF = SIZE / 2;
    private static float[] arr = new float[SIZE];
    private static long a;

    public static void main(String[] args) {
        updateArr (arr);
        long firstMethodTime = firstMethod (arr);
        System.out.println ("First method time: " + firstMethodTime + " milliseconds");
        updateArr (arr);
        long secondMethodTime = secondMethod (arr);
        System.out.println ("multi thread second method time: " + secondMethodTime + " milliseconds");
    }

    private static void updateArr(float[] arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
    }
    private static long firstMethod(float[] arr) {
        a = System.currentTimeMillis ();
        calculate (arr);
        long singleTime = System.currentTimeMillis () - a;
        return singleTime;
    }

    private static long secondMethod(float[] arr) {
        float[] HALF_1 = new float[HALF];
        float[] HALF_2 = new float[HALF];

        a = System.currentTimeMillis ();

        System.arraycopy (arr, 0, HALF_1, 0, HALF);
        System.arraycopy (arr, HALF, HALF_2, 0, HALF);

        float[] finalHALF_1 = HALF_1;
        new Thread (() -> {
            calculate (finalHALF_1);
        }).start ();

        float[] finalHALF_2 = HALF_2;
        new Thread (() -> {
            calculate (finalHALF_2);
        }).start ();

        System.arraycopy (HALF_1, 0, arr, 0, HALF);
        System.arraycopy (HALF_2, 0, arr, HALF_1.length, HALF_2.length);

        return System.currentTimeMillis () - a;
    }

    private static void calculate(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin (0.2f + i / 5) * Math.cos (0.2f + i / 5) * Math.cos (0.4f + i / 2));
        }
    }
}
