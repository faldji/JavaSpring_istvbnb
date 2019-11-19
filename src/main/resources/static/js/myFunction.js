$(function() {
    $('.custom-range').prev().prev().text($(this).val()); // Valeur par défaut
    $('.custom-range').on('input', function() {
        var $set = $(this).val();
       var $newSet =  $set.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1 ').concat(' ');
        $(this).prev().prev().text($newSet);
    });
});
function showModal() {
    if (document.getElementById("showModal") != null){
        $('#exampleModalCenter').modal('show');
        $('#exampleModalCenter').on('shown.bs.modal', function () { $('#inlineFormLocation').trigger('focus')});
    }

}
function showResModal() {
    if (document.getElementById("showResModal") != null){
        $('#resModal').modal('show');
        $('#resModal').on('shown.bs.modal', function () { $('#inlineFormLocation').trigger('focus')});
    }
}