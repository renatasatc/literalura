package br.com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade JPA que representa um AUTOR.
 * Cada autor pode ter vários livros.
 */
@Entity
@Table(name = "autores")
public class Autor {

    /**
     * Identificador único do autor no banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do autor.
     * O atributo unique garante que não haverá
     * dois autores com o mesmo nome.
     */
    @Column(unique = true)
    private String nome;

    /**
     * Ano de nascimento do autor.
     * Pode ser null se a API não informar.
     */
    private Integer anoNascimento;

    /**
     * Ano de falecimento do autor.
     * Null significa que o autor ainda está vivo
     * ou que a informação não existe.
     */
    private Integer anoFalecimento;

    /**
     * Relacionamento UM AUTOR → MUITOS LIVROS.
     */
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    /**
     * Construtor padrão exigido pelo JPA.
     */
    public Autor() {}

    /**
     * Construtor que recebe os dados vindos da API (Gutendex).
     */
    public Autor(AutorDados autorDados) {
        this.nome = autorDados.nome();
        this.anoNascimento = autorDados.anoNascimento();
        this.anoFalecimento = autorDados.anoFalecimento();
    }

    /**
     * 🔹 Construtor para quando o livro NÃO vem com autor na API
     * Exemplo: "Autor desconhecido"
     */
    public Autor(String nome) {
        this.nome = nome;
        this.anoNascimento = null;
        this.anoFalecimento = null;
    }

    // ===== GETTERS E SETTERS =====

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    /**
     * Garante que cada livro da lista
     * tenha este autor associado.
     */
    public void setLivros(List<Livro> livros) {
        livros.forEach(livro -> livro.setAutor(this));
        this.livros = livros;
    }

    @Override
    public String toString() {
        return "Autor: " + nome +
                " (" +
                (anoNascimento != null ? anoNascimento : "?") +
                " - " +
                (anoFalecimento != null ? anoFalecimento : "Vivo") +
                ")";
    }
}
