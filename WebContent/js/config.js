// Original concepts provided by Backbone Boilerplate project: https://github.com/tbranyen/backbone-boilerplate
require.config({
  // Initialize the application with the main application file
  deps: ["main"],

  baseUrl: "./js",

  paths: {
    // Libraries
    'jquery': "lib/jquery",
    'lodash': "lib/lodash.min",
    'backbone': "lib/backbone.min",
    'semantic': "lib/semantic.min"
  },

  shim: {
    'jqeury': {
      exports: '$'
    },
    'lodash': {
      exports: '_'
    },
    'backbone': {
      deps: ['jquery','lodash'],
      exports: "Backbone"
    },
    'semantic': {
      deps: ['jquery'],
      exports: '$'
    }
  }
});