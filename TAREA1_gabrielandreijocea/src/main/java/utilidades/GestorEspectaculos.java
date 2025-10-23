package utilidades;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entidades.Espectaculo;

public class GestorEspectaculos {

	public static void verEspectaculos(String ruta) {
		System.out.println("--- LISTA DE ESPECTÁCULOS ---");
        List<Espectaculo> espectaculos = cargarEspectaculos(ruta);

        if (espectaculos.isEmpty()) {
            System.out.println("No hay espectáculos registrados por el momento.");
        } else {
        	
        	espectaculos.sort(Comparator.comparing(Espectaculo::getNombre));
        	
            for (Espectaculo e : espectaculos) {
                System.out.println("---------------------------------");
                System.out.println("ID: " + e.getId());
                System.out.println("Nombre: " + e.getNombre());
                System.out.println("Fecha inicio: " + e.getFechaini());
                System.out.println("Fecha fin: " + e.getFechafin());
            }
        }
        System.out.println("---------------------------------");
    }
	
	public static boolean comprobarNombreEspectaculo(String nombre, String ruta) {
	        
	        if (nombre.length() > 25) {
	            System.out.println("El nombre del espectáculo no puede ser superior a 25 caracteres");
	            return false;
	        }
	        if (nombre.isBlank()) {
	            System.out.println("El nombre no puede estar vacío");
	            return false;
	        }
	
	        List<Espectaculo> espectaculos = cargarEspectaculos(ruta);
	        
	        for (Espectaculo e : espectaculos) {
	            if (e.getNombre().equalsIgnoreCase(nombre)) {
	                System.out.println("Ya existe un espectáculo con ese nombre asignado");
	                return false;
	            }
	        }
	        
	        return true;
	    }
	
	public static boolean comprobarFechasEspectaculo(String fechaini, String fechafin) {
        if (fechaini.isBlank() || fechafin.isBlank()) {
            System.out.println("Las fechas no pueden estar vacías.");
            return false;
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaInicio;
        LocalDate fechaFin;

        try {
            fechaInicio = LocalDate.parse(fechaini, formato);
            fechaFin = LocalDate.parse(fechafin, formato);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Asegúrese de usar dd/MM/yyyy y que sea una fecha real (ej: 29/02/2024).");
            return false;
        }

        if (fechaFin.isBefore(fechaInicio)) {
            System.out.println("La fecha de fin no puede ser anterior a la fecha de inicio.");
            return false;
        }

        LocalDate fechaMaxima = fechaInicio.plusYears(1);
        if (fechaFin.isAfter(fechaMaxima)) {
            System.out.println("El periodo del espectáculo no puede ser superior a 1 año.");
            return false;
        }
        
        return true;
    }
	
	private static List<Espectaculo> cargarEspectaculos(String ruta) {
        List<Espectaculo> espectaculos = new ArrayList<>();
        File fichero = new File(ruta);

        if (!fichero.exists()) {
            return espectaculos;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))) {
            while (true) {
                espectaculos.add((Espectaculo) ois.readObject());
            }
        } catch (EOFException e) {

        } catch (IOException | ClassNotFoundException e) {
             if (!(e instanceof EOFException)) {
                System.out.println("Error de lectura");
            }
        }
        return espectaculos;
    }

    private static long obtenerSiguienteId(String ruta) {
        List<Espectaculo> espectaculos = cargarEspectaculos(ruta);
        if (espectaculos.isEmpty()) {
            return 1L;
        }
        
        long maxId = 0;
        for (Espectaculo e : espectaculos) {
            if (e.getId() > maxId) {
                maxId = e.getId();
            }
        }
        return maxId + 1;
    }
    
public static void crearEspectaculo(Espectaculo nuevoEspectaculo, Long idCoordinador, String ruta) {
        
        nuevoEspectaculo.setIdCoord(idCoordinador);
        nuevoEspectaculo.setId(obtenerSiguienteId(ruta));

        File fichero = new File(ruta);
        try {
            if (fichero.exists() && fichero.length() > 0) {
                try (AppendingObjectOutputStream aoos = new AppendingObjectOutputStream(new FileOutputStream(fichero, true))) {
                    aoos.writeObject(nuevoEspectaculo);
                }
            } else {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero))) {
                    oos.writeObject(nuevoEspectaculo);
                }
            }
            System.out.println("Espectáculo " + nuevoEspectaculo.getNombre() + " registrado correctamente con ID: " + nuevoEspectaculo.getId());
        
        } catch (IOException e) {
            System.out.println("Error al guardar el espectáculo");
        }
    }
	
}
