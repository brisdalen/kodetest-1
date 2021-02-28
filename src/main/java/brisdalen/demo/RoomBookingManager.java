package brisdalen.demo;
/*
Lag en metode for å booke et rom i systemet.
Ta rom-ID, antall personer og en booking-dato som parameter.
 */

import domain.Booking;
import domain.Rom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@Component("roomBookingManager")
public class RoomBookingManager {
    @Autowired
    private RoomManager roomManager;
    private List<Booking> registrerteBookings;

    public boolean bookRom(String romId, Date bookingDato, int antallPersoner) {
        Rom gjeldenRom;
        try {
             gjeldenRom = getRoom(romId);
             verifiser(romId, bookingDato, antallPersoner);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }

        var booking = new Booking(gjeldenRom, bookingDato, antallPersoner);
        registrerteBookings.add(booking);
        return true;
    }

    public Rom getRoom(String romId) {
        Optional<Rom> toReturn = roomManager.getRegistrerteRom().stream()
                .filter(rom -> rom.getId().equals(romId))
                .findFirst();
        if(toReturn.isPresent()) {
            return toReturn.get();
        } else {
            throw new RuntimeException("Dette rommet eksiterer ikke.");
        }
    }

    private void verifiser(String romId, Date bookingDato, int antallPersoner) {
        if(!ledigDato(romId, bookingDato)) {
            throw new RuntimeException("Dette rommet er ikke ledig under den forsøkte bookingsdaoten.");
        }

        if(!riktigAntallPersoner(romId, antallPersoner)) {
            throw new RuntimeException("Dette rommer har ikke plass til ønsket antall personer.");
        }
    }

    private boolean ledigDato(String romId, Date dato) {
        return registrerteBookings.stream()
                .filter(booking -> booking.getGjeldendeRom().getId().equals(romId))
                .noneMatch(booking -> booking.getGjeldendeDato() == dato);
    }

    private boolean riktigAntallPersoner(String romId, int antallPersoner) {
        return registrerteBookings.stream()
                .filter(booking -> booking.getGjeldendeRom().getId().equals(romId))
                .allMatch(booking -> booking.getGjeldendeRom().getSengeplasser() >= antallPersoner);
    }
}
