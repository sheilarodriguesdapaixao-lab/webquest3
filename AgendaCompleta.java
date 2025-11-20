import java.io.*;
import java.util.*;

 class Contato {
    private String nome;
    private String telefone;
    private String email;

    public Contato(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return "Contato{" +
                "nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
 interface GerenciadorContatos {
    void adicionarContatos(String nome)throws ContatoExistenteException;
    Contato buscarContatoPor(String nome) throws ContatoNaoEncontradoException;
    void removerContato(String nome) throws ContatoNaoEncontradoException;
    List<Contato> listarTodosOsContatos ();
}
 class ContatoExistenteException extends Exception {
     public ContatoExistenteException(String mensagem) {
        super(mensagem);
    }
}
class ContatoNaoEncontradoException extends Exception {
    public ContatoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
class AgendaManager {
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
public class AgendaCompleta {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AgendaManager agenda = new AgendaManager();

       int opcao = -1;
            while (opcao != 0) {
                System.out.println("menu:");
                System.out.println("1. Adicionar Contato");
                System.out.println("2. Buscar Contato");
                System.out.println("3. Remover Contato");
                System.out.println("4. Listar Todos os Contatos");
                System.out.println("5. Salvar em CSV");
                System.out.println("7. Listar Contatos Ordenados");
                System.out.println("8. Buscar por Domínio de Email");
                System.out.println("9. Sair");
                System.out.print("Escolha a opção: ");


                opcao = Integer.parseInt(scanner.nextLine());
                scanner.nextLine();

            try {
                switch (opcao) {
                    case 1:
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Telefone: ");
                        String telefone = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        agenda.adicionarContato(new Contato(nome, telefone, email));
                        System.out.println("Contato adicionado com sucesso!");
                        break;


                    case 2:
                        System.out.print("Nome do contato: ");
                        String nomeBusca = scanner.nextLine();
                        Contato encontrado = agenda.buscarContato(nomeBusca);
                        System.out.println("Contato encontrado: " + encontrado);
                        break;

                    case 3:
                        System.out.print("Nome do contato a remover: ");
                        String nomeRemover = scanner.nextLine();
                        agenda.removerContato(nomeRemover);
                        System.out.println("Contato removido com sucesso!");
                        break;

                    case 4:
                        List<Contato> contatos = agenda.listarTodosContatos();
                        if (contatos.isEmpty()) {
                            System.out.println("Nenhum contato cadastrado.");
                        } else {
                            System.out.println("Lista de contatos:");
                            for (Contato c : contatos) {
                                System.out.println(c);
                            }
                        }
                        break;

                    case 5:
                       System.out.print("Nome do arquivo para salvar (ex: agenda.csv): ");
                        String arquivoSalvar = scanner.nextLine();
                        agenda.salvarContatosCSV(arquivoSalvar);
                        System.out.println("Contatos salvos com sucesso!");
                        break;

                    case 6:
                        System.out.print("Nome do arquivo para carregar (ex: agenda.csv): ");
                        String arquivoCarregar = scanner.nextLine();
                        agenda.carregarContatosCSV(arquivoCarregar);
                        System.out.println("Contatos carregados com sucesso!");
                        break;

                    case 7:
                       List<Contato> ordenados = agenda.listarContatosOrdenados();
                       if (ordenados.isEmpty()) {
                          System.out.println("Nenhum contato encontrado.");
                       } else {
                          System.out.println("Contatos ordenados por nome:");
                      for (Contato c : ordenados) {
                          System.out.println(c);
                     }
                  }
                  break;

                   case 8:
                       System.out.print("Digite o domínio de email (ex: gmail.com): ");
                       String dominio = scanner.nextLine();
                       List<Contato> filtrados = agenda.buscarPorDominioEmail(dominio);
                       if (filtrados.isEmpty()) {
                          System.out.println("Nenhum contato com esse domínio.");
                      } else {
                        System.out.println("Contatos com domínio @" + dominio + ":");
                        for (Contato c : filtrados) {
                            System.out.println(c);
                         }
                 }
                   break;
                    
                    case 9:
                        System.out.println("Encerrando o programa...");
                        break;

                   default:
                       System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        scanner.close();
    }

 
            }

