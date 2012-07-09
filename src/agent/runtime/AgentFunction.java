package agent.runtime;

import behavior.goalie.Goalie;
import communication.utils.MessageType;
import communication.utils.SayEffector;
import connection.utils.ServerCyrcles;
import coordination.main.Coordination;

import perceptor.worldstate.GameState;
import motion.utils.PerformMovement;
import action.complex.GoKickBallToGoal;
import action.handler.ActionEffector;
import action.sensor.CheckIfFall;
import action.simple.TurnToLocate;
import action.vision.HeadMovement;
import action.vision.VisionType;


public class AgentFunction {

	public static String Act;
	public static String Say;
	public static String Head;

	public static void Act() {

		/*
		 * Agent check if he is fallen on the ground. If yes, he will manage to
		 * understand through this function the his fall, and then he will try
		 * to get up.
		 */
		CheckIfFall.Check();

		if (InitAgent.isPlayerInited()) {

			if (!CheckIfFall.fallen) {
				Goalie.Act();
			}
		}


		//		if (InitAgent.isPlayerInited()) {
		//
		//			if (!GameState.getGameState().equalsIgnoreCase("BeforeKickOff")) {
		//
		//				Say = SayEffector.Say(MessageType.getType());
		//
		//				Coordination.MakeCoordination();
		//
		//				if (!CheckIfFall.fallen) {
		//					ActionEffector.Act();
		//				}
		//
		//				ServerCyrcles.setGameCyrcles(AgentRuntime.GameCycles++);
		//
		//			} else {
		//
		//				if (!CheckIfFall.fallen) {
		//					TurnToLocate.Act();
		//				}
		//
		//			}
		//
		//		}

		Act = PerformMovement.run();

		Head = HeadMovement.MoveHead(VisionType.getType());

	}

}
