/*******************************************************************************
 * Technical University of Crete
 * 
 * Thesis Project
 * 
 * Author:	Methenitis K. Georgios
 * 
 * Abstract  : Player Behavior and Team Strategy 
 * 			for the RoboCup 3D Simulation League
 * 
 * Date:  		July 2012
 ******************************************************************************/
package behavior.old;

public class BehaviorDone {

	public static String name;
	public static boolean behaviorDone;

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		BehaviorDone.name = name;
	}

	public static boolean isBehaviorDone() {
		return behaviorDone;
	}

	public static void setBehaviorDone(boolean behaviorDone) {
		BehaviorDone.behaviorDone = behaviorDone;
	}

}
