package Servidor;
import Entidades.Localizacao;
import Entidades.Recompensa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class RewardsSystem
{
    private Map<Localizacao, List<Recompensa>> rewardsMap = new HashMap<>();
    private static List<Localizacao> trotinetes;
    private static Localizacao userLoc;

    public Map<Localizacao, List<Recompensa>> getRewardsMap() {
        return rewardsMap;
    }

    public void setRewardsMap(Map<Localizacao, List<Recompensa>> rewardsMap) {
        this.rewardsMap = rewardsMap;
    }

    public RewardsSystem(List<Localizacao> trotinetes, Localizacao origem)
    {
        this.trotinetes = trotinetes;
        this.userLoc = origem;
    }


    public List<Localizacao> findLocalCVariasTrotis()
    {
        List<Localizacao> LocalCVariasTrotis =  new ArrayList<>();

        for (Localizacao local : trotinetes)
        {
            if (local.getNumTrotinetes() >= 2 )
            {
                LocalCVariasTrotis.add(local);
            }
        }

        return LocalCVariasTrotis;
    }

    public List<Localizacao> findLocalSTrotis()
    {
        List<Localizacao> LocalSTrotis = new ArrayList<>();

        for (Localizacao local : trotinetes)
        {
            if (local.getNumTrotinetes() == 0)
            {
                LocalSTrotis.add(local);
            }
        }

        return LocalSTrotis;
    }

    public void addToRewardsMap(Localizacao origem, List<Recompensa> destinos)
    {
        System.out.println("ADD to Rewards");
        rewardsMap.put(origem, destinos);
    }


    public double calculateReward(Localizacao loc)
    {
        double rewards = 0;

        int x = loc.getX();
        int y = loc.getY();

        rewards = Math.sqrt(Math.pow(x - userLoc.getX(), 2) + Math.pow(y - userLoc.getY(), 2));


        return (int) (rewards * 10);
    }


    public boolean aux(Localizacao destino){
        double dist= 3;
        int count=0;
        int t =0;

        for (Localizacao loc : trotinetes)
            if(Math.sqrt(Math.pow(loc.getX() - destino.getX(), 2) + Math.pow(loc.getY() - destino.getY(), 2)) <= dist)
                t++;

        for (Localizacao loc : findLocalSTrotis())
            if(Math.sqrt(Math.pow(loc.getX() - destino.getX(), 2) + Math.pow(loc.getY() - destino.getY(), 2)) <= dist)
                count++;


        return (count==t) ;
    }

    public void calculateRewards()
    {
        List<Localizacao> locaisCtrotis = findLocalCVariasTrotis();
        List<Recompensa> destinos = new ArrayList<>();
        List<Recompensa> recompensas = new ArrayList<>();
        List<Localizacao> semTrotis = findLocalSTrotis();
        int i = 0;

        for(Localizacao loc : locaisCtrotis)
        {
            if (loc.getX() == userLoc.getX() && loc.getY() == userLoc.getY())
            {
                for (i = 0 ; i < semTrotis.size(); i++)
                {
                    Recompensa res = new Recompensa(loc, semTrotis.get(i), calculateReward(semTrotis.get(i)));
                    destinos.add(res);
                }

                for (Recompensa res : destinos)
                {
                    if (aux(res.getDestino()))
                    {
                        recompensas.add(res);
                    }

                }

                addToRewardsMap(userLoc, recompensas);
                break;
            }

        }

    }

}