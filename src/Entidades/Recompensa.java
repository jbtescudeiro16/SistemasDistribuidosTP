package Entidades;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class Recompensa {
    double creditos;
    Localizacao destino;
    Localizacao origem;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recompensa that = (Recompensa) o;
        return creditos == that.creditos && destino.equals(that.destino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditos, destino);
    }

    @Override
    public String toString() {
        return "Reward{ " +
                "Origem = " + origem.toString() +
                ", Destino = " + destino.toString() +
                ", Valor = " + creditos + "}";
    }

    public Recompensa(Localizacao ori, Localizacao dest, double creditos) {
        this.creditos = creditos;
        this.destino = dest;
        this.origem = ori;
    }

    public Localizacao getDestino() {
        return destino;
    }

    public void setDestino(Localizacao destino) {
        this.destino = destino;
    }

    public double getCreditos() {
        return creditos;
    }

    public void setCreditos(double creditos) {
        this.creditos = creditos;
    }

    public void serialize(DataOutputStream out) throws IOException {

        origem.serialize(out);
        destino.serialize(out);
        out.writeDouble(creditos);

        out.flush();
    }

    public static Recompensa deserialize(DataInputStream in) throws IOException{

        Localizacao origem = Localizacao.deserialize(in);
        Localizacao destino = Localizacao.deserialize(in);
        double creditos = in.readDouble();

        return new Recompensa(origem, destino, creditos);
    }





}
