package Entidades;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Utilizador {

    private String username;
    private double creditos;
    private String password;
    private int x;
    private int y;


    public Utilizador(String username, double creditos, String password, int x, int y) {
        this.username = username;
        this.creditos = creditos;
        this.password = password;
        this.x = x;
        this.y = y;

    }

    public Utilizador(String username, String password) {
        this.username = username;
        this.creditos = 0;
        this.password = password;
    }


   public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

   public double getCreditos() {
        return creditos;
    }

    public void setCreditos(double creditos) {
        this.creditos = creditos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Localizacao getLocation(){
        return new Localizacao(x, y, -1);
    }

    public void setLocation(Localizacao loc){
        x = loc.getX();
        y = loc.getY();
    }

    public void addCreditos(double creditos){
        this.creditos += creditos;
    }

    @Override
    public String toString() {
        return "username=" + username +
                ",creditos=" + creditos +
                ",password=" + password;
    }

    public String toStringAccountInfo() {
        return username +"," + password;
    }



    public void serialize (DataOutputStream out) throws IOException {

        out.writeUTF(username);


        out.writeDouble(creditos);
        out.writeUTF(password);
        out.writeInt(x);
        out.writeInt(y);


        out.flush();
    }

    public void serializeBasics (DataOutputStream out) throws IOException {
        out.writeUTF(username);
        out.writeUTF(password);
        out.flush();
    }
    //
    public static Utilizador deserialize (DataInputStream in) throws IOException {
        String username = in.readUTF();
        double creditos = in.readDouble();
        String password = in.readUTF();
        int x = in.readInt();
        int y = in.readInt();

        return new Utilizador(username , creditos,password,x,y);
    }

    public static Utilizador deserializeBasics (DataInputStream in) throws IOException {
        String username = in.readUTF();
        String password = in.readUTF();

        return new Utilizador(username, password);
    }

}