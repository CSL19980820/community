$(function () {
    layui.use(['form','layer'], function(){
        var form = layui.form
            ,layer = layui.layer;
        bootStrapUtilR.init();
        bootStrapUtilL.init();
        putEmail.init();
        //监听提交
        form.on('submit(formDemo)', function(data){
            layer.msg(JSON.stringify(data.field));
            return false;
        });
    });

});
var putEmail ={
    valid:false,
    emailSend:"no",
    startListen:function () {
        var _this = this;
        _this.findError();
        _this.putEmail();

    },findError:function () {
        var _this = this;
        $("#email").on('keyup.property',function (e) {
            _this.errorFind();
        })
        $("#email").hover(function () {
            _this.errorFind();
        })
    },errorFind:function () {
        var flag =$('#registerForm').data('bootstrapValidator').isValidField('email');
        var className = $("#putEmail")[0].classList.value.indexOf('disabled')>0;
        if(flag){
            $("#putEmail").removeClass("layui-btn-disabled");
            $("#putEmail").attr("disabled",false);
        }else{
            if(!className){
                $("#putEmail").attr("disabled",true);
                $("#putEmail").addClass("layui-btn-disabled");
            }
        }
    },putEmail:function () {
        var _this = this;
        $("#putEmail").on("click.putEmail",function () {
            $('#registerForm').data('bootstrapValidator').revalidateField('email');
            var flag =$('#registerForm').data('bootstrapValidator').isValidField('email');
            _this.getEmail();
            if(_this.valid == true && flag){
                _this.sendEmail();
                if(_this.emailSend == 'yes'){
                    layer.msg("邮件已发送,请及时注册",{time:2*1000})
                }else if(_this.emailSend == 'more'){
                    layer.msg("邮件已发送,30分钟内请勿重复发送",{time:2*1000})
                }else if(_this.emailSend == 'dayMore'){
                    layer.msg("该邮箱当天注册邮件已达3次,请您明天再来",{time:2*1000})
                }else if(_this.emailSend =='ipMore'){
                    layer.msg("当前IP一天发送验证码次数过多,请您明天再来",{time:2*1000})
                }else{
                    layer.msg("功能异常,请联系管理员",{time:2*1000})
                }
            }else{
                layer.msg("邮箱已被注册,请重新输入",{time:2*1000})
            }

        })
    },sendEmail:function () {
        var _this = this;
        $.ajax({
            url:"/send/mail",
            type:"post",
            async:false,
            dataType:"text",
            data:{
                email : $("#email").val(),
                title:"【沙雕社区】账号注册-邮箱验证"
            },
            success:function (data) {
                _this.emailSend = data;
            }
        })
    },getEmail:function () {
        var _this = this;
         $.ajax({
            url:"/getEmail",
            type:"post",
            async:false,
            dataType:'json',
            data:{email : $("#email").val()},
            success:function (json) {
                _this.valid = json.valid;
            }
        })

    }
    ,init:function(){
        this.startListen();
    }
}
var bootStrapUtilR = {
    startListen:function () {
        $('#registerForm').bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                'name':{
                    message: '用户名验证失败',
                    validators: {
                        notEmpty: {
                            message: '用户名必填不能为空'
                        },
                        stringLength: {/*长度提示*/
                            min: 3,
                            max: 15,
                            message: '用户名长度必须在3到15之间'
                        }
                    }
                },
                'email': {
                    message: '邮箱验证失败',
                    validators: {
                        notEmpty: {
                            message: '邮箱必填不能为空'
                        },
                        regexp: {/* 只需加此键值对，包含正则表达式，和提示 */
                            regexp: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
                            message: '请输入正确的邮箱地址'
                        }
                    }
                },
                'password': {
                    message: '密码验证失败',
                    validators: {
                        notEmpty: {
                            message: '密码必填不能为空'
                        },
                        regexp: {/* 只需加此键值对，包含正则表达式，和提示 */
                            regexp: /^[a-zA-Z0-9_\.]+$/,
                            message: '只能是数字和字母_.'
                        },
                        stringLength: {/*长度提示*/
                            min: 6,
                            max: 15,
                            message: '密码长度必须在6到15之间'
                        }
                    }
                },
                'passwordSecond':{
                    message: '重复密码验证失败',
                    validators: {
                        notEmpty: {
                            message: '密码必填不能为空'
                        },
                        identical: {
                            field: 'password',
                            message: '两次输入的密码不相符'
                        }

                    }
                },
                'code':{
                    message: '邮箱码验证失败',
                    validators: {
                        notEmpty: {
                            message: '邮箱码必填不能为空'
                        },
                        stringLength: {/*长度提示*/
                            min: 6,
                            max: 6,
                            message: '验证码必须是6位长度'
                        },
                        threshold:6,
                        remote:{
                            url:"/contrastCode",
                            type:"post",
                            message: '邮箱码不存在或邮箱码错误',
                            async:false,
                            //自定义提交数据，默认值提交当前input value
                            data: function(validator) {
                                return {
                                    email : $("#email").val(),
                                    code : $("#code").val()
                                };
                            },
                            delay: 1000
                        }
                    }
                }

            },
            submitHandler: function (validator, form, submitButton) {
                /*var flag = $('#registerForm').data("bootstrapValidator").isValid();//校验合格

                if(flag){
                    layer.msg('提交成功',{time:4*1000})
                    layer.close();
                }*/
            }
        }).on('success.form.bv',function(){

            var flag = $('#registerForm').data("bootstrapValidator").isValid();//校验合格

            if(flag){
                layer.msg('注册成功',{time:1*1000})
            }
        });
    },
    init:function () {
        this.startListen();
    }
}

var bootStrapUtilL = {
    startListen:function () {
        $('#loginForm').bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                'loginName':{
                    message: '账号验证失败',
                    validators: {
                        notEmpty: {
                            message: '账号必填不能为空'
                        },
                        stringLength: {/*长度提示*/
                            min: 3,
                            message: '账号长度必须在3以上'
                        }
                    }
                },
                'loginPassword': {
                    message: '登录密码验证失败',
                    validators: {
                        notEmpty: {
                            message: '登录密码必填不能为空'
                        }
                    }
                }

            },
            submitHandler: function (validator, form, submitButton) {

            }
        }).on('success.form.bv',function(){

            var flag = $('#loginForm').data("bootstrapValidator").isValid();//校验合格

            if(flag){
                layer.msg('提交成功',{time:4*1000})
            }
        });
    },
    init:function () {
        this.startListen();

    }
}


