package motion.xml;

import action.undeclared.ActionStateMachine;

public class CheckKickEnd {

	public static boolean Check(){

		if(MotionPlaying.getMotionPhase()!=null){
			
			if(MotionPlaying.getMotionPhase().equalsIgnoreCase("rigth_front_front_kick4")){

				ActionStateMachine.setState("GoToBall");
				return true;

			}
			
		}


		return false;


	}

}
