<!-- jQuery 2.2.3 -->
<script src="/adminlte/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/adminlte/bootstrap/js/bootstrap.min.js"></script>
<!-- Select2 -->
<script src="/adminlte/plugins/select2/select2.full.min.js"></script>
<!-- InputMask -->
<script src="/adminlte/plugins/input-mask/jquery.inputmask.js"></script>
<script src="/adminlte/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="/adminlte/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<!-- DataTables -->
<script src="/adminlte/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/adminlte/plugins/datatables/dataTables.bootstrap.min.js"></script>
<!-- date-range-picker -->
<script src="/adminlte/plugins/daterangepicker/moment.js"></script>
<script src="/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap datepicker -->
<script src="/adminlte/plugins/datepicker/bootstrap-datepicker.js"></script>
<!-- bootstrap color picker -->
<script src="/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
<!-- bootstrap time picker -->
<script src="/adminlte/plugins/timepicker/bootstrap-timepicker.min.js"></script>
<!-- SlimScroll 1.3.0 -->
<script src="/adminlte/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- iCheck 1.0.1 -->
<script src="/adminlte/plugins/iCheck/icheck.min.js"></script>
<!-- FastClick -->
<script src="/adminlte/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="/adminlte/dist/js/app.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="/adminlte/dist/js/demo.js"></script>
<script src="/adminlte/plugins/pace/pace.js"></script>
<script type="text/javascript" src="/adminlte/plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/adminlte/plugins/easyui/easyui_zh.js"></script>
<script type="text/javascript" src="/custom/js/base.js"></script>
<script type="text/javascript" src="/adminlte/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="/adminlte/plugins/newtoast/js/toast.js"></script> 
<script type="text/javascript" src="/adminlte/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="/adminlte/plugins/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="/adminlte/plugins/bootstrap-treeview/js/bootstrap-treeview.js"></script>
<script>
    $.ajaxSetup({cache: true});
    toastr.options.positionClass = 'toast-bottom-right';
    $(document).ajaxStart(function() { Pace.restart(); });
    $(document).ajaxError(
        function(event,xhr,options,exc ){
            if(xhr.status == 'undefined'){
                return;
            }
            switch(xhr.status){
                case 403:
                    toastrwarning("系统拒绝：您没有访问权限。");
                    break;
                case 404:
                    toastrwarning("您访问的资源不存在");
                    break;
                case 500:
                    toastrerror("系统错误，请联系管理员");
                    break;
            }
        }
    );
</script>
<!--百度文件上传插件-->
<script type="text/javascript" src="/adminlte/plugins/baiduwebuploader/webuploader.min.js"></script>
<script type="text/javascript" src="/adminlte/plugins/jquery-validation/jquery.validate.min.js"></script>
