package Entidades;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Localizacao {
    int x;
    int y;
    int numTrotinetes;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Localizacao that = (Localizacao) o;
        return x == that.x && y == that.y && numTrotinetes == that.numTrotinetes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, numTrotinetes);
    }

    public Localizacao(int x, int y, int numTrotinetes) {
        this.x = x;
        this.y = y;
        this.numTrotinetes = numTrotinetes;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNumTrotinetes() {
        return numTrotinetes;
    }

    public void setNumTrotinetes(int numTrotinetes) {
        this.numTrotinetes = numTrotinetes;
    }

    public void serialize (DataOutputStream out) throws IOException {


        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(numTrotinetes);


        out.flush();
    }

    public static Localizacao deserialize (DataInputStream in) throws IOException {
        int x = in.readInt();
        int y = in.readInt();
        int numTrotinetes = in.readInt();

        Localizacao localizacao = new Localizacao(x,y, numTrotinetes);
        return localizacao;


    }

    public static List<Localizacao> listdeserialize (DataInputStream in) throws IOException {
        int r = in.readInt();
        int i = 0;
        List<Localizacao> loc = new ArrayList<>(r);
        while(i<r) {
            int x = in.readInt();
            int y = in.readInt();
            int numTroti = in.readInt();
            loc.add(new Localizacao(x,y,numTroti));
            i++;
        }
        return loc;


    }



    @Override
    public String toString() {
        return "(" + x + "," + y +")" + " Trotinetes= " + numTrotinetes;
    }
}
