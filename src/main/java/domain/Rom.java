package domain;
/*
 Et rom skal ha en unik id (generert av systemet), et antall sengeplasser og en pris (per dag).
 Prisen er den samme uansett hvor mange som sover på rommet.
 Rommet skal også ha en og bare en type, fra lista
 Bryllupssuite, Businessuite, Kvalitetsrom, Lavprisrom.
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Rom {
    private String id;
    private int sengeplasser;
    private double pris;
    private RomType romType;
}
