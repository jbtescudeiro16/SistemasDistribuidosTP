package Client;



import Entidades.Localizacao;
import Entidades.Utilizador;
import Servidor.Message.ListLoc;
import Servidor.Message.ListRec;
import Servidor.Message.Message;
import Servidor.Message.ReservationMessage;

import java.io.*;
import java.util.Scanner;

import static Servidor.Message.MessageType.*;

public class Menu {
   static Client client;
   static boolean notifications;
   static Scanner ler = new Scanner(System.in);

   public Menu (Client client){
       this.client = client;
   }

    public static void menuSemConta(DataOutputStream out) throws IOException {
        String user = "";
        String pass = "";
        String nome = "";

        System.out.println("------------------SD-TP-GRUPO-21--------------");
        System.out.println("|Insira a operação que pretende realizar :   |");
        System.out.println("|1->Registar utilizador                      |");
        System.out.println("|2->Autenticar                               |");
        System.out.println("----------------------------------------------");

        int opcao = ler.nextInt(); ler.nextLine();

        if (opcao == 1) {
            new Message(REGISTER, menuGetUtilizador()).serialize(out);
            System.out.println("[DEBUG] REGISTER message sent");
        }
        if (opcao == 2) {
            new Message(CONNECTION, menuGetUtilizador()).serialize(out);
            System.out.println("[DEBUG] CONNECTION message sent");
        }
    }

    public static void menuLogado(DataOutputStream out) throws IOException {
        System.out.println("------------------SD-TP-GRUPO-21--------------");
        System.out.println("|Insira a operação que pretende realizar :   |");
        System.out.println("|1-> Obter as trotinetes mais próximas       |");
        System.out.println("|2-> Obter as recompensas mais próximas      |");
        System.out.println("|3-> Reservar Trotinete                      |");
        optionNotification(4);
        System.out.println("|5-> Logout                                  |");
        System.out.println("----------------------------------------------");
        int opt = ler.nextInt();
        ler.nextLine();
        switch(opt){
            case 1: // Trotinetes perto
                new Message(NEARBY_SCOOTERS , menuGetLocalização()).serialize(out);
                System.out.println("[DEBUG] Sent NEARBY_SCOOTERS to server");
                break;
            case 2: // Recompensas perto
                new Message(NEARBY_REWARDS , menuGetLocalização()).serialize(out);
                System.out.println("[DEBUG] Sent NEARBY_REWARDS to server");
                break;
            case 3: // Reservar trotinete
                new Message(SCOOTER_RESERVATION_REQUEST,menuGetLocalização()).serialize(out);
                System.out.println("[DEBUG] Sent SCOOTER_RESERVATION_REQUEST to server");
                break;
            case 4: // Ligar/desligar notificações
                new Message(TOGGLE_NOTIFICATION, null).serialize(out);
                System.out.println("[DEBUG] Sent TOGGLE_NOTIFICATION to server from Notification Socket");
                notifications = !notifications;
                menuLogado(out);
                break;
            case 5: // Logout
                new Message(DESCONNECTION , null).serialize(out);
                System.out.println("[DEBUG] Sent DESCONNECTION to server");
                break;
            default:
                System.out.println("Isso não é uma opção válida :/");
                menuLogado(out);
                break;
        }
    }

    public static void menuViagem(DataOutputStream out) throws IOException {
        System.out.println("------------------SD-TP-GRUPO-21--------------");
        System.out.println("|Insira a operação que pretende realizar :   |");
        System.out.println("|1->Terminar viagem                          |");
        System.out.println("----------------------------------------------");

        // Ler input aqui
        int opt = ler.nextInt();
        ler.nextLine();

        switch (opt) {
            case 1: // Terminar viagem
                // We need to get location and reservation code
                Localizacao loc = menuGetLocalização();
                System.out.println("Insira código de reserva :");
                String reservationCode = ler.nextLine();

                new Message(END_TRIP, new ReservationMessage(reservationCode, loc)).serialize(out);
                System.out.println("[DEBUG] Sent END_TRIP to server");
                break;
            default:
                System.out.println("Isso não é uma opção válida :/");
                menuViagem(out);
                break;
        }
    }

    public static void menuReserve(DataOutputStream out) throws IOException {
        System.out.println("------------------SD-TP-GRUPO-21--------------");
        System.out.println("|Insira a operação que pretende realizar :   |");
        System.out.println("|1->Iniciar Viagem                           |");
        System.out.println("----------------------------------------------");

        // Ler input aqui
        int opt = ler.nextInt(); ler.nextLine();
        switch (opt) {
            case 1: // Iniciar viagem
                // We need to get the reservation code
                System.out.println("Insira código de reserva :");
                String reservationCode = ler.nextLine();

                new Message(START_TRIP, reservationCode).serialize(out);
                System.out.println("[DEBUG] Sent START_TRIP to server");

                menuViagem(out);
                break;
            default:
                System.out.println("Isso não é uma opção válida :/");
                menuReserve(out);
                break;
        }
    }

    public static void imprimeTrotis(ListLoc list){
        for (int i = 0; i < list.getSize() ; i++) {
            System.out.println(list.getObjects().get(i));
        }
    }

    public static void imprimeRecompensas(ListRec list){
        for (int i = 0; i < list.getSize() ; i+=2) {
            System.out.println(list.getObjects().get(i).toString());
        }
    }

    private static Utilizador menuGetUtilizador(){
        System.out.println("Insira o username :");
        String username = ler.nextLine();
        System.out.println("Insira a password :");
        String pass = ler.nextLine();
        return new Utilizador(username, pass);
    }
    private static Localizacao menuGetLocalização(){
        System.out.println("Insira a coordenada X :");
        int x = ler.nextInt();
        ler.nextLine();
        System.out.println("Insira a coordenada Y :");
        int y = ler.nextInt();
        ler.nextLine();
        return new Localizacao(x,y,-1);
    }

    private static void optionNotification(int number){
        if(notifications)
            System.out.println("|"+ number + "-> Desligar notificações                   |");
        else
            System.out.println("|"+ number + "-> Ligar notificações                      |");
    }

}




