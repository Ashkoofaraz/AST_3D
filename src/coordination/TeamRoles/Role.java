package coordination.TeamRoles;

import coordination.communication.message.CoordinationMessage;

public class Role {

	public CoordinationMessage Agent;
	public int Role;

	public Role(CoordinationMessage agent, int role) {

		Agent = agent;
		Role = role;

	}

	public CoordinationMessage getAgent() {
		return Agent;
	}

	public void setAgent(CoordinationMessage agent) {
		Agent = agent;
	}

	public int getRole() {
		return Role;
	}

	public void setRole(int role) {
		Role = role;
	}

}
