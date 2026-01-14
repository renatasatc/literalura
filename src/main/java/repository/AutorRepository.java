package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repositório responsável pelo acesso à tabela AUTOR no banco de dados.
 *
 * JpaRepository já fornece:
 * - save()
 * - findAll()
 * - findById()
 * - delete()
 * - etc
 */
public interface AutorRepository extends JpaRepository<Autor, Long> {

    /**
     * Busca um autor pelo nome exato.
     *
     * O Spring Data JPA cria essa consulta automaticamente
     * baseado no nome do metodo.
     */
    Optional<Autor> findByNome(String nome);

    /**
     * Busca autores vivos em determinado ano.
     *
     * Esta é uma consulta JPQL (não é SQL puro).
     * Ela usa os nomes dos ATRIBUTOS da classe Autor,
     * e não os nomes das colunas do banco.
     *
     * Regras:
     * - autor nasceu antes ou no ano informado
     * - e não morreu ainda OU morreu depois desse ano
     */
    @Query("""
        SELECT a
        FROM Autor a
        WHERE a.anoNascimento <= :ano
          AND (a.anoFalecimento >= :ano OR a.anoFalecimento IS NULL)
    """)
    List<Autor> autoresVivosNoAno(int ano);
}
