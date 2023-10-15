package br.com.leonardobrandao.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Annotation do lombok: adiciona os getters e setters. Se quiser só em alguns atributos, colocar
 * annotation em cima do atributo. Se quiser só getter, @Getter, setter, @Setter;
 */
@Data
@Entity(name = "tb_user") // Annotation pra fazer o mapeamento do objeto pra tabela do BD
public class UserModel {
    
    @Id // Annotation pra ser a chave primária da tabela
    @GeneratedValue(generator = "UUID") // Annotation pra definir que o JPA que vai gerenciar esse ID;
    private UUID idUser;

    // @Column(name = "nome_de_usuario") Annotation pra definir que esse atributo vai ser mapeado pra coluna "nome_de_usuario"
    @Column(unique = true)
    private String username;
    private String name;
    private String password;

    @CreationTimestamp // Banco de dados vai saber quando foi criado 
    private LocalDateTime createdAt;

}
