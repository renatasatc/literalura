# Literalura 📚

[![Java](https://img.shields.io/badge/Java-20-blue)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)

Literalura é um projeto em **Java + Spring Boot** que permite buscar, cadastrar e listar livros e autores usando dados da API [Gutendex](https://gutendex.com/). O sistema suporta livros em diferentes idiomas e fornece informações detalhadas sobre autores e obras.

Os dados são persistidos em um banco PostgreSQL e manipulados por meio de um menu interativo no console.

---

📌 Funcionalidades

🔍 Buscar e cadastrar livros pelo título

📚 Listar todos os livros cadastrados

✍️ Listar todos os autores cadastrados

🗓️ Listar autores vivos em um determinado ano

🌎 Listar livros por idioma

---
## Idiomas suportados no filtro

- **Inglês (en)**

- **Francês (fr)**

- **Espanhol (es)**

- **Português (pt)**

- **Outros → Alemão, Italiano, Russo, etc.**

A opção “Outros” retorna automaticamente todos os livros que não estão nos quatro idiomas principais.

---
## Tecnologias utilizadas

- **Java 20**
- **Spring Boot 4**
- **Spring Data JPA**
- **PostgreSQL**
- **REST API** (Gutendex)
- **Maven** para build e gerenciamento de dependências

---

## Configuração do PostgreSQL

1. Crie um banco no PostgreSQL, por exemplo `literalura`:

```sql
CREATE DATABASE literalura;

Configure o application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

Como executar

Clone este repositório:

git clone https://github.com/seu-usuario/literalura.git
cd literalura


Configure o banco de dados no application.properties.

Execute a aplicação:

mvn spring-boot:run


📋 Menu Interativo no Console

=== MENU ===
1. Buscar livro pelo título e cadastrar
2. Listar livros cadastrados
3. Listar autores cadastrados
4. Listar autores vivos em determinado ano
5. Listar livros em determinado idioma
0. Sair

📘 Exemplo de Uso

1️⃣ Buscar e cadastrar um livro
Informe o título do livro para pesquisa: Les mille et une nuits

Livro cadastrado com sucesso!
Título: Les mille et une nuits
Autor: Antoine Galland (1646 - 1715)
Idioma: FRANCES (fr)
Downloads: 3898

2️⃣ Listar livros cadastrados
=== Livros Cadastrados ===
- Les mille et une nuits | Antoine Galland | FRANCES (fr) | Downloads: 3898
- Die Traumdeutung | Sigmund Freud | ALEMAO (de) | Downloads: 4885

3️⃣ Listar autores cadastrados
=== Autores Cadastrados ===
- Antoine Galland (1646 - 1715)
- Sigmund Freud (1856 - 1939)

4️⃣ Listar autores vivos em determinado ano
Escolha uma opção: 4
Informe o ano: 1700
Autores vivos em 1700:
Autor: Antoine Galland (1646 - 1715)

5️⃣ Listar livros por idioma
Escolha o idioma:
Escolha o idioma:
1 - Inglês
2 - Francês
3 - Espanhol
4 - Português
5 - Outros (alemão, italiano, etc)

Exemplo: Português
=== Livros em Português ===
- Dom Casmurro | Machado de Assis | PORTUGUES (pt) | Downloads: 1238

Exemplo: Outros
=== Livros em Outros Idiomas ===
- Die Traumdeutung | Sigmund Freud | ALEMAO (de)
- Piccoli eroi | Virginia Treves | ITALIANO (it)

Estrutura do projeto
literalura/
├── .idea/
├── .mvn/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── br/
        │       └── com/
        │           └── alura/
        │               └── literalura/
        │
        │                   ├── LiteraluraApplication.java
        │
        │                   ├── principal/
        │                   │   └── Menu.java
        │
        │                   ├── model/
        │                   │   ├── Autor.java
        │                   │   ├── Livro.java
        │                   │   ├── Idioma.java
        │                   │   ├── AutorDados.java
        │                   │   ├── LivroDados.java
        │                   │   └── DadosAPI.java
        │
        │                   ├── service/
        │                   │   ├── ConsumirAPI.java
        │                   │   └── ConverterDados.java
        │
        │                   └── repository/
        │                       ├── AutorRepository.java
        │                       └── LivroRepository.java
        │
        └── resources/
            └── application.properties


Observações

A busca na API não diferencia maiúsculas e minúsculas e normaliza acentos.
Campos anoNascimento e anoFalecimento de autores podem ser NULL se a informação não estiver disponível.
Para a opção “Outros” em idiomas, serão listados todos os livros em idiomas diferentes dos cinco padrões (en, fr, de, es, pt).

👤 Autor

Projeto criado por **Renata Saturnino Costa** 💻📚  
Curso: One(ORACLE) + Alura
Para estudos de Java, Spring Boot, PostgreSQL e integração com APIs externas.