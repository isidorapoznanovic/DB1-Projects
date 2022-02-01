package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import dao.DrzavaDAO;
import dao.SkakacDAO;
import dao.SkakaonicaDAO;
import dao.SkokDAO;
import dao.impl.DrzavaDAOImpl;
import dao.impl.SkakacDAOImpl;
import dao.impl.SkakaonicaDAOImpl;
import dao.impl.SkokDAOImpl;
import dto.complexquery1.SkakaonicaSaSkokovimaDTO;
import dto.complexquery2.DrzaveSaSkokovimaDTO;
import dto.complexquery3.TehnikalijeTransakcijeDTO;
import dto.complexquery4.SkakaonicaSaDrzavom;
import model.Drzava;
import model.Skakaonica;
import model.Skok;



public class ComplexFuncionalityService {
	private static final DrzavaDAO drzavaDAO = new DrzavaDAOImpl();
	private static final SkakaonicaDAO skakaonicaDAO = new SkakaonicaDAOImpl();
	private static final SkakacDAO skakacDAO = new SkakacDAOImpl();
	private static final SkokDAO skokDAO = new SkokDAOImpl();

	//zad 3
	public SkakaonicaSaSkokovimaDTO zad3(String id) throws SQLException{
		SkakaonicaSaSkokovimaDTO s = new SkakaonicaSaSkokovimaDTO();
		s.setSkakaonica(skakaonicaDAO.findById(id));
		ArrayList<Skok> sviSkokovi = (ArrayList<Skok>) skokDAO.findAll();
		ArrayList<Skok> trazeniSkokovi = new ArrayList<Skok>();
		HashSet<Integer> skakaci = new HashSet<Integer>();
		for(Skok skok : sviSkokovi){
			if(skok.getIdSkakkaonice().equals(id)){
				trazeniSkokovi.add(skok);
				skakaci.add(skok.getIdSkakaca());
			}
		}
		s.setSkokovi(trazeniSkokovi);
		s.setBrSkakaca(skakaci.size());
		return s;
	}
	
	//zad 4
	public List<DrzaveSaSkokovimaDTO> zad4() throws SQLException{
		List<DrzaveSaSkokovimaDTO> dtos = new ArrayList<DrzaveSaSkokovimaDTO>();
		
		for(Drzava d : drzavaDAO.findAll()){
			DrzaveSaSkokovimaDTO dto = new DrzaveSaSkokovimaDTO();
			dto.setDrzava(d);
			dto.setSkokovi((List<Skok>) skokDAO.uDrzaviskaceSKizDrzave(d.getId()));
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	//zad 5
	public TehnikalijeTransakcijeDTO zad5(String idSkok, int bvetar) throws SQLException{
		TehnikalijeTransakcijeDTO dto = new TehnikalijeTransakcijeDTO();
		if(!skokDAO.existsById(idSkok)){
			dto.setPostojiSkok(false);
			return dto;
		}else{
			dto.setPostojiSkok(true);
			dto.setBvetar(bvetar);
			dto.setSkokStari(skokDAO.findById(idSkok));
			dto.setSkakac(skakacDAO.findById(skokDAO.findById(idSkok).getIdSkakaca()));
			dto = skokDAO.transakcija(dto);
		}
		return dto;
	} 
	
	//zad 6
	public List<SkakaonicaSaDrzavom> zad6(int a, int b) throws SQLException{
		List<SkakaonicaSaDrzavom> dtos = new ArrayList<SkakaonicaSaDrzavom>();
		
		for(Skakaonica s : skakaonicaDAO.nadjiUIntervalu(a, b)){
			SkakaonicaSaDrzavom dto = new SkakaonicaSaDrzavom();
			dto.setSkakaonica(s);
			dto.setDrzava(drzavaDAO.findById(s.getIdDrzave()));
			dtos.add(dto);
		}
		
		return dtos;
	}
}
