// Trabalho de ATP Wordle Parte 3
// Trabalho feito por: Emanuel Nogueira, Miguel Sadi e Igor Cruz Duarte.

import java.util.*;
import java.io.*;

public class Termo {
    public static void main(String[] args) throws IOException {
        // Declaração de variáveis;
        Scanner sc = new Scanner(System.in);
        String tentativa = "", resp;
        int pos;// Variável de posição para ajuda.
        Random r = new Random();
        Dicionario dicionario = new Dicionario(2326, "palavrasJogo.txt"); // declarando uma nova classe dicionario.
        System.out.println(" \033[H\033[2J"); // Limpa terminal
        System.out.println("T E R M O");
        resp = dicionario.sortearPalavra(r.nextInt(2325)); // Variável resp (resposta) recebendo uma palavra aleátoria
                                                           // através da classe dicionario.
        for (int i = 6; i >= 0;) { // Esse for é responsável para contar as tentativas que a pessoa possui.

            System.out.println("Digite uma palavra de 5 letras:");
            tentativa = sc.next(); // Variável de tentativa recebe a resposta da pessoa.

            if (tentativa.length() == 5) { // Esse if verifica se a palavra inserida pelo usuário possui as 5
                                           // letras, nem mais nem menos.
                if (dicionario.checarPalavra(tentativa)) { // Chama o método para checar se a palavra existe na língua portuguesa.
                    if (tentativa.equals(resp)) { // Esse if interrompe o for que conta as tentativas se a pessoa
                                                  // acertar a resposta.
                        i = -1;
                    } else {
                        for (int a = 0; a < 5; a++) {
                            pos = resp.indexOf(tentativa.charAt(a)); // Se pos receber -1, quer dizer que não existe
                                                                     // aquela letra na resposta, então ja printa
                                                                     // cinza.
                            if (pos == -1) {
                                System.out.print("\033[1;90m"); // código de negrito (1) e cinza(90)
                                System.out.print(tentativa.charAt(a));
                                System.out.print("\033[0;39m"); // código para texto padrão (0 e 39)
                            } else { // Se a letra existir já entra aqui:
                                if (tentativa.charAt(a) == resp.charAt(a)) { // Se a letra da tentativa estiver na
                                                                             // mesma posição da resposta printa
                                                                             // verde.
                                    System.out.print("\033[1;92m"); // código de negrito (1) e verde claro(92)
                                    System.out.print(tentativa.charAt(a));
                                    System.out.print("\033[0;39m"); // código para texto padrão (0 e 39)
                                } else { // Se nenhuma das duas condições acima for verdadeira ele printa amarelo.
                                    System.out.print("\033[1;93m"); // código de negrito (1) e amarelo claro(93)
                                    System.out.print(tentativa.charAt(a));
                                    System.out.print("\033[0;39m"); // código para texto padrão (0 e 39)
                                }
                            }
                        }
                        System.out.println("");
                        System.out.println("Você possui: " + i + " tentativa(s)..."); // Sysout para mostrar quantas
                                                                                    // tentativas a pessoa possui a
                                                                                    // partir do for.
                        i--; // Esse i-- está aqui pois só é descontada uma chance se a palavra inserida
                             // tiver 5 letras.
                    }
                } else {
                    System.out.println("A palavra não existe na língua portuguesa, tente novamente!"); // caso não exista a tentativa na língua portuguesa cai nesse else.
                }
            } else {
                System.out.println(""); // Aviso caso a palavra não tenha 5 letras:
                System.out.println("É necessário ter 5 letras!!!");
            }

        }
        if (tentativa.equals(resp)) { // If para checar o motivo de saída do for.
            System.out.print("\033[1;92m"); // código de negrito (1) e verde claro(92)
            System.out.print(resp); // Printar a resposta toda verde caso a pessoa acerte.
            System.out.print("\033[0;39m"); // código para texto padrão (0 e 39)
            System.out.println(""); // Caso a pessoa tenha acertado a palavra printa o parabéns:
            System.out.println("Parabéns, você acertou a palavra!!!");
        } else {
            System.out.println("Suas tentativas acabaram!!"); // Caso não, printa que acabou as tentativas e mostra
                                                              // a resposta:
            System.out.println("A palavra era: " + resp);
        }
        sc.close(); // Fechamento do scannner.
    }
}