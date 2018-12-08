package am.app.gymnotes.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "table_exercise_detail")
public class ExerciseDetail {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "exercise_id")
    private String exerciseId;

    @ColumnInfo(name = "set")
    private int set;

    @ColumnInfo(name = "rep")
    private int rep;

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

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }
}
