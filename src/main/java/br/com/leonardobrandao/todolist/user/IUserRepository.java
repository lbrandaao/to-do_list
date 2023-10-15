package br.com.leonardobrandao.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


// < > é um generator. Atributos mais "dinâmicos";
// < Classe que esse repositório tá representando, tipo de ID que essa entidade tem >
public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    
    UserModel findByUsername(String username);

}
