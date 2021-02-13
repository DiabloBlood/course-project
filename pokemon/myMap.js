function loadMapScenario() {
	var map = new Microsoft.Maps.Map(document.getElementById('myMap'), {
		credentials: 'Asx_UTnt4xMGMIuJrOskse2TcIrhxCdRd9oB1joMEfzJVv2Bzd-qAUjgf2_KKk7I'
	});
	var pushpin = new Microsoft.Maps.Pushpin(map.getCenter(), 
				{ icon: 'https://github.com/DiabloBlood/Pokemon_Frontend/blob/master/images/icon.png',
		anchor: new Microsoft.Maps.Point(12, 39) });
	map.entities.push(pushpin);

}
