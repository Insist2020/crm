package hr.tvz.crm.baza;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import hr.tvz.crm.main.Klijent;
import hr.tvz.crm.main.Popravak;
import hr.tvz.crm.main.Tip;

public class BazaPodataka {
	
	public static Connection SpojiSeNaBazu(){
		Connection veza = null;
		
		try(BufferedReader in = new BufferedReader(new FileReader("properties.txt"))){
			String bazaPodatakaUrl = in.readLine();
			String korisnickoIme = in.readLine();
			String lozinka = in.readLine();
			int port = Integer.parseInt(in.readLine());
			String baza = in.readLine();			
			try{
				//veza = DriverManager.getConnection(bazaPodatakaUrl, korisnickoIme, lozinka );
				MysqlDataSource dataSource = new MysqlDataSource();
				dataSource.setUser(korisnickoIme);
				dataSource.setPassword(lozinka);
				dataSource.setServerName(bazaPodatakaUrl);
				dataSource.setPort(port);
				dataSource.setDatabaseName(baza);
				dataSource.setUseUnicode(true);
				dataSource.setEncoding("utf8");
				veza = dataSource.getConnection();
			}
			catch(SQLException ex){
				System.err.println(ex);
			}
		}
		catch(IOException ex){
			System.err.println(ex);
		}
		return veza;
	}
	
	public static void zatvoriVezuSBazom(Connection veza){
		try{
			veza.close();
		}
		catch(SQLException ex){
			System.err.println(ex);
		}
	}
	
	public static List<Klijent> dohvatiSveKlijente() throws Exception { 
		Connection connection = SpojiSeNaBazu();
		List<Klijent> klijenti = new ArrayList<>();
		
		String queryString = "SELECT * FROM klijent";
		PreparedStatement preparedStatement = connection.prepareStatement(queryString); 
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) { 
			int id = resultSet.getInt("id"); 
			String ime = resultSet.getString("ime"); 
			String prezime = resultSet.getString("prezime");
			String adresa = resultSet.getString("adresa");
			String kontakt = resultSet.getString("kontakt_broj");
			String email = resultSet.getString("email");
			int registracija = resultSet.getInt("tstamp_registracije");
			
			Klijent k = new Klijent(id, ime, prezime, adresa, kontakt, email, registracija); 
			klijenti.add(k);
		} 
		
