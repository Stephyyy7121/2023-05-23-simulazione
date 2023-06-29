package it.polito.tdp.baseball.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.baseball.model.Appearances;
import it.polito.tdp.baseball.model.Arco;
//import it.polito.tdp.baseball.model.Arco;
import it.polito.tdp.baseball.model.People;
import it.polito.tdp.baseball.model.Team;


public class BaseballDAO {
	
	public List<People> readAllPlayers(){
		String sql = "SELECT * "
				+ "FROM people";
		List<People> result = new ArrayList<People>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new People(rs.getString("playerID"), 
						rs.getString("birthCountry"), 
						rs.getString("birthCity"), 
						rs.getString("deathCountry"), 
						rs.getString("deathCity"),
						rs.getString("nameFirst"), 
						rs.getString("nameLast"), 
						rs.getInt("weight"), 
						rs.getInt("height"), 
						rs.getString("bats"), 
						rs.getString("throws"),
						getBirthDate(rs), 
						getDebutDate(rs), 
						getFinalGameDate(rs), 
						getDeathDate(rs)) );
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	
	public List<Team> readAllTeams(){
		String sql = "SELECT * "
				+ "FROM  teams";
		List<Team> result = new ArrayList<Team>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Team( rs.getInt("iD"),
						rs.getInt("year"), 
						rs.getString("teamCode"), 
						rs.getString("divID"), 
						rs.getInt("div_ID"), 
						rs.getInt("teamRank"),
						rs.getInt("games"), 
						rs.getInt("gamesHome"), 
						rs.getInt("wins"), 
						rs.getInt("losses"), 
						rs.getString("divisionWinnner"), 
						rs.getString("leagueWinner"),
						rs.getString("worldSeriesWinnner"), 
						rs.getInt("runs"), 
						rs.getInt("hits"), 
						rs.getInt("homeruns"), 
						rs.getInt("stolenBases"),
						rs.getInt("hitsAllowed"), 
						rs.getInt("homerunsAllowed"), 
						rs.getString("name"), 
						rs.getString("park")  ) );
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	
	
	//=================================================================
	//==================== HELPER FUNCTIONS   =========================
	//=================================================================
	
	
	
	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * @param rs
	 * @return
	 */
	private LocalDateTime getBirthDate(ResultSet rs) {
		try {
			if (rs.getTimestamp("birth_date") != null) {
				return rs.getTimestamp("birth_date").toLocalDateTime();
			} else {
				return null;
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * @param rs
	 * @return
	 */
	private LocalDateTime getDebutDate(ResultSet rs) {
		try {
			if (rs.getTimestamp("debut_date") != null) {
				return rs.getTimestamp("debut_date").toLocalDateTime();
			} else {
				return null;
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * @param rs
	 * @return
	 */
	private LocalDateTime getFinalGameDate(ResultSet rs) {
		try {
			if (rs.getTimestamp("finalgame_date") != null) {
				return rs.getTimestamp("finalgame_date").toLocalDateTime();
			} else {
				return null;
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * @param rs
	 * @return
	 */
	private LocalDateTime getDeathDate(ResultSet rs) {
		try {
			if (rs.getTimestamp("death_date") != null) {
				return rs.getTimestamp("death_date").toLocalDateTime();
			} else {
				return null;
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	//metodo per ottenere i vertici del grafo
	public List<People> getVertices(int anno, double salario) {
		
		String sql = "SELECT DISTINCT p.* "
				+ "FROM people p, salaries s "
				+ "WHERE p.playerID = s.playerID AND s.year = ? AND s.salary > ? "
				+ "GROUP BY p.playerID";
		
		List<People> result = new ArrayList<People>();
		
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setDouble(2, salario);
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				
				String playerIS = rs.getString("playerID");
			    String birthCountry = rs.getString("birthCountry");
			    String birthCity = rs.getString("birthCity");
			    String deathCountry = rs.getString("deathCountry");
			    String deathCity = rs.getString("deathCity");
			    String nameFirst = rs.getString("nameFirst");
			    String nameLast = rs.getString("nameLast");
			    Integer weight = rs.getInt("weight");
			    Integer height = rs.getInt("height");
			    String bats = rs.getString("bats");
			    String throwString = rs.getString("throws");
			    LocalDateTime birthDate = getBirthDate(rs);
			    LocalDateTime debutDate = getDebutDate(rs);
			    LocalDateTime finalgameDate = getFinalGameDate(rs);
			    LocalDateTime deathDate = getDeathDate(rs);
			    
			    result.add(new People(playerIS, birthCountry, birthCity, deathCountry,deathCity,nameFirst, nameLast,weight, height, bats, throwString, birthDate, debutDate, finalgameDate, deathDate));
			    
			    conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	//metodo per ottenere gli archi 
	
	//creare una classe ARCO 
	/*public List<Arco> getEdge(int anno, double salario, Map<String, People> idMapPlayer) {
		
		String sql = "SELECT a1.playerID AS id1, a2.playerID AS id2, a1.teamID "
				+ "FROM appearances a1, appearances a2 "
				+ "WHERE a1.playerID < a2.playerID AND a1.year = a2.year AND a1.year= ? AND a1.teamID = a2.teamID "
				+ "AND a1.playerID IN (SELECT p.playerID "
				+ "						FROM people p, salaries "
				+ "						WHERE p.playerID = s.playerID AND s.`year` = ? "
				+ "						GROUP BY p.playerID "
				+ "						HAVING SUM(s.salary)> ?) "
				+ "AND a2.playerID IN (SELECT p.playerID\n"
				+ "						FROM people p, salaries s\n"
				+ "						WHERE p.playerID = s.playerID AND s.`year` = ? "
				+ "						GROUP BY p.playerID "
				+ "						HAVING SUM(s.salary)> ?) ";
		
		List<Arco> result = new ArrayList<Arco>();
		Connection conn = DBConnect.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			st.setDouble(3, salario);
			st.setInt(4, anno);
			st.setDouble(5, salario);
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				
				String idPlayer1 = rs.getString("id1");
				String idPlayer2 = rs.getString("id2");
				
				People source = idMapPlayer.get(idPlayer1);
				People target = idMapPlayer.get(idPlayer2);
				
				result.add(new Arco(source, target));
				
				conn.close();
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return result;
		
	}*/
	public List<Arco> getEdge(int anno, double salario, Map<String, People> playerIDMap) {
		String sql = "SELECT a1.playerID as pid1, a2.playerID as pid2, a1.teamID "
				+ "FROM appearances a1, appearances a2 "
				+ "WHERE a1.playerID < a2.playerID AND a1.teamID = a2.teamID AND a1.year = a2.year AND a1.year = ? "
				+ "AND a1.playerID IN (SELECT p.PlayerID "
				+ "						FROM people p, salaries s "
				+ "						WHERE p.playerID = s.playerID AND s.year=? "
				+ "						Group By p.playerID "
				+ "						HAVING SUM(s.salary) > ?) "
				+ "AND a2.playerID IN (SELECT p.PlayerID "
				+ "						FROM people p, salaries s "
				+ "						WHERE p.playerID = s.playerID AND s.year=? "
				+ "						Group By p.playerID "
				+ "						HAVING SUM(s.salary) > ?)";
		List<Arco> result = new ArrayList<Arco>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			st.setDouble(3, salario);
			st.setInt(4, anno);
			st.setDouble(5, salario);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				People player1 = playerIDMap.get(rs.getString("pid1"));
				People player2 = playerIDMap.get(rs.getString("pid2"));
				result.add(new Arco(player1, 
						player2) );
			}
			
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	

}
