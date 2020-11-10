define(function (require, exports, module) {
    require('./css/bootstrap-datetimepicker.min.css');
    require('./js/bootstrap-datetimepicker.min.js');
   // require('./js/locales/zh-CN.js');

    ;(function($){
        $.fn.datetimepicker.dates['zh-CN'] = {
            days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
            daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
            daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
            months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            today: "今天",
            suffix: [],
            meridiem: ["上午", "下午"]
        }
    }(jQuery));
    $.fn.datetimepicker.defaults = {
        language:"zh-CN",
        autoclose:true,
        format:"yyyy-mm-dd"
    }

    $(".form-date").datetimepicker({
    	minView :'month',
    	pickerPosition: "bottom-left",
        todayHighlight: true
    }).on('hide changeDate', function(event) {
        event.preventDefault();
        event.stopPropagation();
    });


    $("[data-role='start-date']").datetimepicker({
        minView :'month'
    }).on('hide changeDate', function(event) {
        event.preventDefault();
        event.stopPropagation();
    });

    $("[data-role='end-date']").datetimepicker({
        minView :'month'
    }).on('hide changeDate', function(event) {
        event.preventDefault();
        event.stopPropagation();
    });

    $("[data-role='start-date']").datetimepicker().on("changeDate", function (e) {
        $("[data-role='end-date']").datetimepicker('setStartDate',eventDate(e.date));
    });

    $("[data-role='end-date']").datetimepicker().on("changeDate", function (e) {
       $("[data-role='start-date']").datetimepicker('setEndDate',eventDate(e.date));
    });

    //月份
    $("[data-role='start-month']").datetimepicker({
        format: 'yyyy-mm',
        startView:'year',
        minView :'year'
    }).on('hide changeDate', function(event) {
        event.preventDefault();
        event.stopPropagation();
    });

    $("[data-role='end-month']").datetimepicker({
        format: 'yyyy-mm',
        startView:'year',
        minView :'year'
    }).on('hide changeDate', function(event) {
        event.preventDefault();
        event.stopPropagation();
    });

    $("[data-role='start-month']").datetimepicker().on("changeDate", function (e) {
        $("[data-role='end-month']").datetimepicker('setStartDate',eventDate(e.date));
    });

    $("[data-role='end-month']").datetimepicker().on("changeDate", function (e) {
       $("[data-role='start-month']").datetimepicker('setEndDate',eventDate(e.date));
    });

    function eventDate(date){
        var yy = date.getFullYear(),
            mm = date.getMonth() + 1, 
            dd = date.getDate();

            mm = mm < 10 ? '0'+mm : mm;
            dd = dd < 10 ? '0'+dd : dd;
        return yy + "-" + mm + "-" + dd;
    }
});