		zatvoriVezuSBazom(connection);
		return klijenti;
	}
	
	public static List<Popravak> dohvatiPopravke(int idKlijenta) throws Exception { 
		Connection connection = SpojiSeNaBazu();
		List<Popravak> popravci = new ArrayList<>();
		
		String queryString = "SELECT * FROM popravak WHERE id_klijenta = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(queryString); 
		preparedStatement.setInt(1, idKlijenta);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) { 
			int id = resultSet.getInt("id"); 
			String naslov = resultSet.getString("naziv"); 
			String opis = resultSet.getString("opis");
			String vozilo = resultSet.getString("vozilo");
			Double cijena = resultSet.getDouble("cijena");
			int idKlijent = resultSet.getInt("id_klijenta");
			Long datum = resultSet.getLong("tstamp");
			int tip = resultSet.getInt("tip");
			
			Popravak p = new Popravak(id, naslov, opis, vozilo, cijena, idKlijent, datum, tip); 
			popravci.add(p);
		} 
		
		zatvoriVezuSBazom(connection);
		return popravci;
	}
	
	public static Popravak dohvatiPopravak(int idPopravka) throws Exception { 
		Connection connection = SpojiSeNaBazu();
		
		String queryString = "SELECT * FROM popravak WHERE id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(queryString); 
		preparedStatement.setInt(1, idPopravka);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if(resultSet.next()){		
			int id = resultSet.getInt("id"); 
			String naslov = resultSet.getString("naziv"); 
			String opis = resultSet.getString("opis");
			String vozilo = resultSet.getString("vozilo");
			Double cijena = resultSet.getDouble("cijena");
			int idKlijent = resultSet.getInt("id_klijenta");
			Long datum = resultSet.getLong("datum");
			int tip = resultSet.getInt("tip");
			
			Popravak p = new Popravak(id, naslov, opis, vozilo, cijena, idKlijent, datum, tip);
			
			zatvoriVezuSBazom(connection);
			
			return p;
		} else {
			zatvoriVezuSBazom(connection);
			return null;
		}
	}
	
	public static boolean dodajNovogKlijenta(Klijent noviKlijent) throws Exception { 
		Connection connection = SpojiSeNaBazu();
		
		String queryString = "INSERT INTO klijent(ime, prezime, adresa, kontakt_broj, email, tstamp_registracije) VALUES(?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(queryString); 
		preparedStatement.setString(1, noviKlijent.getIme());
		preparedStatement.setString(2, noviKlijent.getPrezime());
		preparedStatement.setString(3, noviKlijent.getAdresa());
		preparedStatement.setString(4, noviKlijent.getKontakt());
		preparedStatement.setString(5, noviKlijent.getEmail());
		preparedStatement.setLong(6, Instant.now().getEpochSecond());
		preparedStatement.executeUpdate();
		
		zatvoriVezuSBazom(connection);
		return true;
	}
	
	public static boolean obrisiKlijenta(int id) throws Exception{ 
		Connection connection = SpojiSeNaBazu();
		
		String queryString = "DELETE FROM klijent WHERE id = ?";
		PreparedStatement preparedStatement;
		preparedStatement = connection.prepareStatement(queryString);
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();
		
		zatvoriVezuSBazom(connection);
		return true;
	}
	
	public static boolean dodajNoviPopravak(Popravak noviPopravak) throws Exception { 
		Connection connection = SpojiSeNaBazu();
		
		String queryString = "INSERT INTO popravak(naziv, opis, vozilo, cijena, id_klijenta, tip, tstamp) VALUES(?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(queryString); 
		preparedStatement.setString(1, noviPopravak.getNaziv());
		preparedStatement.setString(2, noviPopravak.getOpis());
		preparedStatement.setString(3, noviPopravak.getVozilo());
		preparedStatement.setDouble(4, noviPopravak.getCijena());
		preparedStatement.setInt(5, noviPopravak.getIdKlijent());
		preparedStatement.setInt(6, noviPopravak.getTip());
		preparedStatement.setLong(7, Instant.now().getEpochSecond());
		preparedStatement.executeUpdate();
		
		zatvoriVezuSBazom(connection);
		return true;
	}
	
	public static boolean obrisiPopravak(int id) throws Exception{ 
		Connection connection = SpojiSeNaBazu();
		
		String queryString = "DELETE FROM popravak WHERE id = ?";
		PreparedStatement preparedStatement;
		preparedStatement = connection.prepareStatement(queryString);
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();
		
		zatvoriVezuSBazom(connection);
		return true;
	}
	
	public static List<Tip> dohvatiSveTipove() throws Exception { 
		Connection connection = SpojiSeNaBazu();
		List<Tip> tipovi = new ArrayList<>();
		
		String queryString = "SELECT * FROM tip_popravka";
		PreparedStatement preparedStatement = connection.prepareStatement(queryString); 
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) { 
			int id = resultSet.getInt("id");
			String tip = resultSet.getString("tip");
			
			Tip t = new Tip(id, tip); 
			tipovi.add(t);
		} 
		
		zatvoriVezuSBazom(connection);
		return tipovi;
	}

	public static Map<String, Integer> dohvatiStatistikuPopravaka() throws Exception { 
		Connection connection = SpojiSeNaBazu();
		
		String queryString = "SELECT tip_popravka.tip AS tip, "
				+ "COUNT(*) AS suma "
				+ "FROM popravak JOIN "
				+ "tip_popravka ON popravak.tip = tip_popravka.id "
				+ "GROUP BY 1";
		PreparedStatement preparedStatement = connection.prepareStatement(queryString);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		Map<String, Integer> rez = new HashMap<>();
		
		/*if (!resultSet.isBeforeFirst() ) {
			zatvoriVezuSBazom(connection);
			return rez; 
		}*/
		
		while(resultSet.next()){		
			int suma = resultSet.getInt("suma"); 
			String tip = resultSet.getString("tip");
			
			rez.put(tip, suma);
		}
		
		zatvoriVezuSBazom(connection);		
		return rez;
		
	}

	public static Map<String, Integer> dohvatiStatistikuBrojaKlijenata() throws Exception { 
		Connection connection = SpojiSeNaBazu();
		
		String queryString = "SELECT YEAR(FROM_UNIXTIME(`tstamp_registracije`)) AS year, "
				+ "MONTH(FROM_UNIXTIME(`tstamp_registracije`)) AS month, "
				+ "COUNT(*) AS suma "
				+ "FROM `klijent` "
				+ "GROUP BY 1, 2 "
				+ "ORDER BY 1, 2";
		PreparedStatement preparedStatement = connection.prepareStatement(queryString);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		Map<String, Integer> rez = new HashMap<>();
		
		while(resultSet.next()){		
			int suma = resultSet.getInt("suma"); 
			int year = resultSet.getInt("year");
			int month = resultSet.getInt("month");
			
			String key = Integer.toString(year) + " - " + Integer.toString(month);
			
			rez.put(key, suma);
		}
		
		zatvoriVezuSBazom(connection);		
		return rez;
		
	}

	public static Map<String, Integer> dohvatiStatistikuBrojaPopravaka() throws Exception { 
		Connection connection = SpojiSeNaBazu();
		
		String queryString = "SELECT YEAR(FROM_UNIXTIME(`tstamp`)) AS year, "
				+ "MONTH(FROM_UNIXTIME(`tstamp`)) AS month, "
				+ "COUNT(*) AS suma "
				+ "FROM `popravak` "
				+ "GROUP BY 1, 2 "
				+ "ORDER BY 1, 2";
		PreparedStatement preparedStatement = connection.prepareStatement(queryString);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		Map<String, Integer> rez = new HashMap<>();
		
		while(resultSet.next()){		
			int suma = resultSet.getInt("suma"); 
			int year = resultSet.getInt("year");
			int month = resultSet.getInt("month");
			
			String key = Integer.toString(year) + " - " + Integer.toString(month);
			
			rez.put(key, suma);
		}
		
		zatvoriVezuSBazom(connection);		
		return rez;
		
	}
}
