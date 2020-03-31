package app.fastyleApplication.fastyle.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import app.fastyleApplication.fastyle.dto.CitaDTO;
import app.fastyleApplication.fastyle.model.Cita;
import app.fastyleApplication.fastyle.model.Cliente;
import app.fastyleApplication.fastyle.model.Esteticista;
import app.fastyleApplication.fastyle.model.ServicioEstetico;
import app.fastyleApplication.fastyle.model.Usuario;
import app.fastyleApplication.fastyle.services.CitaService;
import app.fastyleApplication.fastyle.services.ClienteService;
import app.fastyleApplication.fastyle.services.PaypalService;
import app.fastyleApplication.fastyle.services.UsuarioService;
import app.fastyleApplication.fastyle.util.URLUtils;

@Controller
public class PaypalController {

	@Autowired
	PaypalService service;
	
	@Autowired
	CitaService citaService;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	UsuarioService usuarioService;

	public static final String PAYPAL_SUCCESS_URL = "pagoCorrecto";
	public static final String PAYPAL_CANCEL_URL = "cancel";

	@PostMapping("/pay")
	public String payment(@ModelAttribute("cita") CitaDTO order,HttpServletRequest request, HttpSession session, Model model) {
		Esteticista esteticista = (Esteticista) request.getSession().getAttribute("servicios");
		ServicioEstetico servicio = (ServicioEstetico) request.getSession().getAttribute("servicioEstetico");
		LocalDate futuro = LocalDate.parse(order.getFecha());
		LocalTime tFuturo = LocalTime.parse(order.getHora());
		LocalDate ahora = LocalDate.now();
		LocalTime tAhora = LocalTime.now();
		
		if(futuro.isAfter(ahora) || futuro.isEqual(ahora)) {
		if(futuro.isEqual(ahora) && tFuturo.isBefore(tAhora)) {
			session.setAttribute("fallo", true);
			return "redirect:/citaCrear/"+ servicio.getId()+"/"+esteticista.getId();
		}else {
			try {
				String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
				String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
				Payment payment = service.createPayment(servicio.getPrecio().toString(), "EUR", "paypal",
						"sale", "Compra de servicio Estetico de "+ servicio.getTipo(), cancelUrl,
						successUrl);
				Cita cita = new Cita();
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
				Usuario u = this.usuarioService.findByUsuario(username);
				Cliente c = this.clienteService.findByUsuario(u);
				cita.setCliente(c);
				cita.setDetalle(order.getDetalle());
				cita.setEsteticista(esteticista);
				cita.setFecha(order.getFecha());
				cita.setHora(order.getHora());
				cita.setServicioEstetico(servicio);
				
				for(Links link:payment.getLinks()) {
					if(link.getRel().equals("approval_url")) {
						session.setAttribute("citaSave", cita);
						return "redirect:"+link.getHref();
					}
				}
				
			} catch (PayPalRESTException e) {
			
				e.printStackTrace();
			}
		}
		}else {
			session.setAttribute("fallo", true);
			return "redirect:/citaCrear/"+ servicio.getId()+"/"+esteticista.getId();
		}
		return "redirect:/";
	}
	
//	@PostMapping("/pay")
//	public String paymentTest(@ModelAttribute("cita") Cita order,HttpServletRequest request) {
//		return "pagoCorrecto";
//	}
	
	 @GetMapping(value = PAYPAL_CANCEL_URL)
	    public String cancelPay() {
	        return "cancel";
	    }

	    @GetMapping(value = PAYPAL_SUCCESS_URL)
	    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,HttpServletRequest request) {
	    	Cita cita =  (Cita) request.getSession().getAttribute("citaSave");
	    	Cita saved = new Cita();
	        try {
	            Payment payment = service.executePayment(paymentId, payerId);
	            System.out.println(payment.toJSON());
	            if (payment.getState().equals("approved")) {
	            	try {
	    				saved = citaService.createOrUpdateCita(cita);
	    			} catch (Exception e) {
	    				return "error";
	    			}
	                return "pagoCorrecto";
	            }
	        } catch (PayPalRESTException e) {
	         System.out.println(e.getMessage());
	        }
	        return "pagoCorrecto";
	    }

}
