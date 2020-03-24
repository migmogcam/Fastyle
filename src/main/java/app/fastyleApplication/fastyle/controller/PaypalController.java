package app.fastyleApplication.fastyle.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Order;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;

import app.fastyleApplication.fastyle.dto.CitaDTO;
import app.fastyleApplication.fastyle.model.Cita;
import app.fastyleApplication.fastyle.model.Esteticista;
import app.fastyleApplication.fastyle.model.ServicioEstetico;
import app.fastyleApplication.fastyle.services.CitaService;
import app.fastyleApplication.fastyle.services.PaypalService;
import app.fastyleApplication.fastyle.util.URLUtils;

@Controller
public class PaypalController {

	@Autowired
	PaypalService service;
	
	@Autowired
	CitaService citaService;

	public static final String PAYPAL_SUCCESS_URL = "pagoCorrecto";
	public static final String PAYPAL_CANCEL_URL = "cancel";

	@PostMapping("/pay")
	public String payment(@ModelAttribute("cita") CitaDTO order,HttpServletRequest request, HttpSession session) {
		try {
			Esteticista esteticista = (Esteticista) request.getSession().getAttribute("servicios");
			ServicioEstetico servicio = (ServicioEstetico) request.getSession().getAttribute("servicioEstetico");
			String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
			String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
			Payment payment = service.createPayment(servicio.getPrecio().toString(), "EUR", "paypal",
					"sale", "Compra de servicio Estetico de "+ servicio.getTipo(), cancelUrl,
					successUrl);
			Cita cita = new Cita();
//TODO Cliente loggeado
//			cita.setCliente(cliente);
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
	    				// TODO: pagina de error
	    			}
	                return "pagoCorrecto";
	            }
	        } catch (PayPalRESTException e) {
	         System.out.println(e.getMessage());
	        }
	        return "pagoCorrecto";
	    }

}
