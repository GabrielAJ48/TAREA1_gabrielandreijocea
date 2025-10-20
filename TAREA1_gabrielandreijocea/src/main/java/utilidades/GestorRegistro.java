package utilidades;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import entidades.Artista;
import entidades.Coordinacion;
import entidades.Persona;

public class GestorRegistro {

	public static void registrarPersona(Persona persona, String nombreUsuario, String contrasenia, String perfil, String ruta) {
		
		try {
			long nuevoIdPersona = obtenerSiguienteIdPersona(ruta);
			persona.setId(nuevoIdPersona);

			int[] contadores = obtenerContadoresPerfiles(ruta);
			if (persona instanceof Coordinacion) {
				long nuevoIdCoord = contadores[0] + 1;
				((Coordinacion) persona).setIdCoord(nuevoIdCoord);
			} else if (persona instanceof Artista) {
				long nuevoIdArt = contadores[1] + 1;
				((Artista) persona).setIdArt(nuevoIdArt);
			}

			String nuevaLinea = String.join("|",
					String.valueOf(persona.getId()),
					nombreUsuario.toLowerCase(),
					contrasenia,
					persona.getEmail(),
					persona.getNombre(),
					persona.getNacionalidad(),
					perfil.toLowerCase()
			);

			try (FileWriter fw = new FileWriter(ruta, true);
				 PrintWriter pw = new PrintWriter(fw)) {
				
				pw.println(nuevaLinea);
			}

		} catch (IOException e) {
			System.out.println("Error al intentar escribir en el fichero de credenciales");
		}
	}

	private static long obtenerSiguienteIdPersona(String ruta) {
		int maxId = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				if (!linea.trim().isEmpty()) {
					String[] partes = linea.split("\\|");
					try {
						if (partes.length > 0) {
							int idActual = Integer.parseInt(partes[0]);
							if (idActual > maxId) {
								maxId = idActual;
							}
						}
					} catch (NumberFormatException e) {
						System.out.println("Se encontró una línea con formato de ID incorrecto.");
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Error de lectura del fichero de credenciales");
		}
		return maxId + 1;
	}
	
	private static int[] obtenerContadoresPerfiles(String ruta) {
		int[] contadores = new int[2];
		
		try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				if (!linea.trim().isEmpty()) {
					String[] partes = linea.split("\\|");
					if (partes.length == 7) {
						String perfil = partes[6].trim().toLowerCase();
						if ("coordinacion".equals(perfil)) {
							contadores[0]++;
						} else if ("artista".equals(perfil)) {
							contadores[1]++;
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println("");
		}
		
		return contadores;
	}
}