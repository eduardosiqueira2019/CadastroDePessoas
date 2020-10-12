package br.com.cadastrodepessoas.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.cadastrodepessoas.R;
import br.com.cadastrodepessoas.adapters.RecyclerViewListaAdapter;
import br.com.cadastrodepessoas.data.DatabaseRoom;
import br.com.cadastrodepessoas.interfaces.PessoaDAO;
import br.com.cadastrodepessoas.model.Pessoa;

public class FragmentListaPessoas extends Fragment {
    PessoaDAO pessoaDAO;
    List<Pessoa> listaDePessoas = new ArrayList<>();

    public FragmentListaPessoas() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_lista_pessoas, container, false);
        final FragmentActivity fragmentActivity = getActivity();
        final RecyclerView recyclerView = view.findViewById(R.id.rvFragmentListaPessoa);
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentActivity);
        recyclerView.setLayoutManager(layoutManager);





        new Thread(new Runnable() {
            @Override
            public void run() {
                final RecyclerViewListaAdapter adapter = new RecyclerViewListaAdapter(ListaTodasPessoas());
                fragmentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.getItemCount();


                    }
                });
            }
        }).start();

        return view;
    }

    private List<Pessoa> ListaTodasPessoas() {

        DatabaseRoom room = DatabaseRoom.getDatabase(getContext());
        pessoaDAO = room.pessoaDAO();
        Pessoa pessoa = pessoaDAO.getAll();
        listaDePessoas.addAll(Collections.singleton(pessoa));
        return listaDePessoas;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
