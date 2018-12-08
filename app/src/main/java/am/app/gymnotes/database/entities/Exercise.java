package am.app.gymnotes.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "table_exercise")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "exercise_id")
    private String exerciseId;

    @ColumnInfo(name = "exercise_name")
    private String exerciseName;

    @ColumnInfo(name = "exercise_date")
    private String exerciseDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseDate() {
        return exerciseDate;
    }

    public void setExerciseDate(String exerciseDate) {
        this.exerciseDate = exerciseDate;
    }
}
