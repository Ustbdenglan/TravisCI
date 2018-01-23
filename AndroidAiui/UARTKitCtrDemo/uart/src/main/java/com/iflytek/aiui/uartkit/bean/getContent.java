package com.iflytek.aiui.uartkit.bean;

import java.util.List;

/**
 * Created by 00005001 on 2017/11/10.
 */

public class getContent {

    /**
     * content : {"result":{"sid":"cid6f195047@ch00510d609a6b010098","intent":{"sid":"cid6f195047@ch00510d609a6b010098","text":"北京","uuid":"atn0026528c@ch74900d609a6c6f2601","cid":"cid6f195047@ch00510d608222000000","rc":4}},"arg2":0,"eventType":1,"arg1":0,"info":{"data":[{"content":[{"dtf":"json","cnt_id":"0","dte":"utf8"}],"params":{"rstid":1,"sub":"nlp","lrst":false}}]}}
     * type : aiui_event
     */

    private ContentBeanX content;
    private String type;

    public ContentBeanX getContent() {
        return content;
    }

    public void setContent(ContentBeanX content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class ContentBeanX {
        /**
         * result : {"sid":"cid6f195047@ch00510d609a6b010098","intent":{"sid":"cid6f195047@ch00510d609a6b010098","text":"北京","uuid":"atn0026528c@ch74900d609a6c6f2601","cid":"cid6f195047@ch00510d608222000000","rc":4}}
         * arg2 : 0
         * eventType : 1
         * arg1 : 0
         * info : {"data":[{"content":[{"dtf":"json","cnt_id":"0","dte":"utf8"}],"params":{"rstid":1,"sub":"nlp","lrst":false}}]}
         */

        private ResultBean result;
        private int arg2;
        private int eventType;
        private int arg1;
        private InfoBean info;

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public int getArg2() {
            return arg2;
        }

        public void setArg2(int arg2) {
            this.arg2 = arg2;
        }

        public int getEventType() {
            return eventType;
        }

        public void setEventType(int eventType) {
            this.eventType = eventType;
        }

        public int getArg1() {
            return arg1;
        }

        public void setArg1(int arg1) {
            this.arg1 = arg1;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class ResultBean {
            /**
             * sid : cid6f195047@ch00510d609a6b010098
             * intent : {"sid":"cid6f195047@ch00510d609a6b010098","text":"北京","uuid":"atn0026528c@ch74900d609a6c6f2601","cid":"cid6f195047@ch00510d608222000000","rc":4}
             */

            private String sid;
            private IntentBean intent;

            public String getSid() {
                return sid;
            }

            public void setSid(String sid) {
                this.sid = sid;
            }

            public IntentBean getIntent() {
                return intent;
            }

            public void setIntent(IntentBean intent) {
                this.intent = intent;
            }

            public static class IntentBean {
                /**
                 * sid : cid6f195047@ch00510d609a6b010098
                 * text : 北京
                 * uuid : atn0026528c@ch74900d609a6c6f2601
                 * cid : cid6f195047@ch00510d608222000000
                 * rc : 4
                 */

                private String sid;
                private String text;
                private String uuid;
                private String cid;
                private int rc;

                public String getSid() {
                    return sid;
                }

                public void setSid(String sid) {
                    this.sid = sid;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getUuid() {
                    return uuid;
                }

                public void setUuid(String uuid) {
                    this.uuid = uuid;
                }

                public String getCid() {
                    return cid;
                }

                public void setCid(String cid) {
                    this.cid = cid;
                }

                public int getRc() {
                    return rc;
                }

                public void setRc(int rc) {
                    this.rc = rc;
                }
            }
        }

        public static class InfoBean {
            private List<DataBean> data;

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * content : [{"dtf":"json","cnt_id":"0","dte":"utf8"}]
                 * params : {"rstid":1,"sub":"nlp","lrst":false}
                 */

                private ParamsBean params;
                private List<ContentBean> content;

                public ParamsBean getParams() {
                    return params;
                }

                public void setParams(ParamsBean params) {
                    this.params = params;
                }

                public List<ContentBean> getContent() {
                    return content;
                }

                public void setContent(List<ContentBean> content) {
                    this.content = content;
                }

                public static class ParamsBean {
                    /**
                     * rstid : 1
                     * sub : nlp
                     * lrst : false
                     */

                    private int rstid;
                    private String sub;
                    private boolean lrst;

                    public int getRstid() {
                        return rstid;
                    }

                    public void setRstid(int rstid) {
                        this.rstid = rstid;
                    }

                    public String getSub() {
                        return sub;
                    }

                    public void setSub(String sub) {
                        this.sub = sub;
                    }

                    public boolean isLrst() {
                        return lrst;
                    }

                    public void setLrst(boolean lrst) {
                        this.lrst = lrst;
                    }
                }

                public static class ContentBean {
                    /**
                     * dtf : json
                     * cnt_id : 0
                     * dte : utf8
                     */

                    private String dtf;
                    private String cnt_id;
                    private String dte;

                    public String getDtf() {
                        return dtf;
                    }

                    public void setDtf(String dtf) {
                        this.dtf = dtf;
                    }

                    public String getCnt_id() {
                        return cnt_id;
                    }

                    public void setCnt_id(String cnt_id) {
                        this.cnt_id = cnt_id;
                    }

                    public String getDte() {
                        return dte;
                    }

                    public void setDte(String dte) {
                        this.dte = dte;
                    }
                }
            }
        }
    }
}
