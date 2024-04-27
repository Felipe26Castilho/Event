package br.gov.sp.etesp.event.controller;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.gov.sp.etesp.event.model.EventoEntity;
import br.gov.sp.etesp.event.repository.EventoRepository;
import br.gov.sp.etesp.event.util.Status;



@Controller
public class HomeController {
	
	@Autowired 
	EventoRepository repository;
	
	@GetMapping("/")
	public String abrirHome(Model model1) {	
		List<EventoEntity> eventos = repository.findAll();
		model1.addAttribute("eventos", eventos);
		
		return "home";
	}
	
	@PostMapping("/adicionar")	
	public ModelAndView adicionarEvento(EventoEntity evento) {
		
		
				evento.setDtCadatro(LocalDate.now());
				evento.setStatus(Status.ABERTO.name());
				
			    repository.save(evento);	
			    
			    List<EventoEntity> eventos = repository.findAll();
				 
		 ModelAndView view = new ModelAndView("home");
		 view.addObject("eventos",eventos);			
		return view;		
	}
	
	@GetMapping("/encerrar/{id}")
	public String encerrarEvento( @PathVariable long id, Model model) {
		EventoEntity evento = repository.findById(id).get();
		evento.setStatus(Status.FECHADO.name());
		evento.setDtncerramento(LocalDate.now());
		repository.save(evento);
		List<EventoEntity> eventos = repository.findAll();
		model.addAttribute("eventos", eventos); 
		return "home";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluirEvento( @PathVariable long id, Model model) {
		repository.deleteById(id);
		List<EventoEntity> eventos = repository.findAll();
		model.addAttribute("eventos", eventos); 
		return "home";
		
	}
	
	@GetMapping("/editar/{id}")
	public String editarEvento( @PathVariable long id, Model model) {
		var evento =repository.findById(id).get();
		model.addAttribute("evento", evento); 
		return "editar-evento";
		
	}

} 
