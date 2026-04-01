import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Island {
    private Location[][] locations;
    private int width = 20;
    private int height = 10;
    private Random random = new Random();
    private int day = 0;

    public Island() {
        locations = new Location[width][height];
        initializeIsland();
    }

    private void initializeIsland() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                locations[x][y] = new Location();

                for (int i = 0; i < random.nextInt(20) + 5; i++) {
                    locations[x][y].addPlant(new Plant());
                }
            }
        }

        initializeAnimals();
    }

    private void initializeAnimals() {
        // Инициализация хищников
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (random.nextDouble() < 0.1) locations[x][y].addAnimal(new Predator("Wolf", 50, 30, 8, "🐺"));
                if (random.nextDouble() < 0.05) locations[x][y].addAnimal(new Predator("Boa", 15, 30, 3, "🐍"));
                if (random.nextDouble() < 0.08) locations[x][y].addAnimal(new Predator("Fox", 8, 30, 2, "🦊"));
                if (random.nextDouble() < 0.06) locations[x][y].addAnimal(new Predator("Eagle", 6, 20, 1, "🦅"));

                // Инициализация травоядных
                if (random.nextDouble() < 0.1) locations[x][y].addAnimal(new Herbivore("Duck", 1, 200, 0.15, "🦆"));
                if (random.nextDouble() < 0.07) locations[x][y].addAnimal(new Herbivore("Horse", 400, 20, 60, "🐎"));
                if (random.nextDouble() < 0.08) locations[x][y].addAnimal(new Herbivore("Cow", 700, 10, 100, "🐃"));
                if (random.nextDouble() < 0.15) locations[x][y].addAnimal(new Herbivore("Rabbit", 2, 150, 0.45, "🐇"));
                if (random.nextDouble() < 0.1) locations[x][y].addAnimal(new Herbivore("Sheep", 70, 140, 15, "🐑"));
                if (random.nextDouble() < 0.12) locations[x][y].addAnimal(new Herbivore("Hamster", 0.05, 500, 0.01, "🐁"));
            }
        }
    }

    public void simulateDay() {
        day++;

        processEating();
        processReproduction();
        processMovement();
        processPlantsAndStarvation();
    }

    private void processEating() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location location = locations[x][y];

                for (Animal animal : location.getAnimals()) {
                    if (!animal.isAlive()) continue;

                    // Хищники едят травоядных
                    if (animal instanceof Predator) {
                        for (Animal other : location.getAnimals()) {
                            if (other != animal && other.isAlive() && other instanceof Herbivore) {
                                animal.eat(other);
                            }
                        }
                    }

                    // Травоядные едят растения
                    if (animal instanceof Herbivore && !location.getPlants().isEmpty()) {
                        Plant plant = location.getPlants().get(0);
                        if (animal.eat(plant)) {
                            location.removePlant(plant);
                        }
                    }
                }
            }
        }
    }

    private void processReproduction() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location location = locations[x][y];
                List<Animal> newAnimals = new ArrayList<>();

                for (Animal animal : location.getAnimals()) {
                    if (animal.isAlive()) {
                        Animal baby = animal.reproduce();
                        if (baby != null) {
                            newAnimals.add(baby);
                        }
                    }
                }

                for (Animal baby : newAnimals) {
                    location.addAnimal(baby);
                }
            }
        }
    }

    private void processMovement() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location location = locations[x][y];

                for (Animal animal : location.getAnimals()) {
                    if (animal.isAlive()) {
                        animal.move(this, x, y);
                    }
                }
            }
        }
    }

    public void moveAnimal(Animal animal, int fromX, int fromY, int toX, int toY) {
        if (toX >= 0 && toX < width && toY >= 0 && toY < height) {
            Location fromLocation = locations[fromX][fromY];
            Location toLocation = locations[toX][toY];

            fromLocation.removeAnimal(animal);
            toLocation.addAnimal(animal);
        }
    }

    private void processPlantsAndStarvation() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location location = locations[x][y];

                location.growPlants();

                for (Animal animal : location.getAnimals()) {
                    if (animal.isAlive()) {
                        animal.decreaseSaturation();
                    }
                }

                location.getAnimals().removeIf(animal -> !animal.isAlive());
            }
        }
    }

    public String getStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("Day: ").append(day).append("\n\n");

        // Статистика по хищникам
        stats.append("Predators:\n");
        stats.append(getAnimalStats("Wolf", "🐺"));
        stats.append(getAnimalStats("Boa", "🐍"));
        stats.append(getAnimalStats("Fox", "🦊"));
        stats.append(getAnimalStats("Eagle", "🦅"));

        stats.append("\nHerbivores:\n");
        stats.append(getAnimalStats("Duck", "🦆"));
        stats.append(getAnimalStats("Horse", "🐎"));
        stats.append(getAnimalStats("Cow", "🐃"));
        stats.append(getAnimalStats("Rabbit", "🐇"));
        stats.append(getAnimalStats("Sheep", "🐑"));
        stats.append(getAnimalStats("Hamster", "🐁"));

        int totalAnimals = getTotalAnimals();
        int totalPlants = getTotalPlants();
        stats.append("\nTotal: ").append(totalAnimals).append(" animals, ").append(totalPlants).append(" plants");

        return stats.toString();
    }

    private String getAnimalStats(String type, String emoji) {
        int count = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location location = locations[x][y];
                for (Animal animal : location.getAnimals()) {
                    if (animal.isAlive() && animal.getType().equals(type)) {
                        count++;
                    }
                }
            }
        }
        return String.format("  %s %s: %d\n", emoji, type, count);
    }

    private int getTotalAnimals() {
        int total = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location location = locations[x][y];
                for (Animal animal : location.getAnimals()) {
                    if (animal.isAlive()) {
                        total++;
                    }
                }
            }
        }
        return total;
    }

    private int getTotalPlants() {
        int total = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                total += locations[x][y].getPlants().size();
            }
        }
        return total;
    }
}