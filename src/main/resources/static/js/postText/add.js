$(function () {
    layui.use('layer', function(){
        var layer = layui.layer;
        bootStrapUtil.init();

    });
},jQuery)

    var bootStrapUtil = {
        startListen:function () {
            $('#postTextForm').bootstrapValidator({
                message: 'This value is not valid',
                fields: {
                    'title':{
                        message: '标题验证失败',
                        validators: {
                            notEmpty: {
                                message: '标题不能为空'
                            },
                            threshold:1,
                            remote:{

                                url:"/getTitle",
                                type:"post",
                                message: '该分类下此标题已存在,请重新输入',
                                async:false,
                                //自定义提交数据，默认值提交当前input value
                                data: function(validator) {
                                    return {
                                        title : $("#title").val(),
                                        type : $("#type").val()
                                    };
                                },
                                delay: 1000
                            }
                        }
                    },
                    'description':{
                        message: '简介验证失败',
                        validators: {
                            notEmpty: {
                                message: '简介不能为空'
                            }
                        }
                    }

                },
                submitHandler: function (validator, form, submitButton) {
                /*    consol.info(validator);
                    consol.info(form);
                    consol.info(submitButton);*/
                    // 表单提交成功时会调用此方法
                    // validator: 表单验证实例对象
                    // form  jq对象  指定表单对象
                    // submitButton  jq对象  指定提交按钮的对象
                }
            }).on('success.form.bv',function(){

                var flag = $('#postTextForm').data("bootstrapValidator").isValid();//校验合格

                if(flag){
                    layer.msg('提交成功',{time:0.8*1000})
                }
            });
        },
        init:function () {
            this.startListen();
        }
    }



