/**
 * Raphael extention
 */
Raphael.fn.makeLink = function (from, to) {
	/**
	 * create links member if not
	 */
    if (from.links == 'undefined') from.link = [];
    if (to.links   == 'undefined') to.link = [];

    /**
     * compute path
     */
    var bb1 = from.getBBox(),
	    bb2 = to.getBBox(),
	    /**
	     * build path
	     */
		x1 = bb1.x + bb1.width / 2,
		y1 = bb1.y + bb1.height / 2,
		x2 = bb2.x + bb2.width / 2,
		y2 = bb2.y + bb2.height / 2,
		xm = x1 + (x2 - x1) / 2, 
		ym = y1 + (y2 - y1) / 2,
	    /**
	     * leaf elements
	     */
	    path = {
	    		x1: x1.toFixed(0), y1: y1.toFixed(0),
	    		x2: xm.toFixed(0), y2: y1.toFixed(0),
	    		x3: xm.toFixed(0), y3: ym.toFixed(0),
	    		x4: xm.toFixed(0), y4: y2.toFixed(0),
	    		x5: x2.toFixed(0), y5: y2.toFixed(0)
    	};
    
	paper.text(path.x1, path.y1, "1: "+path.x1+","+path.y1);
	paper.text(path.x2, path.y2, "2: "+path.x2+","+path.y2);
	paper.text(path.x3, path.y3, "3: "+path.x3+","+path.y3);
	paper.text(path.x4, path.y4, "4: "+path.x4+","+path.y4);
	paper.text(path.x5, path.y5, "5: "+path.x5+","+path.y5);

	var pathway = [ 
		"M"+path.x1+","+path.y1+
		"Q"+path.x2+","+path.y2+" "+path.x3+","+path.y3+
		"T"+path.x5+","+path.y5
		];

	paper.path(pathway);

    console.log('computed path: ' + JSON.stringify(path));
}

Raphael.fn.connection = function (obj1, obj2, line, bg) {
    if (obj1.line && obj1.from && obj1.to) {
        line = obj1;
        obj1 = line.from;
        obj2 = line.to;
    }
    var bb1 = obj1.getBBox(),
        bb2 = obj2.getBBox(),
        p = [{x: bb1.x + bb1.width / 2, y: bb1.y - 1},
        {x: bb1.x + bb1.width / 2, y: bb1.y + bb1.height + 1},
        {x: bb1.x - 1, y: bb1.y + bb1.height / 2},
        {x: bb1.x + bb1.width + 1, y: bb1.y + bb1.height / 2},
        {x: bb2.x + bb2.width / 2, y: bb2.y - 1},
        {x: bb2.x + bb2.width / 2, y: bb2.y + bb2.height + 1},
        {x: bb2.x - 1, y: bb2.y + bb2.height / 2},
        {x: bb2.x + bb2.width + 1, y: bb2.y + bb2.height / 2}],
        d = {}, dis = [];
    for (var i = 0; i < 4; i++) {
        for (var j = 4; j < 8; j++) {
            var dx = Math.abs(p[i].x - p[j].x),
                dy = Math.abs(p[i].y - p[j].y);
            if ((i == j - 4) || (((i != 3 && j != 6) || p[i].x < p[j].x) && ((i != 2 && j != 7) || p[i].x > p[j].x) && ((i != 0 && j != 5) || p[i].y > p[j].y) && ((i != 1 && j != 4) || p[i].y < p[j].y))) {
                dis.push(dx + dy);
                d[dis[dis.length - 1]] = [i, j];
            }
        }
    }
    if (dis.length == 0) {
        var res = [0, 4];
    } else {
        res = d[Math.min.apply(Math, dis)];
    }
    var x1 = p[res[0]].x,
        y1 = p[res[0]].y,
        x4 = p[res[1]].x,
        y4 = p[res[1]].y;
    dx = Math.max(Math.abs(x1 - x4) / 2, 10);
    dy = Math.max(Math.abs(y1 - y4) / 2, 10);
    var x2 = [x1, x1, x1 - dx, x1 + dx][res[0]].toFixed(3),
        y2 = [y1 - dy, y1 + dy, y1, y1][res[0]].toFixed(3),
        x3 = [0, 0, 0, 0, x4, x4, x4 - dx, x4 + dx][res[1]].toFixed(3),
        y3 = [0, 0, 0, 0, y1 + dy, y1 - dy, y4, y4][res[1]].toFixed(3);
    var path = ["M", x1.toFixed(3), y1.toFixed(3), "C", x2, y2, x3, y3, x4.toFixed(3), y4.toFixed(3)].join(",");
    if (line && line.line) {
        line.bg && line.bg.attr({path: path});
        line.line.attr({path: path});
    } else {
        var color = typeof line == "string" ? line : "#000";
        return {
            bg: bg && bg.split && this.path(path).attr({stroke: bg.split("|")[0], fill: "none", "stroke-width": bg.split("|")[1] || 3}),
            line: this.path(path).attr({stroke: color, fill: "none"}),
            from: obj1,
            to: obj2
        };
    }
};

