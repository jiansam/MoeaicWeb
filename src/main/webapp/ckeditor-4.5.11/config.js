/**
 * @license Copyright (c) 2003-2016, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.allowedContent = true;
	
	CKEDITOR.config.pasteFromWordRemoveFontStyles = false;
	CKEDITOR.config.pasteFromWordRemoveStyles = false;
	//config.removePlugins = 'flash';

	CKEDITOR.config.toolbar = [
	  	{ name: 'document', groups: [ 'mode', 'document'], items: [ 'Source', 'Preview', 'Print'] },
	  	{ name: 'clipboard', groups: [ 'clipboard' ], items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord' ] },
//	  	{ name: 'paragraph', groups: [ 'list', 'align' ], items: [ 'NumberedList', 'BulletedList', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] },
	  	{ name: 'paragraph', items: [ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] },
	  	{ name: 'insert', items: [ 'Image', 'Link', 'Unlink', 'Table', 'HorizontalRule' ] },
	  	{ name: 'styles', items: [ 'Format', 'Font', 'FontSize' ] },
	  	{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat' ] },
	  	{ name: 'colors', items: [ 'TextColor', 'BGColor' ] },
	  	{ name: 'symbols', items: [ 'SpecialChar', 'PageBreak' ] },
	  	{ name: 'tools', items: [ 'Maximize'] }
	];
};
