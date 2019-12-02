/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    document.getElementById('edtGrpNfo').onclick = function (e) {
               
        document.getElementById('edtGrpNfobtn').removeAttribute('hidden');
        document.getElementById('edtGrpNm').removeAttribute('disabled');
        document.getElementById('edtGrpNm').removeAttribute('disabled');
        document.getElementById('edtGrpTp').removeAttribute('disabled');
        document.getElementById('edtGrpSz').removeAttribute('disabled');
        document.getElementById('edtGrpNfo').style.display = 'none';
        document.getElementById('edtLdrNfo').style.display = 'none';
    };
    document.getElementById('edtLdrNfo').onclick = function (e) {
               
        document.getElementById('edtLdrNfobtn').removeAttribute('hidden');
        document.getElementById('ldrFNAME01').removeAttribute('disabled');
        document.getElementById('ldrFNAME02').removeAttribute('disabled');
        document.getElementById('ldrFNAME03').removeAttribute('disabled');
        document.getElementById('ldrLNAME01').removeAttribute('disabled');
        document.getElementById('ldrLNAME02').removeAttribute('disabled');
        document.getElementById('ldrLNAME03').removeAttribute('disabled');
        document.getElementById('ldrTITLE01').removeAttribute('disabled');
        document.getElementById('ldrTITLE02').removeAttribute('disabled');
        document.getElementById('ldrTITLE03').removeAttribute('disabled');
        document.getElementById('edtLdrNfo').style.display = 'none';
        document.getElementById('edtGrpNfo').style.display = 'none';
    };
});
