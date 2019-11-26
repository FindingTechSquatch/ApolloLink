/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    document.getElementById('edtGrpNfo').onclick = function (e) {
        if(document.getElementById('edtGrpNfo').get)
        
        alert('click');
        document.getElementById('edtGrpNfobtn').removeAttribute('hidden');
        document.getElementById('edtGrpNm').removeAttribute('disabled');
        document.getElementById('edtGrpNm').removeAttribute('disabled');
        document.getElementById('edtGrpTp').removeAttribute('disabled');
        document.getElementById('edtGrpSz').removeAttribute('disabled');
    }

});
