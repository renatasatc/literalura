package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representa um autor vindo da API Gutendex.
 * Não é entidade JPA, é usado apenas para mapeamento JSON → Java.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDados(

        /**
         * Nome do autor, vindo do JSON ("name").
         */
        @JsonAlias("name")
        String nome,

        /**
         * Ano de nascimento do autor, JSON "birth_year".
         * Pode ser null.
         */
        @JsonAlias("birth_year")
        Integer anoNascimento,

        /**
         * Ano de falecimento do autor, JSON "death_year".
         * Pode ser null.
         */
        @JsonAlias("death_year")
        Integer anoFalecimento
) {}
