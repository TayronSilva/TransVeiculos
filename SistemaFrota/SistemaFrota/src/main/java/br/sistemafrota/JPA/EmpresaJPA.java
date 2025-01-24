package br.sistemafrota.JPA;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "empresa")
public class EmpresaJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "empresa", nullable = false)
    private String empresa;

    @Column(name = "cnpj", length = 14, unique = true, nullable = false)
    private String cnpj;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Transient
    private String confirmarSenha;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<FrotaJPA> frotas = new ArrayList<>();
}
