package Servidor.Message;//SD Protocol
import Entidades.Localizacao;
import Entidades.Utilizador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class Message {
    private MessageType type;
    private Object message;

    @Override
    public String toString() {
        return "Message{ " +
                "type = " + type.toString() + ", " +
                "message = " + message.toString() +
                " }";
    }

    public Message(MessageType type, Object message){
        this.type=type;
        this.message = message;
    }

    public MessageType getType(){return this.type;}
    public void setType (MessageType type){this.type = type;}

    public Object getMessage(){return this.message;}
    public void setMessage(Object message){this.message = message;}

    public static Message deserialize (DataInputStream in) throws IOException {
        MessageType type = MessageType.fromInteger(in.readInt());
        Object message = new Object();
        switch (type) {
            case REGISTER, CONNECTION -> message = Utilizador.deserializeBasics(in);
            case GENERIC, CONNECTION_RESPONSE, DESCONNECTION_RESPONSE, START_TRIP, SCOOTER_RESERVATION_RESPONSE  -> message =in.readUTF();
            case NEARBY_SCOOTERS, NEARBY_REWARDS,SCOOTER_RESERVATION_REQUEST -> message = Localizacao.deserialize(in);
            case END_TRIP -> message = ReservationMessage.deserialize(in);
            case LIST_SCOOTERS -> message = ListLoc.deserialize(in);
            case LIST_REWARDS, NOTIFICATION_MSG -> message = ListRec.deserialize(in);
            case COST_REWARD -> message = CostReward.deserialize(in);
            case TOGGLE_NOTIFICATION -> message = null;
            default -> {System.out.println("[DEBUG] IDK this message");}
        }
        return new Message(type, message);
    }

    public void serialize(DataOutputStream out) throws IOException {
        out.writeInt(MessageType.toInteger(this.type));
        switch (this.type){
            case REGISTER:
            case CONNECTION:
                if(message instanceof Utilizador user)
                    user.serializeBasics(out);
                break;
            case GENERIC:
            case CONNECTION_RESPONSE:
            case DESCONNECTION_RESPONSE:
            case SCOOTER_RESERVATION_RESPONSE:
            case START_TRIP:
                if(message instanceof String connRes)
                    out.writeUTF(connRes);
                break;
            case NEARBY_SCOOTERS:
            case NEARBY_REWARDS:
            case SCOOTER_RESERVATION_REQUEST:
                if(message instanceof Localizacao loc)
                    loc.serialize(out);
                break;
            case END_TRIP:
                if(message instanceof ReservationMessage res)
                    res.serialize(out);
                break;
            case LIST_SCOOTERS:
                if(message instanceof ListLoc list)
                    list.serialize(out);
                break;
            case LIST_REWARDS:
            case NOTIFICATION_MSG:
                if(message instanceof ListRec list)
                    list.serialize(out);
                break;
            case COST_REWARD:
                if(message instanceof CostReward custo)
                    custo.serialize(out);
                break;
            case DESCONNECTION:
            case TOGGLE_NOTIFICATION:
            default:
                break;
        }

        out.flush();
    }

}

