package racingcar.domain.car;

public class Position {
    private int currentPosition;

    public Position() {
        this.currentPosition = 0;
    }

    public int addPosition() {
        return ++currentPosition;
    }

    public boolean isGreaterThan(Integer otherCarPosition) {
        return currentPosition > otherCarPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
}