package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/*
En spesifikk booking gjelder for et og bare et rom, for en og bare en dato.
(Ja, oppholdet for en booking på et rom har en varighet på bare én dag.
Et rom kan bookes flere dager, men da i seperate bookinger.)
En booking inneholder også antall personer som skal bo på rommet.
 */
@Getter
@Setter
@AllArgsConstructor
public class Booking {
    private Rom gjeldendeRom;
    private Date gjeldendeDato;
    private int antallPersoner;
}
