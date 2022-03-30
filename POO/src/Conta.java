import java.util.*;

public class Conta {
	
	String login;
	String senha;
	String nome;
	String apelido;
	String descricao;
	ArrayList<String> comunidades = new ArrayList<String>();
	
	//ArrayList<String> solAmizades =  new ArrayList<String>();
	//ArrayList<String> Amizades = new ArrayList<String>();
	
	public Conta(String login, String senha, String nome) {
		// TODO Auto-generated constructor stub
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.apelido = nome;
		this.descricao = "";
	}
	
	public Conta(String login, String senha, String nome, String apelido) {
		// TODO Auto-generated constructor stub
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.apelido = apelido;
		this.descricao = "";
	}
	
	public Conta(String login, String senha, String nome, String apelido, String descricao) {
		// TODO Auto-generated constructor stub
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.apelido = apelido;
		this.descricao = descricao;
	}
	
	public int modifica(String atributo, String novoDado) {
		if(atributo.equals("senha")) {
			this.senha = novoDado;
		} else if(atributo.equals("nome")) {
			this.nome = novoDado;
		} else if(atributo.equals("apelido")) {
			this.apelido = novoDado;
		} else if(atributo.equals("descricao")) {
			this.descricao = novoDado;
		} else {
			return 0;
		}
		
		return 1;
	}
	
	public void mostrarDados() {
		System.out.println("Login: " + login);
		System.out.println("Senha: " + senha);
		System.out.println("Nome: " + nome);
		System.out.println("Apelido: " + apelido);
		System.out.println("Descricao: " + descricao);
		
		for(int i=0; i<comunidades.size(); i++) {
			System.out.println("Dono da comunidade: " + comunidades.get(i) + "\n");
		}
		
		System.out.println("Fim");
	}
	
	public void solicitaAmizade(String requerente, String amigo) {
		
	}
	
	public void addComunidade(String nomeComunidade) {
		this.comunidades.add(nomeComunidade);
	}
	
	public static void main(String[] args) {
		
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}