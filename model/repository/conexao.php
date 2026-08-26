<?php

/*require_once __DIR__ . "/../../vendor/autoload.php";

$dotenv = Dotenv\Dotenv::createImmutable(__DIR__ . "/../..");
$dotenv->load();
*/

if (!function_exists('carregarEnv')) {
     function carregarEnv($caminhoArquivo) {
     if (!file_exists($caminhoArquivo)) {
         return;
     }

     $linhas = file($caminhoArquivo, FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);
      foreach ($linhas as $linha) {
            // Ignora comentários
            if (str_starts_with(trim($linha), '#')) {
                continue;
            }

         // Divide a linha em chave e valor
         list($chave, $valor) = explode('=', $linha, 2);
         $chave = trim($chave);
         $valor = trim($valor);

         // Remove aspas caso o valor esteja entre aspas
            $valor = trim($valor, '"\'');

         // Define nas superglobais
         if (!array_key_exists($chave, $_SERVER) && !array_key_exists($chave, $_ENV)) {
             putenv("$chave=$valor");
             $_ENV[$chave] = $valor;
             $_SERVER[$chave] = $valor;
            }
     }
    }
}


// Chamando a função apontando para o seu arquivo .env
carregarEnv(__DIR__ . '/../../.env');
$conexao = mysqli_connect(
    $_ENV["DB_HOST"],
    $_ENV["DB_USER"],
    $_ENV["DB_PASSWORD"],
    $_ENV["DB_NAME"]
);

if (!$conexao) {
    die("Erro na conexão: " . mysqli_connect_error());
}

?>