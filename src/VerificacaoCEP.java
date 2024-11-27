import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class VerificacaoCEP {
    Scanner leitura = new Scanner(System.in);

    public Endereco ImportaBuscaCEP() {
        HttpResponse<String> response;
        try {
            System.out.println("Digite um CEP:");
            String linkApi = "https://viacep.com.br/ws/" + leitura.nextInt() + "/json/";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(linkApi))
                    .build();
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            System.out.println(json);

        } catch (Exception e) {
            throw new RuntimeException("Erro na consulta do CEP. Tente novamente.");
        }

        return new Gson().fromJson(response.body(), Endereco.class);

    }
}
