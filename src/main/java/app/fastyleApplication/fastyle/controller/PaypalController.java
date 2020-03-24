//package app.fastyleApplication.fastyle.controller;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.paypal.api.payments.Links;
//import com.paypal.api.payments.Order;
//import com.paypal.api.payments.Payment;
//import com.paypal.base.rest.PayPalRESTException;
//
//import app.fastyleApplication.fastyle.model.Cita;
//import app.fastyleApplication.fastyle.services.PaypalService;
//import app.fastyleApplication.fastyle.util.URLUtils;
//
//@Controller
//public class PaypalController {
//
//	@Autowired
//	PaypalService service;
//
//	public static final String PAYPAL_SUCCESS_URL = "pay/success";
//	public static final String PAYPAL_CANCEL_URL = "pay/cancel";
//
////	@PostMapping("/pay")
////	public String payment(@ModelAttribute("cita") Cita order,HttpServletRequest request) {
////		try {
////			String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
////			String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
////			Payment payment = service.createPayment(10.00, "EUR", "paypal",
////					"sale", "Compra de servicio Estetico", cancelUrl,
////					successUrl);
////			for(Links link:payment.getLinks()) {
////				if(link.getRel().equals("approval_url")) {
////					return "redirect:"+link.getHref();
////				}
////			}
////			
////		} catch (PayPalRESTException e) {
////		
////			e.printStackTrace();
////		}
////		return "redirect:/";
////	}
//	
//	@PostMapping("/pay")
//	public String paymentTest(@ModelAttribute("cita") Cita order,HttpServletRequest request) {
//		return "pagoCorrecto";
//	}
//	
//	 @GetMapping(value = PAYPAL_CANCEL_URL)
//	    public String cancelPay() {
//	        return "cancel";
//	    }
//
//	    @GetMapping(value = PAYPAL_SUCCESS_URL)
//	    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
//	        try {
//	            Payment payment = service.executePayment(paymentId, payerId);
//	            System.out.println(payment.toJSON());
//	            if (payment.getState().equals("approved")) {
//	                return "success";
//	            }
//	        } catch (PayPalRESTException e) {
//	         System.out.println(e.getMessage());
//	        }
//	        return "redirect:/";
//	    }
//
//}
