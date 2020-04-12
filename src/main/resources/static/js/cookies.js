



$(document).ready(function(){
	const cookieContainer = document.querySelector(".cookie-container");
	const cookieButton = document.querySelector(".cookie-accept");
	
	cookieButton.addEventListener("click", () => {
		cookieContainer.classList.remove("active");
		Cookies.set("fastyle", "true", {expires: 365});
		Cookies.get();
		
	});
	
	
	setTimeout(()=>{
		
		if(!Cookies.get("fastyle")){
		cookieContainer.classList.add("active");
		}
		
	}, 2000);
	

		});


 


