import java.io.Serializable; 
import java.sql.Date;
import java.time.LocalDateTime;

public class Reserva implements Serializable{//ver porque hay que serializar la clase
	String nombre;
	int nTlf;
	LocalDateTime fechaHoraReserva;
	int nComensales;
	
	public Reserva(String nombre, int nTlf, LocalDateTime fechaHoraReserva, int nComensales) {
		this.nombre = nombre;
		this.nTlf = nTlf;
		this.fechaHoraReserva = fechaHoraReserva;
		this.nComensales = nComensales;
	}

}
