package main;

import entidades.Perfiles;
import entidades.Sesion;

public class Principal {

	public static void main(String[] args) {
		
		Perfiles perfil = Sesion.getPerfil();
		
		switch(perfil) {
		
		case INVITADO: {
			System.out.println("1. Ver espectáculos");
			System.out.println("2. Iniciar sesión");
			System.out.println("3. Salir");
		}
		
		case ARTISTA: {
			System.out.println("1. Ver espectáculos");
			System.out.println("2. Cerrar sesión");
			System.out.println("3. Ver ficha personal");
			System.out.println("4. Salir");
		}
		
		case COORDINACION: {
			System.out.println("1. Ver espectáculos");
			System.out.println("2. Crear nuevo espectáculo");
			System.out.println("3. Cerrar sesión");
			System.out.println("4. Salir");
		}
		
		case ADMIN: {
			System.out.println("1. Ver espectáculos");
			System.out.println("2. Crear nuevo espectáculo");
			System.out.println("3. Registrar persona");
			System.out.println("4. Asignar perfil y credenciales");
			System.out.println("5. Cerrar sesión");
			System.out.println("6. Salir");
		}
		
		}
	
}
}