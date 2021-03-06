package hlt;

public class Position {
    public final int x;
    public final int y;

    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Position directionalOffset(final Direction d) {
        final int dx;
        final int dy;

        switch (d) {
            case NORTH:
                dx = 0;
                dy = -1;
                break;
            case SOUTH:
                dx = 0;
                dy = 1;
                break;
            case EAST:
                dx = 1;
                dy = 0;
                break;
            case WEST:
                dx = -1;
                dy = 0;
                break;
            case STILL:
                dx = 0;
                dy = 0;
                break;
            default:
                throw new IllegalStateException("Unknown direction " + d);
        }

        return new Position(x + dx, y + dy);
    }

    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Position)) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
