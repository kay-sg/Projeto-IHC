<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");

require "conexao.php";

$id = $_GET["id"];

$sql = "DELETE FROM itens WHERE id = ?";

$stmt = mysqli_prepare($conexao, $sql);

mysqli_stmt_bind_param($stmt, "i", $id);

if (mysqli_stmt_execute($stmt)) {
    echo json_encode([
        "mensagem" => "Item excluído"
    ]);
} else {
    echo json_encode([
        "erro" => "Erro ao excluir item"
    ]);
}

mysqli_close($conexao);

?>