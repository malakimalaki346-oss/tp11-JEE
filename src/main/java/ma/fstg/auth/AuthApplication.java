package ma.fstg.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
		System.out.println("🚀 Application d'authentification démarrée avec succès!");
		System.out.println("📍 Accédez à: http://localhost:8080/login");
		System.out.println("👤 Compte admin: malak_admin / Admin123!");
		System.out.println("👥 Compte user: malak_user / User123!");
	}
}