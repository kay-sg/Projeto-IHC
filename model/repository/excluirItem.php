<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");

require "conexao.php";

if (!isset($_GET["id"]) || !is_numeric($_GET["id"])) {

    http_response_code(400);

    echo json_encode([
        "erro" => "ID inválido ou não informado"
    ]);

    exit;
}

$id = (int) $_GET["id"];

$sql = "DELETE FROM itens WHERE id = ?";

$stmt = mysqli_prepare($conexao, $sql);

if (!$stmt) {

    http_response_code(500);

    echo json_encode([
        "erro" => "Erro ao preparar exclusão"
    ]);

    exit;
}

mysqli_stmt_bind_param($stmt, "i", $id);

if (mysqli_stmt_execute($stmt)) {

    echo json_encode([
        "mensagem" => "Item excluído",
        "id" => $id
    ]);

} else {

    http_response_code(500);

    echo json_encode([
        "erro" => "Erro ao excluir item"
    ]);
}

mysqli_stmt_close($stmt);
mysqli_close($conexao);

?>