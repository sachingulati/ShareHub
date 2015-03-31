/**
 * Created by intelligrape on 23/3/15.
 */

var reportDelay = 4000;
var $reportTimeOutVar = null;
function successReport(reportText){
    $('#report').addClass('alert-success');
    report(reportText);
}
function infoReport(reportText){
    $('#report').addClass('alert-info');
    report(reportText);
}
function warningReport(reportText){
    $('#report').addClass('alert-danger');
    report(reportText);
}
function report(reportText){
    var $report = $('#report');
    $report.find('.reportText').html(reportText);
    $report.slideDown("fast", function(){
        $reportTimeOutVar = setTimeout(hide, reportDelay, $report);
    });
}
function hide($obj){
    $obj.slideUp("fast");
    if($reportTimeOutVar != null){
        clearTimeout($reportTimeOutVar)
    }
}
