<?php

$conexao = mysqli_connect(
    "localhost",
    "user",
    "password",
    "inventario"
);

if (!$conexao) {
    die("Erro na conexão: " . mysqli_connect_error());
}

?>