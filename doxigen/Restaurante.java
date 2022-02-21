import java.time.LocalTime;
import java.util.ArrayList;

public class Restaurante {
		ArrayList<Reserva> listaReservas = new ArrayList<>();
		int nMaxComensalesxMesa = 4;
		int nMesasDisponibles = 4;
		
		LocalTime horaAperturaAlmuerzos = LocalTime.of(13, 00);
		LocalTime horaCierreAlmuerzos = LocalTime.of(15, 00);
		LocalTime horaAperturaCenas = LocalTime.of(19, 00);
		LocalTime horaCierreCenas = LocalTime.of(22, 00);
	
}
