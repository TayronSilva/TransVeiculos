package br.sistemafrota.JPA;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "motorista")
public class MotoristaJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "identidade", unique = true, nullable = false)
    private String identidade;

    @Column(name = "habilitacao", unique = true, nullable = false)
    private String habilitacao;

    @Column(name = "precohora", nullable = false)
    private Double precohora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresaid")
    private EmpresaJPA empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frotaid")
    private FrotaJPA frota;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;
}

