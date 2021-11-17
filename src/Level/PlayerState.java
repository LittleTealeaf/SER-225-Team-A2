package Level;

// This enum represents different states the Player can be in
public enum PlayerState {
    STAND, WALK, JUMP, CROUCH, FALL,
    DEATH,
    @Deprecated
    ATTACKING;

    private String left,right;

    PlayerState() {
        left = this + "_LEFT";
        right = this + "_RIGHT";
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public String get(Facing facing) {
        return facing == Facing.LEFT ? left : right;
    }

    public enum Facing {
        LEFT(-1),RIGHT(1);

        public final int mod;
        Facing(int i) {
            this.mod = i;
        }
    }
}
