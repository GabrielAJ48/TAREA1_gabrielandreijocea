package main;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import entidades.Espectaculo;
import entidades.Perfiles;
import entidades.Sesion;
import utilidades.GestorCredenciales;
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
		
		
		int op = 0;
		
		while (true) {
			
			System.out.println("Usuario: "+Sesion.getNombre());
			System.out.println("Perfil: "+Sesion.getPerfil());
			System.out.println();
		
			switch(Sesion.getPerfil()) {
			
			case INVITADO: {
				System.out.println("Escoja una opción:");
				System.out.println("1. Ver espectáculos");
				System.out.println("2. Iniciar sesión");
				System.out.println("3. Salir");
				op = leer.nextInt();
				leer.nextLine();
				
				switch(op) {
				
				case 1: GestorEspectaculos.verEspectaculos(props.getProperty("ficheroespectaculos"));
						break;
					
				case 2: if (Sesion.getPerfil()!=Perfiles.INVITADO) {
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
							boolean existe = GestorCredenciales.comprobarCredenciales(nombreUsuario, contrasenia, props.getProperty("ficherocredenciales"));
							
							if (existe) {
								
								System.out.println("Sesión iniciada como "+Sesion.getNombre()+" con perfil de "+Sesion.getPerfil());
								System.out.println();
							}
							else
	                            System.out.println("Credenciales incorrectas.");
						}
						break;
						
				case 3:	
						break;
					
				}
				break;
			}
			
			case ARTISTA: {
				System.out.println("Escoja una opción:");
				System.out.println("1. Ver espectáculos");
				System.out.println("2. Cerrar sesión");
				System.out.println("3. Ver ficha personal");
				System.out.println("4. Salir");
				op = leer.nextInt();
				
				switch(op) {
				
				}
				break;
			}
			
			case COORDINACION: {
				System.out.println("Escoja una opción:");
				System.out.println("1. Ver espectáculos");
				System.out.println("2. Crear nuevo espectáculo");
				System.out.println("3. Cerrar sesión");
				System.out.println("4. Salir");
				op = leer.nextInt();
				
				switch(op) {
				
				}
				break;
			}
			
			case ADMIN: {
				System.out.println("Escoja una opción:");
				System.out.println("1. Ver espectáculos");
				System.out.println("2. Crear nuevo espectáculo");
				System.out.println("3. Registrar persona");
				System.out.println("4. Asignar perfil y credenciales");
				System.out.println("5. Cerrar sesión");
				System.out.println("6. Salir");
				op = leer.nextInt();
				
				switch(op) {
				
				}
				break;
			}
			
			
			}
		}
}
}