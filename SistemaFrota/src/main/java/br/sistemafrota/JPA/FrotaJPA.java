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
@Table(name = "frota")
public class FrotaJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "veiculo", nullable = false)
    private String veiculo;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "placa", unique = true, nullable = false)
    private String placa;

    @Column(name = "cor", nullable = false)
    private String cor;

    @Column(name = "ano", nullable = false)
    private int ano;

    @Column(name = "ipva", nullable = false)
    private Double ipva;

    @Column(name = "seguro", nullable = false)
    private Double seguro;

    @Column(name = "licenciamento", nullable = false)
    private Double licenciamento;

    @Column(name = "obrigatorio", nullable = false)
    private Double obrigatorio;

    @Column(name = "documentacao", nullable = false)
    private LocalDate documentacao;

    @Column(name = "precoaluguel", nullable = false)
    private Double precoaluguel;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    // Relacionamento Many-to-One com a entidade EmpresaJPA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresaid", nullable = false)
    private EmpresaJPA empresa;
}
