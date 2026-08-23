<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");

require "conexao.php";

$sql = "SELECT * FROM itens";

$resultado = mysqli_query($conexao, $sql);

$itens = [];

while ($linha = mysqli_fetch_assoc($resultado)) {
    $itens[] = $linha;
}

header("Content-Type: application/json");

echo json_encode($itens);

mysqli_free_result($resultado);
mysqli_close($conexao);

?>