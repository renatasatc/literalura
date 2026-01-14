package br.com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @Enumerated(EnumType.STRING)
    private Idioma idioma;

    // Guarda o código real da API: it, de, ru, en...
    @Column(name = "codigo_idioma")
    private String codigoIdioma;

    private Integer numeroDownloads;

    @ManyToOne
    private Autor autor;

    public Livro() {
    }

    public Livro(LivroDados livroDados, Autor autor) {
        this.titulo = livroDados.titulo();

        if (livroDados.idiomas() != null && !livroDados.idiomas().isEmpty()) {
            this.codigoIdioma = livroDados.idiomas().get(0).toLowerCase();
            this.idioma = Idioma.fromCodigoApi(this.codigoIdioma);
        } else {
            this.codigoIdioma = "desconhecido";
            this.idioma = Idioma.OUTRO;
        }

        this.numeroDownloads = livroDados.numeroDownloads() != null
                ? livroDados.numeroDownloads().intValue()
                : 0;

        this.autor = autor;
    }

    // ===========================
    // GETTERS & SETTERS
    // ===========================

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public String getCodigoIdioma() {
        return codigoIdioma;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    // ===========================
    // toString
    // ===========================
    @Override
    public String toString() {
        return "Livro: " + titulo +
                " | Autor: " + (autor != null ? autor.getNome() : "N/A") +
                " | Idioma: " + idioma.getDescricao() +
                " (" + codigoIdioma + ")" +
                " | Downloads: " + numeroDownloads;
    }
}
