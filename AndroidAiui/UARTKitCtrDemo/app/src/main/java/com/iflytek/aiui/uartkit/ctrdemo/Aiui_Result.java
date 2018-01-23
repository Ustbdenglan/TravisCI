package com.iflytek.aiui.uartkit.ctrdemo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/21/021.
 */

public class Aiui_Result {
    /**
     * content : {"arg1":0,"arg2":0,"eventType":1,
     * "info":{"data":[{"content":[{"cnt_id":"0","dte":"utf8","dtf":"json"}],
     * "params":{"lrst":false,"rstid":1,"sub":"nlp"}}]},
     * "result":{"intent":{"answer":{"text":"您想什么时候走呢？"},
     * "dialog_stat":"dataInvalid","rc":0,"save_history":true,
     * "semantic":[{"intent":"QUERY","slots":[{"name":"endLoccity","normValue":"上海市","value":"上海市"},
     * {"name":"endLoccityAddr","normValue":"上海","value":"上海"},
     * {"name":"endLoctype","normValue":"LOCBASIC","value":"LOCBASIC"},
     * {"name":"startLoccity","normValue":"北京市","value":"北京市"},
     * {"name":"startLoccityAddr","normValue":"北京","value":"北京"},
     * "name":"startLoctype","normValue":"LOCBASIC","value":"LOCBASIC"}]}],"service":"train","sid":"cid6f1832ed@ch00230dbf69c2010052","text":"北京去上海",
     * "used_state":{"state":"default","state_key":"fgefault"},
     * "uuid":"atn0086dd8b@ch67140dbf69c46f2001"},"sid":"cid6f1832ed@ch00230dbf69c2010052"}}
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
         * arg1 : 0
         * arg2 : 0
         * eventType : 1
         * info : {"data":[{"content":[{"cnt_id":"0","dte":"utf8","dtf":"json"}],"params":{"lrst":false,"rstid":1,"sub":"nlp"}}]}
         * result : {"intent":{"answer":{"text":"您想什么时候走呢？"},"dialog_stat":"dataInvalid","rc":0,"save_history":true,"semantic":[{"intent":"QUERY","slots":[{"name":"endLoccity","normValue":"上海市","value":"上海市"},{"name":"endLoccityAddr","normValue":"上海","value":"上海"},{"name":"endLoctype","normValue":"LOCBASIC","value":"LOCBASIC"},{"name":"startLoccity","normValue":"北京市","value":"北京市"},{"name":"startLoccityAddr","normValue":"北京","value":"北京"},{"name":"startLoctype","normValue":"LOCBASIC","value":"LOCBASIC"}]}],"service":"train","sid":"cid6f1832ed@ch00230dbf69c2010052","text":"北京去上海","used_state":{"state":"default","state_key":"fgefault"},"uuid":"atn0086dd8b@ch67140dbf69c46f2001"},"sid":"cid6f1832ed@ch00230dbf69c2010052"}
         */

        private int arg1;
        private int arg2;
        private int eventType;
        private InfoBean info;
        private ResultBean result;

        public int getArg1() {
            return arg1;
        }

        public void setArg1(int arg1) {
            this.arg1 = arg1;
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

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
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
                 * content : [{"cnt_id":"0","dte":"utf8","dtf":"json"}]
                 * params : {"lrst":false,"rstid":1,"sub":"nlp"}
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
                     * lrst : false
                     * rstid : 1
                     * sub : nlp
                     */

                    private boolean lrst;
                    private int rstid;
                    private String sub;
                    public boolean isLrst() {
                        return lrst;
                    }

                    public void setLrst(boolean lrst) {
                        this.lrst = lrst;
                    }

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
                }

                public static class ContentBean {
                    /**
                     * cnt_id : 0
                     * dte : utf8
                     * dtf : json
                     */

                    private String cnt_id;
                    private String dte;
                    private String dtf;

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

                    public String getDtf() {
                        return dtf;
                    }

