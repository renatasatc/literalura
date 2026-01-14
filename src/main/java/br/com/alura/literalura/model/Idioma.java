package br.com.alura.literalura.model;

import java.util.Locale;

public enum Idioma {

    PORTUGUES("pt", "Português"),
    INGLES("en", "Inglês"),
    ESPANHOL("es", "Espanhol"),
    FRANCES("fr", "Francês"),
    ITALIANO("it", "Italiano"),
    ALEMAO("de", "Alemão"),
    RUSSO("ru", "Russo"),
    OUTRO("outro", "Outro");

    private final String codigoApi;
    private final String descricao;

    Idioma(String codigoApi, String descricao) {
        this.codigoApi = codigoApi;
        this.descricao = descricao;
    }

    public String getCodigoApi() {
        return codigoApi;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Idioma fromCodigoApi(String codigo) {
        if (codigo == null || codigo.isBlank()) return OUTRO;

        String normalizado = codigo.toLowerCase(Locale.ROOT);

        for (Idioma idioma : values()) {
            if (idioma.codigoApi.equals(normalizado)) {
                return idioma;
            }
        }
        return OUTRO;
    }
}