var el;
var paper;

/**
 * handle drag phase
 * @returns
 */
function dragElement() {
    this.ox = this.attr("x");
    this.oy = this.attr("y");
    this.animate({"fill-opacity": .2}, 500);
}

/**
 * handle moving phase of an element
 * @param dx
 * @param dy
 */
function moveElement(dx, dy) {
    moveNode(this, this.ox + dx, this.oy + dy);
}

/**
 * release element
 */
function releaseElement() {
    this.animate({"fill-opacity": 0}, 500);
    /**
     * update last position
     */
    moveNode(this, this.attr("x"), this.attr("y"));
}

function add(shape) {
	_session.model.nodes.push(shape);
	buildGraph(_session, _session.config);
}

/**
 * move embeded element to position {x,y}
 * @param embeded
 * @param x
 * @param y
 */
function moveNode(element, x, y) {
	var node = element.owner;

    /**
     * fix all basic element of this
     * shape
     */
    node.shape.attr({x: x, y: y});
    console.log('move shape to ' + JSON.stringify({x: x, y: y}));

    /**
     * fix all elements linked to this shape
     */
	console.log('move '+node.elements.length+' element(s) to ' + JSON.stringify({cx: x, cy: y}));
	for (var i = 0, ii = node.elements.length; i < ii; i++) {
		switch(hash(node.elements[i])) {
			case 0:
				 node.elements[i].attr({cx: x + node.elements[i].dx, cy: y + node.elements[i].dy});
				 break;
			case 1:
				 node.elements[i].attr({x: x + node.elements[i].dx, y: y + node.elements[i].dy});
				 break;
			default:
		}
		console.log('move element ' + node.elements[i]._ref + ' to ' + JSON.stringify({cx: x + node.elements[i].dx, cy: y + node.elements[i].dy}));
	}

    /**
     * draw connector
     */
    for (var i = _session.model.connections.length; i--;) {
        paper.connection(_session.model.connections[i]);
    }
    paper.safari();

    /**
	 * fix core element
	 */
    //element.embeded.core.x = x;
    //element.embeded.core.y = y;
    
    //dumpShape(element);
}

function hoverIn() {
	this.g = this.glow();
}

function hoverOut() {
	this.g.remove();
}

/**
 * local session to handle
 * elements
 */
