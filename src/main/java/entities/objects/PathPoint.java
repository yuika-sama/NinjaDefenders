package entities.objects;

public class PathPoint {
    private int xCord, yCord;

    public PathPoint(int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public int getxCord() {
        return xCord;
    }

    public int getyCord() {
        return yCord;
    }
}
