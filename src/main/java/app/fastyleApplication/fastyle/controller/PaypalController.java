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

		Cita saved = new Cita();

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
					saved = citaService.createOrUpdateCita(cita);
				} catch (Exception e) {
					e.printStackTrace();
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
		return "redirect:/";
	}

	@GetMapping("/pagarPuntos/{id}")
	public String pagarPuntos(@PathVariable("id") Integer id, Model model) {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario u = this.usuarioService.findByUsuario(username);
			Cliente c = this.clienteService.findByUsuario(u);
			Cita cita = this.citaService.getCitaById(id);
			if (c.getPuntos() >= cita.getServicioEstetico().getPrecio()) {
				Double puntos = c.getPuntos()-cita.getServicioEstetico().getPrecio();		
				c.setPuntos(puntos);
				LocalDate ahoraPuntos1 = LocalDate.now();
				LocalTime tAhoraPuntos1 = LocalTime.now();
				Integer añoPuntos1 = ahoraPuntos1.getYear();
				Integer mesPuntos1 = ahoraPuntos1.getMonthValue();
				Integer diaPuntos1 = ahoraPuntos1.getDayOfMonth();
				Integer horaPuntos1 = tAhoraPuntos1.getHour();
				Integer minutoPuntos1 = tAhoraPuntos1.getMinute();
				String stringAñoPuntos1 = añoPuntos1.toString();
				String stringMesPuntos1 = mesPuntos1.toString();
				String stringDiaPuntos1 = diaPuntos1.toString();
				String stringHoraPuntos1 = horaPuntos1.toString();
				String stringMinutoPuntos1 = minutoPuntos1.toString();
				if (mesPuntos1 < 10) {
					stringMesPuntos1 = "0" + stringMesPuntos1;
				}
				if (diaPuntos1 < 10) {
					stringDiaPuntos1 = "0" + stringDiaPuntos1;
				}
				if (horaPuntos1 < 10) {
					stringHoraPuntos1 = "0" + stringHoraPuntos1;
				}
				if (minutoPuntos1 < 10) {
					stringMinutoPuntos1 = "0" + stringMinutoPuntos1;
				}
				String momentoPuntos1 = stringAñoPuntos1 + "-" + stringMesPuntos1 + "-" + stringDiaPuntos1 + " " + stringHoraPuntos1 + ":" + stringMinutoPuntos1;
				cita.setMomento(momentoPuntos1);
				this.clienteService.createOrUpdateCliente(c);
			} else {
				return "cancel";
			}
			cita.setEstado("PAGADA");
			citaService.createOrUpdateCita(cita);
			return "redirect:/";
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
			HttpServletRequest request) {
		Cita cita = (Cita) request.getSession().getAttribute("citaSave");
		Cita saved = new Cita();
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
					LocalDate ahoraPago1 = LocalDate.now();
					LocalTime tAhoraPago1 = LocalTime.now();
					Integer añoPago1 = ahoraPago1.getYear();
					Integer mesPago1 = ahoraPago1.getMonthValue();
					Integer diaPago1 = ahoraPago1.getDayOfMonth();
					Integer horaPago1 = tAhoraPago1.getHour();
					Integer minutoPago1 = tAhoraPago1.getMinute();
					String stringAñoPago1 = añoPago1.toString();
					String stringMesPago1 = mesPago1.toString();
					String stringDiaPago1 = diaPago1.toString();
					String stringHoraPago1 = horaPago1.toString();
					String stringMinutoPago1 = minutoPago1.toString();
					if (mesPago1 < 10) {
						stringMesPago1 = "0" + stringMesPago1;
					}
					if (diaPago1 < 10) {
						stringDiaPago1 = "0" + stringDiaPago1;
					}
					if (horaPago1 < 10) {
						stringHoraPago1 = "0" + stringHoraPago1;
					}
					if (minutoPago1 < 10) {
						stringMinutoPago1 = "0" + stringMinutoPago1;
					}
					String momentoPago1 = stringAñoPago1 + "-" + stringMesPago1 + "-" + stringDiaPago1 + " " + stringHoraPago1 + ":" + stringMinutoPago1;
					cita.setMomento(momentoPago1);
					cita.setEstado("PAGADA");
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
