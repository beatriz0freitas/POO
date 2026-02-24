# **POO - SpotifUM - Plataforma de Streaming de Música**

**SpotifUM** é uma aplicação desktop de gestão e reprodução de músicas, inspirada em plataformas de streaming como o Spotify. Desenvolvida em Java utilizando programação orientada a objetos, oferece uma experiência completa para os utilizadores gerarem, organizarem e reproduzirem coleções de música.

---

### Como executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.10.1 ou superior

### Compilação

```bash
mvn compile
```

### Execução de Testes

```bash
mvn test
```

### Execução da Aplicação

```bash
./run.sh
```

### Gerar Documentação (Javadoc)

```bash
javadoc -d docs -sourcepath src/main/java -subpackages model:app:controller:view:utils:exceptions
```

---

## Persistência de Dados

A aplicação utiliza serialização Java para guardar e carregar o estado completo:

- Estado do modelo (utilizadores, álbuns, playlists)
- Históricos de reprodução
- Informações de subscrição
- Favoritos

**Métodos da API:**

- `controller.salvarDados()`: Guardar estado
- `controller.carregarDados()`: Carregar estado

---

## Fluxo Típico de Utilização

1. **Inicializar aplicação**: `./run.sh`
2. **Carregar dados existentes** ou criar novos
3. **Criar utilizadores** com diferentes planos
4. **Adicionar álbuns** com músicas
5. **Criar playlists** manualmente ou automaticamente
6. **Reproduzir música**: Navegar através de coleções
7. **Marcar favoritos**: Guardar músicas preferidas
8. **Consultar estatísticas**: Analisar padrões de escuta
9. **Guardar dados**: Persistir alterações

---

**Universidade do MInho - Escola de Engenharia - 2024/2025**
