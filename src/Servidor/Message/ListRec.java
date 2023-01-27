package Servidor.Message;

import Entidades.Localizacao;
import Entidades.Recompensa;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListRec {
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Recompensa> getObjects() {
        return objects;
    }

    public void setObjects(List<Recompensa> objects) {
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
    private List<Recompensa> objects;

    public ListRec(int size, List<Recompensa> objects) throws IOException {
        this.size = size;
        this.objects = objects;
    }

    public void serialize(DataOutputStream out) throws IOException {
        out.writeInt(size);
        for (Recompensa rec : objects) {
            rec.serialize(out);
        }
        out.flush();
    }

    public static ListRec deserialize(DataInputStream in) throws IOException {
        int size = in.readInt();
        List<Recompensa> objects = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Recompensa c = Recompensa.deserialize(in);
            objects.add(c);
        }
        return new ListRec(size, objects);
    }


   }
