define(
['jquery','lodash','backbone','model/article-model'],

function($,_,Backbone,Article) {
	var Articles = Backbone.Collection.extend({
		model: Article,
		
		"url": "./article"
	});
	return Articles;
});