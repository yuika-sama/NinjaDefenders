package helpz;

import java.util.ArrayList;

public class Utils {
    public static int[][] ArrayListTo2Dint(ArrayList<Integer> list, int ySize, int xSize) {
        int[][] newArr = new int[ySize][xSize];
        for (int j = 0; j < newArr.length; j++) {
            for (int i = 0; i < newArr[j].length; i++) {
                int index = j * ySize + i;
                newArr[j][i] = list.get(index);
            }
        }
        return newArr;
    }

    public static int[] _2DIntTo1DInt(int[][] idArr) {
        int[] arr = new int[idArr.length * idArr[0].length];
        for (int j = 0; j < idArr.length; j++) {
            for (int i = 0; i < idArr[j].length; i++) {
                int index = j * idArr[j].length + i;
                arr[index] = idArr[j][i];
            }
        }
        return arr;
    }

    public static int getDistance(float x1, float y1, float x2, float y2){
        float xDiff = Math.abs(x1-x2);
        float yDiff = Math.abs(y1-y2);
        return (int)Math.hypot(xDiff, yDiff);
    }
}
