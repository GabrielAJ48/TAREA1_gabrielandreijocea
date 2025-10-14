package main;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import entidades.Espectaculo;
import entidades.Perfiles;
import entidades.Sesion;
import utilidades.GestorEspectaculos;

public class Principal {
	
	private static Scanner leer = new Scanner(System.in);
	
	private static final Properties props = new Properties();
	
	 static {
	        try (InputStream input = Principal.class.getClassLoader().getResourceAsStream("application.properties")) {
	            if (input == null) {
	                System.out.println("No se encontró el archivo application.properties");
	            } else {
	                props.load(input); //
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	public static void main(String[] args) {
		
		Perfiles perfil = Sesion.getPerfil();
		int op = 0;
		
		switch(perfil) {
		
		case INVITADO: {
			System.out.println("1. Ver espectáculos");
			System.out.println("2. Iniciar sesión");
			System.out.println("3. Salir");
			System.out.println("Escoja una opción:");
			op = leer.nextInt();
			
			switch(op) {
			
			case 1: GestorEspectaculos.verEspectaculos(props.getProperty("ficheroespectaculos"));
				
			case 2: if (perfil!=Perfiles.INVITADO) {
						System.out.println("Ya hay una sesión activa");
						break;
					}
					System.out.println("Introduzca su nombre de usuario: ");
					String nombreUsuario = leer.nextLine();
					System.out.println("Introduzca su contraseña");
					String contrasenia = leer.nextLine();
					
					if (nombreUsuario.equals(props.getProperty("usuarioAdmin")) && contrasenia.equals(props.getProperty("passwordAdmin"))) {
						Sesion.setNombre("Administrador");
						Sesion.setPerfil(Perfiles.ADMIN);
						System.out.println("Sesión iniciada como "+Sesion.getNombre());
					}
					else {
						
					}
				
			case 3:
				
				
			}
		}
		
		case ARTISTA: {
			System.out.println("1. Ver espectáculos");
			System.out.println("2. Cerrar sesión");
			System.out.println("3. Ver ficha personal");
			System.out.println("4. Salir");
			System.out.println("Escoja una opción:");
			op = leer.nextInt();
			
			switch(op) {
			
			}
		}
		
		case COORDINACION: {
			System.out.println("1. Ver espectáculos");
			System.out.println("2. Crear nuevo espectáculo");
			System.out.println("3. Cerrar sesión");
			System.out.println("4. Salir");
			System.out.println("Escoja una opción:");
			op = leer.nextInt();
			
			switch(op) {
			
			}
		}
		
		case ADMIN: {
			System.out.println("1. Ver espectáculos");
			System.out.println("2. Crear nuevo espectáculo");
			System.out.println("3. Registrar persona");
			System.out.println("4. Asignar perfil y credenciales");
			System.out.println("5. Cerrar sesión");
			System.out.println("6. Salir");
			System.out.println("Escoja una opción:");
			op = leer.nextInt();
			
			switch(op) {
			
			}
		}
		
		}
	
}
}