var _session = {
	/**
	 * default properties
	 */
	config:{
		anchor:{
			width:80,
			height:20,
			radius:2
		},
		aggregate:{
			width:10,
			height:10,
			rotate:45
		},
		circle:{
			iterator:20,
			radius:2
		}
	},
	/**
	 * model stored in this session
	 */
	model:{
		nodes:[
		   {
			   key:'#1',
			   x:100,
			   y:100,
			   classifier:'org.jonas.config',
			   fields:[
			           {name:'test1', type:'string', value: ''},
			           {name:'test2', type:'int', value: 0},
			           {name:'test3', type:'object', value: {key:'#2'}},
			           {name:'test4', type:'list', value: [{key:'#2'},{key:'#3'}]}
			   ]
		   },
		   {
			   key:'#2',
			   x:200,
			   y:200,
			   classifier:'org.jonas.config',
			   fields:[
			           {name:'test1', type:'string', value: ''},
			           {name:'test2', type:'int', value: 0},
			           {name:'test3', type:'object', value: {key:'#3'}},
			           {name:'test4', type:'list', value: [{key:'#2'},{key:'#3'}]}
			   ]
		   },
		   {key:'#3', x:300, y:300, classifier:'org.jonas.config', fields:[{name:'test3', type:'object', value: {key:'#4'}}]},
		   {key:'#4', x:100, y:20, classifier:'org.jonas.config', fields:[]}
		   ],
		connections:[
		   ]
	}
};

function hash(node) {
	if(node.type == 'circle') {
		return 0;
	}
	if(node.type == 'text') {
		return 1;
	}
	if(node.type == 'rect') {
		return 1;
	}
	return -1;
}

function fixElement(node, element, classifier) {
	node.elements.push(element);
	node.elements[node.last]._ref = classifier;
	node.elements[node.last].attr({"title": classifier, "fill": "#bf0000"});
	node.last++;
	return element;
}

/**
 * build a new node
 * @param node
 * @param config
 */
function buildNode(node, config) {
	/**
	 * core shape is the background element of the global
	 * shape
	 * 
	 *  ------------- 
	 * | title       |/\ <- main shape
	 *  ------------- \/
	 *        |
	 *       [ ]
	 *        |
	 *       [ ] <- field : native, array, object
	 *        |
	 *       [ ]
	 */

	node.shape = paper.rect(
			node.x,
			node.y,
    		config.anchor.width,
    		config.anchor.height,
    		config.anchor.radius);

    /**
     * register behaviour
     * - dragElement called when drag phase begin
     * - moveElement called during moving phase
     * - releaseElement called at the end
     */
	node.shape.drag(moveElement, dragElement, releaseElement);
	node.shape.attr({fill: "#bf0000", stroke: "#bf0000", "fill-opacity": 0, "stroke-width": 1, cursor: "move"});
	node.shape.owner = node;

	/**
	 * iterate on fields
	 * and build elements on the fly
	 */
    node.elements = [];
    node.last = 0;
	
	/**
	 * text property
	 */
    fixElement(
    		node,
    		paper.text(
    				node.x + config.anchor.width  / 2,
    				node.y + config.anchor.height / 2,
    				node.classifier),
			"#text");

	/**
	 * "add item" property
	 * simple rect with 45 deg of rotation
	 */
    fixElement(node, paper.rect(node.shape.getBBox().x - 20, node.shape.getBBox().y + (node.shape.getBBox().height / 4), config.aggregate.width, config.aggregate.height), "#items");

    /**
     * compute max figure height
     */
    var bottom = node.shape.getBBox().y2;

    /**
     * discover fields and build
     * object
     */
    for (var i = 0, ii = node.fields.length; i < ii; i++) {
		var field = node.fields[i];
		var element = undefined;

	    /**
	     * circle
	     */
		element = fixElement(node, paper.circle(node.x + config.anchor.width, node.shape.getBBox().y2 + config.circle.iterator * (i+1), config.circle.radius), "#circle#"+field.name);

	    /**
	     * text representation for this field
	     */
		element = fixElement(node, paper.text(node.elements[node.last-1].getBBox().x2 - 40, node.elements[node.last-1].getBBox().y2 - 2, field.name), "#field#"+field.name);
		
		/**
		 * compute bottom
		 */
		if((element.getBBox().y2+20) > bottom) bottom = (element.getBBox().y2+20);
	}

    /**
     * automatic bottom detection
     */
    node.shape.attr({height: bottom - node.shape.getBBox().y});

	/**
	 * no compute all meta data to handle
	 * those raphael objects
	 */
	for (var i = 0, ii = node.elements.length; i < ii; i++) {
		node.elements[i].owner = node;
		switch(hash(node.elements[i])) {
			case 0:
				node.elements[i].dx = node.elements[i].attr('cx') - node.x;
				node.elements[i].dy = node.elements[i].attr('cy') - node.y;
				break;
			case 1:
				node.elements[i].dx = node.elements[i].attr('x') - node.x;
				node.elements[i].dy = node.elements[i].attr('y') - node.y;
				break;
			default:
				console.log('unhandled element of type ' + node.elements[i].type);
				break;
		}
		console.log('fix element ' + node.elements[i]._ref + ' relative index is ' + node.elements[i].dx + ', ' + node.elements[i].dy);
	}
}

