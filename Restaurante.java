import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Cliente {
    private String nome;
    private int mesa;
    private double totalVendas;
    private ArrayList<String> pedidos;

    public Cliente(String nome, int mesa) {
        this.nome = nome;
        this.mesa = mesa;
        this.totalVendas = 0.0;
        this.pedidos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public int getMesa() {
        return mesa;
    }

    public double getTotalVendas() {
        return totalVendas;
    }

    public ArrayList<String> getPedidos() {
        return pedidos;
    }

    public void adicionarVenda(double valor) {
        totalVendas += valor;
    }

    public void adicionarPedido(String prato) {
        pedidos.add(prato);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", mesa=" + mesa +
                ", totalVendas=" + totalVendas +
                ", pedidos=" + pedidos +
                '}';
    }
}

public class Restaurante {
    private ArrayList<Cliente> listaClientes;
    private Map<String, Double> menu;

    public Restaurante() {
        this.listaClientes = new ArrayList<>();
        this.menu = new HashMap<>();
        inicializarMenu();
    }

    private void inicializarMenu() {
        menu.put("Hamburguer", 14.99);
        menu.put("Pizza", 34.99);
        menu.put("Salada", 9.99);
        menu.put("Refrigerante", 3.99);
    }

    public void adicionarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public void realizarPedido(Cliente cliente) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("==== Menu ====");
        for (Map.Entry<String, Double> prato : menu.entrySet()) {
            System.out.println(prato.getKey() + ": R$" + prato.getValue());
        }

        System.out.print("Escolha um prato do menu: ");
        String pratoEscolhido = scanner.nextLine();

        if (menu.containsKey(pratoEscolhido)) {
            double precoPrato = menu.get(pratoEscolhido);
            cliente.adicionarPedido(pratoEscolhido);
            cliente.adicionarVenda(precoPrato);
            System.out.println("Pedido registrado com sucesso!");
        } else {
            System.out.println("Prato não encontrado no menu.");
        }
    }

    public void consultarMesasOcupadas() {
        System.out.println("==== Mesas Ocupadas ====");
        for (Cliente cliente : listaClientes) {
            System.out.println("Mesa " + cliente.getMesa() + ": " + cliente.getNome());
        }
    }

    public void exibirRelatorio() {
        System.out.println("==== Relatorio de Vendas ====");
        for (Cliente cliente : listaClientes) {
            System.out.println(cliente);
        }

        double totalGeral = listaClientes.stream().mapToDouble(Cliente::getTotalVendas).sum();
        System.out.println("Total Geral de Vendas: R$" + totalGeral);
    }

    public static void main(String[] args) {
        Restaurante restaurante = new Restaurante();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== Restaurante ====");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Realizar Pedido");
            System.out.println("3 - Consultar Mesas");
            System.out.println("4 - Finalizar Expediente");
            System.out.print("Escolha uma opcao: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1:
                    System.out.print("Digite o nome do cliente: ");
                    String nomeCliente = scanner.nextLine();

                    System.out.print("Digite o numero da mesa: ");
                    int numeroMesa = scanner.nextInt();
                    scanner.nextLine();

                    Cliente cliente = new Cliente(nomeCliente, numeroMesa);
                    restaurante.adicionarCliente(cliente);
                    break;

                case 2:
                    System.out.print("Digite o numero da mesa para fazer o pedido: ");
                    int mesaPedido = scanner.nextInt();
                    scanner.nextLine();

                    Cliente clientePedido = restaurante.listaClientes.stream()
                            .filter(clienteAtual -> clienteAtual.getMesa() == mesaPedido)
                            .findFirst()
                            .orElse(null);

                    if (clientePedido != null) {
                        restaurante.realizarPedido(clientePedido);
                    } else {
                        System.out.println("Mesa nao encontrada.");
                    }
                    break;

                case 3:
                    restaurante.consultarMesasOcupadas();
                    break;

                case 4:
                    restaurante.exibirRelatorio();
                    System.out.println("Expediente finalizado. Obrigado!");
                    System.exit(0);

                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        }
    }
}