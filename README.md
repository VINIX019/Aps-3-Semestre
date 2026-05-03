# Sistema de Gerenciamento de Estoque
## APS - UNIP 2026/1 | Ciência da Computação & Sistemas de Informação

---
## Estrutura do projeto

```
estoque/
├── src/
│   ├── Main.java           ← Programa principal + menus
│   ├── Produto.java        ← Classe de dados Produto
│   ├── Fornecedor.java     ← Classe de dados Fornecedor
│   ├── ProdutoDAO.java     ← Persistência CSV de Produtos
│   └── FornecedorDAO.java  ← Persistência CSV de Fornecedores
├── dados/                  ← Criado automaticamente ao rodar
│   ├── produtos.csv
│   └── fornecedores.csv
└── bin/                    ← Criado ao compilar
```

---

## Funcionalidades implementadas

### Produtos
- Listar todos os produtos
- Listar ordenado por nome
- Listar ordenado por preço
- Listar ordenado por nome e preço (dois critérios)
- Buscar por nome (parcial)
- Buscar por categoria (parcial)
- Buscar por fornecedor
- Inserir novo produto (com validação)

### Fornecedores
- Listar todos os fornecedores
- Buscar por nome
- Inserir novo fornecedor (com validação de CNPJ e email)

---

## Dados de exemplo
Ao iniciar pela primeira vez, o sistema cria automaticamente:
- 3 fornecedores de exemplo
- 8 produtos de exemplo
