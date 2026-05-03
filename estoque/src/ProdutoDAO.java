import java.io.*;
import java.util.*;
import java.util.stream.*;

public class ProdutoDAO {
    private static final String ARQUIVO = "dados/produtos.csv";

    public List<Produto> listarTodos() {
        List<Produto> lista = new ArrayList<>();
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeira = true;
            while ((linha = br.readLine()) != null) {
                if (primeira) { primeira = false; continue; }
                if (!linha.trim().isEmpty()) {
                    lista.add(Produto.fromCsv(linha));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler produtos: " + e.getMessage());
        }
        return lista;
    }

    public void salvarTodos(List<Produto> lista) {
        new File("dados").mkdirs();
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            pw.println("id;nome;categoria;preco;quantidade;idFornecedor");
            for (Produto p : lista) {
                pw.println(p.toCsv());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }

    public void inserir(Produto produto) {
        List<Produto> lista = listarTodos();
        lista.add(produto);
        salvarTodos(lista);
    }

    public List<Produto> ordenarPorNome() {
        return listarTodos().stream()
            .sorted(Comparator.comparing(Produto::getNome))
            .collect(Collectors.toList());
    }

    public List<Produto> ordenarPorPreco() {
        return listarTodos().stream()
            .sorted(Comparator.comparingDouble(Produto::getPreco))
            .collect(Collectors.toList());
    }

    public List<Produto> ordenarPorNomeEPreco() {
        return listarTodos().stream()
            .sorted(Comparator.comparing(Produto::getNome)
                .thenComparingDouble(Produto::getPreco))
            .collect(Collectors.toList());
    }

    public List<Produto> buscarPorNome(String termo) {
        String t = termo.toLowerCase();
        return listarTodos().stream()
            .filter(p -> p.getNome().toLowerCase().contains(t))
            .collect(Collectors.toList());
    }

    public List<Produto> buscarPorCategoria(String categoria) {
        String c = categoria.toLowerCase();
        return listarTodos().stream()
            .filter(p -> p.getCategoria().toLowerCase().contains(c))
            .collect(Collectors.toList());
    }

    public List<Produto> buscarPorFornecedor(int idFornecedor) {
        return listarTodos().stream()
            .filter(p -> p.getIdFornecedor() == idFornecedor)
            .collect(Collectors.toList());
    }

    public int proximoId() {
        List<Produto> lista = listarTodos();
        if (lista.isEmpty()) return 1;
        return lista.stream().mapToInt(Produto::getId).max().getAsInt() + 1;
    }
}
