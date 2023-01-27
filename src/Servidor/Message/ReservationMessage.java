package Servidor.Message;

import Entidades.Localizacao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ReservationMessage {
    private String information;
    private Localizacao location;

    public ReservationMessage(String information, Localizacao location) throws IOException {
        this.information = information;
        this.location = location;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Localizacao getLocation() {
        return location;
    }

    public void setLocation(Localizacao location) {
        this.location = location;
    }

    public void serialize(DataOutputStream out) throws IOException {
        out.writeUTF(information);
        location.serialize(out);
        out.flush();
    }

    public static ReservationMessage deserialize(DataInputStream in) throws IOException {
        return new ReservationMessage(in.readUTF(), Localizacao.deserialize(in));
    }

    @Override
    public String toString(){
        return "{ Information = " + this.information + ", " +
                " Location = " + this.location + " }";
    }
}
