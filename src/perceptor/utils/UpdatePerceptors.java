/***********************************************************************************
 * Copyright 2012, Technical University of Crete
 * Academic Year 2011-2012
 *
 * Thesis Project
 *
 * @author Methenitis Georgios Student ID:2006030085	
 *
 * Abstract: Player Behavior and Team Strategy for the RoboCup 3D Simulation League
 * Start date: 25-04-2012											 
 * End date  : xx-xx-2012
 ***********************************************************************************/
package perceptor.utils;

import java.util.Vector;

import javax.vecmath.Vector3d;

import localization.AgentPosition;
import localization.Coordinate;
import localization.Landmark;
import localization.LocalizationResults;
import localization.TriangleLocalization;
import perceptor.joints.HingeJointPerceptor;
import perceptor.sensors.Accelerometer;
import perceptor.sensors.ForceResistancePerceptor;
import perceptor.sensors.GyroScope;
import perceptor.vision.Ball;
import perceptor.vision.Vision;
import perceptor.worldstate.GameState;
import perceptor.worldstate.ServerTime;
import perceptor.worldstate.TeamState;
import agent.Agent;
import agent.AgentType;
import agent.Constraints;

import communication.coordination.admin.AdminCordMessageReceiver;
import communication.coordination.field.FieldCordMessageReceiver;

import connection.TCPSocket.Connection;


public class UpdatePerceptors {
	Coordinate curloc=new Coordinate(0, 0);
	public UpdatePerceptors() {

	}
	int k=0;
	
