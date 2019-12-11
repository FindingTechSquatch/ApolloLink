/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () 
{
    document.getElementById('edtEvtNfo1').onclick = function (e) 
    {
               
        document.getElementById('edtEvtNfoBtn1').removeAttribute('hidden');
        document.getElementById('edtEvtNm').removeAttribute('disabled');
        document.getElementById('edtEvtHst').removeAttribute('disabled');
        document.getElementById('edtEvtTyp').removeAttribute('disabled');
        document.getElementById('edtEvtStrtDteTm').removeAttribute('disabled');
        document.getElementById('edtEvtEndDteTm').removeAttribute('disabled');
        document.getElementById('edtEvtBlkSz').removeAttribute('disabled');
        document.getElementById('edtEvtNfo1').style.display = 'none';
        document.getElementById('edtEvtNfo2').style.display = 'none';
        document.getElementById('edtEvtNfo3').style.display = 'none';
    };
    document.getElementById('edtEvtNfo2').onclick = function (e) 
    {
               
        document.getElementById('edtEvtNfoBtn2').removeAttribute('hidden');
        document.getElementById('edtEvtLoc').removeAttribute('disabled');
        document.getElementById('edtEvtAddr1').removeAttribute('disabled');
        document.getElementById('edtEvtAddr2').removeAttribute('disabled');
        document.getElementById('edtEvtCity').removeAttribute('disabled');
        document.getElementById('edtEvtState').removeAttribute('disabled');
        document.getElementById('edtEvtNfo2').style.display = 'none';
        document.getElementById('edtEvtNfo3').style.display = 'none';
        document.getElementById('edtEvtNfo1').style.display = 'none';
    };
    document.getElementById('edtEvtNfo3').onclick = function (e) 
    {
               
        document.getElementById('edtEvtNfoBtn3').removeAttribute('hidden');
        document.getElementById('edtEvtEarlyStrtDte').removeAttribute('disabled');
        document.getElementById('edtEvtEarlyEndDte').removeAttribute('disabled');
        document.getElementById('edtEvtEarlyCst').removeAttribute('disabled');
        document.getElementById('edtEvtRegStrtDte').removeAttribute('disabled');
        document.getElementById('edtEvtRegEndDte').removeAttribute('disabled');
        document.getElementById('edtEvtRegCst').removeAttribute('disabled');
        document.getElementById('edtEvtLateStrtDte').removeAttribute('disabled');
        document.getElementById('edtEvtLateEndDte').removeAttribute('disabled');
        document.getElementById('edtEvtLateCst').removeAttribute('disabled');
        document.getElementById('edtEvtNfo3').style.display = 'none';
        document.getElementById('edtEvtNfo1').style.display = 'none';
        document.getElementById('edtEvtNfo2').style.display = 'none';
    };
});


