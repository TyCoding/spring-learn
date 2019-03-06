new Vue({
    el: '#app',
    data() {
        return {
            list: null,
            msg: '',
            qtime: '',
            searchMap: {
                keyword: '',
                current: '',
                rows: '',
            },

            //分页选项
            pageConf: {
                current: 1, //当前页
                rows: 20, //每页显示的记录数
                total: 100, //总记录数
                pageOption: [20, 25, 30], //分页选项
            },
        }
    },
    methods: {
        //搜索
        search() {
            this.$http.post('/search', this.searchMap).then(result => {
                if (result.body.data != null) {
                    if (result.body.data != []) {
                        this.list = result.body.data;
                        this.pageConf.total = result.body.total;
                    } else {
                        this.list = null;
                    }
                } else {
                    this.list = null;
                }
                this.msg = result.body.msg;
                this.qtime = result.body.qtime;
                //处理分页数据
                this.pageConf.total = result.body.total;
            });
        },

        //pageSize改变时触发的函数
        handleSizeChange(val) {
            this.searchMap.rows = val;
            this.searchMap.current = this.pageConf.current;
            this.search(this.pageConf.current, val);
        },
        //当前页改变时触发的函数
        handleCurrentChange(val) {
            this.searchMap.current = val;
            this.searchMap.rows = this.pageConf.rows;
            this.search(val, this.searchMap.rows);
        },
    },
    //声明周期钩子函数-->在data和methods渲染结束后执行
    created() {
        this.searchMap.current = this.pageConf.current; //初始化设置分页参数
        this.searchMap.rows = this.pageConf.rows;
        this.search();
    }
});