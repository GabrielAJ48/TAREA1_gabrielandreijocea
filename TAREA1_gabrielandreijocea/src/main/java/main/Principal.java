package main;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Pattern;
import entidades.Artista;
import entidades.Coordinacion;
import entidades.Especialidades;
import entidades.Espectaculo;
import entidades.Perfiles;
import entidades.Persona;
import entidades.Sesion;
import utilidades.GestorCredenciales;
import utilidades.GestorEspectaculos;
import utilidades.GestorNacionalidades;
import utilidades.GestorRegistro;

public class Principal {

	private static Scanner leer = new Scanner(System.in);
	private static final Properties props = new Properties();

	static {
		try (InputStream input = Principal.class.getClassLoader().getResourceAsStream("application.properties")) {
			if (input == null) {
				System.out.println("No se encontró el archivo application.properties");
			} else {
				props.load(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void cerrarSesion() {
		Sesion.setNombre("INVITADO");
		Sesion.setPerfil(Perfiles.INVITADO);
		Sesion.setIdPersona(null);
		System.out.println("Sesión cerrada, ha vuelto al perfil de invitado.");
	}

	public static boolean confirmarSalida() {
		while (true) {
			System.out.println("¿Está seguro que desea salir? (S/N)");
			String input = leer.nextLine().trim().toUpperCase();
			if (input.equals("S")) {
				System.out.println("¡Adiós!");
				return true;
			} else if (input.equals("N")) {
				return false;
			} else {
				System.out.println("Opción inválida. Por favor, introduzca S o N.");
			}
		}
	}

	public static void main(String[] args) {

		GestorNacionalidades.cargarPaises(props.getProperty("ficheronacionalidades"));

		boolean confirmarSalir = false;
		int op = 0;

		do {
			System.out.println("======================================");
			System.out.println("Usuario: " + Sesion.getNombre());
			System.out.println("Perfil: " + Sesion.getPerfil());
			System.out.println("======================================");

			switch (Sesion.getPerfil()) {

			case INVITADO: {
				System.out.println("Escoja una opción:");
				System.out.println("1. Ver espectáculos");
				System.out.println("2. Iniciar sesión");
				System.out.println("3. Salir");
				try {
				    op = leer.nextInt();
				    leer.nextLine();
				} catch (InputMismatchException e) {
				    System.out.println("Entrada inválida. Introduzca un número válido.");
				    leer.nextLine();
				    op = -1;
				}

				switch (op) {
				case 1:
					GestorEspectaculos.verEspectaculos(props.getProperty("ficheroespectaculos"));
					break;
				case 2:
					if (Sesion.getPerfil() != Perfiles.INVITADO) {
						System.out.println("Ya hay una sesión activa.");
						break;
					}
					
					System.out.println("Introduzca su nombre de usuario:");
					String nombreUsuario = leer.nextLine();
					System.out.println("Introduzca su contraseña:");
					String contrasenia = leer.nextLine();
					
					if (nombreUsuario.isBlank() || contrasenia.isBlank()) {
                        System.out.println("El nombre de usuario y la contraseña no pueden estar vacíos.");
                        break;
                    }

					if (nombreUsuario.equals(props.getProperty("usuarioAdmin")) && contrasenia.equals(props.getProperty("passwordAdmin"))) {
						Sesion.setNombre("Administrador");
						Sesion.setPerfil(Perfiles.ADMIN);
						System.out.println("Sesión iniciada como " + Sesion.getNombre());
					} else {
						boolean existe = GestorCredenciales.comprobarCredenciales(nombreUsuario, contrasenia, props.getProperty("ficherocredenciales"));
						if (existe) {
							System.out.println("Sesión iniciada como " + Sesion.getNombre() + " con perfil de " + Sesion.getPerfil());
						} else {
							System.out.println("Credenciales incorrectas.");
						}
					}
					break;
				case 3:
					confirmarSalir = confirmarSalida();
					break;
				default:
					System.out.println("Escoja una opción válida (1-3)");
					break;
				}
				break;
			}

			case ARTISTA: {
				System.out.println("Escoja una opción:");
				System.out.println("1. Ver espectáculos");
				System.out.println("2. Ver ficha personal");
				System.out.println("3. Cerrar sesión");
				System.out.println("4. Salir");
				op = leer.nextInt();
				leer.nextLine();

				switch (op) {
				case 1:
					GestorEspectaculos.verEspectaculos(props.getProperty("ficheroespectaculos"));
					break;
				case 2:
					System.out.println("Funcionalidad no implementada en esta tarea.");
					break;
				case 3:
					cerrarSesion();
					break;
				case 4:
					confirmarSalir = confirmarSalida();
					break;
				default:
					System.out.println("Escoja una opción válida (1-4)");
					break;
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
				leer.nextLine();

				switch (op) {
				case 1: GestorEspectaculos.verEspectaculos(props.getProperty("ficheroespectaculos"));				    
					break;
				case 2: 
					String nombreEspectaculo;
                    String fechaini;
                    String fechafin;
					
					while (true) {
                    System.out.println("Introduzca el nombre del espectáculo:");
                    nombreEspectaculo = leer.nextLine();
                    if (GestorEspectaculos.comprobarNombreEspectaculo(nombreEspectaculo, props.getProperty("ficheroespectaculos"))) {
                        break;
                    }
                }
				
				while (true) {
                    System.out.println("Introduzca la fecha de inicio (dd/MM/yyyy):");
                    fechaini = leer.nextLine();
                    System.out.println("Introduzca la fecha de fin (dd/MM/yyyy):");
                    fechafin = leer.nextLine();
                    if (GestorEspectaculos.comprobarFechasEspectaculo(fechaini, fechafin)) {
                        break;
                    }
                }
				
				long idPersonaPropia = Sesion.getIdPersona();
                Long idCoordPropio = GestorCredenciales.getIdCoordPorIdPersona(idPersonaPropia, props.getProperty("ficherocredenciales"));
                if (idCoordPropio == null) {
                    System.out.println("No se pudo encontrar su ID de coordinador.");
                    break;
                }
                
                Espectaculo nuevoEspectaculo = new Espectaculo();
                nuevoEspectaculo.setNombre(nombreEspectaculo);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                nuevoEspectaculo.setFechaini(LocalDate.parse(fechaini, formatter));
                nuevoEspectaculo.setFechafin(LocalDate.parse(fechafin, formatter));
                
                GestorEspectaculos.crearEspectaculo(nuevoEspectaculo, idCoordPropio, props.getProperty("ficheroespectaculos"));
					break;
				case 3:
					cerrarSesion();
					break;
				case 4:
					confirmarSalir = confirmarSalida();
					break;
				default:
					System.out.println("Escoja una opción válida (1-4)");
					break;
				}
				break;
			}

			case ADMIN: {
				System.out.println("Escoja una opción:");
				System.out.println("1. Ver espectáculos");
				System.out.println("2. Crear nuevo espectáculo");
				System.out.println("3. Registrar persona");
				System.out.println("4. Cerrar sesión");
				System.out.println("5. Salir");
				op = leer.nextInt();
				leer.nextLine();

				switch (op) {
				case 1: GestorEspectaculos.verEspectaculos(props.getProperty("ficheroespectaculos"));
					break;
				case 2:
					String nombreEspectaculo;
                    String fechaStrInicio;
                    String fechaStrFin;
                    
                    while (true) {
                        System.out.println("Introduzca el nombre del espectáculo:");
                        nombreEspectaculo = leer.nextLine();
                        if (GestorEspectaculos.comprobarNombreEspectaculo(nombreEspectaculo, props.getProperty("ficheroespectaculos"))) {
                            break;
                        }
                    }
                    
                    while (true) {
                        System.out.println("Introduzca la fecha de inicio (dd/MM/yyyy):");
                        fechaStrInicio = leer.nextLine();
                        System.out.println("Introduzca la fecha de fin (dd/MM/yyyy):");
                        fechaStrFin = leer.nextLine();
                        if (GestorEspectaculos.comprobarFechasEspectaculo(fechaStrInicio, fechaStrFin)) {
                            break;
                        }
                    }
                    
                    Map<Long, String> listaCoordinadores = GestorCredenciales.getCoordinadoresPorIdCoordMap(props.getProperty("ficherocredenciales"));
                    if (listaCoordinadores.isEmpty()) {
                        System.out.println("No hay coordinadores registrados. No se puede crear un espectáculo.");
                        break;
                    }

                    System.out.println("--- Lista de Coordinadores Disponibles ---");
                    listaCoordinadores.forEach((idCoord, nombre) -> System.out.println("ID Coordinador: " + idCoord + " - Nombre: " + nombre));
                    
                    Long idCoordElegido;
                    while (true) {
                        System.out.println("Introduzca el ID del Coordinador a asignar:");
                        try {
                            idCoordElegido = Long.parseLong(leer.nextLine());
                            if (listaCoordinadores.containsKey(idCoordElegido)) {
                                break;
                            } else {
                                System.out.println("ID no encontrado en la lista de coordinadores.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Por favor, introduzca un número de ID válido.");
                        }
                    }
                    
                    Espectaculo nuevoEspectaculo = new Espectaculo();
                    nuevoEspectaculo.setNombre(nombreEspectaculo);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    nuevoEspectaculo.setFechaini(LocalDate.parse(fechaStrInicio, formatter));
                    nuevoEspectaculo.setFechafin(LocalDate.parse(fechaStrFin, formatter));
                    
                    GestorEspectaculos.crearEspectaculo(nuevoEspectaculo, idCoordElegido, props.getProperty("ficheroespectaculos"));
                    
					break;
				case 3:
					Persona persona = null;

					System.out.println("Introduzca el nombre real de la persona:");
					String nombreReal = leer.nextLine().trim();
					if (nombreReal.isEmpty()) {
						System.out.println("El nombre no puede estar vacío.");
						break;
					}

					String email;
					while (true) {
						System.out.println("Introduzca el email de la persona:");
						email = leer.nextLine().trim();
						String regex = "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$";
						if (!Pattern.matches(regex, email)) {
							System.out.println("Formato de email incorrecto. Ejemplo: nombre@dominio.com");
							continue;
						}
						if (!GestorCredenciales.comprobarEmail(email, props.getProperty("ficherocredenciales"))) {
							System.out.println("El email introducido ya está asociado a otro usuario.");
							continue;
						}
						break;
					}

					System.out.println("Lista de países:");
					GestorNacionalidades.mostrarPaises();
					String nacionalidad;
					while (true) {
						System.out.println("Introduzca el ID del país de la persona:");
						String idNacionalidad = leer.nextLine().trim();
						if (GestorNacionalidades.comprobarNacionalidad(idNacionalidad)) {
							nacionalidad = GestorNacionalidades.obtenerNombreCompleto(idNacionalidad);
							break;
						} else {
							System.out.println("ID de país incorrecto. Intente de nuevo.");
						}
					}
					
					char tipoPersona;
					while(true) {
					    System.out.println("¿Es un coordinador? (S/N):");
					    String inputTipo = leer.nextLine().trim().toUpperCase();
					    if (inputTipo.length() == 1 && (inputTipo.charAt(0) == 'S' || inputTipo.charAt(0) == 'N')) {
					        tipoPersona = inputTipo.charAt(0);
					        break;
					    } else {
					        System.out.println("Opción inválida. Debe escoger S (Sí) o N (No).");
					    }
					}

					if (tipoPersona == 'S') {
						Coordinacion c = new Coordinacion();
						c.setNombre(nombreReal);
						c.setEmail(email);
						c.setNacionalidad(nacionalidad);
						
						boolean esSenior = false;
						while (true) {
							System.out.print("¿Es senior? (S/N): ");
							String inputSenior = leer.nextLine().trim().toUpperCase();
							if (inputSenior.equals("S")) {
								esSenior = true;
								break;
							} else if (inputSenior.equals("N")) {
								esSenior = false;
								break;
							} else {
								System.out.println("Opción inválida. Por favor, introduzca S o N.");
								
							}
						}


						if (esSenior) {
							c.setSenior(true);
							LocalDate fechaSenior = null;
							while (true) {
								System.out.print("¿Desde qué fecha es senior? (dd/MM/yyyy): ");
								String fechaStr = leer.nextLine().trim();
								if (fechaStr.isBlank()) {
									System.out.println("La fecha no puede estar vacía.");
									continue;
								}
								try {
									DateTimeFormatter seniorFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
									fechaSenior = LocalDate.parse(fechaStr, seniorFormatter);
									if (fechaSenior.isAfter(LocalDate.now())) {
										System.out.println("La fecha no puede ser futura.");
									} else {
										c.setFechasenior(fechaSenior);
										break; 
									}
								} catch (DateTimeParseException e) {
									System.out.println("❌ Formato de fecha inválido. Use dd/MM/yyyy.");
								}
							}
						}
						persona = c;

					} else {
						Artista a = new Artista();
						a.setNombre(nombreReal);
						a.setEmail(email);
						a.setNacionalidad(nacionalidad);

						boolean tieneApodo = false;
						while (true) {
							System.out.print("¿Tiene apodo artístico? (S/N): ");
							String inputApodo = leer.nextLine().trim().toUpperCase();
							if (inputApodo.equals("S")) {
								tieneApodo = true;
								break;
							} else if (inputApodo.equals("N")) {
								tieneApodo = false;
								break;
							} else {
								System.out.println("Opción inválida. Por favor, introduzca S o N.");
							}
						}
						
						if (tieneApodo) {
							System.out.print("Introduzca el apodo: ");
							String apodo = leer.nextLine().trim();
							if (!apodo.isBlank()) {
								a.setApodo(apodo);
							} else {
								System.out.println("Apodo vacío, no se asignará.");
							}
						}

						do {
							System.out.println("Especialidades disponibles: ACROBACIA, HUMOR, MAGIA, EQUILIBRISMO, MALABARISMO");
							System.out.println("Introduzca las especialidades separadas por comas (Ej: ACROBACIA,HUMOR):");
							String entrada = leer.nextLine().trim();
							String[] partes = entrada.split(",");
							for (String p : partes) {
								try {
									Especialidades esp = Especialidades.valueOf(p.trim().toUpperCase());
									a.addEspecialidad(esp);
								} catch (IllegalArgumentException e) {
									if (!p.isEmpty())
										System.out.println(p.trim() + " no es una especialidad válida y será ignorada.");
								}
							}
							if (a.getEspecialidades().isEmpty())
								System.out.println("Debe introducir al menos una especialidad válida.");
						} while (a.getEspecialidades().isEmpty());
						persona = a;
					}

					String nombreUsuario;
					while (true) {
						System.out.println("Introduzca el nombre de usuario:");
						nombreUsuario = leer.nextLine().trim();
						if (GestorCredenciales.comprobarUsuario(nombreUsuario, props.getProperty("ficherocredenciales"))) {
							break;
						}
					}

					String contrasenia;
					while (true) {
						System.out.println("Introduzca la contraseña:");
						contrasenia = leer.nextLine().trim();
						if (GestorCredenciales.comprobarContrasenia(contrasenia, props.getProperty("ficherocredenciales"))) {
							break;
						}
					}

					String perfil;
					if (persona instanceof Coordinacion) {
						perfil = "COORDINACION";
					} else {
						perfil = "ARTISTA";
					}

					GestorRegistro.registrarPersona(persona, nombreUsuario, contrasenia, perfil, props.getProperty("ficherocredenciales"));
					System.out.println("Persona registrada correctamente.");
					break;

				case 4:
					cerrarSesion();
					break;
				case 5:
					confirmarSalir = confirmarSalida();
					break;
				default:
					System.out.println("Escoja una opción válida (1-5)");
					break;
				}
				break;
			}
			}
		} while (!confirmarSalir);
		
		leer.close();
	}
}