/**
 * Created by intelligrape on 23/3/15.
 */

var reportDelay = 4000;
var $reportTimeOutVar = null;
function successReport(reportText){
    $('#report').addClass('alert-success');
    $('#report').removeClass('alert-info');
    $('#report').removeClass('alert-danger');
    report(reportText);
}
function infoReport(reportText){
    $('#report').removeClass('alert-success');
    $('#report').addClass('alert-info');
    $('#report').removeClass('alert-danger');
    report(reportText);
}
function warningReport(reportText){
    $('#report').removeClass('alert-success');
    $('#report').removeClass('alert-info');
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
