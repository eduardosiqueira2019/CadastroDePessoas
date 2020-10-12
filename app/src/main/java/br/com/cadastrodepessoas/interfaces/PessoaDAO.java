package br.com.cadastrodepessoas.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import br.com.cadastrodepessoas.model.Pessoa;


@Dao
public interface PessoaDAO {

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    void insert(Pessoa pessoa);

    @Update()
    void update(Pessoa pessoa);

    @Delete()
    void delete(Pessoa pessoa);

    @Query("SELECT * from pessoa ORDER BY nome ASC")
    Pessoa getAll();

    @Query("SELECT * from pessoa WHERE id = :id")
    Pessoa getById(long id);

    @Query("SELECT * from pessoa WHERE nome = :nome")
    Pessoa getByNome(String nome);

}
