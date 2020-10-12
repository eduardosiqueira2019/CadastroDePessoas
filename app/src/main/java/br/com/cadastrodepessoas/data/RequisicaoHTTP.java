package br.com.cadastrodepessoas.data;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import br.com.cadastrodepessoas.model.CEP;

public class RequisicaoHTTP extends AsyncTask<Void,Void, CEP> {

    private final String cep;

    public RequisicaoHTTP(String cep) {
        this.cep = cep;
    }

    @Override
    protected CEP doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        String urlAcessada;

        if (this.cep != null && this.cep.length() == 8) {

            urlAcessada = "viacep.com.br/ws/" + this.cep + "/json/";
            URL url = null;
            URI uri = null;
            String uriString = "https://" + urlAcessada;

            try {
                uri = new URI(uriString);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            try {
                url = uri.toURL();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {

                HttpURLConnection connection = null;
                if (url != null) {
                    connection = (HttpURLConnection) url.openConnection();
                }
                try {
                    connection.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                connection.setRequestProperty("Content-type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                try {
                    connection.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scanner scanner = null;
                if (url != null) {
                    scanner = new Scanner(url.openStream());
                }
                while (scanner.hasNext()) {
                    resposta.append(scanner.next());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new Gson().fromJson(resposta.toString(), CEP.class);
    }
}
