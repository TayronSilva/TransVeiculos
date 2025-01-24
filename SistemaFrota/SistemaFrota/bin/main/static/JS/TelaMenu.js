// Funções para manipulação do menu

function toggleMenu() {
  document.getElementById('menuModal').style.display = 'block';
}

function closeModal(event) {
  if (event.target.className === 'modal') {
      document.getElementById('menuModal').style.display = 'none';
  }
}

// Funções de navegação
function cadastrarFrota() {
  window.location.href = '/cadastro/frota';
}

function cadastrarMotorista() {
  window.location.href = '/cadastro/motorista';
}

function cadastrarPlanejado() {
  window.location.href = '/cadastro/planejado';
}

function gerenciarFretes() {
  window.location.href = '/fretes';
}

function entrarPerfil() {
  window.location.href = '/perfil';
}

function logout() {
  window.location.href = '/logout';
}