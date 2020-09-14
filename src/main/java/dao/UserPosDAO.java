package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.Userposjava;

public class UserPosDAO {

	private Connection connection;
	public UserPosDAO() {
		connection = SingleConnection.getConnection();
	}
	
	//SALVAR OS DADOS
	public void salvar(Userposjava userposjava) {
		try {
			//APOS CRIAR O SEQUENCIADOR O ID SERA GERADOR AUTOMATICO E NAO PRECISA INFORMAR NO SQL
			String sql = "insert into userposjava (nome, email) values (?,?)";// String DO SQL
														
			PreparedStatement insert = connection.prepareStatement(sql); // Retorna o obejto de instrução - INSERT
			//PASSANDO OS DADOS DE FORMA ESTATICA
			//insert.setLong(1,userposjava.getId()); //3 é o id no banco
			insert.setString(1, userposjava.getNome()); // Parâmetro sendo 	1 -2 SÃO AS POSIÇOES NA TABELA
			insert.setString(2, userposjava.getEmail()); 
			insert.execute();// SQL sendo excutado no banco de dados
			connection.commit();// salva no banco

		} catch (Exception e) {
			try {
				connection.rollback();// reverte operação caso tenha erros
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();

		}
	}
	
	//SALVAR TELEFONE
	public void salvarTelefone(Telefone telefone) {

		try {

			String sql = "INSERT INTO telefoneuser(numero, tipo, usuariopessoa) VALUES (?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUsuario());//A FK DO USUÁRIO DA TABELA PAI - userposjava
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}
	
	
	
	//CONSULTA
	public List<Userposjava> listar() throws Exception {
		List<Userposjava> list = new ArrayList<Userposjava>();// Lista de // retorno do
																// método

		String sql = "select * from userposjava"; // Instrução SQL na tabela userposjava
		PreparedStatement statement = connection.prepareStatement(sql);// Objeto de instrução
		ResultSet resultado = statement.executeQuery();// Executa a consulta ao // banco de dados
														

		while (resultado.next()) {// Iteramos percorrendo o objeto ResultSet que tem os dados
								
			Userposjava userposjava = new Userposjava();// Criamos um novo obejtos para cada linha
			//PEGANDO OS DADOS														
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));// Setamos os valores para o objeto
			userposjava.setEmail(resultado.getString("email"));

			list.add(userposjava); // Para cada objetos adicionamos ele na lista de retorno
								
		}

		return list;
	}
	
	//METODO QUE BUSCA APENAS UM OBJETO
	public Userposjava buscar(Long id) throws Exception {
		
		Userposjava retorno = new Userposjava();
		String sql = "select * from userposjava where id = " + id; 
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { // retorna apenas um ou nenhum

			retorno.setId(resultado.getLong("id")); 
			retorno.setNome(resultado.getString("nome"));
			retorno.setEmail(resultado.getString("email"));

		}
		return retorno;
	}

	//ATUALIZAR OS DADOS
	public void atualizar(Userposjava userposjava) {
		try {
			// Sql usando o SET para informa o nome valor
			String sql = "update userposjava set nome = ? where id = " + userposjava.getId();

			PreparedStatement statement = connection.prepareStatement(sql); // Compilando o SQL
			statement.setString(1, userposjava.getNome()); // Passando o parâmetro para  update
												
			statement.execute(); // Executando a atualização
			connection.commit(); // Comitando/Gravando no banco de dados

		} catch (Exception e) {
			try {
				connection.rollback();// Reverte caso dê algum erro
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	//DELETE
	public void deletar(Long id) {
		try {

			String sql = "delete from userposjava where id = " + id; // SQL para 																	// delete
			PreparedStatement preparedStatement = connection.prepareStatement(sql); // Compilando
			preparedStatement.execute();// Executando no banco de dados
			connection.commit();// Efetuando o commit/gravando no banco de dados

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();

		}
	}
	
	//USANDO O JOIN DAS PALAVRAS NA DAO - SELECT DE DADOS
	public List<BeanUserFone> listaUserFone(Long idUser) {

		List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();

		String sql = " select nome, numero, email from telefoneuser as fone ";
		sql += " inner join userposjava as userp ";
		sql += " on fone.usuariopessoa = userp.id ";
		sql += "where userp.id = " + idUser;

		try {

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			//ENQUANTO TIVER DADOS NA LISTA
			while (resultSet.next()) {
				BeanUserFone userFone = new BeanUserFone();

				userFone.setEmail(resultSet.getString("email"));
				userFone.setNome(resultSet.getString("nome"));
				userFone.setNumero(resultSet.getString("numero"));
                //COLOCANDO OS DADOS NA LISTA
				beanUserFones.add(userFone);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return beanUserFones;

	}
	//DELETANDO EM CASCATA
	public void deleteFonesPorUser(Long idUser) {
		try {

			String sqlFone = "delete from telefoneuser where usuariopessoa =" + idUser;
			String sqlUser = "delete from userposjava where id =" + idUser;

			PreparedStatement preparedStatement = connection.prepareStatement(sqlFone);
			preparedStatement.executeUpdate();
			connection.commit();
			
			preparedStatement = connection.prepareStatement(sqlUser);
			preparedStatement.executeUpdate();
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}
	
	
}
