import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

public class VerificacaoEndereco {

    Scanner leitura = new Scanner(System.in);

    public Endereco ImportaBuscaEndereco() {
        String linkApi;
        String uf;
        String cidade;
        String logradouro;

        HttpResponse<String> response;
        try {
            System.out.println("Digite o UF:");
            uf = leitura.nextLine();
            System.out.println("Digite a cidade:");
            cidade = leitura.nextLine();
            System.out.println("Digite o logradouro:");
            logradouro = leitura.nextLine();

            linkApi = "https://viacep.com.br/ws/" +
                    uf.toUpperCase() + "/" +
                    cidade.replace(" ", "%20") + "/" +
                    logradouro.replace(" ", "+") +
                    "/json/";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(linkApi))
                    .build();
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            System.out.println(json);

            if (json.startsWith("[")) {
                Type listType = new TypeToken<List<Endereco>>(){}.getType();
                List<Endereco> enderecos = new Gson().fromJson(json, listType);

                if (!enderecos.isEmpty()) {
                    return enderecos.get(0);
                } else {
                    System.out.println("Nenhum resultado encontrado.");
                    return null;
                }
            } else {
                return new Gson().fromJson(json, Endereco.class);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro na consulta do CEP. Tente novamente.");
        }
    }

    public static void main(String[] args) {
        VerificacaoEndereco ve = new VerificacaoEndereco();
        Endereco endereco = ve.ImportaBuscaEndereco();
        if (endereco != null) {
            System.out.println("CEP encontrado: " + endereco.cep());
            System.out.println("Logradouro: " + endereco.logradouro());
            System.out.println("Complemento: " + endereco.complemento());
            System.out.println("Cidade: " + endereco.localidade());
            System.out.println("UF: " + endereco.uf());
        }
    }
}

