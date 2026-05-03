import java.io.*;
import java.util.*;

public class FornecedorDAO {
    private static final String ARQUIVO = "dados/fornecedores.csv";

    public List<Fornecedor> listarTodos() {
        List<Fornecedor> lista = new ArrayList<>();
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeira = true;
            while ((linha = br.readLine()) != null) {
                if (primeira) { primeira = false; continue; } // pula cabeçalho
                if (!linha.trim().isEmpty()) {
                    lista.add(Fornecedor.fromCsv(linha));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler fornecedores: " + e.getMessage());
        }
        return lista;
    }

    public void salvarTodos(List<Fornecedor> lista) {
        new File("dados").mkdirs();
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            pw.println("id;nome;cnpj;telefone;email");
            for (Fornecedor f : lista) {
                pw.println(f.toCsv());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar fornecedores: " + e.getMessage());
        }
    }

    public void inserir(Fornecedor fornecedor) {
        List<Fornecedor> lista = listarTodos();
        lista.add(fornecedor);
        salvarTodos(lista);
    }

    public Fornecedor buscarPorId(int id) {
        for (Fornecedor f : listarTodos()) {
            if (f.getId() == id) return f;
        }
        return null;
    }

    public int proximoId() {
        List<Fornecedor> lista = listarTodos();
        if (lista.isEmpty()) return 1;
        return lista.stream().mapToInt(Fornecedor::getId).max().getAsInt() + 1;
    }
}
