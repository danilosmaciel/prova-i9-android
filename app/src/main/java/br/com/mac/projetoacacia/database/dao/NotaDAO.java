package br.com.mac.projetoacacia.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.mac.projetoacacia.model.Nota;
import retrofit2.http.DELETE;

@Dao
public interface NotaDAO {

    @Insert
    long save(final Nota nota);

    @Update
    void update(final Nota nota);

    @Query("SELECT * FROM Nota")
    List<Nota> getAll();

    @Query("SELECT * FROM Nota WHERE id = :id")
    Nota getNota(String id);

    @Delete
    void remove(Nota produto);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<Nota> notas);

    @Query("DELETE FROM Nota")
    void removeAll();
}