Raphael.fn.drawGrid = function(x, y, w, h, wv, hv, color) {
	color = color || "#cacaca";
	var path = ["M", Math.round(x) + .5, Math.round(y) + .5, "L", Math.round(x + w) + .5, Math.round(y) + .5, Math.round(x + w) + .5, Math.round(y + h) + .5, Math.round(x) + .5, Math.round(y + h) + .5, Math.round(x) + .5, Math.round(y) + .5],
		rowHeight = h / hv,
		columnWidth = w / wv;
	for(var i = 1; i < hv; i++) {
		path = path.concat(["M", Math.round(x) + .5, Math.round(y + i * rowHeight) + .5, "H", Math.round(x + w) + .5]);
	}
	for(i = 1; i < wv; i++) {
		path = path.concat(["M", Math.round(x + i * columnWidth) + .5, Math.round(y) + .5, "V", Math.round(y + h) + .5]);
	}
	return this.path(path.join(",")).attr({
		stroke: color
	});
};

function findItemByKey(nodes, key) {
    for (var i = 0, ii = nodes.length; i < ii; i++) {
		var node = nodes[i];
		if(node.key && node.key == key) return node;
    }
}

function buildConnectors(connections, nodes, node) {
    /**
     * parse fields and create connectors
     */
    for (var i = 0, ii = node.fields.length; i < ii; i++) {
		var field = node.fields[i];
		if (field.value && field.value.constructor == Object) {
			/**
			 * field is a simple object
			 */
			var to = findItemByKey(nodes, field.value.key);
			console.log('buildConnectors: [object] ' + field + '/' + to);
			//connections.push(paper.connection(node.shape, to.shape, "#000"));
			paper.makeLink(node.shape, to.shape);
		}
    }
}

/**
 * build all the graph
 * @param session
 */
function buildGraph(session) {
	/**
	 * clear Raphael element
	 * then rebuild the model
	 */
	paper.clear();
	paper.drawGrid(0, 0, 640, 480, 100, 100, '#dddddd');
	
	/**
	 * flush current connector
	 * then iterate and rebuild the model
	 */
	session.model.connections = [];
	for (var i = 0, ii = session.model.nodes.length; i < ii; i++) {
	    var node = buildNode(session.model.nodes[i],session.config);
	}

	/**
	 * build connections
	 */
	for (var i = 0, ii = session.model.nodes.length; i < ii; i++) {
		/**
		 * build connections from fields
		 * element
		 */
		buildConnectors(session.model.connections, session.model.nodes, session.model.nodes[i]);
		//session.model.connections.push(paper.connection(origin, session.model.nodes[1].shape, "#000"));
	}

	//session.connections.push(paper.connection(session.logicalShapes[1].data, session.logicalShapes[2].data, "#fff"));
	//session.connections.push(paper.connection(session.logicalShapes[1].data, session.logicalShapes[3].data, "#fff"));
}

function dumpShape(shape) {
	$('#keyPosX').val(
			JSON.stringify(shape.embeded.anchor.element.getBBox())+'\n');
}

$(document).ready(function () {
	/**
	 * build dialog
	 */
	//$( "#dialogDetail" ).dialog();
	$( "#dialogDetail" ).dialog({
	      modal: false,
	      buttons: {
	        Ok: function() {
	          //$( this ).dialog( "close" );
	        }
	      }
	    });
	
	/**
	 * build Raphael
	 */
	paper = Raphael("holder", 1024, 768);
	
	buildGraph(_session);
});
