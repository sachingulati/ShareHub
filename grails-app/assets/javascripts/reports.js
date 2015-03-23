/**
 * Created by intelligrape on 23/3/15.
 */

var reportDelay = 5000;
function successReport(reportText){
    var $success = $('#successReport');
    $success.find('.reportText').html(reportText);
    $success.show();
    setTimeout(hide, reportDelay, $success);
}
function infoReport(reportText){
    var $info = $('#info');
    $info.find('.reportText').html(reportText);
    $info.show();
    setTimeout(hide, reportDelay, $info);
}
function warningReport(reportText){
    var $warningReport = $('#warningReport');
    $warningReport.find('.reportText').html(reportText);
    $warningReport.show();
    setTimeout(hide, reportDelay, $warningReport);
}
