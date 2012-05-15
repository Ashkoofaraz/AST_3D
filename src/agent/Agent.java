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
package agent;

import communication.HearMessage;
import communication.SendMessage;
import localization.BallPosition;
import localization.Coordinate;
import localization.LocalizationResults;
import motion.old.CurrentMotion;
import motion.old.MotionStorage;
import motion.old.MotionTrigger;
import motion.xml.XMLMotionStorage;
import motion.xml.XMLMovement;
import behavior.Think;
import action.complex.GoKickBall;
import action.simple.TurnToBall;
import action.simple.TurnToSeeBall;
import action.simple.WalkTo;
import action.simple.WalkToBall;
import action.undeclared.ActionStateMachine;
import action.vision.SeekBall;
import connection.Connection;
import connection.MessageController;
import connection.ServerCyrcles;
import worldState.GameState;

import java.lang.String;

import perceptor.HingeJointPerceptor;
import perceptor.isFallen;
import perceptor.vision.Ball;
import perceptor.vision.Vision;


public class Agent {


	public static int num=0;
	@SuppressWarnings("unused")
	private static CurrentMotion mt;
	@SuppressWarnings("unused")
	private static float max;

	public static String Teamname="";
	public static void main(String[] args) {

		Check Ch=new Check();
		MessageController Gp = new MessageController();
		SeekBall Sb = new SeekBall();
		SendMessage sm = new SendMessage();
		Think think=new Think();
		isFallen iF=new isFallen();
		MotionStorage Ms=new MotionStorage();
		XMLMotionStorage nMs=new XMLMotionStorage();
		XMLMovement pXML=new XMLMovement();
		
		///
		ActionStateMachine.setState("GoToBall");

		//connection config
		String host = "127.0.0.1";
		int port=3100;
		
		//initializes the connection
		Connection con = new Connection(host,port);

		//read old .motion files
		System.out.print("loading old .motion files");
		Ms.StoreMotions();
		System.out.println("OK");

		//read new XML motion files
		System.out.print("loading new .XML files");
		nMs.StoreMotions();
		System.out.println("OK");



		boolean isConnected = false;
		//establish the connection between agent and server
		isConnected = con.establishConnection();
		//Creation of Nao robot
		if(isConnected==true){
			InitAgent.CreateAgent(con);
		}
		//server cyrcles
		int i=0;
		
		//player number
		num=7;
		Teamname="tuc";
		// team name
		
		//player position
		Ch.Number(num);
		
		while(con.isConnected()){

			//update server cyrcles
			i++;
			
			//update perceptors
			Gp.GetPerceptors(con);
			
			//update ball position
			BallPosition.WhereIsTheBall();
			
			//update server cyrcles
			ServerCyrcles.setCyrclesNow(i);
			
			//init Agent
			InitAgent.Init(Teamname, num, con);
			//think
			
			String AgentAct="";
			if(!GameState.getGameState().equalsIgnoreCase("BeforeKickOff") && InitAgent.isPlayerInited()==true){	
				
//				think.Role(num);
//				sm.Say("distance", con);
//				HearMessage.MessageDecoder();				
//				
//				if(MotionTrigger.getMotion().equalsIgnoreCase("Forwards50")){
//					AgentAct = pXML.execute("walk_fine");
//				}else if(MotionTrigger.getMotion().equalsIgnoreCase("TurnLeft40")){
//					AgentAct = pXML.execute("turn_left");
//				}else if(MotionTrigger.getMotion().equalsIgnoreCase("TurnRight40")){
//					AgentAct= pXML.execute("turn_right");
//				}else if(MotionTrigger.getMotion().equalsIgnoreCase("SideStepRight")){
//					AgentAct= pXML.execute("strafe_right");
//				}else if(MotionTrigger.getMotion().equalsIgnoreCase("SideStepLeft")){
//					AgentAct= pXML.execute("strafe_left");
//				}else if(MotionTrigger.getMotion().equalsIgnoreCase("KickForwardRight")||MotionTrigger.getMotion().equalsIgnoreCase("KickForwardLeft")){
//					AgentAct= pXML.execute("rigth_front_front_kick");
//				}else{
//					AgentAct= pXML.execute("");
//				}
			}
			
			/****************************experiments***************************/
			
			//AgentAct= pXML.execute("walk_fine");
			
			//if(Vision.isiSee()){
			//MotionTrigger.setMotion("KickForwardRight");
				GoKickBall.Act();
			//}
			
			if(MotionTrigger.getMotion().equalsIgnoreCase("Forwards50")){
				AgentAct = pXML.execute("walk_fine");
			}else if(MotionTrigger.getMotion().equalsIgnoreCase("TurnLeft40")){
				AgentAct = pXML.execute("turn_left");
			}else if(MotionTrigger.getMotion().equalsIgnoreCase("TurnRight40")){
				AgentAct= pXML.execute("turn_right");
			}else if(MotionTrigger.getMotion().equalsIgnoreCase("SideStepRight")){
				AgentAct= pXML.execute("strafe_right");
			}else if(MotionTrigger.getMotion().equalsIgnoreCase("SideStepLeft")){
				AgentAct= pXML.execute("strafe_left");
			}else if(MotionTrigger.getMotion().equalsIgnoreCase("KickForwardRight")||MotionTrigger.getMotion().equalsIgnoreCase("KickForwardLeft")){
				AgentAct= pXML.execute("rigth_front_front_kick");
			}else{
				AgentAct= pXML.execute("");
			}
			
			
			System.out.println("--"+Ball.getDistance());
			System.out.println("-"+Ball.RealDistance());

			System.out.println("----------------");
			

			/*******************************************************************/
			
			
			//check if i am down
			iF.Check();
		
			//get the head movement
			String headAct=Sb.MoveHead(1);
		
			//create the hole agents actions
			String Act=headAct+AgentAct;
			
			//Act
			con.sendMessage(Act);
			
			

		}

	}
}