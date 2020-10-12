package br.com.cadastrodepessoas.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pessoa")
public class Pessoa {


    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name = "idade")
    private int idade;

    @ColumnInfo(name = "telefone")
    private String telefone;

    @ColumnInfo(name = "cep")
    private String cep;

    @ColumnInfo(name = "endereco")
    private String endereco;

    @ColumnInfo(name = "complemento")
    private String complemento;

    @ColumnInfo(name = "bairro")
    private String bairro;

    @ColumnInfo(name = "localidade")
    private String cidade;

    @ColumnInfo(name = "uf")
    private String uf;


    public Pessoa(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

//    public Pessoa(long id, String nome, int idade, String telefone) {
//        this.nome = nome;
//        this.telefone = telefone;
//    }
//
//    public Pessoa(long id, String nome, int idade, String telefone, String cep, String endereco, String complemento, String bairro, String cidade, String uf) {
//        this.id = id;
//        this.nome = nome;
//        this.idade = idade;
//        this.telefone = telefone;
//        this.cep = cep;
//        this.endereco = endereco;
//        this.complemento = complemento;
//        this.bairro = bairro;
//        this.cidade = cidade;
//        this.uf = uf;
//    }


    public Pessoa(String nome, int idade, String telefone, String cep, String endereco, String complemento, String bairro, String cidade, String uf) {
        this.nome = nome;
        this.idade = idade;
        this.telefone = telefone;
        this.cep = cep;
        this.endereco = endereco;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public Pessoa() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
