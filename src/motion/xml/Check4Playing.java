package motion.xml;

public class Check4Playing {

	public static boolean check(String name){

		if(MotionPlaying.getMotionName()!=null){

			if(MotionPlaying.getMotionName().equalsIgnoreCase(name)){

				return true;

			}

		}

		return false;	

	}

}
