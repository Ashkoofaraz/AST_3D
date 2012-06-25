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

package agent.constraints;

import perceptor.localization.Coordinate;

public class Beam {

	public static Coordinate playersInitPositions[] = new Coordinate[] {

	new Coordinate(-10.5, 0.0), new Coordinate(-8.5, -3.0),
			new Coordinate(-8.5, 3.0), new Coordinate(-9.0, 0.0),
			new Coordinate(-6.0, -1.0), new Coordinate(-6.0, 1.0),
			new Coordinate(-4.0, -2), new Coordinate(-4.0, 2),
			new Coordinate(-3, -0.0)

	};

	/*
	 * rcssserver 0.6.6 11 player 30x20 soccer field
	 * 
	 * public static Coordinate playersInitPositions[] = new Coordinate[] {
	 * 
	 * new Coordinate(-10.5, 0.0), new Coordinate(-8.5, -3.0), new
	 * Coordinate(-8.5, 3.0), new Coordinate(-9.0, 0.0), new Coordinate(-6.0,
	 * -1.0), new Coordinate(-6.0, 1.0), new Coordinate(-4.0, -1.0), new
	 * Coordinate(-4.0, 1.0), new Coordinate(-8.5, -3.0)
	 * 
	 * };
	 */

	public String Init(int number) {

		String beam;
		String beamX = null;
		String beamY = null;
		String beamTheta = null;

		if (number >= 1 && number <= 9) {
			beamX = String.valueOf(playersInitPositions[(number - 1)].X);
			beamY = String.valueOf(playersInitPositions[(number - 1)].Y);
			beamTheta = "0.0";
		} else {

		}

		beam = beamX + " " + beamY + " " + beamTheta;

		return beam;
	}

}