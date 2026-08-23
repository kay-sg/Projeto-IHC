<?php

require_once __DIR__ . "/../../vendor/autoload.php";

$dotenv = Dotenv\Dotenv::createImmutable(__DIR__ . "/../..");
$dotenv->load();

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