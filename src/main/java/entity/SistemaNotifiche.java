package entity;

public class SistemaNotifiche {

    //istanziamento d Singleton
    private static SistemaNotifiche istanza;
    private SistemaNotifiche() {
    }

    public static SistemaNotifiche getIstanza() {
        if (istanza == null) {
            istanza = new SistemaNotifiche();
        }
        return istanza;
    }
}
