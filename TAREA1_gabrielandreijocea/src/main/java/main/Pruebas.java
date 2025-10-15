package main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import entidades.Sesion;
import utilidades.GestorCredenciales;

public class Pruebas {

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

		System.out.println(Sesion.getNombre());
		System.out.println(Sesion.getPerfil());

		Scanner leer = new Scanner(System.in);
		String ruta = props.getProperty("ficherocredenciales");
		String nombreUsuario = leer.nextLine();
		String contraseña = leer.nextLine();

		System.out.println(GestorCredenciales.comprobarCredenciales(nombreUsuario, contraseña, ruta));

		System.out.println(Sesion.getNombre());
		System.out.println(Sesion.getPerfil());
	}

}
