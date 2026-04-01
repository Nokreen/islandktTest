import java.util.ArrayList;
import java.util.List;

public class Location {
    private List<Animal> animals = new ArrayList<>();
    private List<Plant> plants = new ArrayList<>();

    public void addAnimal(Animal animal) {
        long sameTypeCount = animals.stream()
                .filter(a -> a.getType().equals(animal.getType()))
                .count();

        if (sameTypeCount < animal.maxPerCell) {
            animals.add(animal);
        }
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public void addPlant(Plant plant) {
        if (plants.size() < 200) {
            plants.add(plant);
        }
    }

    public void removePlant(Plant plant) {
        plants.remove(plant);
    }

    public List<Animal> getAnimals() {
        return new ArrayList<>(animals);
    }

    public List<Plant> getPlants() {
        return new ArrayList<>(plants);
    }

    public void growPlants() {
        if (plants.size() < 200 && Math.random() < 0.1) {
            addPlant(new Plant());
        }
    }
}