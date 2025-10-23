package entidades;

public class Sesion {
	
	private static String nombre ="Invitado";
	private static Perfiles perfil = Perfiles.INVITADO;
	private static Long idPersona = null;
	
	public Sesion() {
		
	}

	public Sesion(String nombre, Perfiles perfil) {
		Sesion.nombre = nombre;
		Sesion.perfil = perfil;
	}

	public static String getNombre() {
		return nombre;
	}

	public static void setNombre(String nombre) {
		Sesion.nombre = nombre;
	}

	public static Perfiles getPerfil() {
		return perfil;
	}

	public static void setPerfil(Perfiles perfil) {
		Sesion.perfil = perfil;
	}
	
	public static Long getIdPersona() {
		return idPersona;
	}

	public static void setIdPersona(Long idPersona) {
		Sesion.idPersona = idPersona;
	}
}
