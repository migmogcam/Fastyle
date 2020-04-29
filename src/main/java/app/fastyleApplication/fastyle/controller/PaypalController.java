package app.fastyleApplication.fastyle.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	private static final Logger logger = Logger.getLogger(CitaController.class.getName());

	public static final String PAYPAL_SUCCESS_URL = "pagoCorrecto";
	public static final String PAYPAL_CANCEL_URL = "cancel";

	@PostMapping("/nuevaCita")
	public String nuevaCita(@ModelAttribute("cita") CitaDTO order, HttpServletRequest request, HttpSession session,
			Model model) {
		Esteticista esteticista = (Esteticista) request.getSession().getAttribute("servicios");
		ServicioEstetico servicio = (ServicioEstetico) request.getSession().getAttribute("servicioEstetico");
		LocalDate futuro = LocalDate.parse(order.getFecha());
		LocalTime tFuturo = LocalTime.parse(order.getHora());
		LocalDate ahora = LocalDate.now();
		LocalTime tAhora = LocalTime.now();
		Integer año = ahora.getYear();
		Integer mes = ahora.getMonthValue();
		Integer dia = ahora.getDayOfMonth();
		Integer hora = tAhora.getHour();
		Integer minuto = tAhora.getMinute();
		String stringAño = año.toString();
		String stringMes = mes.toString();
		String stringDia = dia.toString();
		String stringHora = hora.toString();
		String stringMinuto = minuto.toString();
		if (mes < 10) {
			stringMes = "0" + stringMes;
		}
		if (dia < 10) {
			stringDia = "0" + stringDia;
		}
		if (hora < 10) {
			stringHora = "0" + stringHora;
		}
		if (minuto < 10) {
			stringMinuto = "0" + stringMinuto;
		}
		String momento = stringAño + "-" + stringMes + "-" + stringDia + " " + stringHora + ":" + stringMinuto;

		if (futuro.isAfter(ahora) || futuro.isEqual(ahora)) {
			if (futuro.isEqual(ahora) && tFuturo.isBefore(tAhora)) {
				session.setAttribute("fallo", true);
				return "redirect:/citaCrear/" + servicio.getId() + "/" + esteticista.getId();
			} else {
				try {
					Cita cita = new Cita();
					String username = SecurityContextHolder.getContext().getAuthentication().getName();
					Usuario u = this.usuarioService.findByUsuario(username);
					Cliente c = this.clienteService.findByUsuario(u);
					cita.setCliente(c);
					cita.setDetalle(order.getDetalle());
					cita.setEsteticista(esteticista);
					cita.setFecha(order.getFecha());
					cita.setMomento(momento);
					cita.setHora(order.getHora());
					cita.setServicioEstetico(servicio);
					cita.setEstado("PENDIENTE");
					citaService.createOrUpdateCita(cita);
				} catch (Exception e) {
					logger.log(Logger.Level.FATAL, e.getMessage());
				}
			}
		} else {
			session.setAttribute("fallo", true);
			return "redirect:/citaCrear/" + servicio.getId() + "/" + esteticista.getId();
		}
		session.setAttribute("correcto", true);
		return "redirect:/misCitas";
	}

	@PostMapping("/pay")
	public String payment(@ModelAttribute("cita") Cita order, HttpServletRequest request, HttpSession session,
			Model model) {
		String redirect1 = "redirect:/";
		try {
			String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
			String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
			Payment payment = service.createPayment(order.getServicioEstetico().getPrecio().toString(), "EUR", "paypal",
					"sale", "Compra de servicio Estetico de " + order.getServicioEstetico().getTipo(), cancelUrl,
					successUrl);
			for (Links link : payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {
					session.setAttribute("citaSave", order);
					return "redirect:" + link.getHref();
				}
			}
		} catch (PayPalRESTException e) {

			e.printStackTrace();

		}
		return redirect1;
	}

	@GetMapping("/pagarPuntos/{id}")
	public String pagarPuntos(@PathVariable("id") Integer id, Model model) {
		String redirect2 = "redirect:/";
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			Cliente c = this.clienteService.findByUsuario(u);
			Cita cita = this.citaService.getCitaById(id);
			if (c.getPuntos() >= cita.getServicioEstetico().getPrecio()) {
				Double puntos = c.getPuntos()-cita.getServicioEstetico().getPrecio();		
				c.setPuntos(puntos);
				LocalDate ahora1 = LocalDate.now();
				LocalTime tAhora1 = LocalTime.now();
				Integer año1 = ahora1.getYear();
				Integer mes1 = ahora1.getMonthValue();
				Integer dia1 = ahora1.getDayOfMonth();
				Integer hora1 = tAhora1.getHour();
				Integer minuto1 = tAhora1.getMinute();
				String stringAño1 = año1.toString();
				String stringMes1 = mes1.toString();
				String stringDia1 = dia1.toString();
				String stringHora1= hora1.toString();
				String stringMinuto1= minuto1.toString();
				if (mes1 < 10) {
					stringMes1 = "0" + stringMes1;
				}
				if (dia1 < 10) {
					stringDia1 = "0" + stringDia1;
				}
				if (hora1 < 10) {
					stringHora1 = "0" + stringHora1;
				}
				if (minuto1 < 10) {
					stringMinuto1 = "0" + stringMinuto1;
				}
				String momento1 = stringAño1 + "-" + stringMes1 + "-" + stringDia1 + " " + stringHora1 + ":" + stringMinuto1;
				cita.setMomento(momento1);
				this.clienteService.createOrUpdateCliente(c);
			} else {
				return "cancel";
			}
			cita.setEstado("PAGADA");
			citaService.createOrUpdateCita(cita);
			return redirect2;
		} catch (Exception e) {
			logger.log(Logger.Level.FATAL, e.getMessage());
		}
		return redirect2;
	}

	@GetMapping(value = PAYPAL_CANCEL_URL)
	public String cancelPay() {
		return "cancel";
	}

	@GetMapping(value = PAYPAL_SUCCESS_URL)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
			HttpServletRequest request) {
		Cita cita = (Cita) request.getSession().getAttribute("citaSave");
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario u = this.usuarioService.findByUsuario(username);
		Cliente c = this.clienteService.findByUsuario(u);
		try {
			Payment payment = service.executePayment(paymentId, payerId);
			if (payment.getState().equals("approved")) {
				try {
					Double puntos = (cita.getServicioEstetico().getPrecio()*0.1) + c.getPuntos();
					c.setPuntos(puntos);
					this.clienteService.createOrUpdateCliente(c);
					LocalDate ahora2 = LocalDate.now();
					LocalTime tAhora2= LocalTime.now();
					Integer año2= ahora2.getYear();
					Integer mes2 = ahora2.getMonthValue();
					Integer dia2 = ahora2.getDayOfMonth();
					Integer hora2 = tAhora2.getHour();
					Integer minuto2 = tAhora2.getMinute();
					String stringAño2 = año2.toString();
					String stringMes2 = mes2.toString();
					String stringDia2 = dia2.toString();
					String stringHora2 = hora2.toString();
					String stringMinuto2 = minuto2.toString();
					if (mes2 < 10) {
						stringMes2 = "0" + stringMes2;
					}
					if (dia2 < 10) {
						stringDia2 = "0" + stringDia2;
					}
					if (hora2 < 10) {
						stringHora2 = "0" + stringHora2;
					}
					if (minuto2 < 10) {
						stringMinuto2 = "0" + stringMinuto2;
					}
					String momento2 = stringAño2 + "-" + stringMes2 + "-" + stringDia2 + " " + stringHora2 + ":" + stringMinuto2;
					cita.setMomento(momento2);
					cita.setEstado("PAGADA");
					citaService.createOrUpdateCita(cita);
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
