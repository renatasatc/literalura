package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório JPA para gerenciar entidades Livro.
 */
public interface LivroRepository extends JpaRepository<Livro, Long> {

    /**
     * Busca todos os livros de um determinado idioma.
     * @param idioma Idioma do livro (enum Idioma)
     * @return Lista de livros no idioma informado
     */
    List<Livro> findByIdioma(Idioma idioma);

    /**
     * Busca um livro pelo título e autor.
     * @param titulo Título do livro
     * @param autor Autor do livro
     * @return Optional contendo o livro, se encontrado
     */
    Optional<Livro> findByTituloAndAutor(String titulo, Autor autor);

    /**
     * Verifica se um livro já existe pelo título e autor.
     * @param titulo Título do livro
     * @param autor Autor do livro
     * @return true se existir, false caso contrário
     */
    boolean existsByTituloAndAutor(String titulo, Autor autor);
}
