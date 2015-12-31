define(
['jquery','lodash','backbone'],

function($,_,Backbone) {
	var Article = Backbone.Model.extend({
		"url": "./article",
		"idAttribute": "idx",

		defaults:{
			email: '',
			pwd: '',
			body: ''
		}
	});
	return Article;
});