<?php

$conexao = mysqli_connect(
    "localhost",
    "root",
    "branquinha",
    "inventario"
);

if (!$conexao) {
    die("Erro na conexão: " . mysqli_connect_error());
}

?>