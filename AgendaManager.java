import java.io.*;
import java.util.*;

public
class AgendaManager implements GerenciadorContatos {
    private List<Contato> contatos;

    public AgendaManager() {
        this.contatos = new ArrayList<>();
    }

    public void adicionarContato(Contato contato) throws ContatoExistenteException {
        for (Contato c : contatos) {
            if (c.getNome().equalsIgnoreCase(contato.getNome())) {
                throw new ContatoExistenteException("Contato já existe: " + contato.getNome());
            }
        }
        contatos.add(contato);
    }

    public Contato buscarContato(String nome) throws ContatoNaoEncontradoException {
        for (Contato c : contatos) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                return c;
            }
        }
        throw new ContatoNaoEncontradoException("Contato não encontrado: " + nome);
    }

    public void removerContato(String nome) throws ContatoNaoEncontradoException {
        Contato contato = buscarContato(nome);
        contatos.remove(contato);
    }

    public List<Contato> listarTodosContatos() {
        return new ArrayList<>(contatos);
    }

    public void carregarContatosCSV(String nomeArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            contatos.clear();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    contatos.add(new Contato(dados[0], dados[1], dados[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar contatos: " + e.getMessage());
        }
    }

    public void salvarContatosCSV(String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            for (Contato c : contatos) {
                writer.println(c.getNome() + ";" + c.getTelefone() + ";" + c.getEmail());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar contatos: " + e.getMessage());
        }
    }

    public List<Contato> listarContatosOrdenados() {
        List<Contato> ordenados = new ArrayList<>(contatos);
        ordenados.sort(Comparator.comparing(Contato::getNome));
        return ordenados;
    }

    public List<Contato> buscarPorDominioEmail(String dominio) {
        List<Contato> resultado = new ArrayList<>();
        for (Contato c : contatos) {
            if (c.getEmail().endsWith("@" + dominio)) {
                resultado.add(c);
            }
        }
        return resultado;
    }
}