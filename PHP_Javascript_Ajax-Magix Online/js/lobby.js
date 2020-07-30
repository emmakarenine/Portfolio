//*********************************/
// Makraphone Phouttama
// TP Magix
// Remise: Mardi, 28 avril 2020
//*********************************/

let BACT_COUNT = 1;
let doctor = null;
let ctx = null;
let canvas = null;
let spriteList = [];

let bgimage = new Image();

window.addEventListener("load", () => {

	canvas = document.getElementById("lobby-canvas");
	ctx = canvas.getContext("2d");

	doctor = new Doctor()
	spriteList.push(doctor);
	// for (let i = 0; i < BACT_COUNT; i++) {
	spriteList.push(new Bacteriophage());
	//}

	//Mettre l'image dans le tick
	//bgimage.src = "images/cell_vert.jpeg";

	tick();

});

const tick = () => {

	// Tjrs clearer le ctx
	ctx.clearRect(0, 0, canvas.width, canvas.height);
	// ctx.fillStyle = "rgba(0, 81, 144,1)";

	//ctx.fillRect(0, 0, canvas.width, canvas.height);

	// Remettre le background
	// if (bgimage.complete) { // le dwl est fait

	// 	ctx.drawImage(bg_image, 0, 0);
	// }

	if (doctor.isAway() ) {
		spriteList.push(new Bacteriophage());
	}

	// Redessiner les sprites
	for (let i = 0; i < spriteList.length; i++) {

		spriteList[i].tick();
	}

	window.requestAnimationFrame(tick);
}

// Styliser le chat
const applyStyles = iframe => {
	let styles = {
		fontColor : "#000",
		backgroundColor : "rgba(201, 221, 242, 1)",
		fontGoogleName : "Sofia",
		fontSize : "20px",
		height: "300px;"
	}

	iframe.contentWindow.postMessage(JSON.stringify(styles), "*");
}