	@SuppressWarnings("static-access")
	/*This class takes the messages from server
	 * and updates every perceptor
	 */
	public void GetPerceptors(Connection con) {

		Vector<String> ReceivedMessage = con.GetVector();
		Vector<Landmark> landmarks = new Vector<Landmark>();
		Vector<Landmark> coplayers = new Vector<Landmark>();
		Vector<Landmark> rivals = new Vector<Landmark>();
		TriangleLocalization localizer = new TriangleLocalization();

		boolean iSeeTheBall = false;
		boolean see = false;
		int i = 0;
		int j=0;

		do {

			// System.out.println("--"+message.elementAt(i));
			if (!ReceivedMessage.elementAt(i).equalsIgnoreCase(null)) {

				if (ReceivedMessage.elementAt(i).equalsIgnoreCase("time")) {

					ServerTime.setName(ReceivedMessage.elementAt(i + 1).toString());
					float time = Float.parseFloat(ReceivedMessage.elementAt(i + 2)
							.toString());
					ServerTime.setTime(time);
					// System.out.println("time @ "+i);
					i = i + 3;

				} else if (ReceivedMessage.elementAt(i).equalsIgnoreCase("GS")) {

					if(ReceivedMessage.elementAt(i+1).equalsIgnoreCase("t")){
						float time = Float.parseFloat(ReceivedMessage.elementAt(i + 2).toString());
						GameState.setGameTime(time);
						GameState.setGameState(ReceivedMessage.elementAt(i + 4).toString());
						// System.out.println("game state @ "+i);
						i = i + 5;
					}else if(ReceivedMessage.elementAt(i+1).equalsIgnoreCase("unum")){
						int num=Integer.parseInt(ReceivedMessage.elementAt(i+2));
						AgentType.setPlayerNum(num);
						TeamState.setTeamSide(ReceivedMessage.elementAt(i+4));
						float time = Float.parseFloat(ReceivedMessage.elementAt(i + 6).toString());
						GameState.setGameTime(time);
						GameState.setGameState(ReceivedMessage.elementAt(i + 8).toString());
						i=i+9;
					}


				} else if (ReceivedMessage.elementAt(i).equalsIgnoreCase("GYR")) {

					float anglex = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
							.toString());
					GyroScope.setAngleX(anglex);
					float angley = Float.parseFloat(ReceivedMessage.elementAt(i + 5)
							.toString());
					GyroScope.setAngleY(angley);
					float anglez = Float.parseFloat(ReceivedMessage.elementAt(i + 6)
							.toString());
					GyroScope.setAngleZ(anglez);
					// System.out.println("gyroscope @ "+i);
					i = i + 7;

				} else if (ReceivedMessage.elementAt(i).equalsIgnoreCase("ACC")) {

					float accX = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
							.toString());
					Accelerometer.setAccX(accX);
					float accY = Float.parseFloat(ReceivedMessage.elementAt(i + 5)
							.toString());
					Accelerometer.setAccX(accY);
					float accZ = Float.parseFloat(ReceivedMessage.elementAt(i + 6)
							.toString());
					Accelerometer.setAccX(accZ);
					// System.out.println("accelerometer @ "+i);
					i = i + 7;

				} else if (ReceivedMessage.elementAt(i).equalsIgnoreCase("UJ")) {

					// System.out.println("univ joint @ "+i);
					i = i + 1;

				} else if (ReceivedMessage.elementAt(i).equalsIgnoreCase("TCH")) {

					// System.out.println("touch perceptor @ "+i);
					i = i + 1;

				} else if (ReceivedMessage.elementAt(i).equalsIgnoreCase("HJ")) {
					float axis = 0;
					if (ReceivedMessage.elementAt(i + 2).equalsIgnoreCase("hj1")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setHj1(axis);
					} else if (ReceivedMessage.elementAt(i + 2).equalsIgnoreCase("hj2")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setHj2(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("laj1")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setLaj1(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("laj2")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setLaj2(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("laj3")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setLaj3(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("laj4")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setLaj4(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("llj1")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setLlj1(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("llj2")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setLlj2(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("llj3")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setLlj3(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("llj4")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setLlj4(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("llj5")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setLlj5(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("llj6")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setLlj6(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("rlj1")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setRlj1(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("rlj2")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setRlj2(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("rlj3")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setRlj3(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("rlj4")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setRlj4(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("rlj5")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setRlj5(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("rlj6")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setRlj6(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("raj1")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setRaj1(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("raj2")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setRaj2(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("raj3")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setRaj3(axis);
					} else if (ReceivedMessage.elementAt(i + 2)
							.equalsIgnoreCase("raj4")) {
						axis = Float.parseFloat(ReceivedMessage.elementAt(i + 4)
								.toString());
						HingeJointPerceptor.setRaj4(axis);
					}

					//System.out.println("hint joint @ " + axis);
					i = i + 5;

				} else if (ReceivedMessage.elementAt(i).equalsIgnoreCase("hear")) {

					//float time = Float.parseFloat(ReceivedMessage.elementAt(i+1));

					if(ReceivedMessage.elementAt(i+2).equalsIgnoreCase("self")){
						
					}else{
						
						//float direction = Float.parseFloat(ReceivedMessage.elementAt(i+2));
						String msg = ReceivedMessage.elementAt(i+3);
						
						
						
						//This player is the administrator of the coordination function
						if(AgentType.getPlayerNum()==Constraints.CoordinationPlayer){
							
							AdminCordMessageReceiver.MessageHandler(msg);
	
					
						//These players are the field players of the coordination function
						}else{
							
							
							FieldCordMessageReceiver.MessageHandler(msg);
							
							
							
						}
					
					}

					i = i + 4;

				} else if (ReceivedMessage.elementAt(i).equalsIgnoreCase("See")) {
					j=i;
					j++;
					do{
						if(ReceivedMessage.elementAt(j).equalsIgnoreCase("f1l")){
							Landmark lm=new Landmark("f1l", Float.parseFloat(ReceivedMessage.elementAt(j+2).toString()),Float.parseFloat(ReceivedMessage.elementAt(j+4).toString()), Float.parseFloat(ReceivedMessage.elementAt(j+3).toString()));
							landmarks.add(lm);
							j=j+4;
						}else if(ReceivedMessage.elementAt(j).equalsIgnoreCase("f2l")){
							Landmark lm=new Landmark("f2l", Float.parseFloat(ReceivedMessage.elementAt(j+2).toString()),Float.parseFloat(ReceivedMessage.elementAt(j+4).toString()), Float.parseFloat(ReceivedMessage.elementAt(j+3).toString()));
							landmarks.add(lm);
							j=j+4;
						}else if(ReceivedMessage.elementAt(j).equalsIgnoreCase("f1r")){
							Landmark lm=new Landmark("f1r", Float.parseFloat(ReceivedMessage.elementAt(j+2).toString()),Float.parseFloat(ReceivedMessage.elementAt(j+4).toString()), Float.parseFloat(ReceivedMessage.elementAt(j+3).toString()));
							landmarks.add(lm);
							j=j+4;
						}else if(ReceivedMessage.elementAt(j).equalsIgnoreCase("f2r")){
							Landmark lm=new Landmark("f2r", Float.parseFloat(ReceivedMessage.elementAt(j+2).toString()),Float.parseFloat(ReceivedMessage.elementAt(j+4).toString()), Float.parseFloat(ReceivedMessage.elementAt(j+3).toString()));
							landmarks.add(lm);
							j=j+4;
						}else if(ReceivedMessage.elementAt(j).equalsIgnoreCase("g1l")){
							Landmark lm=new Landmark("g1l", Float.parseFloat(ReceivedMessage.elementAt(j+2).toString()),Float.parseFloat(ReceivedMessage.elementAt(j+4).toString()), Float.parseFloat(ReceivedMessage.elementAt(j+3).toString()));
							landmarks.add(lm);
							j=j+4;
						}else if(ReceivedMessage.elementAt(j).equalsIgnoreCase("g2l")){
							Landmark lm=new Landmark("g2l", Float.parseFloat(ReceivedMessage.elementAt(j+2).toString()),Float.parseFloat(ReceivedMessage.elementAt(j+4).toString()), Float.parseFloat(ReceivedMessage.elementAt(j+3).toString()));
							landmarks.add(lm);
							j=j+4;
						}else if(ReceivedMessage.elementAt(j).equalsIgnoreCase("g1r")){
							Landmark lm=new Landmark("g1r", Float.parseFloat(ReceivedMessage.elementAt(j+2).toString()),Float.parseFloat(ReceivedMessage.elementAt(j+4).toString()), Float.parseFloat(ReceivedMessage.elementAt(j+3).toString()));
							landmarks.add(lm);
							j=j+4;
						}
						else if(ReceivedMessage.elementAt(j).equalsIgnoreCase("g2r")){
							Landmark lm=new Landmark("g2r", Float.parseFloat(ReceivedMessage.elementAt(j+2).toString()),Float.parseFloat(ReceivedMessage.elementAt(j+4).toString()), Float.parseFloat(ReceivedMessage.elementAt(j+3).toString()));
							landmarks.add(lm);
							j=j+4;

						}
						else if(ReceivedMessage.elementAt(j).equalsIgnoreCase("b")){

							iSeeTheBall = true;
							float distance = Float.parseFloat(ReceivedMessage.elementAt(j + 2)
									.toString());
							Ball.setDistance(distance);
							float angleX = Float.parseFloat(ReceivedMessage.elementAt(j + 3)
									.toString());
							Ball.setAngleX(angleX);
							float angleY = Float.parseFloat(ReceivedMessage.elementAt(j + 4)
									.toString());
							Ball.setAngleY(angleY);
							// System.out.println("ball @ "+i);

							j=j+4;
						}else if(ReceivedMessage.elementAt(j).equalsIgnoreCase("p")){
							j++;
							String team_name=null;
							String player_id=null;
							double player_distance=0;
							double player_horizontal_angle=0;
							double player_vertical_angle=0;
							j++;
							team_name=ReceivedMessage.elementAt(j);
							j++; //message=id
							j++;
							player_id=ReceivedMessage.elementAt(j);
							j++;
							int k=0;
							while(ReceivedMessage.elementAt(j).equalsIgnoreCase("head")||ReceivedMessage.elementAt(j).equalsIgnoreCase("rlowerarm")
									||ReceivedMessage.elementAt(j).equalsIgnoreCase("llowerarm")||ReceivedMessage.elementAt(j).equalsIgnoreCase("rfoot")
									||ReceivedMessage.elementAt(j).equalsIgnoreCase("lfoot")){
								k++;
								j++; //message=pol
								j++;
								player_distance=player_distance+Float.parseFloat(ReceivedMessage.elementAt(j));
								j++;
								player_horizontal_angle=player_horizontal_angle+Float.parseFloat(ReceivedMessage.elementAt(j));
								j++;
								player_vertical_angle=player_horizontal_angle+Float.parseFloat(ReceivedMessage.elementAt(j));
								j++;
							}
							j--;
							if(k!=0){
								player_distance=player_distance/k;
								player_horizontal_angle=(player_horizontal_angle/k)+HingeJointPerceptor.getHj1();
								player_vertical_angle=(player_horizontal_angle/k)+HingeJointPerceptor.getHj2();	
							}
							Landmark player=new Landmark(player_id, player_distance, player_vertical_angle, player_horizontal_angle);
							if(team_name.equalsIgnoreCase(Agent.Teamname)){
								if(!player.getName().equalsIgnoreCase(Agent.num+"")){
									coplayers.add(player);
								}
							}else{
								rivals.add(player);
							}





						}else{
							//System.out.println("WHAT THE FUCK!!!");
						}
						j++;

					}while (ReceivedMessage.elementAt(j).equalsIgnoreCase("f1l")||ReceivedMessage.elementAt(j).equalsIgnoreCase("f2l")||
							ReceivedMessage.elementAt(j).equalsIgnoreCase("f2r")||ReceivedMessage.elementAt(j).equalsIgnoreCase("f1r")||
							ReceivedMessage.elementAt(j).equalsIgnoreCase("g1l")||ReceivedMessage.elementAt(j).equalsIgnoreCase("g2l")||
							ReceivedMessage.elementAt(j).equalsIgnoreCase("g1r")||ReceivedMessage.elementAt(j).equalsIgnoreCase("g2r")||
							ReceivedMessage.elementAt(j).equalsIgnoreCase("b")||ReceivedMessage.elementAt(j).equalsIgnoreCase("p")
					);
					Coordinate loc_buffer=new Coordinate(0, 0);
					
					int k=0;
					double x=0;
					double y=0;

					if(landmarks.size()<=1){
						
					}

					for(int ii=0;ii<landmarks.size();ii++){
						
						for(int jj=ii+1;jj<landmarks.size();jj++){
							
							loc_buffer=localizer.Localize(landmarks.elementAt(ii),landmarks.elementAt(jj));
							x=x+loc_buffer.X;
							y=y+loc_buffer.Y;
							k++;
							
						}
					}
					
					
					if(x!=0 && y!=0){
						if(k!=0){
							
							curloc=new Coordinate(x/k, y/k);
							
						}
						else{
							
							curloc=new Coordinate(x, y);
							
						}
					}

					
					AgentPosition.setX((float) curloc.getX());
					AgentPosition.setY((float) curloc.getY());
					LocalizationResults.setCurrent_location(curloc);

					int l=0;
					double angle_buffer=0;
					double angle=0;
					
					for (int ii=0;ii<landmarks.size();ii++){
						
						angle_buffer=localizer.universal_angle(landmarks.elementAt(ii), curloc);
						angle=angle+angle_buffer;
						l++;
						
					}
					
					double head_angle=0;
					
					if(angle!=0){
						
						if(l!=0){
							
							head_angle=angle/l;
							
						}else{
							
							head_angle=angle;
							
						}
					}

					
					AgentPosition.setTheta((float) (head_angle+HingeJointPerceptor.getHj1()));
					
					LocalizationResults.setHead_angle(head_angle);
					LocalizationResults.setBody_angle((head_angle-HingeJointPerceptor.getHj1()));

					
					Coordinate Ball_det=localizer.get_det_with_distance_angle(curloc.getX(), curloc.getY(), (head_angle+Ball.getAngleX()), Ball.getDistance());
					
					LocalizationResults.setBall_location(Ball_det);
					LocalizationResults.setLandmarks(landmarks);
					LocalizationResults.setCoplayers(coplayers);
					LocalizationResults.setRivals(rivals);
					
					for(int jj=0;jj<coplayers.size();jj++){
						
						@SuppressWarnings("unused")
						Coordinate found_player=localizer.get_det_with_distance_angle(curloc.getX(),curloc.getY() , (head_angle+coplayers.elementAt(jj).getHorizontal_Angle()), coplayers.elementAt(jj).getDistance());
						
					}
					if(coplayers.size()==0){
						
					}
					for(int jj=0;jj<rivals.size();jj++){
						
						@SuppressWarnings("unused")
						Coordinate found_player=localizer.get_det_with_distance_angle(curloc.getX(),curloc.getY() , (head_angle+rivals.elementAt(jj).getHorizontal_Angle()), rivals.elementAt(jj).getDistance());
					}
					if(rivals.size()==0){
						//System.out.println("I see no rivals");
					}
					//System.out.println("I see ball at ( "+Ball_det.getX()+" , "+Ball_det.getY()+" ) at angle "+ Ball.getDistance());
					//System.out.println("-------------------------------------------------------------------------------------------");
					//coplayers.removeAllElements();
					//rivals.removeAllElements();
					//landmarks.removeAllElements();
					i=j;
					j=0;
					see=true;
					Vision.setiSee(see);

				} else if (ReceivedMessage.elementAt(i).equalsIgnoreCase("FRP")) {

					ForceResistancePerceptor.setName(ReceivedMessage.elementAt(i+2).toString());
					
					Vector3d forceOr= new Vector3d();
					Vector3d force= new Vector3d();
					
					forceOr.x=Float.parseFloat(ReceivedMessage.elementAt(i+4).toString());
					forceOr.y=Float.parseFloat(ReceivedMessage.elementAt(i+5).toString());
					forceOr.z=Float.parseFloat(ReceivedMessage.elementAt(i+6).toString());
					
					force.x=Float.parseFloat(ReceivedMessage.elementAt(i+8).toString());
					force.y=Float.parseFloat(ReceivedMessage.elementAt(i+9).toString());
					force.z=Float.parseFloat(ReceivedMessage.elementAt(i+10).toString());
					
					ForceResistancePerceptor.setForceOrigin(forceOr);
					ForceResistancePerceptor.setForce(force);
					
					i = i + 11;

				} else {
					i++;
				}
			}

			if(i==ReceivedMessage.size()){
				
				Vision.setiSee(see);
				Ball.setSeeTheBall(iSeeTheBall);
				ReceivedMessage.removeAllElements();
				break;
				
			}

			
		} while (i < ReceivedMessage.size()+1);



	}

}