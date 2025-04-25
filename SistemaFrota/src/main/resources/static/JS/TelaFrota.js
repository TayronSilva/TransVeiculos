document.getElementById('veiculo-form').addEventListener('submit', function(event) {
    event.preventDefault();  // Impede o envio automático do formulário

    const form = event.target;
    const tipoVeiculo = form.veiculo.value.trim();
    const modelo = form.modelo.value.trim();
    const placa = form.placa.value.trim();
    const cor = form.cor.value.trim();
    const ano = parseInt(form.ano.value);
    const ipva = parseFloat(form.ipva.value);
    const seguro = parseFloat(form.seguro.value);
    const licenciamento = parseFloat(form.licenciamento.value);
    const obrigatorio = parseFloat(form.obrigatorio.value);
    const precoaluguel = parseFloat(form.precoaluguel.value);
    const documentacao = form.documentacao.value;
    const hoje = new Date().toISOString().split('T')[0];
    const status = form.status.value;

    // Validações básicas de campos obrigatórios
    if (!tipoVeiculo || !modelo || !placa || !cor || isNaN(ano) || !status || !precoaluguel) {
        Swal.fire({
            icon: 'error',
            title: 'Erro!',
            text: 'Por favor, preencha todos os campos obrigatórios.',
        });
        return;
    }

    // Validação do ano
    if (ano < 1900 || ano > new Date().getFullYear()) {
        Swal.fire({
            icon: 'error',
            title: 'Erro!',
            text: 'Ano inválido!',
        });
        return;
    }

    // Validação de valores monetários
    if (ipva < 0 || seguro < 0 || licenciamento < 0 || obrigatorio < 0 || precoaluguel <= 0) {
        Swal.fire({
            icon: 'error',
            title: 'Erro!',
            text: 'Todos os valores monetários devem ser positivos.',
        });
        return;
    }

    // Validação da data da documentação
    if (!documentacao) {
        Swal.fire({
            icon: 'error',
            title: 'Erro!',
            text: 'Por favor, insira uma data válida para a documentação.',
        });
        return;
    }

    if (documentacao < hoje) {
        Swal.fire({
            icon: 'error',
            title: 'Erro!',
            text: 'A data da documentação não pode ser anterior à data atual.',
        });
        return;
    }

    // Se todas as validações passarem, exibe mensagem de sucesso
    Swal.fire({
        icon: 'success',
        title: 'Sucesso!',
        text: 'Veículo cadastrado com sucesso!',
    }).then(() => {
        // Envia o formulário após a confirmação do usuário
        form.submit();
    });
});
