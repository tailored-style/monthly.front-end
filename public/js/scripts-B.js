"use strict";

$(document).ready(function() {

	// mobile menu
	$('#js-mobile-menu-button').on('click', function(e) {
		e.preventDefault();
		$('.page-header').toggleClass('page-header_top-menu-opened');
		$('.list-submenu').slideUp();
	});

	// search toggle
	$('#js-search-toggle-button').on('click', function(e) {
		e.preventDefault();
		$('.page-header').removeClass('page-header_top-menu-opened').toggleClass('page-header_search-toggled');
	});

	// submenu handling
	if (Modernizr.mq('(max-width : 1100px)')) {
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
	}

	// scroll to top
	$('#js-button-to-top').on('click', function(e) {
		$('html, body').animate({
			scrollTop: 0
		}, 1200);
		return false;
	});

	// tabs in sidebar blog
	$.ionTabs("#js-tabs-blog-sidebar");

	// init main plugins only after all images are loaded
	$('body').imagesLoaded({ background: true }).always(function(e) {

		// page preloader animation
		$('.page-preloader-wrapper').addClass('page-preloader-wrapper_loaded');

		// parallax init
		$('.js-jarallax_type_1').jarallax({
			type: "scroll",
			speed: 0.40
		});

		$('.js-jarallax_type_2').jarallax({
			type: "scroll",
			speed: 0.90
		});
		// - parallax init

		// slider portfolio 
		var sliderPortfolio = new Swiper('.js-slider-portfolio', {
			speed: 600,
			loop: true,
			loopAdditionalSlides: 5,
			direction: 'horizontal',
			centeredSlides: true,
			slidesPerView: 3,
			spaceBetween: 0,
			nextButton: '.slider-portfolio__controls .swiper-button-next',
			prevButton: '.slider-portfolio__controls .swiper-button-prev',
			slideToClickedSlide: true,
			breakpoints: {
				991: {
					slidesPerView: 2
				},
				767: {
					slidesPerView: 1
				}
			}
		});

		// slider testimonials
		var sliderTestimonials = new Swiper('.js-slider-testimonials', {
			speed: 600,
			loop: true,
			slidesPerView: 2,
			spaceBetween: 30,
			pagination: '.swiper-pagination',
			paginationClickable: true,
			autoHeight: true,
			nextButton: '.slider-testimonials__controls .swiper-button-next',
			prevButton: '.slider-testimonials__controls .swiper-button-prev',
			breakpoints: {
				767: {
					slidesPerView: 1
				}
			}
		});

		// slider team 
		var sliderPortfolio = new Swiper('.js-slider-team', {
			speed: 600,
			loop: true,
			loopAdditionalSlides: 6,
			direction: 'horizontal',
			centeredSlides: true,
			slidesPerView: 3,
			spaceBetween: 0,
			nextButton: '.slider-team__controls .swiper-button-next',
			prevButton: '.slider-team__controls .swiper-button-prev',
			breakpoints: {
				991: {
					slidesPerView: 2
				},
				480: {
					slidesPerView: 1
				}
			}
		});

		// slider from-blog
		var sliderFromBlog = new Swiper('.js-slider-from-blog', {
			speed: 600,
			loop: true,
			loopAdditionalSlides: 6,
			direction: 'horizontal',
			centeredSlides: true,
			slidesPerView: 3,
			spaceBetween: 0,
			nextButton: '.slider-from-blog__controls .swiper-button-next',
			prevButton: '.slider-from-blog__controls .swiper-button-prev',
			breakpoints: {
				991: {
					slidesPerView: 2
				},
				767: {
					slidesPerView: 1
				}
			}
		});

		// slider portfolio details
		var sliderPortfolioDetails = new Swiper('.js-slider-portfolio-details', {
			speed: 600,
			loop: true,
			loopAdditionalSlides: 6,
			direction: 'horizontal',
			pagination: '.slider-portfolio-details__pagination',
			paginationClickable: true,
			nextButton: '.slider-portfolio-details__button-prev',
			prevButton: '.slider-portfolio-details__button-next'
		});

		// slider work steps
		var sliderWorkSteps = new Swiper('.js-slider-work-steps', {
			speed: 600,
			loop: true,
			loopAdditionalSlides: 0,
			direction: 'horizontal',
			centeredSlides: true,
			spaceBetween: 8,
			slidesPerView: 'auto',
			pagination: '.swiper-pagination',
			paginationClickable: true,
			breakpoints: {
				767: {
					slidesPerView: 1
				}
			}			
		});

		// slider blog gallery post
		var sliderBlogGallery = new Swiper('.js-slider-blog-gallery', {
			autoHeight: true,
			speed: 600,
			loop: true,
			direction: 'horizontal',
			nextButton: '.slider-blog-gallery__button-next',
			prevButton: '.slider-blog-gallery__button-prev'
		});
		
		// masonry portfolio grid
		var gridPortfolio = '.js-masonry-grid-portfolio';
		$(gridPortfolio).masonry({
			columnWidth: '.js-masonry-grid-portfolio__sizer',
			itemSelector: '.js-masonry-grid-portfolio__item',
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
	});

	// init google map
	if($('.js-google-map').length) {
		var mapData = [{
		        lat: 45.5,
		        lon: 10.9,
		        title: 'Greco',
		        zoom: 15,
		        icon: 'img/B/icons/icon-map-marker.png'
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