package brisdalen.demo;

import domain.Rom;
import domain.RomType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
Lag en metode for å registrere et rom i systemet.
Ta antall sengeplasser, romtype og pris per dag som parametere til metoden.
Rommets ID bør genereres av systemet.
 */
@Getter
@Setter
@Component("roomManager")
public class RoomManager {

    private List<Rom> registrerteRom;

    public RoomManager() {
        registrerteRom = new ArrayList<>();
    }

    public void registrerNyttRom(int antallSengeplasser, double pris, RomType romType) {
        var nyttRom = new Rom(nextId(), antallSengeplasser, pris, romType);
        registrerteRom.add(nyttRom);
    }

    private String nextId() {
        int amount = registrerteRom.size();
        // Prefix "00-" hvis man senere skal ha flere etasjer
        return "00-" + (amount == 0 ? 1 : amount + 1);
    }
}
