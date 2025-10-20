package entidades;

public class Credenciales {
	
	private Long id;
	private String nombreUsuario;
	private String password;
	private Perfiles perfil;
	
	public Credenciales() {
		
	}

	public Credenciales(Long id, String nombre, String password, Perfiles perfil) {
		super();
		this.id = id;
		this.nombreUsuario = nombre;
		this.password = password;
		this.perfil = perfil;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombre) {
		this.nombreUsuario = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Perfiles getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfiles perfil) {
		this.perfil = perfil;
	}
	
	
	
}
