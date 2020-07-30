//*********************************/
// Makraphone Phouttama
// TP Magix
// Remise: Mardi, 28 avril 2020
//*********************************/

class VirusVert
{
	constructor(x, y)
	{
		this.x = x;
		this.y = y;
		this.speedY = 0;

		this.node = document.createElement("div");
		this.node.className = "login-virus";
		this.node.style.backgroundImage = "url(images/login-virus-vert.png)";
		this.node.style.backgroundRepeat = "no-repeat";
		this.node.style.position = 'absolute';
		this.node.style.left = x+"px";
		this.node.style.top = y+"px";
		this.node.style.height = 100+'px';
		this.node.style.width = 100+'px';

		document.body.appendChild(this.node);
	}

	tick ()
	{
		this.speedY += 0.05;
		this.y += this.speedY; // va tomber

		let alive = this.y < 900;

		this.node.style.top = this.y + "px";

		if (!alive) {
			this.node.remove(); // se retire soi-meme
		}

		return alive;
	}
}