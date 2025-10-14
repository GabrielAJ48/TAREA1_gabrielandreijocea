package entidades;

public class Sesion {
	
	private static String nombre ="Invitado";
	private static Perfiles perfil = Perfiles.INVITADO;
	
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
	
	public static boolean esAdmin() {
			return perfil == Perfiles.ADMIN;
    }

    public static boolean esCoordinacion() {
        return perfil == Perfiles.COORDINACION;
    }

    public static boolean esArtista() {
        return perfil == Perfiles.ARTISTA;
    }

    public static boolean esInvitado() {
        return perfil == Perfiles.INVITADO;
    }
	
	
}
