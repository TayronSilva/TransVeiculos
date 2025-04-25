document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("formCadastroEmpresa");
    const nomeInput = document.getElementById("empresa");
    const emailInput = document.getElementById("email");
    const cnpjInput = document.getElementById("cnpj");
    const senhaInput = document.getElementById("senha");
    const confirmarSenhaInput = document.getElementById("confirmarSenha");
    const toggleSenha = document.getElementById("toggleSenha");
    const toggleConfirmarSenha = document.getElementById("toggleConfirmarSenha");

    form.addEventListener("submit", function (event) {
        let isValid = true;
        clearErrors();

        if (nomeInput.value.trim() === "") {
            showError(nomeInput, "O nome da empresa é obrigatório.");
            isValid = false;
        }

        if (emailInput.value.trim() === "") {
            showError(emailInput, "O e-mail da empresa é obrigatório.");
            isValid = false;
        }

        if (cnpjInput.value.trim() === "") {
            showError(cnpjInput, "O CNPJ é obrigatório.");
            isValid = false;
        }

        if (senhaInput.value.trim() === "") {
            showError(senhaInput, "A senha é obrigatória.");
            isValid = false;
        }

        if (senhaInput.value !== confirmarSenhaInput.value) {
            showError(confirmarSenhaInput, "As senhas não coincidem.");
            isValid = false;
        }

        if (!isValid) {
            event.preventDefault();
            SweetAlertError("Erro no Cadastro", "Verifique os campos e tente novamente.");
        } else {
            SweetAlertSuccess("Cadastro Realizado", "Cadastro da empresa realizado com sucesso!");
        }
    });

    toggleSenha.addEventListener("click", () => togglePasswordVisibility(senhaInput, toggleSenha));
    toggleConfirmarSenha.addEventListener("click", () => togglePasswordVisibility(confirmarSenhaInput, toggleConfirmarSenha));

    function togglePasswordVisibility(input, toggleIcon) {
        if (input.type === "password") {
            input.type = "text";
            toggleIcon.classList.remove("fa-eye");
            toggleIcon.classList.add("fa-eye-slash");
        } else {
            input.type = "password";
            toggleIcon.classList.remove("fa-eye-slash");
            toggleIcon.classList.add("fa-eye");
        }
    }

    function SweetAlertError(title, text) {
        Swal.fire({
            icon: "error",
            title: title,
            text: text,
            confirmButtonColor: "#511A79",
        });
    }

    function SweetAlertSuccess(title, text) {
        Swal.fire({
            icon: "success",
            title: title,
            text: text,
            confirmButtonColor: "#511A79",
        });
    }

    function showError(input, message) {
        const error = document.createElement("span");
        error.classList.add("error");
        error.style.color = "red";
        error.textContent = message;
        input.parentElement.appendChild(error);
    }

    function clearErrors() {
        document.querySelectorAll(".error").forEach((el) => el.remove());
    }
});
