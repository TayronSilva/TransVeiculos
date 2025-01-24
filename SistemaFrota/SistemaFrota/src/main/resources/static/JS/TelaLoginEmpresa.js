document.addEventListener('DOMContentLoaded', function () {
    const formLogin = document.querySelector('form[action="/login"]');
    formLogin.addEventListener('submit', function (event) {
        event.preventDefault(); // Impede o envio automático

        const cnpj = document.getElementById("cnpj").value.trim();
        const senha = document.getElementById("senha").value.trim();

        // Verificar se o CNPJ está vazio
        if (cnpj === "") {
            Swal.fire({
                icon: 'error',
                title: 'Erro!',
                text: 'O CNPJ não pode estar vazio.',
                confirmButtonColor: '#511A79'
            });
            return;
        }

        // Verificar se a senha está vazia
        if (senha === "") {
            Swal.fire({
                icon: 'error',
                title: 'Erro!',
                text: 'A senha não pode estar vazia.',
                confirmButtonColor: '#511A79'
            });
            return;
        }

        // Enviar o formulário se tudo estiver correto
        formLogin.submit();
    });
});
