//*********************************/
// Makraphone Phouttama
// TP Magix
// Remise: Mardi, 28 avril 2020
//*********************************/

let spriteList = [];

onmousemove = (event) => {

	// Capter la position de la souris
	let x = event.pageX;
	let y = event.pageY;

	// Lui assigner une image de virus (un peu plus bas que le curseur)
	spriteList.push( new VirusVert(x-25, y+25) );
}

window.addEventListener("load", () => {

	tick();
})

const tick = () => {

	//tick passe a travers la collection d'objets a animer
	for (let i = 0; i < spriteList.length; i++) {

		const element = spriteList[i];
		let y = element.tick();


		let alive = element.tick();

		if (!alive) {
			spriteList.splice(i, 1); // Suppression des virus rendus tres bas
			i--;
		}
	}

	window.requestAnimationFrame(tick);
}