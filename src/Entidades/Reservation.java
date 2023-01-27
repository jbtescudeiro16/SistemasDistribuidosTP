package Entidades;

import java.util.Random;

public class Reservation {
    //identifier
    private String reservationCode;

    private String user;
    //When
    private Long startTime;
    private Long endTime;
    //Where
    private Localizacao startLocation;
    private Localizacao endLocation;


    public Reservation(String user, Localizacao loc) {
        generateRandomCode();
        this.user = user;
        this.startLocation = loc;

    }


    public Reservation() {}

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode() {
        generateRandomCode();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Localizacao getStartLocation() {
        return startLocation;
    }

    public Localizacao getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Localizacao endLocation) {
        this.endLocation = endLocation;
    }

    public void endReservation(Localizacao endLocation){
        this.endLocation = endLocation;
        this.endTime = System.currentTimeMillis();
    }

    private void generateRandomCode(){
        Random random = new Random();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append((char) (random.nextInt(26) + 'A'));
        }
        this.reservationCode = sb.toString();
    }

}
