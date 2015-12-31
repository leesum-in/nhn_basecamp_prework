require([
	'jquery',
	'lodash',
	'backbone',
     'view/article-view',
     'model/article-model',
     'model/article-collection'
],

function($, _, Backbone,ArticlesView,Article,Articles){

    var AppRouter = Backbone.Router.extend({
        initialize:function(){
            this.articles = new Articles();
            this.articlesView = new ArticlesView({
                model : this.articles
            });
            this.articles.fetch({
                reset:true
            });     	   
        },
    	
    });
    window.app = new AppRouter();
    Backbone.history.start();
}); //End require