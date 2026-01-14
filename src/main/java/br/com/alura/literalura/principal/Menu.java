package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.*;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumirAPI;
import br.com.alura.literalura.service.ConverterDados;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private final Scanner leitura = new Scanner(System.in);
    private final ConsumirAPI consumo = new ConsumirAPI();
    private final ConverterDados conversor = new ConverterDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public Menu(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    // ======================================================
    // MENU PRINCIPAL
    // ======================================================

    public void exibeMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n================= MENU =================");
            System.out.println("1. Buscar livro pelo título e cadastrar");
            System.out.println("2. Listar livros cadastrados");
            System.out.println("3. Listar autores cadastrados");
            System.out.println("4. Listar autores vivos em determinado ano");
            System.out.println("5. Listar livros em determinado idioma");
            System.out.println("0. Sair");

            System.out.print("Escolha uma opção: ");
            String entrada = leitura.nextLine().trim();

            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("\n❌ Digite apenas números válidos.");
                continue;
            }

            switch (opcao) {
                case 1 -> buscarECadastrarLivro();
                case 2 -> listarLivrosCadastrados();
                case 3 -> listarAutoresCadastrados();
                case 4 -> listarAutoresVivosEmDeterminadoAno();
                case 5 -> listarLivrosPorIdioma();
                case 0 -> System.out.println("\nSaindo do sistema...\n");
                default -> System.out.println("\n❌ Opção inexistente no menu!");
            }
        }
    }

    // ======================================================
    // BUSCAR E CADASTRAR LIVRO
    // ======================================================

    private void buscarECadastrarLivro() {
        System.out.print("Informe o título do livro para pesquisa: ");
        String nomeLivro = leitura.nextLine().trim();

        if (nomeLivro.isBlank()) {
            System.out.println("O título não pode estar vazio.");
            return;
        }

        String json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));
        DadosAPI dados = conversor.obterDados(json, DadosAPI.class);

        if (dados.resultados() == null || dados.resultados().isEmpty()) {
            System.out.println("\nLivro não encontrado.");
            return;
        }

        LivroDados livroDados = dados.resultados().get(0);

        // === AUTOR (com fallback para "Autor desconhecido") ===
        Autor autor;
        if (livroDados.autores() == null || livroDados.autores().isEmpty()) {
            autor = autorRepository.findByNome("Autor desconhecido")
                    .orElseGet(() -> autorRepository.save(new Autor("Autor desconhecido")));
        } else {
            AutorDados autorDados = livroDados.autores().get(0);
            autor = autorRepository.findByNome(autorDados.nome())
                    .orElseGet(() -> autorRepository.save(new Autor(autorDados)));
        }

        if (livroRepository.existsByTituloAndAutor(livroDados.titulo(), autor)) {
            System.out.println("\nLivro já cadastrado.");
            return;
        }

        Livro livro = new Livro(livroDados, autor);
        livroRepository.save(livro);

        System.out.println("\nLivro cadastrado com sucesso!");
        System.out.println("Título: " + livro.getTitulo());

        Autor a = livro.getAutor();
        System.out.print("Autor: " + a.getNome());

        System.out.print(" (");
        System.out.print(a.getAnoNascimento() != null ? a.getAnoNascimento() : "?");
        System.out.print(" - ");
        System.out.print(a.getAnoFalecimento() != null ? a.getAnoFalecimento() : "Vivo");
        System.out.println(")");

        System.out.println("Idioma: " + livro.getIdioma() + " (" + livro.getCodigoIdioma() + ")");
        System.out.println("Downloads: " + livro.getNumeroDownloads());
        System.out.println("-----------------------------------");
    }

    // ======================================================
    // LISTAR LIVROS
    // ======================================================

    private void listarLivrosCadastrados() {
        List<Livro> livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            System.out.println("\nNenhum livro cadastrado.");
            return;
        }

        System.out.println("\n=== Livros Cadastrados ===\n");
        livros.forEach(l ->
                System.out.println("- " + l.getTitulo() +
                        " | " + l.getAutor().getNome() +
                        " | " + l.getIdioma() + " (" + l.getCodigoIdioma() + ")" +
                        " | Downloads: " + l.getNumeroDownloads())
        );
        System.out.println("-----------------------------------");
    }

    // ======================================================
    // LISTAR AUTORES
    // ======================================================

    private void listarAutoresCadastrados() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("\nNenhum autor cadastrado.");
            return;
        }

        System.out.println("\n=== Autores ===\n");
        autores.forEach(a ->
                System.out.println("- " + a.getNome() +
                        " (" + (a.getAnoNascimento() != null ? a.getAnoNascimento() : "?") +
                        " - " + (a.getAnoFalecimento() != null ? a.getAnoFalecimento() : "Vivo") + ")")
        );
        System.out.println("-----------------------------------");
    }

    // ======================================================
    // AUTORES VIVOS EM UM ANO
    // ======================================================

    private void listarAutoresVivosEmDeterminadoAno() {
        System.out.print("Informe o ano: ");
        String entrada = leitura.nextLine();

        int ano;
        try {
            ano = Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("\n❌ Digite um ano válido.");
            return;
        }

        List<Autor> autores = autorRepository.findAll().stream()
                .filter(a -> a.getAnoNascimento() != null &&
                        a.getAnoNascimento() <= ano &&
                        (a.getAnoFalecimento() == null || a.getAnoFalecimento() > ano))
                .toList();

        if (autores.isEmpty()) {
            System.out.println("\nNenhum autor vivo nesse ano.");
            return;
        }

        System.out.println("\nAutores vivos em " + ano + ":\n");
        autores.forEach(a ->
                System.out.println("Autor: " + a.getNome() +
                        " (" + a.getAnoNascimento() + " - " +
                        (a.getAnoFalecimento() != null ? a.getAnoFalecimento() : "Vivo") + ")")
        );
        System.out.println("-----------------------------------");
    }

    // ======================================================
    // LISTAR LIVROS POR IDIOMA
    // ======================================================

    private void listarLivrosPorIdioma() {
        System.out.println("""
Escolha o idioma:
1 - Inglês
2 - Francês
3 - Espanhol
4 - Português
5 - Outros (alemão, italiano, etc)
""");

        System.out.print("Opção: ");
        String entrada = leitura.nextLine().trim();

        List<Livro> livros = livroRepository.findAll();
        List<Livro> filtrados;

        switch (entrada) {
            case "1" -> filtrados = livros.stream().filter(l -> "en".equalsIgnoreCase(l.getCodigoIdioma())).toList();
            case "2" -> filtrados = livros.stream().filter(l -> "fr".equalsIgnoreCase(l.getCodigoIdioma())).toList();
            case "3" -> filtrados = livros.stream().filter(l -> "es".equalsIgnoreCase(l.getCodigoIdioma())).toList();
            case "4" -> filtrados = livros.stream().filter(l -> "pt".equalsIgnoreCase(l.getCodigoIdioma())).toList();
            case "5" -> filtrados = livros.stream()
                    .filter(l -> !l.getCodigoIdioma().equalsIgnoreCase("en")
                            && !l.getCodigoIdioma().equalsIgnoreCase("fr")
                            && !l.getCodigoIdioma().equalsIgnoreCase("es")
                            && !l.getCodigoIdioma().equalsIgnoreCase("pt"))
                    .toList();
            default -> {
                System.out.println("\n❌ Opção inválida.");
                return;
            }
        }

        if (filtrados.isEmpty()) {
            System.out.println("\nNenhum livro encontrado para este idioma.");
            return;
        }

        filtrados.forEach(l ->
                System.out.println("- " + l.getTitulo()
                        + " | " + l.getAutor().getNome()
                        + " | " + l.getIdioma() + " (" + l.getCodigoIdioma() + ")"
                        + " | Downloads: " + l.getNumeroDownloads())
        );
    }
}
