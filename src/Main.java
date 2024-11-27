import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner leitura = new Scanner(System.in);
        int escolhaMenu = 1;
        List<Endereco> listaDeCep = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while (escolhaMenu != 0){
            System.out.println("""
        ****************************************
        
        Bem-vindo(a) ao identificador de CEP.
        
        1 - Informações sobre um CEP.
        2 - Localizar CEP.
        0 - Sair.
        
        ****************************************""");
            System.out.println("Escolha uma opção:");
            escolhaMenu = leitura.nextInt();

            if (escolhaMenu == 1){
                VerificacaoCEP verificacaoCep = new VerificacaoCEP();
                listaDeCep.add(verificacaoCep.ImportaBuscaCEP());
            } else if (escolhaMenu == 2){
                VerificacaoEndereco verificacaoEndereco = new VerificacaoEndereco();
                listaDeCep.add(verificacaoEndereco.ImportaBuscaEndereco());
            } else if (escolhaMenu == 0) {
                System.out.println("Obrigado por fazer uso da aplicação.");
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
        FileWriter escrita = new FileWriter("listaCEP.json");
        escrita.write(gson.toJson(listaDeCep));
        escrita.close();
    }
}