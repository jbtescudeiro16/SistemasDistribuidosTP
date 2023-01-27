package Entidades;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Trotinete{

    private String idTrotinete;
    //O estar reservada é um atributo da trotinete ou da reserva?
    private boolean reservada;
    private Localizacao localizacao;

    public Trotinete(String idTrotinete, boolean reservada, Localizacao localizacao) {
        this.idTrotinete = idTrotinete;
        this.reservada = reservada;
        this.localizacao = localizacao;
    }

    public String getIdTrotinete() {
        return idTrotinete;
    }

    public void setIdTrotinete(String idTrotinete) {
        this.idTrotinete = idTrotinete;
    }

    public boolean isReservada() {
        return reservada;
    }

    public void setReservada(boolean reservada) {
        this.reservada = reservada;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    @Override
    public String toString() {
        return "Entidades.Trotinete{" +
                "idTrotinete='" + idTrotinete + '\'' +
                ", reservada=" + reservada +
                ", localizacao=" + localizacao +
                '}';
    }


    public void serialize (DataOutputStream out) throws IOException {
        out.writeUTF(idTrotinete);
        out.writeBoolean(reservada);
        localizacao.serialize(out);

        out.flush();
    }

    public static Trotinete deserialize (DataInputStream in) throws IOException {
        String idTrotinete = in.readUTF();
        boolean reservada = in.readBoolean();
        Localizacao localizacao = new Localizacao(in.readInt(), in.readInt(), in.readInt());

        return new Trotinete(idTrotinete,reservada , localizacao);


    }



}