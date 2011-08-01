/**
 * 
 */
function listeToArray(){
	var liste = "un,deux,troix";
	return liste.split(',');
}

function regExpFn(){
	var texte='Il est temps de mettre un veste car le temps n\'est plus estival';
	return texte.replace(/\best\b/g, 'était');
}

function dateJour(){
	var date = new Date();
	var date2 = date + 7 * 24 * 60 * 60 * 1000;
	return date2;
}

function regExpFn2(){
	var texte='f-c.o,u-.p';
	return texte.replace(/\W/g, ',');
}

function testParam(){
	for (var i = 0;i<testParam.length;i++){
		alert('param ' + i + ' : ' + arguments[i]);
	}
}

function fonctionSurI(i,f){
	return f(i);
}

