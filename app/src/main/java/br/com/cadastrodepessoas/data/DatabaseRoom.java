package br.com.cadastrodepessoas.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.cadastrodepessoas.interfaces.PessoaDAO;
import br.com.cadastrodepessoas.model.Pessoa;


@Database(entities = {Pessoa.class},version = 3,exportSchema = false)
public abstract class DatabaseRoom extends RoomDatabase {

    public abstract PessoaDAO pessoaDAO();
    private static volatile DatabaseRoom INSTANCE;

    public static DatabaseRoom getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (DatabaseRoom.class) {
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DatabaseRoom.class,"sqlite_room_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
