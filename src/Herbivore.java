public class Herbivore extends Animal {
    private String species;

    public Herbivore(String species, double weight, int maxPerCell, double maxSaturation, String emoji) {
        super(weight, maxPerCell, maxSaturation, emoji);
        this.species = species;
    }

    @Override
    public boolean canEat(Object food) {
        return food instanceof Plant;
    }

    @Override
    public boolean eat(Object food) {
        if (canEat(food) && random.nextDouble() < getEatingProbability()) {
            Plant plant = (Plant) food;
            currentSaturation = Math.min(maxSaturation, currentSaturation + plant.getWeight());
            return true;
        }
        return false;
    }

    private double getEatingProbability() {
        switch (species) {
            case "Duck": return 0.9;
            case "Horse": return 0.8;
            case "Cow": return 0.7;
            case "Rabbit": return 0.9;
            case "Sheep": return 0.7;
            case "Hamster": return 0.8;
            default: return 0.8;
        }
    }

    @Override
    public void move(Island island, int x, int y) {
        double moveProbability = getMoveProbability();
        if (random.nextDouble() < moveProbability) {
            int newX = x + random.nextInt(3) - 1;
            int newY = y + random.nextInt(3) - 1;
            island.moveAnimal(this, x, y, newX, newY);
        }
    }

    private double getMoveProbability() {
        switch (species) {
            case "Duck": return 0.8;
            case "Horse": return 0.6;
            case "Cow": return 0.4;
            case "Rabbit": return 0.7;
            case "Sheep": return 0.5;
            case "Hamster": return 0.6;
            default: return 0.6;
        }
    }

    @Override
    public Animal reproduce() {
        if (currentSaturation > maxSaturation * 0.4 && random.nextDouble() < getReproductionProbability()) {
            return new Herbivore(species, weight, maxPerCell, maxSaturation, emoji);
        }
        return null;
    }

    private double getReproductionProbability() {
        switch (species) {
            case "Duck": return 0.4;
            case "Horse": return 0.2;
            case "Cow": return 0.3;
            case "Rabbit": return 0.6;
            case "Sheep": return 0.4;
            case "Hamster": return 0.7;
            default: return 0.4;
        }
    }

    @Override
    public String getType() {
        return species;
    }

    public String getSpecies() {
        return species;
    }
}