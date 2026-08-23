<?php

$conexao = mysqli_connect(
    "localhost",
    "xxx",
    "xxx",
    "inventario"
);

if (!$conexao) {
    die("Erro na conexão: " . mysqli_connect_error());
}

?>