package br.com.alura.literalura.service;

// Exceção lançada quando o Jackson falha ao converter o JSON
import com.fasterxml.jackson.core.JsonProcessingException;

// Classe principal do Jackson que converte JSON em objetos Java
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe responsável por converter uma String JSON
 * retornada pela API Gutendex em objetos Java.
 *
 * Exemplo:
 * JSON → DadosAPI → LivroDados → AutorDados
 */
public class ConverterDados {

    // ObjectMapper é o "coração" do Jackson
    // Ele faz o mapeamento automático usando os nomes dos campos
    // e anotações como @JsonAlias
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Converte um JSON em um objeto Java do tipo informado.
     *
     * @param json    JSON retornado pela API
     * @param classe Classe de destino (ex: DadosAPI.class)
     * @param <T>    Tipo genérico
     * @return Objeto Java preenchido
     */
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            // Faz a leitura do JSON e mapeia para a classe informada
            return mapper.readValue(json, classe);

        } catch (JsonProcessingException e) {
            // Caso o JSON não corresponda à estrutura esperada
            throw new RuntimeException("Erro ao converter JSON com Jackson", e);
        }
    }
}
