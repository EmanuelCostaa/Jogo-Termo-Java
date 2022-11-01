import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// MIT License
// Copyright (c) 2022 Luiz Felipe dos Reis Amorim
//                    João Caram (refactoring, test and documentation)
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE

/** Dicionário com palavras de 5 letras. Contém as operações:
 * -- sortear uma palavra a partir de um número inteiro
 * -- verificar se uma palavra de 5 letras existe no dicionário
 */
public class Dicionario{

    private final int tam;
    private Entrada[] palavras;

    /**
     * Cria o dicionário. O tamanho máximo deve ser passado como parâmetro
     * @param tamanho Tamanho do dicionário criado.
     * @param nomeArquivo O nome do arquivo texto a ser carregado no dicionário
     * @throws FileNotFoundException Exceção de arquivo não encontrado
     */
    public Dicionario(int tamanho, String nomeArquivo) throws FileNotFoundException{
        this.tam = tamanho;
        this.palavras = new Entrada[this.tam];

        for(int i = 0; i < this.tam; i++)
            palavras[i] = new Entrada();

        carregarDados(nomeArquivo);
    }

    /**
     * Carrega as palavras de um arquivo de texto para o dicionário, filtrando somente palavras de 5 letras
     * @param nomeArquivo Nome do arquivo contendo os dados
     * @throws FileNotFoundException Em caso de arquivo não encontrado, causa esta exceção
     */
    private void carregarDados(String nomeArquivo) throws FileNotFoundException {
        Scanner arquivo = new Scanner(new File(nomeArquivo), "UTF-8");
       
        while(arquivo.hasNextLine()){
            String palavra = arquivo.nextLine();
            if(palavra.length() == 5)
                inserir(palavra);
        }
        arquivo.close();
    }

    /**
     * Calcula um código hash para uma chave string / palavra
     * @param chave A palavra usada como chave
     * @return O valor do código (inteiro)
     */
    private int codigoHash(String chave){
        int codigo = 0;
        for(int i = 0; i < chave.length(); i++){
            int valor = chave.charAt(i) - 96;
            codigo += (int)(valor * Math.pow(19, chave.length() - (i + 1)));
        }
        return codigo;
    }

    /**
     * Mapeia um código para o espaço da tabela do dicionário
     * @param codigo Código a ser mapeado
     * @return Posição mapeada dentro do dicionário
     */
    private int mapear(int codigo){
        int pos = -1;
        pos =  codigo % this.tam;//((a * codigo + b) % p) % this.tam;
        return pos;
    }

    /**
     * Localiza uma palavra dentro do dicionário. Colisão tratada por sondagem quadrática
     * @param chave A palavra a ser localizada
     * @return Sua posição no dicionário (-1 para não existente)
     */
    private int localizar(String chave){
        int pos = -1;
        pos = mapear(codigoHash(chave));
        int col = 1;
        while(palavras[pos].valido && !chave.equals(palavras[pos].palavra)){
            pos = (pos + col * col) % this.tam;
            col++;
        }
        return pos;
    }

    /**
     * Sorteia uma palavra do dicionário
     * @param pos Posição desejada para o sorteio
     * @return  String da posição desejada ou da posição mais próxima à frente
     */
    public String sortearPalavra(int pos){
        if(pos < 0)
            pos = 0;
        if(pos > palavras.length - 1)
            pos = palavras.length - 1;
        while(!palavras[pos].valido)
            pos = (pos + 1) % palavras.length;
        return  palavras[pos].getValor();
    }


    /**
     * Checa se uma palavra existe no dicionário
     * @param palavra Palavra que vai ser procurada
     * @return  true se a palavra existir, false se não existir
     */
    public boolean checarPalavra(String palavra){
        boolean existe = false;
        if(!buscar(palavra).equals(""))
            existe = true;
        return existe;
    }

    /**
     * Insere uma nova palavra no dicionário. A palavra é associada a si mesma como chave.
     * @param palavra Palavra a ser armazenada.
     * @return Caso já exista aquela chave, o valor é retirado e retornado.
     */
    public String inserir( String palavra){
        Entrada nova = new Entrada(palavra, palavra);
        int pos = localizar(palavra);

        String retirada = palavras[pos].getValor();
        palavras[pos] = nova;

        return retirada;
    }

    /**
     * Chamada interna para o método de localizar uma palavra
     * @param chave Chave do termo a ser localizado
     * @return O termo associado à chave.
     */
    private String buscar(String chave){
        int pos = localizar(chave);
        return palavras[pos].getValor();
    }
    
    
    /**
     * Imprime todas as palavras contidas no dicionário
     * @return Uma string com todas as palavras, uma por linha
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.tam; i++){
            if(palavras[i].valido)
                sb.append(String.format("%5d", i+1) + ": "+palavras[i].palavra + "\n");
        }
        return sb.toString();
    }
}