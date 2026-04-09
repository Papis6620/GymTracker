package com.papis.gymtracker.seeder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.papis.gymtracker.model.Exercise;
import com.papis.gymtracker.model.MuscleGroup;
import com.papis.gymtracker.model.enums.*;
import com.papis.gymtracker.repository.ExerciseRepository;
import com.papis.gymtracker.repository.MuscleGroupRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Component
public class ExerciseSeeder implements CommandLineRunner {

    private final ExerciseRepository exerciseRepository;
    private final MuscleGroupRepository muscleGroupRepository;
    private final ObjectMapper objectMapper;

    public ExerciseSeeder(ExerciseRepository exerciseRepository, MuscleGroupRepository muscleGroupRepository, ObjectMapper objectMapper) {
        this.exerciseRepository = exerciseRepository;
        this.muscleGroupRepository = muscleGroupRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (exerciseRepository.count() > 0) return; // already seeded

        InputStream is = getClass().getResourceAsStream("/exercises.json");
        JsonNode root = objectMapper.readTree(is);

        for (JsonNode node : root) {
            Exercise exercise = new Exercise();
            exercise.setId(node.get("id").asText());
            exercise.setName(node.get("name").asText());
            exercise.setCategory(parseEnum(Category.class, node.get("category").asText()));
            exercise.setLevel(parseEnum(Level.class, node.get("level").asText()));

            if (node.has("equipment") && !node.get("equipment").isNull()) {
                String raw = node.get("equipment").asText();
                Equipment equipmentEnum = parseEnum(Equipment.class, raw);
                exercise.setEquipment(equipmentEnum);
            }

            // handle nullable fields
            exercise.setForce(node.has("force") && !node.get("force").isNull()
                    ? parseEnum(Force.class, node.get("force").asText()) : null);
            exercise.setMechanic(node.has("mechanic") && !node.get("mechanic").isNull()
                    ? parseEnum(Mechanic.class, node.get("mechanic").asText()) : null);

            // resolve muscle groups
            Set<MuscleGroup> primaryMuscles = resolveMuscles(node.get("primaryMuscles"));
            Set<MuscleGroup> secondaryMuscles = resolveMuscles(node.get("secondaryMuscles"));
            exercise.setPrimaryMuscles(primaryMuscles);
            exercise.setSecondaryMuscles(secondaryMuscles);

            exerciseRepository.save(exercise);
        }
    }

    private <E extends Enum<E>> E parseEnum(Class<E> enumType, String rawValue) {
        if (rawValue == null) {
            return null;
        }
        String normalized = rawValue
                .trim()
                .toUpperCase(Locale.ROOT)
                .replace(' ', '_')
                .replace('-', '_');
        return Enum.valueOf(enumType, normalized);
    }

    private Set<MuscleGroup> resolveMuscles(JsonNode muscleArray) {
        Set<MuscleGroup> result = new HashSet<>();
        if (muscleArray == null) return result;
        for (JsonNode m : muscleArray) {
            String name = m.asText();
            MuscleGroup mg = muscleGroupRepository.findByName(name)
                    .orElseGet(() -> muscleGroupRepository.save(new MuscleGroup(name)));
            result.add(mg);
        }
        return result;
    }
}
