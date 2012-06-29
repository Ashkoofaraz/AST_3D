package coordination.strategy;

import java.util.Vector;

import coordination.TeamRoles.RoleAssignmentFunction;

import perceptor.localization.Coordinate;

public class SupportPositions {
	
	public static Vector<Coordinate> SupportPositionsVector = new Vector<Coordinate>();
	
	public static void Calculate(){
		
		SupportPositionsVector.removeAllElements();
		
		for(int i=0;i<RoleAssignmentFunction.SupportRoles.size();i++){
				
			SupportPositionsVector.add(TeamFormation.TeamFormation[RoleAssignmentFunction.SupportRoles.elementAt(i).getRole()]);
			
		}		
		
	}
}
