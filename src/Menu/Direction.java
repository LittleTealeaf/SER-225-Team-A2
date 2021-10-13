package Menu;

public enum Direction {
    UP(0),RIGHT(1),LEFT(2),DOWN(3);

    int index;
    Direction(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
    public int getOppositeIndex() {
        return (index + 2)%4;
    }
}
