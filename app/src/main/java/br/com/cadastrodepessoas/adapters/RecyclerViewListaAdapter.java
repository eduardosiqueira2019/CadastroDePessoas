package br.com.cadastrodepessoas.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import br.com.cadastrodepessoas.R;
import br.com.cadastrodepessoas.model.Pessoa;

import static br.com.cadastrodepessoas.view.MainActivity.tabLayout;

public class RecyclerViewListaAdapter extends RecyclerView.Adapter<RecyclerViewListaAdapter.ViewHolder> {

    private List<Pessoa> listaPessoas;

    public RecyclerViewListaAdapter(List<Pessoa> listaPessoas) {
        this.listaPessoas = listaPessoas;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_pessoa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pessoa pessoa = listaPessoas.get(position);
        holder.bind(pessoa);

        holder.nomePessoa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Mostra os dados na Tab 1 referente a pessoa selecionada na lista
                Objects.requireNonNull(tabLayout.getTabAt(0)).select();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPessoas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomePessoa;
        TextView telefonePessoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomePessoa = itemView.findViewById(R.id.textViewNomePessoa);
            telefonePessoa = itemView.findViewById(R.id.textViewTelefone);
        }

        public void bind(Pessoa pessoa) {
            try {
                nomePessoa.setText(pessoa.getNome());
                telefonePessoa.setText(pessoa.getTelefone());
            } catch (NullPointerException erro){
                erro.getMessage();
            }

        }
    }
}
