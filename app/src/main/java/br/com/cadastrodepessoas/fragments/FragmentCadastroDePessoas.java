package br.com.cadastrodepessoas.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import br.com.cadastrodepessoas.R;
import br.com.cadastrodepessoas.data.DatabaseRoom;
import br.com.cadastrodepessoas.data.RequisicaoHTTP;
import br.com.cadastrodepessoas.interfaces.PessoaDAO;
import br.com.cadastrodepessoas.model.CEP;
import br.com.cadastrodepessoas.model.Pessoa;
import br.com.cadastrodepessoas.util.EditTextCep;

public class FragmentCadastroDePessoas extends Fragment {

    FloatingActionButton fabNovoRegistro;
    FloatingActionButton fabGravaRegistro;
    FloatingActionButton fabExcluiRegistro;
    ImageButton btBuscaCep;
    EditText editTextNome;
    EditText editTextIdade;
    EditText editTextTelefone;
    EditTextCep editTextCEP;
    EditText editTextEndereco;
    EditText editTextCompl;
    EditText editTextBairro;
    EditText editTextCidade;
    EditText editTextUF;
    String nome = "", telefone = "", cep = "", endereco = "", complemento = "", bairro = "", cidade = "", uf = "";
    int idade = 0;
    long novoid = 0;
    PessoaDAO pessoaDAO;

    public FragmentCadastroDePessoas() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cadastro_de_pessoas, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        InicializarDAO();
        InstanciarCamposDaActivity();

        fabNovoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrepararUmNovoRegistro();
            }
        });

        fabGravaRegistro.setOnClickListener(new View.OnClickListener() {
            //Se a pessoa já existir atualiza seus dados, senão é incluida no cadastro

            @Override
            public void onClick(View v) {

                ObterValoresDosCamposDaActivity();

                if (!ValidarCamposParaGravacao()) return;

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Pessoa pessoa = pessoaDAO.getByNome(nome);

                        if (pessoa != null) {

                            SetarCamposDAO(pessoa);

                            pessoaDAO.update(pessoa);

                            Snackbar.make(Objects.requireNonNull(getView()), "Registro gravado com sucesso.", Snackbar.LENGTH_LONG).show();

                        } else {

                            pessoa = new Pessoa();

                            SetarCamposDAO(pessoa);

                            pessoaDAO.insert(pessoa);

                            Snackbar.make(Objects.requireNonNull(getView()), "Registro inserido com sucesso.", Snackbar.LENGTH_LONG).show();

                        }

                    }

                }).start();

                PrepararUmNovoRegistro();
            }

            private boolean ValidarCamposParaGravacao() {
                String msgAlertaCampo = "";
                Boolean campoOk = true;

                if (nome.trim().length() == 0) {
                    msgAlertaCampo = "Nome deve ser preenchido.";
                }
                if (idade == 0 && msgAlertaCampo.equals("")) {
                    msgAlertaCampo = "Idade inválida.";
                }
                if (telefone.trim().length() == 0 && msgAlertaCampo.equals("")) {
                    msgAlertaCampo = "Telefone deve ser preenchido.";
                }
                if (cep.trim().length() != 9 && msgAlertaCampo.equals("")) {
                    msgAlertaCampo = "CEP inválido.";
                }
                if (endereco.trim().length() == 0 && msgAlertaCampo.equals("")) {
                    msgAlertaCampo = "Endereço deve ser preenchido.";
                }
                if (complemento.trim().length() == 0 && msgAlertaCampo.equals("")) {
                    msgAlertaCampo = "Complemento deve ser preenchido.";
                }
                if (bairro.trim().length() == 0 && msgAlertaCampo.equals("")) {
                    msgAlertaCampo = "Bairro deve ser preenchido.";
                }
                if (cidade.trim().length() == 0 && msgAlertaCampo.equals("")) {
                    msgAlertaCampo = "Cidade deve ser preenchida.";
                }
                if (uf.trim().length() == 0 && msgAlertaCampo.equals("")) {
                    msgAlertaCampo = "UF deve ser preenchida.";
                }

                campoOk = msgAlertaCampo.equals("");

                if (!campoOk)
                    Snackbar.make(Objects.requireNonNull(getView()), msgAlertaCampo, Snackbar.LENGTH_LONG).show();

                return campoOk;

            }

        });

        fabExcluiRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Pessoa pessoa = pessoaDAO.getByNome(nome);

                        if (pessoa != null) {

                            pessoaDAO.delete(pessoa);

                            Snackbar.make(Objects.requireNonNull(getView()), "Registro excluido com sucesso.", Snackbar.LENGTH_LONG).show();
                            PrepararUmNovoRegistro();

                        } else {

                            Snackbar.make(Objects.requireNonNull(getView()), "Registro não existe.", Snackbar.LENGTH_LONG).show();

                        }

                    }

                }).start();

            }
        });


        btBuscaCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cep = Objects.requireNonNull(editTextCEP.getText()).toString().trim();
                if (cep.length() != 9) {
                    Snackbar.make(Objects.requireNonNull(getView()), "Cep inválido.", Snackbar.LENGTH_LONG).show();
                    return;
                }
                try {
                    CEP dadosRetornados = null;
                    try {

                        dadosRetornados = new RequisicaoHTTP(cep.replace("-", "")).execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    if (dadosRetornados != null) {
                        PreencherCamposComDadosDaAPI(dadosRetornados);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            private void PreencherCamposComDadosDaAPI(CEP cepRetornado) {
                editTextEndereco.setText(cepRetornado.getLogradouro());
                editTextCompl.setText(cepRetornado.getComplemento());
                editTextBairro.setText(cepRetornado.getBairro());
                editTextCidade.setText(cepRetornado.getLocalidade());
                editTextUF.setText(cepRetornado.getUf());
            }
        });


        super.onActivityCreated(savedInstanceState);
    }

    private void InicializarDAO() {
        DatabaseRoom room = DatabaseRoom.getDatabase(getContext());
        pessoaDAO = room.pessoaDAO();
    }

    public void PrepararUmNovoRegistro() {
        editTextNome.setText("");
        editTextIdade.setText("");
        editTextTelefone.setText("");
        editTextCEP.setText("");
        editTextEndereco.setText("");
        editTextCompl.setText("");
        editTextBairro.setText("");
        editTextCidade.setText("");
        editTextUF.setText("");
        editTextNome.requestFocus();
    }

    private void InstanciarCamposDaActivity() {
        fabNovoRegistro = getActivity().findViewById(R.id.fabNovo);
        fabGravaRegistro = getActivity().findViewById(R.id.fabGrava);
        fabExcluiRegistro = getActivity().findViewById(R.id.fabExclui);
        editTextNome = getActivity().findViewById(R.id.editTextNomePessoa);
        editTextIdade = getActivity().findViewById(R.id.editTextIdadePessoa);
        editTextTelefone = getActivity().findViewById(R.id.editTextTelefone);
        editTextCEP = getActivity().findViewById(R.id.editTextCEP);
        editTextEndereco = getActivity().findViewById(R.id.editTextEndereco);
        editTextCompl = getActivity().findViewById(R.id.editTextComplemento);
        editTextBairro = getActivity().findViewById(R.id.editTextBairro);
        editTextCidade = getActivity().findViewById(R.id.editTextCidade);
        editTextUF = getActivity().findViewById(R.id.editTextUF);
        btBuscaCep = getActivity().findViewById(R.id.imgButtonBuscaCep);
    }

    private void ObterValoresDosCamposDaActivity() {
        nome = editTextNome.getText().toString();
        idade = editTextIdade.getText().toString().trim().equals("") ? 0 : Integer.parseInt(editTextIdade.getText().toString());
        telefone = editTextTelefone.getText().toString();
        cep = Objects.requireNonNull(editTextCEP.getText()).toString();
        endereco = editTextEndereco.getText().toString();
        complemento = editTextCompl.getText().toString();
        bairro = editTextBairro.getText().toString();
        cidade = editTextCidade.getText().toString();
        uf = editTextUF.getText().toString();
    }

    public void SetarCamposDAO(Pessoa pessoa) {
        pessoa.setNome(nome);
        pessoa.setIdade(idade);
        pessoa.setTelefone(telefone);
        pessoa.setCep(cep);
        pessoa.setEndereco(endereco);
        pessoa.setComplemento(complemento);
        pessoa.setBairro(bairro);
        pessoa.setCidade(cidade);
        pessoa.setUf(uf);
    }
}
