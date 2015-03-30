/**
 * Created by intelligrape on 23/3/15.
 */

var reportDelay = 2000;
function successReport(reportText){
    var $success = $('#successReport');
    $success.find('.reportText').html(reportText);
    $success.slideDown("fast", function(){
        setTimeout(hide, reportDelay, $success);
    });
}
function infoReport(reportText){
    var $info = $('#info');
    $info.find('.reportText').html(reportText);
    $info.slideDown("fast", function(){
        setTimeout(hide, reportDelay, $info);
    });
    setTimeout(hide, reportDelay, $info);
}
function warningReport(reportText){
    var $warningReport = $('#warningReport');
    $warningReport.find('.reportText').html(reportText);
    $warningReport.slideDown("fast", function(){
        setTimeout(hide, reportDelay, $warningReport);
    });
    setTimeout(hide, reportDelay, $warningReport);
}

function hide($obj){
    $obj.slideUp("fast");
}
