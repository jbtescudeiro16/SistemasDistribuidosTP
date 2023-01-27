package Servidor.Message;

public enum MessageType {
    GENERIC,
    REGISTER,
    CONNECTION,
    CONNECTION_RESPONSE,
    DESCONNECTION,
    DESCONNECTION_RESPONSE,
    NEARBY_SCOOTERS,
    LIST_SCOOTERS,
    NEARBY_REWARDS,
    LIST_REWARDS,
    SCOOTER_RESERVATION_REQUEST,
    SCOOTER_RESERVATION_RESPONSE,
    START_TRIP,
    END_TRIP,
    COST_REWARD,
    TOGGLE_NOTIFICATION,

    NOTIFICATION_MSG;

    public static MessageType fromInteger(int x) {
        return switch (x) {
            case 0 -> GENERIC;
            case 1 -> REGISTER;
            case 2 -> CONNECTION;
            case 3 -> CONNECTION_RESPONSE;
            case 4 -> DESCONNECTION;
            case 5 -> DESCONNECTION_RESPONSE;
            case 6 -> NEARBY_SCOOTERS;
            case 7 -> LIST_SCOOTERS;
            case 8 -> NEARBY_REWARDS;
            case 9 -> LIST_REWARDS;
            case 10 -> SCOOTER_RESERVATION_RESPONSE;
            case 11 -> START_TRIP;
            case 12 -> END_TRIP;
            case 13 -> COST_REWARD;
            case 14 -> SCOOTER_RESERVATION_REQUEST;
            case 15 -> TOGGLE_NOTIFICATION;
            case 16 -> NOTIFICATION_MSG;
            default -> null;
        };
    }

    public static int toInteger(MessageType x) {
        return switch (x) {
            case GENERIC -> 0;
            case REGISTER  -> 1;
            case CONNECTION -> 2;
            case CONNECTION_RESPONSE -> 3;
            case DESCONNECTION -> 4;
            case DESCONNECTION_RESPONSE -> 5;
            case NEARBY_SCOOTERS -> 6;
            case LIST_SCOOTERS -> 7;
            case NEARBY_REWARDS -> 8;
            case LIST_REWARDS -> 9;
            case SCOOTER_RESERVATION_RESPONSE -> 10;
            case START_TRIP -> 11;
            case END_TRIP -> 12;
            case COST_REWARD -> 13;
            case SCOOTER_RESERVATION_REQUEST -> 14;
            case TOGGLE_NOTIFICATION -> 15;
            case NOTIFICATION_MSG -> 16;
            default -> -1;
        };
    }
}
