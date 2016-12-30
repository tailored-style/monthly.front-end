"use strict";

$(document).ready(function() {

	// burger menu
	$('#js-burger-menu-toggle').on('click', function(e) {
		e.preventDefault();
		$('.page-header').toggleClass('js-page-header_expanded');

		// collapse all submenus
		$('.list-submenu').slideUp();
	});

	// submenu
	$('.list-top-menu__item_submenu > .list-top-menu__link').on('click', function(e) {
		e.preventDefault();
		var submenu = $(this).parent().find('.list-submenu');
		$('.list-submenu').slideUp();
		if(submenu.is(':visible')) {
			submenu.slideUp();
		} else {
			submenu.slideDown();
		}
	});

	// init main plugins only after all images are loaded
	$('body').imagesLoaded({ background: true }).always(function(e) {

		// preloader
		setTimeout(function() {
			$('#js-page-preloader').removeClass('page-preloader_loading').addClass('page-preloader_loaded');
		}, 1200);

		// parallax init
		$('.js-jarallax_type_1').jarallax({
			type: "scroll",
			speed: 0.40
		});

		$('.js-jarallax_type_2').jarallax({
			type: "scroll",
			speed: 0.90
		});

		// slider intro
		var sliderIntro = new Swiper('.js-slider-intro', {
			speed: 600,
			autoplay: 6000,
			onlyExternal: true,
			pagination: '.swiper-pagination',
			paginationClickable: true,
			direction: 'vertical'
		});

		// slider testimonials
		var sliderTestimonials = new Swiper('.js-slider-testimonials', {
			speed: 600,
			autoplay: 6000,
			onlyExternal: true,
			pagination: '.swiper-pagination',
			paginationClickable: true,
			direction: 'vertical',
			autoHeight: true
		});

		// slider process
		var sliderProcess = new Swiper('.js-slider-process', {
			speed: 600,
			pagination: '.swiper-pagination',
			paginationClickable: true,
			direction: 'horizontal',
			slidesPerView: 3,
			spaceBetween: 30,
			breakpoints: {
				991: {
					slidesPerView: 2
				},
				767: {
					slidesPerView: 1	
				}
			}
		});

		// slider team
		var sliderTeam = new Swiper('.js-slider-team', {
			speed: 600,
			autoHeight: true,
			effect: 'fade',
			nextButton: '.slider-team__controls .slider-button-next',
			prevButton: '.slider-team__controls .slider-button-prev'
		});

		// slider blog
		var sliderBlog = new Swiper('.js-slider-blog-post', {
			speed: 600,
			nextButton: '.slider-blog-post__slider-controls .slider-button-next',
			prevButton: '.slider-blog-post__slider-controls .slider-button-prev'
		});

		// grid services
		$('.js-masonry-grid-services').masonry({
			speed: 600,
			itemSelector: '.js-masonry-grid-services__item',
			columnWidth: '.js-masonry-grid-services__sizer'
		});

		// masonry portfolio grid
		var gridPortfolio = '.js-portfolio-masonry-grid';
		$(gridPortfolio).masonry({
			columnWidth: '.js-portfolio-masonry-grid__sizer',
			itemSelector: '.js-portfolio-masonry-grid__item',
			percentPosition: true
		}).isotope();

		// isotope filtering panel
		$('.js-isotope-filter').on('click', 'a', function(e) {
			e.preventDefault();

			$('.js-isotope-filter').find('.list-portfolio-categories__link_active').removeClass('list-portfolio-categories__link_active');
			$(this).addClass('list-portfolio-categories__link_active');

			var filterValue = $(this).attr('data-filter');

			$(gridPortfolio).isotope({
				filter: filterValue
			});
		});

	}).progress(function(instance, image) {

		var imagesToLoad = instance.images.length;

		//preloader
		$('#js-page-preloader').addClass('page-preloader_loading');
		loadProgress(imagesToLoad);
	});

	// progress page loading (based on amount of images)
	var loadedCount = 0;
	var loadingProgress = 0;

	function loadProgress(imagesToLoad)
	{
		//one more image has been loaded
		loadedCount++;
		loadingProgress = parseInt(loadedCount/imagesToLoad*100, 10);
		$('#js-page-preloader-counter').html(loadingProgress + '%');
		$('#js-page-preloader-progressbar').css('transform', 'translateX(' + loadingProgress + '%)')
	}

	// init google map
	if($('.js-google-map').length) {
		var mapData = [{
		        lat: 45.5,
		        lon: 10.9,
		        title: 'Roberto Valentinuzzi',
		        zoom: 15,
		        icon: 'img/A/icons/icon-map-marker.png'
		    },
		];
		var googleMap = new Maplace({
			locations: mapData,
			map_div: '.js-google-map',
			controls_type: 'list',
			controls_on_map: false
		}).Load();
	}	

});