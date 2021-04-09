package br.com.mac.projetoacacia.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.mac.projetoacacia.database.dao.NotaDAO;
import br.com.mac.projetoacacia.model.Nota;


@Database(entities = {Nota.class}, version = 1, exportSchema = false)
public abstract class ProjetoDatabase extends RoomDatabase {

    private static final String NOME_BANCO_DE_DADOS = "estoque.db";
    private static ProjetoDatabase INSTANCE = null;
    public abstract NotaDAO getNotaoDAO();

    public static ProjetoDatabase getInstance(final Context context) {
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, ProjetoDatabase.class, NOME_BANCO_DE_DADOS).fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

}
