<?php
	require_once("action/AjaxJeuAction.php");

	$action = new AjaxJeuAction();
	$data = $action->execute();

	echo json_encode($data["result"]);