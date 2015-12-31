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
			
			if(email.length==0 || pwd.length==0 || body.length==0){
				alert("Enter the input");
			} else if( !this.checkEmail(email) ){
				alert("Invalid Email");
			} else {
				var article = new Article({'email':email,'pwd':pwd,'body':body});
				this.model.create(article);	//Add to Collection(Articles) & doPOST

				this.addForm.find('input').val('');
				this.addForm.find('textarea').val('');
			}
			
		},

		"checkEmail": function(email){
			var regex=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
			return regex.test(email)
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
			this.readMode();
			return this;
		},

		"events": {
			"click .edit": "clickEdit",
			"click .cancle": "clickCancle",
			"click .save": "clickSave"
		},

		"clickEdit": function(){
			this.editMode();
		},

		"clickCancle": function(){
			this.readMode();
		},

		"clickSave": function(){
			var pwd = this.$el.find('input[name=pwd]').val();
			var body = this.$el.find('textarea[name=body]').val();
			this.model.set({'pwd':pwd,'body':body});
			
			var self = this;
			this.model.save({
				patch: true},{
				success: function(res){
					self.model.unset('pwd');
				},
				error: function(model,res){
					if(res.status==403){
						self.editMode();
						alert("Password Incorrect");
					}
				}
			});
		},

		"editMode": function(){
			this.$el.find('.edit').hide();
			this.$el.find('.text').hide();
			this.$el.find('.editmode').show();
		},

		"readMode": function(){
			this.$el.find('.edit').show();
			this.$el.find('.text').show();
			this.$el.find('.editmode').hide();
		}
	});

	return ArticlesView;
});