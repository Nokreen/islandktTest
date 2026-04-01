public class Predator extends Animal {
    private String species;

    public Predator(String species, double weight, int maxPerCell, double maxSaturation, String emoji) {
        super(weight, maxPerCell, maxSaturation, emoji);
        this.species = species;
    }

    @Override
    public boolean canEat(Object food) {
        return food instanceof Herbivore;
    }

    @Override
    public boolean eat(Object food) {
        if (canEat(food) && random.nextDouble() < getEatingProbability()) {
            Herbivore herbivore = (Herbivore) food;
            if (herbivore.isAlive()) {
                herbivore.die();
                currentSaturation = Math.min(maxSaturation, currentSaturation + herbivore.getWeight());
                return true;
            }
        }
        return false;
    }

    private double getEatingProbability() {
        switch (species) {
            case "Wolf": return 0.6;
            case "Boa": return 0.3;
            case "Fox": return 0.7;
            case "Eagle": return 0.8;
            default: return 0.5;
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
            case "Wolf": return 0.5;
            case "Boa": return 0.3;
            case "Fox": return 0.7;
            case "Eagle": return 0.9;
            default: return 0.5;
        }
    }

    @Override
    public Animal reproduce() {
        if (currentSaturation > maxSaturation * 0.5 && random.nextDouble() < getReproductionProbability()) {
            return new Predator(species, weight, maxPerCell, maxSaturation, emoji);
        }
        return null;
    }

    private double getReproductionProbability() {
        switch (species) {
            case "Wolf": return 0.3;
            case "Boa": return 0.2;
            case "Fox": return 0.4;
            case "Eagle": return 0.3;
            default: return 0.3;
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