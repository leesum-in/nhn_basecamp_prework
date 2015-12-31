define(
['jquery','lodash','backbone'],

function($,_,Backbone,Articles) {
	var ArticlesView = Backbone.View.extend({
		"el": $("#articles"),

		"initialize": function(){
			this.model.bind("reset",this.render,this);
			this.model.bind("add",this.appendList);
		},

		"render": function(){
			_.each(this.model.models,function(article){
				this.appendList(article);
			},this);

			return this;
		},

		"appendList": function(article){
			this.$el.append(new ArticleView({
				model: article
			}).render().el);
		}
	})

	var ArticleView = Backbone.View.extend({
		"initialize": function(){
			this.template = _.template($('#articleTemplate').html());
			this.model.bind("change",this.render,this);
		},

		"render": function(){
			var content = this.template(this.model.toJSON());
			this.$el.html(content);
			console.log(this.model.toJSON());
			return this;
		}
	});

	return ArticlesView;
});