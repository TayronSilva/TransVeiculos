function toggleLogin() {
    var modal = document.getElementById("loginModal");
    modal.style.display = "flex";
}

function closeModal(event) {
    var modal = document.getElementById("loginModal");
    if (event.target === modal) {
        modal.style.display = "none";
    }
}

function registrar() {
    Swal.fire({
        icon: 'info',
        title: 'Redirecionando...',
        text: 'Você será redirecionado para a página de registro.',
        showConfirmButton: false,
        timer: 2000
    }).then(() => {
        window.location.href = "/cadastro/empresa";
    });
}

function login() {
    Swal.fire({
        icon: 'info',
        title: 'Fazendo login...',
        text: 'Você será redirecionado para a página de login.',
        showConfirmButton: false,
        timer: 2000
    }).then(() => {
        window.location.href = "/login";
    });
}
