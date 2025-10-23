package utilidades;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entidades.Perfiles;
import entidades.Sesion;

public class GestorCredenciales {
	
	public static boolean comprobarContrasenia(String contrasenia, String ruta) {
		
		if (contrasenia.length() <= 2) {
	        System.out.println("La contraseña debe tener más de 2 caracteres.");
	        return false;
	    }
	    if (contrasenia.contains(" ")) {
	        System.out.println("La contraseña no puede contener espacios.");
	        return false;
	    }
		
		return true;
	}
	
	public static boolean comprobarUsuario(String usuario, String ruta) {
	    if (usuario.length() <= 2) {
	        System.out.println("El nombre de usuario debe tener más de 2 caracteres.");
	        return false;
	    }
	    if (usuario.contains(" ")) {
	        System.out.println("El nombre de usuario no puede contener espacios.");
	        return false;
	    }
	    if (!usuario.matches("^[a-zA-Z0-9]+$")) {
	        System.out.println("El nombre de usuario solo puede contener letras sin tildes, dieresis o numeros.");
	        return false;
	    }

	    String usuarioMinusculas = usuario.toLowerCase();
	    try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
	        String linea;
	        while ((linea = br.readLine()) != null) {
	            String[] partes = linea.split("\\|");
	            if (partes.length == 7) {
	                String nombreUsuarioExistente = partes[1];
	                if (nombreUsuarioExistente.equals(usuarioMinusculas)) {
	                    return false;
	                }
	            }
	        }
	    }catch (FileNotFoundException e) {
	    } catch (IOException e) {
	        System.out.println("Error de lectura");
	    }

	    return true;
	}

	public static boolean comprobarEmail(String email, String ruta) {
		List<String> lineas = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				lineas.add(linea);
			}

			for (String l : lineas) {
				String[] partes = l.split("\\|");
				if (partes.length == 7) {
					String correoElectronico = partes[3];

					if (correoElectronico.equals(email)) {
						return false;
					}
				}
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			System.out.println("Error de lectura: " + e.getMessage());
		}
		return true;
	}
	
	public static boolean comprobarCredenciales(String nombreUsuario, String contraseña, String ruta) {
		List<String> lineas = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				lineas.add(linea);
			}

			for (String l : lineas) {
				String[] partes = l.split("\\|");
				if (partes.length == 7) {
					String idPersona = partes[0];
					String usuario = partes[1];
					String contrasenia = partes[2];
					String nombrePersona = partes[4];
					Perfiles perfil = Perfiles.valueOf(partes[6].trim().toUpperCase());

					if (usuario.equals(nombreUsuario) && contrasenia.equals(contraseña)) {
						Sesion.setNombre(nombrePersona);
						Sesion.setPerfil(perfil);
						Sesion.setIdPersona(Long.parseLong(idPersona));
						return true;
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Error de lectura");
		}
		return false;
	}
	
	public static Map<Long, String> getCoordinadoresPorIdCoordMap(String ruta) {
        Map<Long, String> coordinadores = new HashMap<>();
        long contadorCoord = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 7 && partes[6].trim().equalsIgnoreCase("coordinacion")) {
                    contadorCoord++;
                    String nombre = partes[4];
                    coordinadores.put(contadorCoord, nombre);
                }
            }
        } catch (IOException e) {
            System.out.println("Error de lectura");
        }
        return coordinadores;
    }

    public static Long getIdCoordPorIdPersona(long idPersonaBuscada, String ruta) {
        long contadorCoord = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 7 && partes[6].trim().equalsIgnoreCase("coordinacion")) {
                    contadorCoord++;
                    long idPersonaActual = Long.parseLong(partes[0]);
                    if (idPersonaActual == idPersonaBuscada) {
                        return contadorCoord;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error de lectura");
        }
        return null;
    }
}
