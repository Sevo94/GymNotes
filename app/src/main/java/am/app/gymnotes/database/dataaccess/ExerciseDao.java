package am.app.gymnotes.database.dataaccess;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import am.app.gymnotes.database.entities.Exercise;

@Dao
public interface ExerciseDao {

    @Query("SELECT * FROM table_exercise")
    List<Exercise> getAll();
//
//    @Query("SELECT * FROM table_exercise where table_exercise LIKE  :firstName AND last_name LIKE :lastName")
//    Exercise findByName(String firstName, String lastName);
//
//    @Query("SELECT COUNT(*) from table_exercise")
//    int countUsers();
//
//    @Insert
//    void insertAll(Exercise... users);
//
//    @Delete
//    void delete(Exercise user);
}
