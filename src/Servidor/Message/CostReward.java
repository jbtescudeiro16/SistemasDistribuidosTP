package Servidor.Message;

import Entidades.Localizacao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CostReward {
    private float cost;
    private double reward;

    public CostReward(float cost, double reward) throws IOException {
        this.cost = cost;
        this.reward = reward;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public void serialize(DataOutputStream out) throws IOException {
        out.writeFloat(cost);
        out.writeDouble(reward);
        out.flush();
    }

    public static CostReward deserialize(DataInputStream in) throws IOException {
        return new CostReward(in.readFloat(), in.readDouble());
    }

    @Override
    public String toString(){
        return "{ Cost = " + this.cost + ", " +
                " Credits = " + this.reward + " }";
    }
}
