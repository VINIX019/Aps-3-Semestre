import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ProdutoDAO produtoDAO = new ProdutoDAO();
    static FornecedorDAO fornecedorDAO = new FornecedorDAO();

    public static void main(String[] args) {
        popularDadosIniciais();
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerInteiro("Opcao: ");
            switch (opcao) {
                case 1 -> menuProdutos();
                case 2 -> menuFornecedores();
                case 0 -> System.out.println("\nSistema encerrado. Ate logo!");
                default -> System.out.println("\n[!] Opcao invalida.");
            }
        } while (opcao != 0);
    }

    // ===================== MENUS =====================

    static void exibirMenuPrincipal() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║    SISTEMA DE GERENCIAMENTO ESTOQUE  ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. Gerenciar Produtos               ║");
        System.out.println("║  2. Gerenciar Fornecedores           ║");
        System.out.println("║  0. Sair                             ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    static void menuProdutos() {
        int opcao;
        do {
            System.out.println("\n--- PRODUTOS ---");
            System.out.println("1. Listar todos");
            System.out.println("2. Listar ordenado por nome");
            System.out.println("3. Listar ordenado por preco");
            System.out.println("4. Listar ordenado por nome e preco");
            System.out.println("5. Buscar por nome");
            System.out.println("6. Buscar por categoria");
            System.out.println("7. Buscar por fornecedor");
            System.out.println("8. Inserir novo produto");
            System.out.println("0. Voltar");
            opcao = lerInteiro("Opcao: ");
            switch (opcao) {
                case 1 -> listarProdutos(produtoDAO.listarTodos());
                case 2 -> listarProdutos(produtoDAO.ordenarPorNome());
                case 3 -> listarProdutos(produtoDAO.ordenarPorPreco());
                case 4 -> listarProdutos(produtoDAO.ordenarPorNomeEPreco());
                case 5 -> {
                    String termo = lerString("Nome a buscar: ");
                    listarProdutos(produtoDAO.buscarPorNome(termo));
                }
                case 6 -> {
                    String cat = lerString("Categoria a buscar: ");
                    listarProdutos(produtoDAO.buscarPorCategoria(cat));
                }
                case 7 -> {
                    int idF = lerInteiro("ID do fornecedor: ");
                    listarProdutos(produtoDAO.buscarPorFornecedor(idF));
                }
                case 8 -> inserirProduto();
                case 0 -> {}
                default -> System.out.println("[!] Opcao invalida.");
            }
        } while (opcao != 0);
    }

    static void menuFornecedores() {
        int opcao;
        do {
            System.out.println("\n--- FORNECEDORES ---");
            System.out.println("1. Listar todos");
            System.out.println("2. Buscar por nome");
            System.out.println("3. Inserir novo fornecedor");
            System.out.println("0. Voltar");
            opcao = lerInteiro("Opcao: ");
            switch (opcao) {
                case 1 -> listarFornecedores(fornecedorDAO.listarTodos());
                case 2 -> {
                    String termo = lerString("Nome a buscar: ");
                    List<Fornecedor> resultado = fornecedorDAO.listarTodos().stream()
                        .filter(f -> f.getNome().toLowerCase().contains(termo.toLowerCase()))
                        .toList();
                    listarFornecedores(resultado);
                }
                case 3 -> inserirFornecedor();
                case 0 -> {}
                default -> System.out.println("[!] Opcao invalida.");
            }
        } while (opcao != 0);
    }

    // ===================== LISTAGENS =====================

    static void listarProdutos(List<Produto> lista) {
        if (lista.isEmpty()) {
            System.out.println("\n[!] Nenhum produto encontrado.");
            return;
        }
        System.out.println("\n" + "=".repeat(100));
        System.out.printf("%-5s %-25s %-15s %10s %6s %s%n",
            "ID", "NOME", "CATEGORIA", "PRECO", "QTD", "FORNECEDOR");
        System.out.println("=".repeat(100));
        for (Produto p : lista) {
            Fornecedor f = fornecedorDAO.buscarPorId(p.getIdFornecedor());
            String nomeForn = f != null ? f.getNome() : "Desconhecido";
            System.out.printf("%-5d %-25s %-15s R$%8.2f %6d %s%n",
                p.getId(), p.getNome(), p.getCategoria(), p.getPreco(), p.getQuantidade(), nomeForn);
        }
        System.out.println("=".repeat(100));
        System.out.println("Total: " + lista.size() + " produto(s).");
    }

    static void listarFornecedores(List<Fornecedor> lista) {
        if (lista.isEmpty()) {
            System.out.println("\n[!] Nenhum fornecedor encontrado.");
            return;
        }
        System.out.println("\n" + "=".repeat(90));
        System.out.printf("%-5s %-25s %-20s %-15s %s%n",
            "ID", "NOME", "CNPJ", "TELEFONE", "EMAIL");
        System.out.println("=".repeat(90));
        for (Fornecedor f : lista) {
            System.out.printf("%-5d %-25s %-20s %-15s %s%n",
                f.getId(), f.getNome(), f.getCnpj(), f.getTelefone(), f.getEmail());
        }
        System.out.println("=".repeat(90));
        System.out.println("Total: " + lista.size() + " fornecedor(es).");
    }

    // ===================== INSERÇÕES =====================

    static void inserirProduto() {
        System.out.println("\n-- Inserir Novo Produto --");
        String nome = lerStringNaoVazia("Nome: ");
        String categoria = lerStringNaoVazia("Categoria: ");
        double preco = lerDouble("Preco: R$ ");
        int quantidade = lerInteiroPositivo("Quantidade em estoque: ");

        listarFornecedores(fornecedorDAO.listarTodos());
        int idFornecedor = lerInteiro("ID do Fornecedor: ");
        if (fornecedorDAO.buscarPorId(idFornecedor) == null) {
            System.out.println("[!] Fornecedor nao encontrado. Produto nao inserido.");
            return;
        }

        Produto p = new Produto(produtoDAO.proximoId(), nome, categoria, preco, quantidade, idFornecedor);
        produtoDAO.inserir(p);
        System.out.println("[✓] Produto inserido com sucesso! ID: " + p.getId());
    }

    static void inserirFornecedor() {
        System.out.println("\n-- Inserir Novo Fornecedor --");
        String nome = lerStringNaoVazia("Nome: ");
        String cnpj = lerCnpj();
        String telefone = lerStringNaoVazia("Telefone: ");
        String email = lerEmail();

        Fornecedor f = new Fornecedor(fornecedorDAO.proximoId(), nome, cnpj, telefone, email);
        fornecedorDAO.inserir(f);
        System.out.println("[✓] Fornecedor inserido com sucesso! ID: " + f.getId());
    }

    // ===================== UTILITÁRIOS DE LEITURA =====================

    static int lerInteiro(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[!] Digite um numero inteiro valido.");
            }
        }
    }

    static int lerInteiroPositivo(String msg) {
        while (true) {
            int v = lerInteiro(msg);
            if (v >= 0) return v;
            System.out.println("[!] O valor nao pode ser negativo.");
        }
    }

    static double lerDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                double v = Double.parseDouble(sc.nextLine().trim().replace(",", "."));
                if (v >= 0) return v;
                System.out.println("[!] O preco nao pode ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("[!] Digite um valor numerico valido (ex: 19.90).");
            }
        }
    }

    static String lerString(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    static String lerStringNaoVazia(String msg) {
        while (true) {
            String s = lerString(msg);
            if (!s.isEmpty()) return s;
            System.out.println("[!] Este campo nao pode ser vazio.");
        }
    }

    static String lerCnpj() {
        while (true) {
            String cnpj = lerStringNaoVazia("CNPJ (formato 00.000.000/0000-00): ");
            if (cnpj.matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")) return cnpj;
            System.out.println("[!] CNPJ invalido. Use o formato 00.000.000/0000-00.");
        }
    }

    static String lerEmail() {
        while (true) {
            String email = lerStringNaoVazia("Email: ");
            if (email.contains("@") && email.contains(".")) return email;
            System.out.println("[!] Email invalido.");
        }
    }

    // ===================== DADOS INICIAIS =====================

    static void popularDadosIniciais() {
        if (!fornecedorDAO.listarTodos().isEmpty()) return;

        fornecedorDAO.inserir(new Fornecedor(1, "TechSupply Ltda", "12.345.678/0001-90", "(11) 91234-5678", "contato@techsupply.com"));
        fornecedorDAO.inserir(new Fornecedor(2, "InfoParts SA", "98.765.432/0001-10", "(19) 98765-4321", "vendas@infoparts.com"));
        fornecedorDAO.inserir(new Fornecedor(3, "MegaDistribuidora", "55.444.333/0001-22", "(11) 93333-4444", "mega@distribuidora.com"));

        produtoDAO.inserir(new Produto(1, "Notebook Dell i5", "Informatica", 3499.90, 15, 1));
        produtoDAO.inserir(new Produto(2, "Mouse Logitech MX", "Periferico", 249.90, 50, 2));
        produtoDAO.inserir(new Produto(3, "Teclado Mecanico", "Periferico", 399.90, 30, 2));
        produtoDAO.inserir(new Produto(4, "Monitor 27 Full HD", "Informatica", 1299.00, 20, 1));
        produtoDAO.inserir(new Produto(5, "Cabo HDMI 2m", "Acessorio", 39.90, 100, 3));
        produtoDAO.inserir(new Produto(6, "SSD 480GB Kingston", "Armazenamento", 299.90, 45, 3));
        produtoDAO.inserir(new Produto(7, "Headset Gamer", "Periferico", 189.90, 25, 2));
        produtoDAO.inserir(new Produto(8, "Webcam Full HD", "Periferico", 219.90, 18, 1));
    }
}
