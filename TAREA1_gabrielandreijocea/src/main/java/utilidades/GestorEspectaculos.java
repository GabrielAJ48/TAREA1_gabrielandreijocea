package utilidades;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import entidades.Espectaculo;

public class GestorEspectaculos {

	public static void verEspectaculos(String ruta) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            while (true) {
                try {
                    Espectaculo e = (Espectaculo) ois.readObject();
                    System.out.println("ID: "+e.getId());
                    System.out.println("Nombre: "+e.getNombre());
                    System.out.println("Fecha inicio: "+e.getFechaini());
                    System.out.println("Fecha fin: "+e.getFechafin());
                }
                catch (EOFException e) {
                   break;
                } catch (ClassNotFoundException ex) {
                    System.out.println("Clase no encontrada"+ex.getMessage());;
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al leer el fichero");
        }
	}
}
