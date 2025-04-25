document.getElementById("form-cadastro").onsubmit = function (evento) {
    evento.preventDefault(); // Previne o envio padrão do formulário

    const form = this; // Referência ao formulário
    const formData = new FormData(form);

    // Validações de campos
    const nome = formData.get("nome");
    const endereco = formData.get("endereco");
    const telefone = formData.get("telefone");
    const identidade = formData.get("identidade");
    const habilitacao = formData.get("habilitacao");
    const precoHora = formData.get("precoHora");

    const validarSomenteLetras = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]+$/;
    const validarEndereco = /^[A-Za-zÀ-ÖØ-öø-ÿ0-9\s,.-]+$/;

    if (!nome || !validarSomenteLetras.test(nome)) {
        Swal.fire({
            title: "Erro!",
            text: "O campo Nome deve conter somente letras.",
            icon: "error",
            confirmButtonText: "OK",
        });
        return;
    }

    if (!endereco || !validarEndereco.test(endereco)) {
        Swal.fire({
            title: "Erro!",
            text: "O campo Endereço deve conter um endereço válido.",
            icon: "error",
            confirmButtonText: "OK",
        });
        return;
    }

    if (!telefone || telefone.length < 11) {
        Swal.fire({
            title: "Erro!",
            text: "O campo Telefone está incompleto.",
            icon: "error",
            confirmButtonText: "OK",
        });
        return;
    }

    if (!identidade || !/^[0-9]+$/.test(identidade)) {
        Swal.fire({
            title: "Erro!",
            text: "O campo Identidade deve conter somente números.",
            icon: "error",
            confirmButtonText: "OK",
        });
        return;
    }

    if (!habilitacao || !/^[0-9]+$/.test(habilitacao)) {
        Swal.fire({
            title: "Erro!",
            text: "O campo Habilitação deve conter somente números.",
            icon: "error",
            confirmButtonText: "OK",
        });
        return;
    }

    /*if (!precoHora || isNaN(Number(precoHora)) || Number(precoHora) <= 0) {
        Swal.fire({
            title: "Erro!",
            text: "O campo Preço Hora deve conter um valor numérico positivo.",
            icon: "error",
            confirmButtonText: "OK",
        });
        return;
    } */

    fetch("/cadastro/motorista", {
        method: "POST",
        headers: { Accept: "application/json" },
        body: formData,
    })
        .then(async (response) => {
            const contentType = response.headers.get("Content-Type");

            if (!contentType || !contentType.includes("application/json")) {
                throw new Error("Resposta inesperada do servidor: Esperado JSON.");
            }

            const data = await response.json();

            if (response.ok && data.success) {
                Swal.fire({
                    title: "Sucesso!",
                    text: "Motorista cadastrado com sucesso!",
                    icon: "success",
                    confirmButtonText: "OK",
                }).then(() => {
                    window.location.href = "/principal"; // Redireciona para a página principal
                });
            } else {
                throw new Error(data.message || "Erro ao cadastrar motorista.");
            }
        })
        .catch((error) => {
            console.error("Erro ao enviar formulário:", error);

            Swal.fire({
                title: "Erro!",
                text: error.message || "Houve um erro ao enviar os dados. Por favor, tente novamente.",
                icon: "error",
                confirmButtonText: "OK",
            });
        });
};