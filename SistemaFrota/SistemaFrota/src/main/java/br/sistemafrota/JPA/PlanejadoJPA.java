package br.sistemafrota.JPA;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "planejado")
public class PlanejadoJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origem", nullable = false)
    private String origem;

    @Column(name = "destino", nullable = false)
    private String destino;

    @Column(name = "volume", nullable = false)
    private Double volume;

    @Column(name = "peso", nullable = false)
    private Double peso;

    @Column(name = "inicio", nullable = false)
    private String inicio;

    @Column(name = "fim", nullable = false)
    private String fim;

    @Column(name = "preco", nullable = false)
    private Double preco;

    @Column(name = "contratado", nullable = false)
    private Boolean contratado = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresaid")
    private EmpresaJPA empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motoristaid")
    private MotoristaJPA motorista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frotaid")
    private FrotaJPA frota;
}