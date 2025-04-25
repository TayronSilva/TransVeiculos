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
@Table(name = "realizados")
public class RealizadoJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "planejadoid")
    private PlanejadoJPA planejado;

    @ManyToOne
    @JoinColumn(name = "motoristaid")
    private MotoristaJPA motorista;

    @ManyToOne
    @JoinColumn(name = "frotaid")
    private FrotaJPA frota;

    @ManyToOne
    @JoinColumn(name = "empresaid")
    private EmpresaJPA empresa;

    @Column(name = "realizado")
    private boolean realizado;

    @Column(name = "cancelado")
    private boolean cancelado;

    @Column(name = "data_inicial")
    private LocalDate dataInicial;

    @Column(name = "data_cancelamento")
    private LocalDate dataCancelamento;

    @Column(name = "data_realizacao")
    private LocalDate dataRealizacao;

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    public LocalDate getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(LocalDate dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public LocalDate getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(LocalDate dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }
}
