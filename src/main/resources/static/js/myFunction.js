$(function() {
    $('.custom-range').prev().prev().text($(this).val()); // Valeur par défaut
    $('.custom-range').on('input', function() {
        var $set = $(this).val();
       var $newSet =  $set.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1 ').concat(' ');
        $(this).prev().prev().text($newSet);
    });
});