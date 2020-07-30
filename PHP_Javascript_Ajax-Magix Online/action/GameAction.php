<?php
	require_once("CommonAction.php");

	// Service games/action
	class GameAction extends CommonAction
	{
		public function __construct()
		{
			parent::__construct(CommonAction::$VISIBILITY_MEMBER);
		}

		protected function executeAction()
		{
			return array();
		}
	}