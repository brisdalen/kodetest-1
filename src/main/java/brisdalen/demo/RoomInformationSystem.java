package brisdalen.demo;

import domain.Rom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/*
Lag en metode som henter ut total inntjening for et rom på tvers av alle bookinger.
 (Både fortidige og fremtidige.)
 Ta rommets id som parameter, og returner total inntjening.
 Hver enkelt booking som er registrert på rommet gir en inntjening tilsvarende rommets pris.

  Lag en metode som henter ut alle rom, sortert synkende etter total inntjening (som beregnet i forrige punkt).

   Lag en metode som finner det høyeste antall dager et gitt rom er blitt booket rett etter hverandre uten avbrudd.
   Ta rommets ID som parameter til metoden.
 */
@Getter
@Setter
@NoArgsConstructor
@Component("roomInformationSystem")
public class RoomInformationSystem {

    @Autowired
    private RoomManager roomManager;
    @Autowired
    private RoomBookingManager roomBookingManager;

    public double totalInntjening(String romId) {
        return roomBookingManager.getRegistrerteBookings().stream()
                .filter(b -> b.getGjeldendeRom().getId().equals(romId))
                .mapToDouble(b -> b.getGjeldendeRom().getPris()).sum();
    }

    public List<Rom> romEtterTotalInntjening() {
        roomManager.getRegistrerteRom().sort(new RomComparator().reversed());
        for(var r : roomManager.getRegistrerteRom()) {
            System.out.println(r.getId() + " - " + totalInntjening(r.getId()));
        }
        return roomManager.getRegistrerteRom();
    }

    class RomComparator implements Comparator<Rom> {
        @Override
        public int compare(Rom o1, Rom o2) {
            return (int) (totalInntjening(o1.getId()) - totalInntjening(o2.getId()));
        }
    }
}
