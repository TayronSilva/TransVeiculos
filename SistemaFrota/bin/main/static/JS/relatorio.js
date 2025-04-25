let myChart = null;
let currentData = null;

// Função para inicializar o gráfico
function initChart(data) {
    currentData = data;
    const ctx = document.getElementById('statsChart').getContext('2d');
    
    if (myChart) {
        myChart.destroy();
    }

    myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: data.labels,
            datasets: [
                {
                    label: 'Frotas',
                    data: data.frotas,
                    borderColor: '#511A79',
                    tension: 0.1
                },
                {
                    label: 'Motoristas',
                    data: data.motoristas,
                    borderColor: '#28a745',
                    tension: 0.1
                },
                {
                    label: 'Entregas',
                    data: data.entregas,
                    borderColor: '#ffc107',
                    tension: 0.1
                }
            ]
        },
        options: {
            responsive: true,
            interaction: {
                intersect: false,
                mode: 'index'
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

// Função para atualizar dados do gráfico
function updateChart(type) {
    const buttons = document.querySelectorAll('.chart-toggle');
    buttons.forEach(btn => btn.classList.remove('active'));
    document.querySelector(`[data-type="${type}"]`).classList.add('active');

    if (type === 'all') {
        myChart.data.datasets.forEach(dataset => dataset.hidden = false);
    } else {
        myChart.data.datasets.forEach(dataset => {
            dataset.hidden = dataset.label.toLowerCase() !== type;
        });
    }
    myChart.update();
}

// Função para gerar PDF
function generatePDF() {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();
    
    // Adicionar título
    doc.setFontSize(16);
    doc.text('Relatório Mensal - TransVeículos', 20, 20);
    
    // Adicionar data
    const date = new Date();
    doc.setFontSize(12);
    doc.text(`Gerado em: ${date.toLocaleDateString()}`, 20, 30);
    
    // Adicionar estatísticas
    doc.setFontSize(14);
    doc.text('Resumo do Período:', 20, 50);
    doc.setFontSize(12);
    doc.text(`Total de Frotas: ${document.querySelector('.stat-card:nth-child(1) p').textContent}`, 30, 60);
    doc.text(`Total de Motoristas: ${document.querySelector('.stat-card:nth-child(2) p').textContent}`, 30, 70);
    doc.text(`Entregas Realizadas: ${document.querySelector('.stat-card:nth-child(3) p').textContent}`, 30, 80);
    
    // Adicionar gráfico
    const canvas = document.getElementById('statsChart');
    const imgData = canvas.toDataURL('image/png');
    doc.addImage(imgData, 'PNG', 15, 100, 180, 100);
    
    // Salvar PDF
    doc.save(`relatorio_${date.getFullYear()}${(date.getMonth()+1).toString().padStart(2, '0')}.pdf`);
}

// Event Listeners
document.addEventListener('DOMContentLoaded', () => {
    // Carregar dados iniciais
    fetchData(new Date().getMonth());
    
    // Configurar listeners dos botões
    document.querySelectorAll('.chart-toggle').forEach(button => {
        button.addEventListener('click', () => {
            updateChart(button.dataset.type);
        });
    });
    
    // Listener para mudança de mês
    document.getElementById('monthSelect').addEventListener('change', (e) => {
        fetchData(parseInt(e.target.value));
    });
    
    // Listener para download do PDF
    document.getElementById('downloadPdf').addEventListener('click', generatePDF);
});

// Função para buscar dados do servidor
async function fetchData(month) {
    try {
        const response = await fetch(`/api/relatorios/dados/${month}`);
        const data = await response.json();
        initChart(data);
    } catch (error) {
        console.error('Erro ao carregar dados:', error);
        alert('Erro ao carregar dados do relatório');
    }
} 