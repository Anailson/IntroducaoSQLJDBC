package conexaojdbc;
import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {
	
	private static String url = "jdbc:postgresql://localhost:5432/posjava";	//Url do banco de dados
	private static String password = "admin";//Senha do banco de dados
	private static String user = "postgres";	//User do banco de dados
	private static Connection connection = null;// Classe de conexão com o banco de dados
	
	static {
		conectar();
	}
	
	public SingleConnection() {
	 conectar();
	}
	private static void conectar(){	//CONEXÃO É FEITA APENAS UMA VEZ - QUE ABRE E FECHA SÃO AS SESSÕES
		try {
		
			if (connection == null){	// Verifica se já existe a conexão
				
			
				Class.forName("org.postgresql.Driver");	/// Registra o driver do banco de dados - CASO FOSSE MSYQL É OUTRO
				
				// Faz a conexão com o banco de dados
				connection = DriverManager.getConnection(url, user, password);
				
				// Configuração para não commitar automaticamente os dados no banco de dados
				connection.setAutoCommit(false);
				
				System.out.println("Conectou com sucesso");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		return connection;
	}

}
