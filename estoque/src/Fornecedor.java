public class Fornecedor {
    private int id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;

    public Fornecedor(int id, String nome, String cnpj, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCnpj() { return cnpj; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }

    public void setNome(String nome) { this.nome = nome; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEmail(String email) { this.email = email; }

    public String toCsv() {
        return id + ";" + nome + ";" + cnpj + ";" + telefone + ";" + email;
    }

    public static Fornecedor fromCsv(String linha) {
        String[] partes = linha.split(";");
        return new Fornecedor(
            Integer.parseInt(partes[0].trim()),
            partes[1].trim(),
            partes[2].trim(),
            partes[3].trim(),
            partes[4].trim()
        );
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %-25s | CNPJ: %-18s | Tel: %-15s | Email: %s",
            id, nome, cnpj, telefone, email);
    }
}
