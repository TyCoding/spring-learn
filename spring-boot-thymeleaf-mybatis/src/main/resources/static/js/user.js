let app = new Vue({
    el: '#app',
    data() {
        var validateName = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('名称不能为空'))
            }
            this.$http.get(api.user.checkName(value, this.form.id)).then(response => {
                if (response.body.code != 200) {
                    callback(new Error(response.body.msg))
                } else {
                    callback();
                }
            })
        }
        return {
            list: [], //用户列表数据
            searchEntity: {}, //查询实体类
            //分页选项
            pageConf: {
                //设置一些初始值(会被覆盖)
                pageCode: 1, //当前页
                pageSize: 6, //每页显示的记录数
                totalPage: 12, //总记录数
                pageOption: [6, 10, 20], //分页选项
            },
            //模态框状态标识
            dialogVisible: false,
            dialogTitle: '',
            //form表单对象
            form: {
                username: '',
                password: '',
                age: '',
                roleIds: [],
            },
            roleList: [], //角色列表数据
            selectIds: [], //Table选中行ID

            checkForm: {
                username: [{ validator: validateName, trigger: 'blur' }]
            },
        }
    },
    created() {
        this.search(this.pageConf.pageCode, this.pageConf.pageSize);
    },
    methods: {
        _notify(message, type) {
            this.$message({
                message: message,
                type: type
            })
        },

        //获取用户列表
        search(pageCode, pageSize) {
            this.$http.post(api.user.list(pageCode, pageSize), this.searchEntity).then(response => {
                this.list = response.body.data.rows;
                this.pageConf.totalPage = response.body.data.total;
            })
        },

        //pageSize改变时触发的函数
        handleSizeChange(val) {
            this.search(this.pageConf.pageCode, val);
        },
        //当前页改变时触发的函数
        handleCurrentChange(val) {
            this.pageConf.pageCode = val; //为了保证刷新列表后页面还是在当前页，而不是跳转到第一页
            this.search(val, this.pageConf.pageSize);
        },

        //触发关闭按钮
        handleClose() {
            this.dialogVisible = false; //关闭模态框
        },

        //触发保存按钮：添加、更新
        handleSave(id) {
            this.clearForm();
            //获取角色列表
            this.$http.post(api.user.roleList).then(response => {
                this.roleList = response.body.data.rows;
            })
            if (id == null) {
                this.dialogTitle = '新增用户'
            } else {
                this.dialogTitle = '修改用户'
                this.$http.get(api.user.getUser(id)).then(response => {
                    this.form = response.body.data;
                    if (response.body.data.roleIds[0] == null) {
                        this.form.roleIds = []
                    }
                })
            }
            this.dialogVisible = true; //打开模态框
        },
        clearForm() {
            if (this.$refs.form != undefined) {
                this.$refs.form.resetFields(); //重置表单校验状态
            }
            this.form.username = ''
            this.form.password = ''
            this.form.age = ''
            this.form.roleIds = []
            this.form.id = null;
            this.form.roleId = "";
        },
        //保存、更新
        save(form) {
            this.$refs[form].validate((valid) => {
                if (valid) {
                    this.dialogVisible = false;
                    if (this.form.id == null || this.form.id == 0) {
                        this.$http.post(api.user.add, JSON.stringify(this.form)).then(response => {
                            if (response.body.code == 200) {
                                this._notify(response.body.msg, 'success')
                            } else {
                                this._notify(response.body.msg, 'error')
                            }
                            this.clearForm();
                            this.search(this.pageConf.pageCode, this.pageConf.pageSize)
                        })
                    } else {
                        //修改
                        this.$http.post(api.user.update, JSON.stringify(this.form)).then(response => {
                            if (response.body.code == 200) {
                                this._notify(response.body.msg, 'success')
                            } else {
                                this._notify(response.body.msg, 'error')
                            }
                            this.clearForm();
                            this.search(this.pageConf.pageCode, this.pageConf.pageSize)
                        })
                    }
                } else {
                    return false;
                }
            })
        },

        //Table选中触发事件
        selectChange(val) {
            this.selectIds = []
            val.forEach(row => {
                this.selectIds.push(row.id)
            })
        },

        //触发删除按钮
        handleDelete(id) {
            if (id != undefined) {
                this.selectIds = [id];
            }
            this.$confirm('你确定永久删除此账户？, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$http.post(api.user.delete, JSON.stringify(this.selectIds)).then(response => {
                    if (response.body.code == 200) {
                        this._notify('删除成功', 'success')
                    } else {
                        this._notify(response.body.msg, 'error')
                    }
                    this.$refs.table.clearSelection();
                    this.selectIds = [];
                    this.search(this.pageConf.pageCode, this.pageConf.pageSize)
                })
            }).catch(() => {
                this._notify('已取消删除', 'info')
            });
        },
    },
});
