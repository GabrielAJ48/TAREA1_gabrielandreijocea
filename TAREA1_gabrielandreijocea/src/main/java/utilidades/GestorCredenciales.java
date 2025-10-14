package utilidades;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entidades.Perfiles;
import entidades.Sesion;

public class GestorCredenciales {

	private static boolean comprobarCredenciales(String nombreUsuario, String contraseña, String ruta) {
	    List<String> lineas = new ArrayList<>();

	    try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
	        String linea;
	        while ((linea = br.readLine()) != null) {
	            lineas.add(linea);
	        }

	        for (String l : lineas) {
	            String[] partes = l.split("\\|");
	            if (partes.length == 7) {
	                String usuario = partes[1];
	                String contrasenia = partes[2];
	                String nombrePersona = partes[4];
	                Perfiles perfil = Perfiles.valueOf(partes[6]);

	                if (usuario.equals(nombreUsuario) && contrasenia.equals(contraseña)) {
	                    Sesion.setNombre(nombrePersona);
	                    Sesion.setPerfil(perfil);
	                    return true;
	                }
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error de lectura: " + e.getMessage());
	    }
	    return false;
	}
}
