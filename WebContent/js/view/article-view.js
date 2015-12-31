define(
['jquery','lodash','backbone','model/article-model'],

function($,_,Backbone,Article) {
	var ArticlesView = Backbone.View.extend({
		"el": $("#content"),

		"initialize": function(){
			this.addForm = this.$el.find('.ui.form');
			this.model.bind("reset",this.render,this);
			this.model.bind("add",this.prependList,this);
		},

		"render": function(){
			_.each(this.model.models,function(article){
				this.prependList(article);
			},this);

			return this;
		},

		"events": {
			"click .submit.button": "addArticle"
		},

		"prependList": function(article){
			this.$el.find('#articles').prepend(new ArticleView({
				model: article
			}).render().el);
		},

		"addArticle": function(){
			var email = this.addForm.find('input[name=email]').val();
			var pwd = this.addForm.find('input[name=password]').val();
			var body = this.addForm.find('textarea[name=body]').val();
			var article = new Article({'email':email,'pwd':pwd,'body':body});
			this.model.create(article);
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