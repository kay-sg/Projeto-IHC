<?php
// index.php (Raiz do projeto)

// 1. Carrega os repositories/models automaticamente (aqui sim o glob é útil!)


// 2. Descobre qual página o usuário quer ver (ex: ?pagina=home ou ?pagina=contato)
$pagina = $_GET['pagina'] ?? 'home'; // Se não passar nada, vai para 'home'

// 3. Decide qual arquivo HTML/PHP da View carregar
switch ($pagina) {
    case 'home':
        // Aqui você pode buscar dados no Model se precisar
        require_once __DIR__ . '/view/index.html'; // ou .php
        break;
        
    default:
        // Página 404 caso o arquivo não exista
        http_response_code(404);
        echo "Página não encontrada!";
        break;
}

?>