package com.papis.gymtracker.dto;

import com.papis.gymtracker.model.WorkoutEntry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public record PersonalRecordResponse(
        String exerciseId,
        String exerciseName,
        BigDecimal estimated1RM,
        BigDecimal bestVolumeKg,
        BigDecimal bestVolumeWeightInSet,
        Integer bestVolumeReps,
        LocalDate achievedOnBestVolume
) {
    public static PersonalRecordResponse from(List<WorkoutEntry> entries){
        if(entries.isEmpty()) return null;

        WorkoutEntry best1RMEntry = null;
        BigDecimal best1RM = BigDecimal.ZERO;

        WorkoutEntry bestVolumeEntry = null;
        BigDecimal bestVolume = BigDecimal.ZERO;

        for(WorkoutEntry entry : entries){
            if(entry.getWeightKg() == null) continue;

            BigDecimal estimated1RM = entry.getWeightKg()
                    .multiply(BigDecimal.ONE.add(
                            BigDecimal.valueOf(entry.getReps()).divide(BigDecimal.valueOf(30), 4, RoundingMode.HALF_UP)));

            if(estimated1RM.compareTo(best1RM) > 0){
                best1RM = estimated1RM;
                best1RMEntry = entry;
            }
            BigDecimal volume = entry.getWeightKg().multiply(BigDecimal.valueOf(entry.getReps()));

            if(volume.compareTo(bestVolume) > 0){
                bestVolume = volume;
                bestVolumeEntry = entry;
            }
        }

        if (best1RMEntry == null) return null;
        if (bestVolumeEntry == null) return null;

        return new PersonalRecordResponse(
                best1RMEntry.getExercise().getId(),
                best1RMEntry.getExercise().getName(),
                best1RM.setScale(2, RoundingMode.HALF_UP),
                bestVolume.setScale(2, RoundingMode.HALF_UP),
                bestVolumeEntry.getWeightKg(),
                bestVolumeEntry.getReps(),
                bestVolumeEntry.getSession().getSessionDate()
        );
    }
}
