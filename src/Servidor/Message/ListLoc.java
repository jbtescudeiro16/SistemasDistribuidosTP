package Servidor.Message;

import Entidades.Localizacao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListLoc {
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Localizacao> getObjects() {
        return objects;
    }

    public void setObjects(List<Localizacao> objects) {
        this.objects = objects;
    }

    @Override
    public String toString() {
        return "ListObject{ " +
                "size = " + size +
                ", objects = " + objects +
                " }";
    }

    private int size;
    private List<Localizacao> objects;

    public ListLoc(int size, List<Localizacao> objects) throws IOException {
        this.size = size;
        this.objects = objects;
    }

    public void serialize(DataOutputStream out) throws IOException {
        out.writeInt(size);
        for (Localizacao loc : objects) {
            out.writeInt(loc.getX());
            out.writeInt(loc.getY());
            out.writeInt(loc.getNumTrotinetes());
        }
        out.flush();
    }

    public static ListLoc deserialize(DataInputStream in) throws IOException {
        int size = in.readInt();
        List<Localizacao> objects = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Localizacao c = Localizacao.deserialize(in);
            objects.add(c);
        }
        return new ListLoc(size, objects);
    }


   }
