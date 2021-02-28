package brisdalen.demo;

import domain.RomType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class Kodetest1ApplicationTests {

    @Autowired
    private RoomManager roomManager;

    @Autowired
    private RoomBookingManager roomBookingManager;

    @Autowired
    private RoomInformationSystem roomInformationSystem;

    @Test
    void testRegistrerNyeRom() {
        // Arrange
        var rom = roomManager.getRegistrerteRom();

        // Act
        roomManager.registrerNyttRom(2, 1000, RomType.Kvalitetsrom);
        roomManager.registrerNyttRom(2, 1000, RomType.Kvalitetsrom);
        roomManager.registrerNyttRom(2, 2000, RomType.Bryllupssuite);

        // Assert
        assertThat(rom.get(0).getId()).isEqualTo("00-1");
        assertThat(rom.get(1).getId()).isEqualTo("00-2");
        assertThat(rom.get(2).getId()).isEqualTo("00-3");
    }

    @Test
    void testTotalInntjening() {
        // Arrange
        var rom = roomManager.getRegistrerteRom();
        Calendar calendar = Calendar.getInstance();
        var date1 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        var date2 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        var date3 = calendar.getTime();

        roomManager.registrerNyttRom(2, 1000, RomType.Kvalitetsrom);
        roomManager.registrerNyttRom(2, 1000, RomType.Kvalitetsrom);
        roomManager.registrerNyttRom(2, 2000, RomType.Bryllupssuite);

        // Act
        roomBookingManager.bookRom("00-1", date1, 2);

        roomBookingManager.bookRom("00-2", date1, 2);
        roomBookingManager.bookRom("00-2", date2, 2);

        roomBookingManager.bookRom("00-3", date1, 2);
        roomBookingManager.bookRom("00-3", date2, 2);
        roomBookingManager.bookRom("00-3", date3, 2);

        // Assert
        assertThat(roomInformationSystem.totalInntjening("00-1")).isEqualTo(1000);
        assertThat(roomInformationSystem.totalInntjening("00-2")).isEqualTo(2000);
        assertThat(roomInformationSystem.totalInntjening("00-3")).isEqualTo(6000);
    }

    @Test
    void testRomEtterTotalInntjening() {
        // Arrange
        var rom = roomManager.getRegistrerteRom();
        Calendar calendar = Calendar.getInstance();
        var date1 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        var date2 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        var date3 = calendar.getTime();

        roomManager.registrerNyttRom(2, 300, RomType.Lavprisrom);
        roomManager.registrerNyttRom(6, 10000, RomType.Businessuite);
        roomManager.registrerNyttRom(2, 2000, RomType.Bryllupssuite);

        roomBookingManager.bookRom("00-1", date1, 2);

        roomBookingManager.bookRom("00-2", date1, 2);
        roomBookingManager.bookRom("00-2", date2, 2);

        roomBookingManager.bookRom("00-3", date1, 2);
        roomBookingManager.bookRom("00-3", date2, 2);
        roomBookingManager.bookRom("00-3", date3, 2);

        roomInformationSystem.romEtterTotalInntjening();

        // Act
        assertThat(rom.get(0).getId()).isEqualTo("00-2");
        assertThat(rom.get(1).getId()).isEqualTo("00-3");
        assertThat(rom.get(2).getId()).isEqualTo("00-1");
    }

}
