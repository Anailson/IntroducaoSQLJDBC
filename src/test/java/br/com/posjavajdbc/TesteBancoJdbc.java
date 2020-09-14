package br.com.posjavajdbc;

import java.util.List;

import org.junit.Test;

import conexaojdbc.SingleConnection;
import dao.BeanUserFone;
import dao.Telefone;
import dao.UserPosDAO;
import model.Userposjava;

public class TesteBancoJdbc {

	@Test
	public void initBanco() {
		
		SingleConnection.getConnection(); //PEGANDO A CONEXÃO DA CLASSE
	}
	
	@Test
	public void  initBancoInsert() {
		
		UserPosDAO userPosDAO = new UserPosDAO();
		Userposjava userposjava = new Userposjava();
        //PASSANDO DE FORMA DINAMICA OS DADOS
		//userposjava.setId(6L); - APOS CRIAR O SEQUENCIADO NÃO PRECISA MAIS INFORMAR O ID
		userposjava.setNome("java analista SEQUENCIADOR 2");
		userposjava.setEmail("teste@gmail.com");
		userPosDAO.salvar(userposjava);
		
	}
	//LISTA
	@Test
	public void initListar() {
		UserPosDAO dao = new UserPosDAO();
		try {
			List<Userposjava> list = dao.listar();

			for (Userposjava userposjava : list) {
				System.out.println(userposjava);
				System.out.println("----------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//BUSCANDO POR ID OU SEJA UM OBJETO
	@Test
	public void initBuscar() {

		UserPosDAO dao = new UserPosDAO();

		try {
			Userposjava userposjava = dao.buscar(1L);

			System.out.println(userposjava);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//ATUALIZAR OS DADOS
	@Test
	public void initAtualizar() {
		try {

			UserPosDAO dao = new UserPosDAO();

			Userposjava objetoBanco = dao.buscar(6L);

			objetoBanco.setNome("Nome mudado com metodo atualizar  Java");
		
			dao.atualizar(objetoBanco);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	//DELETE POR ID
	@Test
	public void initDeletar() {

		try {

			UserPosDAO dao = new UserPosDAO();
			dao.deletar(8L); //DELETANDO O ID - 8

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	//INSER FONE
	@Test
	public void testeInsertTelefone() {

		Telefone telefone = new Telefone();
		telefone.setNumero("(61) 4445-4545");
		telefone.setTipo("Recado");
		telefone.setUsuario(9L);

		UserPosDAO dao = new UserPosDAO();
		dao.salvarTelefone(telefone);

	}
  // CARREAGAR O FONE DO USUARIO
	@Test
	public void testeCarregaFonesUser() {

		//ACESSANDO A CAMADA DE PERSISTENCIA
		UserPosDAO dao = new UserPosDAO();

		List<BeanUserFone> beanUserFones = dao.listaUserFone(9L);

		for (BeanUserFone beanUserFone : beanUserFones) {
			System.out.println(beanUserFone);
			System.out.println("---------------------------------------------");
		}

	}
	//TESTE DELETANDO EM CASCATA
	@Test
	public void testeDeleteUserFone() {

		UserPosDAO dao = new UserPosDAO();
		dao.deleteFonesPorUser(10L);

	}
	
	
	
	
}

