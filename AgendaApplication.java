import java.util.Scanner;
import java.util.List;

public class AgendaApplication {
    public static void main(String[] args) {
        AgendaManager manager = new AgendaManager();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Adicionar Contato");
            System.out.println("2. Buscar Contato");
            System.out.println("3. Remover Contato");
            System.out.println("4. Listar Todos os Contatos");
            System.out.println("5. Salvar em CSV");
            System.out.println("6. Carregar de CSV");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    try {
                        manager.adicionarContato(new Contato(nome, telefone, email));
                        System.out.println("Contato adicionado.");
                    } catch (ContatoExistenteException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Nome do contato: ");
                    nome = scanner.nextLine();
                    try {
                        Contato c = manager.buscarContato(nome);
                        System.out.println(c);
                    } catch (ContatoNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Nome do contato: ");
                    nome = scanner.nextLine();
                    try {
                        manager.removerContato(nome);
                        System.out.println("Contato removido.");
                    } catch (ContatoNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    List<Contato> lista = manager.listarContatosOrdenados();
                    if (lista.isEmpty()) {
                        System.out.println("Nenhum contato na agenda.");
                    } else {
                        for (Contato c : lista) {
                            System.out.println(c);
                        }
                    }
                    break;
                case 5:
                    System.out.print("Nome do arquivo CSV para salvar: ");
                    String arquivoSalvar = scanner.nextLine();
                    manager.salvarContatosCSV(arquivoSalvar);
                    break;
                case 6:
                    System.out.print("Nome do arquivo CSV para carregar: ");
                    String arquivoCarregar = scanner.nextLine();
                    manager.carregarContatosCSV(arquivoCarregar);
                    break;
                case 7:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 7);

        scanner.close();
    }
}
    
