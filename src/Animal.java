import java.util.Random;

public abstract class Animal {
    protected double weight;
    protected int maxPerCell;
    protected double maxSaturation;
    protected double currentSaturation;
    protected boolean isAlive;
    protected Random random = new Random();
    protected String emoji;

    public Animal(double weight, int maxPerCell, double maxSaturation, String emoji) {
        this.weight = weight;
        this.maxPerCell = maxPerCell;
        this.maxSaturation = maxSaturation;
        this.currentSaturation = maxSaturation / 2;
        this.isAlive = true;
        this.emoji = emoji;
    }

    public abstract boolean canEat(Object food);
    public abstract boolean eat(Object food);
    public abstract void move(Island island, int x, int y);
    public abstract Animal reproduce();
    public abstract String getType();

    public void decreaseSaturation() {
        currentSaturation -= maxSaturation * 0.1;
        if (currentSaturation <= 0) {
            die();
        }
    }

    public void die() {
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public double getWeight() {
        return weight;
    }

    public String getEmoji() {
        return emoji;
    }
}