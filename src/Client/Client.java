package Client;



import Servidor.Message.*;

import java.io.*;
import java.net.Socket;

import static Client.Menu.*;


public class Client {

    final int localPort;

    Socket socket;
    Menu menu;


    public static void main(String[] args) throws IOException {
        new Client();
    }

    public Client() throws IOException {
        socket = new Socket("localhost", 4999);
        menu = new Menu(this);
        this.localPort = socket.getLocalPort();
        System.out.println("identifier: " + localPort);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        menu.menuSemConta(out);
        receiveFromServer().start();
    }


    private Thread receiveFromServer() {
        return new Thread(() -> {
            while (true) {
                try {
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    Message packet = Message.deserialize(in);

                    System.out.println("[DEBUG] Recieved packet of type " + packet.getType().toString());
                    processMessage(packet, out).start();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private Thread processMessage(Message packet, DataOutputStream out) {
        return new Thread(() -> {
            try {
                Object message = packet.getMessage();
                switch (packet.getType()) {
                    case GENERIC:
                        if (! (message instanceof String)) break;
                        System.out.println(message);

                        if (message.equals("Registo feito!")|| message.equals("Utilizador já existe!"))
                            menu.menuSemConta(out);
                        break;
                    case SCOOTER_RESERVATION_RESPONSE: // We just need to print the reservation code for the user to user later
                        if (! (message instanceof String)) break;

                        if (message.equals("Nenhuma Trotinete nessa Localizaçao!")){
                            System.out.println(message);
                            menu.menuLogado(out);
                        }
                        else{
                            System.out.println("Código: " + message);
                            System.out.println("Guarda este código para futuro uso");
                            menu.menuReserve(out);
                        }
                        break;
                    //
                    case CONNECTION_RESPONSE:
                        if (! (message instanceof String)) break;

                        System.out.println(message);

                        if (message.equals("Login incorreto!") || message.equals("Este user já está logado noutro cliente."))
                            menu.menuSemConta(out);
                        else menu.menuLogado(out);

                        break;
                    case DESCONNECTION_RESPONSE:
                        if (! (message instanceof String)) break;
                        System.out.println(message);
                        menu.menuSemConta(out);

                        break;
                    case LIST_SCOOTERS:
                        // We just need to print the list so the user can choose
                        if (!(message instanceof ListLoc)) break;
                        ListLoc lista = (ListLoc) message;

                        if(lista.getSize() == 0) System.out.println("Nenhuma recompensa nas proximidades");
                        else imprimeTrotis(lista);

                        menu.menuLogado(out);
                        break;
                    case LIST_REWARDS:
                        // We just need to print the list so the user can choose
                        if (!(message instanceof ListRec)) break;
                        ListRec listaRec = (ListRec) message;

                        if(listaRec.getSize() == 0) System.out.println("Nenhuma recompensa nas proximidades");
                        else imprimeRecompensas(listaRec);

                        menu.menuLogado(out);
                        break;
                    case NOTIFICATION_MSG:
                        // We just need to print the list so the user can choose
                        if (!(message instanceof ListRec)) break;
                        listaRec = (ListRec) message;

                        if(listaRec.getSize() == 0) System.out.println("Nenhuma recompensa nas proximidades");
                        else imprimeRecompensas(listaRec);

                        break;

                    case COST_REWARD:
                        if (!(message instanceof CostReward)) break;
                        CostReward cost = (CostReward) message;

                        System.out.println("Tens de pagar " + cost.getCost() + "€ e ganhaste " + cost.getReward() + " créditos.");
                        menu.menuLogado(out);
                        break;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
