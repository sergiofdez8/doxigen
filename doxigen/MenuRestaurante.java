import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class MenuRestaurante {
	static Scanner teclado = new Scanner(System.in);
	static Restaurante restaurante = new Restaurante();

	public static void Menu() throws ParseException, ClassNotFoundException {

		int menu = 0;

		while (menu != 5) {
			System.out.println("\n" 
					+ "MENÚ\n" 
					+ "1. Crear reserva \n" 
					+ "2. Buscar reserva \n"
					+ "3. Guardado de reservas \n" 
					+ "4. Cargar fichero de reservas \n" 
					+ "5. EXIT \n");

			System.out.println("Escoja opción: ");
			menu = teclado.nextInt();

			switch (menu) {
			case 1: {
				nuevaReserva(restaurante);
				break;
			}
			case 2: {
				subMenu();
				break;
			}
			case 3: {
				ListadoCarga(restaurante);
				break;
			}
			case 4: {
				FicheroReservas(restaurante);
				break;
			}
			case 5: {
				System.out.println("Muchas gracias, por su visita. ");
				break;
			}
			default:

			}
		}

	}

	public static void nuevaReserva(Restaurante restaurant) throws ParseException {
		Scanner teclado = new Scanner(System.in);

		System.out.println("A que nombre es la reserva: ");
		String nombre = teclado.next();

		System.out.println("Dígame su número de telefono: ");
		int nTlf = teclado.nextInt();

		System.out.println("Cuantos comensales son: ");
		int nComensales = teclado.nextInt();

		System.out.println("En que fecha quisiera hacer la reserva y hora (dd/MM/yyyy-HH:mm) ");
		String fechaReserva = teclado.next();
		LocalDateTime fechafinal = LocalDateTime.parse(fechaReserva, DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm"));

		System.out.println(fechafinal);
		int mesasDisponiblesNuevaReserva = mesasNecesariasReservas(restaurant, nComensales);

		if (comprobacionApertura(fechafinal, restaurant)
				&& mesasDisponiblesNuevaReserva <= mesasDisponiblesHora(restaurant, fechafinal)) {
			Reserva nuevaReserva = new Reserva(nombre, nTlf, fechafinal, nComensales);
			restaurant.listaReservas.add(nuevaReserva);
			System.out.println("Su reserva ha sido realizada con éxito.");
		} else {
			System.out.println("ERROR su reserva está fuera de horario.");
		}

	}

	public static boolean comprobacionApertura(LocalDateTime fechaHora, Restaurante restaurant) {

		LocalTime horaReserva = fechaHora.toLocalTime();
		if (horaReserva.isAfter(restaurant.horaAperturaAlmuerzos)
				&& horaReserva.isBefore(restaurant.horaCierreAlmuerzos)) {
			return true;
		} else if (horaReserva.isAfter(restaurant.horaAperturaCenas)
				&& horaReserva.isBefore(restaurant.horaCierreCenas)) {
			return true;
		}
		return false;

	}

	public static int mesasDisponiblesHora(Restaurante restaurant, LocalDateTime fechaReservas) {

		int contadorMesasOcupadas = 0;
		for (Reserva reserv : restaurant.listaReservas) {// for each
			if (reserv.equals(fechaReservas)) {

				contadorMesasOcupadas = contadorMesasOcupadas + mesasNecesariasReservas(restaurant, reserv.nComensales);
			}
		}
		return restaurant.nMesasDisponibles - contadorMesasOcupadas;
	}

	public static int mesasNecesariasReservas(Restaurante restaurant, int nComensales) {
		double mesasNecesarias = (double) nComensales / restaurant.nMaxComensalesxMesa;
		int mesasNecesariasFinal = (int) Math.ceil(mesasNecesarias);
		return mesasNecesariasFinal;
	}

	public static void subMenu(){

		Reserva restaurant = buscarReserva(restaurante);

		int subMenu = 0;

		while (subMenu != 3) {
			System.out.println("\n" 
			+ "Busqueda Reserva:\n" 
			+ "1. Modificar Reserva \n" 
			+ "2. Borrar reserva \n" 
			+ "3. EXIT \n");

			System.out.println("Escoja opción: ");
			subMenu = teclado.nextInt();
			switch (subMenu) {
			case 1: {
				subMenuModificar(restaurant);
				break;
			}
			case 2: {
				borrarReserva(restaurant);
				break;
			}
			default:

			}
		}
	}

	public static Reserva buscarReserva(Restaurante restaurant) {

		System.out.println("Introduce nombre de reserva: ");
		String nombre = teclado.next();

		System.out.println("Introduce fecha de reserva: (dd/MM/yyyy-HH:mm)");
		String fechaReserva = teclado.next();
		LocalDateTime fechaReser = LocalDateTime.parse(fechaReserva, DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm"));

		Reserva reserv = comprobarReserva(restaurant, nombre, fechaReser);

		if (reserv != null) {
			System.out.println("Reserva ya existente.");
			return reserv;
		} else {
			System.out.println("ERROR reserva no existente");
		}
		return null;

	}

	public static Reserva comprobarReserva(Restaurante restaurant, String nombre, LocalDateTime fechaHoraReserva) {

		for (Reserva reserv : restaurant.listaReservas) {
			if (reserv.nombre.equals(nombre) && reserv.fechaHoraReserva.equals(fechaHoraReserva)) {
				return reserv;
			}
		}
		return null;
	}

	public static void subMenuModificar(Reserva reserv){

		int subMenuModificar = 0;
		while (subMenuModificar != 4) {
			System.out.println("\n" + "¿Desea usted hacer algún cambio?:\n" + "1. Modificar nombre\n"
					+ "2. Modificar fecha\n" + "3. Modificar numero de comensales\n" + "4. EXIT \n");

			System.out.println("Escoja opción: ");
			subMenuModificar = teclado.nextInt();

			switch (subMenuModificar) {
			case 1: {
				modificarNombre(reserv);
				break;
			}

			case 2: {
				modificarFechaHoraReserva(reserv);
				break;
			}
			case 3: {
				modificarNComensales(reserv);
				break;
			}

			}
		}
	}
	public static void modificarNombre(Reserva reserv) {
		System.out.println("Introduzca su nuevo nombre para la reserva, por favor: ");
		reserv.nombre = teclado.next();

		System.out.println("El nombre ha sido modificado a " + reserv.nombre);
		
	}
	public static void modificarFechaHoraReserva(Reserva reserv) {
		String fechaHoraReserva;
		

		System.out.println("Introduzca su nueva fecha de reserva, por favor: ");
		fechaHoraReserva = teclado.next();
		LocalDateTime fechaHoraRese = LocalDateTime.parse(fechaHoraReserva,DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm"));
		
		reserv.fechaHoraReserva = fechaHoraRese;
		System.out.println("La fecha ha sido modificada a " + fechaHoraReserva);
	}
	public static void modificarNComensales(Reserva reserv) {
		int nComensales;

		System.out.println("Introduce nuevo numero de comensales: ");
		nComensales = teclado.nextInt();
		reserv.nComensales = nComensales;

		System.out.println("El número de comensales ha sido modificado a " + nComensales);
	}
	

	public static void borrarReserva(Reserva restaurant) {
		restaurante.listaReservas.remove(restaurant);
		System.out.println("La reserva ha sido eliminada, hasta luego gracias.");
	}
	
	public static void FicheroReservas(Restaurante restaurant) {
		FileOutputStream fout;
		try {
			fout = new FileOutputStream("C:\\Users\\SERGIO FERNANDEZ\\eclipse-workspace\\Restaurante\\src\\ReservasRestaurante.ser");
			 ObjectOutputStream oss = new ObjectOutputStream(fout);
				oss.writeObject(restaurant.horaCierreCenas);
			System.out.println("El guardado se ha realizado correctamente.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public static void ListadoCarga(Restaurante restaurant) throws ClassNotFoundException {
		try {
			ObjectInputStream listaDeCarga = new ObjectInputStream(new FileInputStream("C:\\Users\\SERGIO FERNANDEZ\\eclipse-workspace\\Restaurante\\src\\ReservasRestaurante.ser"));
			restaurant.listaReservas = (ArrayList<Reserva>) listaDeCarga.readObject();
			listaDeCarga.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// TODO: handle exception
		}catch (IOException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}	
}