                    public void setDtf(String dtf) {
                        this.dtf = dtf;
                    }
                }
            }
        }

        public static class ResultBean {
            /**
             * intent : {"answer":{"text":"您想什么时候走呢？"},"dialog_stat":"dataInvalid","rc":0,"save_history":true,"semantic":[{"intent":"QUERY","slots":[{"name":"endLoccity","normValue":"上海市","value":"上海市"},{"name":"endLoccityAddr","normValue":"上海","value":"上海"},{"name":"endLoctype","normValue":"LOCBASIC","value":"LOCBASIC"},{"name":"startLoccity","normValue":"北京市","value":"北京市"},{"name":"startLoccityAddr","normValue":"北京","value":"北京"},{"name":"startLoctype","normValue":"LOCBASIC","value":"LOCBASIC"}]}],"service":"train","sid":"cid6f1832ed@ch00230dbf69c2010052","text":"北京去上海","used_state":{"state":"default","state_key":"fgefault"},"uuid":"atn0086dd8b@ch67140dbf69c46f2001"}
             * sid : cid6f1832ed@ch00230dbf69c2010052
             */

            private IntentBean intent;
            private String sid;

            public IntentBean getIntent() {
                return intent;
            }

            public void setIntent(IntentBean intent) {
                this.intent = intent;
            }

            public String getSid() {
                return sid;
            }

            public void setSid(String sid) {
                this.sid = sid;
            }

            public static class IntentBean {
                /**
                 * answer : {"text":"您想什么时候走呢？"}
                 * dialog_stat : dataInvalid
                 * rc : 0
                 * save_history : true
                 * semantic : [{"intent":"QUERY","slots":[{"name":"endLoccity","normValue":"上海市","value":"上海市"},{"name":"endLoccityAddr","normValue":"上海","value":"上海"},{"name":"endLoctype","normValue":"LOCBASIC","value":"LOCBASIC"},{"name":"startLoccity","normValue":"北京市","value":"北京市"},{"name":"startLoccityAddr","normValue":"北京","value":"北京"},{"name":"startLoctype","normValue":"LOCBASIC","value":"LOCBASIC"}]}]
                 * service : train
                 * sid : cid6f1832ed@ch00230dbf69c2010052
                 * text : 北京去上海
                 * used_state : {"state":"default","state_key":"fgefault"}
                 * uuid : atn0086dd8b@ch67140dbf69c46f2001
                 */

                private AnswerBean answer;
                private String dialog_stat;
                private int rc;
                private boolean save_history;
                private String service;
                private String sid;
                private String text;
                private UsedStateBean used_state;
                private String uuid;
                private List<SemanticBean> semantic;

                public AnswerBean getAnswer() {
                    return answer;
                }

                public void setAnswer(AnswerBean answer) {
                    this.answer = answer;
                }

                public String getDialog_stat() {
                    return dialog_stat;
                }

                public void setDialog_stat(String dialog_stat) {
                    this.dialog_stat = dialog_stat;
                }

                public int getRc() {
                    return rc;
                }

                public void setRc(int rc) {
                    this.rc = rc;
                }

                public boolean isSave_history() {
                    return save_history;
                }

                public void setSave_history(boolean save_history) {
                    this.save_history = save_history;
                }

                public String getService() {
                    return service;
                }

                public void setService(String service) {
                    this.service = service;
                }

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

                public UsedStateBean getUsed_state() {
                    return used_state;
                }

                public void setUsed_state(UsedStateBean used_state) {
                    this.used_state = used_state;
                }

                public String getUuid() {
                    return uuid;
                }

                public void setUuid(String uuid) {
                    this.uuid = uuid;
                }

                public List<SemanticBean> getSemantic() {
                    return semantic;
                }

                public void setSemantic(List<SemanticBean> semantic) {
                    this.semantic = semantic;
                }

                public static class AnswerBean {
                    /**
                     * text : 您想什么时候走呢？
                     */

                    private String text;

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }
                }

                public static class UsedStateBean {
                    /**
                     * state : default
                     * state_key : fgefault
                     */

                    private String state;
                    private String state_key;

                    public String getState() {
                        return state;
                    }

                    public void setState(String state) {
                        this.state = state;
                    }

                    public String getState_key() {
                        return state_key;
                    }

                    public void setState_key(String state_key) {
                        this.state_key = state_key;
                    }
                }

                public static class SemanticBean {
                    /**
                     * intent : QUERY
                     * slots : [{"name":"endLoccity","normValue":"上海市","value":"上海市"},{"name":"endLoccityAddr","normValue":"上海","value":"上海"},{"name":"endLoctype","normValue":"LOCBASIC","value":"LOCBASIC"},{"name":"startLoccity","normValue":"北京市","value":"北京市"},{"name":"startLoccityAddr","normValue":"北京","value":"北京"},{"name":"startLoctype","normValue":"LOCBASIC","value":"LOCBASIC"}]
                     */

                    private String intent;
                    private List<SlotsBean> slots;

                    public String getIntent() {
                        return intent;
                    }

                    public void setIntent(String intent) {
                        this.intent = intent;
                    }

                    public List<SlotsBean> getSlots() {
                        return slots;
                    }

                    public void setSlots(List<SlotsBean> slots) {
                        this.slots = slots;
                    }

                    public static class SlotsBean {
                        /**
                         * name : endLoccity
                         * normValue : 上海市
                         * value : 上海市
                         */

                        private String name;
                        private String normValue;
                        private String value;

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public String getNormValue() {
                            return normValue;
                        }

                        public void setNormValue(String normValue) {
                            this.normValue = normValue;
                        }

                        public String getValue() {
                            return value;
                        }

                        public void setValue(String value) {
                            this.value = value;
                        }
                    }
                }
            }
        }
    }
}
