import java.util.*;

public class Operations {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Conta> users = new ArrayList<Conta>();
		ArrayList<Comunidade> comunidades = new ArrayList<Comunidade>();
        String choice = null;
        Scanner scan = new Scanner(System.in).useDelimiter("\n");
        Scanner scanUser = new Scanner(System.in).useDelimiter("\n");
        
        //Estrutura
        String login;
    	String senha;
    	String nome;
    	String apelido;
    	String descricao;
        
        do {
        	System.out.println("Opcoes: ");
            System.out.println("a: Criar conta");
            System.out.println("b: Editar perfil");
            System.out.println("c: Criar comunidade");
            System.out.println("d: Mostrar dados");
            System.out.println("q: Quit");
            choice = scan.nextLine();
            switch (choice) {
            case "a":
            	
            	System.out.printf("Insira o login: ");
            	            	
            	login = scanUser.next();
            	
            	for (Conta usuario : users) {
        			if(usuario.getLogin() == login) {
        				System.out.println("Login existente");
        				break;
        			}
        		} 
            	
            	System.out.printf("Insira a senha: ");
            	senha = scanUser.next();
            	
            	
            	System.out.printf("Insira o nome: ");
            	nome = scanUser.next();
            	
            	
            	System.out.printf("Insira o apelido: ");
            	apelido = scanUser.next();
            	
            	System.out.printf("Insira a descricao: ");
            	descricao = scanUser.next();
            	
            	Conta a = new Conta(login, senha, nome, apelido, descricao);
        		users.add(a);
        		
        		System.out.printf("Conta criada: %s\n", login);
        	
            	//Conta a = new Conta("arthur", "123456", "arthurlps", "apelido", "descricao");
        		//users.add(a);
            	break;
            case "b":
            	System.out.printf("Insira o login: ");
            	login = scanUser.next();
            	
            	for (Conta usuario : users) {
            		//usuario.mostrarDados();
        			if(usuario.getLogin().equals(login)) {
        				System.out.println("Login existente, entre com a senha: ");
        				senha = scanUser.next();
        				
        				if(usuario.getSenha().equals(senha)) {
        					System.out.println("Senha correta, entre com o que deseja modificar: (minusculo e sem acento)");
        					String atributo = scanUser.next();
        					
        					System.out.println("Entre com o novo valor do atributo: ");
        					String novoDado = scanUser.next();
        					
        					if(usuario.modifica(atributo, novoDado) == 1) {
        						System.out.println("Atributo modificado\n");
        					} else{
        						System.out.println("Atributo inexistente\n");
        					}
        				} else {
        					System.out.println("Senha incorreta, xau");
        				}
        				
        				System.out.println("Seus dados pessoais: ");
        				usuario.mostrarDados();
        				break;
        			} else {
        				System.out.println("Login inexistente");
        			}
            	}
            	break;
            case "c":
            	System.out.printf("Insira o login (se for inexistente volta ao menu): ");
            	login = scanUser.next();
            	
            	for (Conta usuario : users) {
            		//usuario.mostrarDados();
        			if(usuario.getLogin().equals(login)) {
        				System.out.println("Login existente, entre com a senha: ");
        				senha = scanUser.next();
        				
        				if(usuario.getSenha().equals(senha)) {
        					System.out.println("Senha correta, insira um nome para a comunidade: ");
        					nome = scanUser.next();
        					
        					System.out.println("Entre com a descricao para a comunidade: ");
        					descricao = scanUser.next();
        					int status = 0;
        					for (Comunidade comunidade : comunidades) {
        						if(comunidade.getNome().equals(nome)) {
        							System.out.println("Comunidade ja existente\n");
        							status = 1;
        							break;
        						}
        					}
        					
        					if(status == 0) {
        						Comunidade b = new Comunidade(nome, descricao, login);
        						comunidades.add(b);
        						
        						usuario.addComunidade(nome);
        						System.out.println("Comunidade criada\n");
        						
        					}
        				} else {
        					System.out.println("Senha incorreta, xau");
        				}
        				
        				//usuario.mostrarDados();
        				break;
        			}
            	}
            	break;
            case "d":
            	
            	System.out.printf("Insira o login (se for inexistente volta ao menu): ");
            	login = scanUser.next();
            	
            	for (Conta usuario : users) {
            		//usuario.mostrarDados();
        			if(usuario.getLogin().equals(login)) {
        				System.out.println("Login existente, entre com a senha: ");
        				senha = scanUser.next();
        				
        				if(usuario.getSenha().equals(senha)) {
        					System.out.println("Senha correta");
        					usuario.mostrarDados();
        				} else {
        					System.out.println("Senha incorreta");
        				}        				        			        					
        			}
            	}	
            }
        } while (!choice.equals("q")); // end of loop
        
        scan.close();
    	scanUser.close();
	}
	
	
}
