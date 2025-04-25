document.addEventListener('DOMContentLoaded', function() {
    carregarPlanejados();
    carregarMotoristas();
    carregarFrotas();
});

function carregarPlanejados() {
    fetch('/api/planejados/disponiveis')
        .then(response => response.json())
        .then(planejados => {
            const select = document.querySelector('select[name="planejadoId"]');
            planejados.forEach(planejado => {
                const option = document.createElement('option');
                option.value = planejado.id;
                option.textContent = `${planejado.origem} → ${planejado.destino}`;
                select.appendChild(option);
            });
        })
        .catch(error => console.error('Erro ao carregar fretes planejados:', error));
}

function carregarMotoristas() {
    fetch('/api/motoristas/disponiveis')
        .then(response => response.json())
        .then(motoristas => {
            const select = document.querySelector('select[name="motoristaId"]');
            motoristas.forEach(motorista => {
                const option = document.createElement('option');
                option.value = motorista.id;
                option.textContent = motorista.nome;
                select.appendChild(option);
            });
        })
        .catch(error => console.error('Erro ao carregar motoristas:', error));
}

function carregarFrotas() {
    fetch('/api/frotas/disponiveis')
        .then(response => response.json())
        .then(frotas => {
            const select = document.querySelector('select[name="frotaId"]');
            frotas.forEach(frota => {
                const option = document.createElement('option');
                option.value = frota.id;
                option.textContent = `${frota.veiculo} - ${frota.placa}`;
                select.appendChild(option);
            });
        })
        .catch(error => console.error('Erro ao carregar frotas:', error));
}

// Confirmação antes de concluir frete
document.querySelectorAll('.btn-concluir').forEach(button => {
    button.addEventListener('click', function(e) {
        e.preventDefault();
        const form = this.closest('form');
        
        Swal.fire({
            title: 'Confirmar conclusão',
            text: "Deseja realmente concluir este frete?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#4CAF50',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sim, concluir',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                form.submit();
            }
        });
    });
});

function confirmarConclusao(form) {
    Swal.fire({
        title: 'Confirmar conclusão',
        text: "Deseja realmente concluir este frete?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#28a745',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sim, concluir',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            // Enviar o formulário de conclusão
            form.submit();
        }
    });
} 