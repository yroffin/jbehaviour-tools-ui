'use strict';

/* App Services module */

angular.module('storyServices', ['ngResource'], 
	function($provide) {
		/**
		 * stories provider
		 */
		console.info('Register Stories');
		$provide.factory('Stories', function($resource) {
			console.info('Stories.stories');
			return $resource('/rest/stories', {}, {
				post: {method:'POST', isArray:true, cache:false}
			});
		});
		/**
		 * story provider
		 */
			console.info('Register Story');
		$provide.factory('Story', function($resource) {
			return $resource('/rest/story', {}, {
				story: {url: '/rest/story', method:'POST', isArray:false, cache:false},
				execute: {url: '/rest/execute', method:'POST', isArray:false, cache:false}
			});
		});
	});
