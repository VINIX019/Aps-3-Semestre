public class Produto {
    private int id;
    private String nome;
    private String categoria;
    private double preco;
    private int quantidade;
    private int idFornecedor;

    public Produto(int id, String nome, String categoria, double preco, int quantidade, int idFornecedor) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidade = quantidade;
        this.idFornecedor = idFornecedor;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public double getPreco() { return preco; }
    public int getQuantidade() { return quantidade; }
    public int getIdFornecedor() { return idFornecedor; }

    // Setters
    public void setNome(String nome) { this.nome = nome; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setPreco(double preco) { this.preco = preco; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public void setIdFornecedor(int idFornecedor) { this.idFornecedor = idFornecedor; }

    public String toCsv() {
        return id + ";" + nome + ";" + categoria + ";" + preco + ";" + quantidade + ";" + idFornecedor;
    }

    public static Produto fromCsv(String linha) {
        String[] partes = linha.split(";");
        return new Produto(
            Integer.parseInt(partes[0].trim()),
            partes[1].trim(),
            partes[2].trim(),
            Double.parseDouble(partes[3].trim()),
            Integer.parseInt(partes[4].trim()),
            Integer.parseInt(partes[5].trim())
        );
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %-25s | Categoria: %-15s | Preco: R$%8.2f | Qtd: %4d | ID Fornecedor: %d",
            id, nome, categoria, preco, quantidade, idFornecedor);
    }
}
